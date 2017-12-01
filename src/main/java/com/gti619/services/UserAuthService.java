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
    // Cherche l'utilisateur de la BD quand authentifiee. Seulement utiliser dans la classe SecurityConfig.class pour verifier les infos d'authentification
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        // Verifier que le compte n'est pas locked
        if(user.getUnlockDate()!=null){
            if(user.getUnlockDate().after(new Date())){
                user.setEnabled(false);
            }
        }

        return user;
    }

}
