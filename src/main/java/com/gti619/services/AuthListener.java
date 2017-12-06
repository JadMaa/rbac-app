package com.gti619.services;

import com.gti619.configurations.AuthParam;
import com.gti619.domain.User;
import com.gti619.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthListener {

    private UserRepository userRepository;

    @Autowired
    /**
     * Contructeur par copie d'attribut
     * @param userRepository la table des utilisateurs
     */
    public AuthListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    /**
     * L'écouteur pour une authentication échouée.
     * @param event l'événement d'authentification
     * @throws Exception
     */
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) throws Exception {

        String username = (String) event.getAuthentication().getPrincipal();
        User user= userRepository.findByUsername(username);
        user.setConsecutiveAttemptsFail(user.getConsecutiveAttemptsFail()+1);

        if(user.getConsecutiveAttemptsFail() > AuthParam.getMaxAttempt()){

            if(user.getUnlockDate() == null){
                Date lockUtil = new Date();
                // Lock l'utilisateur pour x secondes . Ex : si Auth.getTime est = 30, l'utilisateur est locked pour 30 sec
                lockUtil.setTime(lockUtil.getTime() + (1000 * AuthParam.getLockTime()));
                user.setUnlockDate(lockUtil);
                AuthLog.write("The user " +username+" has been locked for "+AuthParam.getLockTime()+" seconds.");
            } else {
                // Si l'utilisateur est encore locked, il doit contacter l'admin
                AuthLog.write("The user " +username+" has been locked, contact the admin please.");
                user.setAccountNonLocked(false);
            }
            user.setConsecutiveAttemptsFail(0);
        }
        this.userRepository.save(user);
    }

    @EventListener
    /**
     * L'écouteur pour une authentification réussie.
     * @param event l'événement d'authentification
     */
    public void authenticationSuccess(AuthenticationSuccessEvent event) {
        User userEvent = (User) event.getAuthentication().getPrincipal();
        // Quand l'utilisateur se connecte sans problèmes, remettre tout à 0 (tentatives, etc.).
        User user= userRepository.findByUsername(userEvent.getUsername());
        user.setConsecutiveAttemptsFail(0);
        user.setUnlockDate(null);
        this.userRepository.save(user);
    }
}