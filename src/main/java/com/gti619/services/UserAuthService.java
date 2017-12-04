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
    /**
     * Constructeur par copie d'attribut
     * @param userRepository la table des types d'utilisateur
     */
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    /**
     * Chercher l'utilisateur de la BD après être authentifié.
     * Remarque : c'est seulement utilisé dans la classe SecurityConfig.class pour vérifier les informations
     * d'authentification.
     * @param username le nom d'usager
     */
    // Cherche l'utilisateur de la BD quand authentifiee. Seulement utiliser dans la classe SecurityConfig.class pour verifier les infos d'authentification
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        // Vérifier que le compte n'est pas vérouillé.
        if(user.getUnlockDate()!=null) {
            if(user.getUnlockDate().after(new Date())) {
                user.setEnabled(false);
            }
        }

        return user;
    }
}