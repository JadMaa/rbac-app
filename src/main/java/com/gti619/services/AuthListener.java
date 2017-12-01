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
    public AuthListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // When
    @EventListener
    public void authenticationFailed( AuthenticationFailureBadCredentialsEvent event) throws Exception {

        String username = (String) event.getAuthentication().getPrincipal();
        User user= userRepository.findByUsername(username);
        user.setConsecutiveAttemptsFail(user.getConsecutiveAttemptsFail()+1);

        if(user.getConsecutiveAttemptsFail() > AuthParam.getMaxAttempt()){

            if(user.getUnlockDate()==null){
                Date lockUtil = new Date();
                // lock the user for some seconds. Ex : if the Auth.getTime is 30, the user is lock for 30 sec.
                lockUtil.setTime(lockUtil.getTime() + (1000 * AuthParam.getLockTime()));
                user.setUnlockDate(lockUtil);
                AuthLog.write("The user " +username+" have been lock for "+AuthParam.getLockTime()+" secondes.");
            } else {
                // if the user fail again after the lock, the account is lock and he need to contact the admin
                AuthLog.write("The user " +username+" have been lock, he need to contact the admin.");
                user.setAccountNonLocked(false);
            }

            user.setConsecutiveAttemptsFail(0);

        }

        this.userRepository.save(user);
    }

    // When the user successfull login , all the stats get initialise ( unlock to null and attempts fail to 0)
    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event) {
        User userEvent = (User) event.getAuthentication().getPrincipal();
        User user= userRepository.findByUsername(userEvent.getUsername());
        user.setConsecutiveAttemptsFail(0);
        user.setUnlockDate(null);
        this.userRepository.save(user);
    }

}
