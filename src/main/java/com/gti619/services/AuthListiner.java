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
public class AuthListiner {

    private UserRepository userRepository;

    @Autowired
    public AuthListiner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void authenticationFailed( AuthenticationFailureBadCredentialsEvent event) throws Exception {

        String username = (String) event.getAuthentication().getPrincipal();
        User user= userRepository.findByUsername(username);
        user.setConsecutiveAttemptsFail(user.getConsecutiveAttemptsFail()+1);

        if(user.getConsecutiveAttemptsFail() > AuthParam.getMaxAttempt()){

            if(user.getUnlockDate()==null){
                Date lockUtil = new Date();
                lockUtil.setTime(lockUtil.getTime() + (1000 * AuthParam.getLockTime()));
                user.setUnlockDate(lockUtil);
            } else {
                user.setAccountNonLocked(false);
            }

            user.setConsecutiveAttemptsFail(0);

        }

        this.userRepository.save(user);
    }

    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event) {
        User userEvent = (User) event.getAuthentication().getPrincipal();
        User user= userRepository.findByUsername(userEvent.getUsername());
        user.setConsecutiveAttemptsFail(0);
        user.setUnlockDate(null);
        this.userRepository.save(user);
    }

}
