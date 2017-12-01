package com.gti619.services;

import com.gti619.domain.User;
import com.gti619.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserAuthService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    // This service fetch the user form the database when we auth. It is only used in the SecurityConfig.class for verify the auth info
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        // verify that the account is note still lock
        if(user.getUnlockDate()!=null){
            if(user.getUnlockDate().after(new Date())){
                user.setEnabled(false);
            }
        }

        return user;
    }

}
