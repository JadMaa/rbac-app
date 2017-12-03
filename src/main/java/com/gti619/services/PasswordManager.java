package com.gti619.services;

import com.gti619.configurations.AuthParam;
import com.gti619.domain.User;
import com.gti619.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PasswordManager {

    private UserRepository userRepository;

    @Autowired
    /**
     * Constructeur par copie d'attribut
     * @param userRepository l'entrée du type d'utilisateur dans la table
     */
    public PasswordManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Changer le mot de passe du client
     * @param username le nom d'usager du client
     * @param newPassword le nouveau mot de passe
     * @return "The password is not complexe enough..." message informant que le mot de passe n'est pas assez complexe
     * @return "You can't use an old password." message informant de l'impossibilité d'utiliser un ancien mot de passe
     * @return "Password changed successfully." message informant que le mot de passe a été modifié avec succès
     */
    public String change(String username, String newPassword) {

        if(AuthParam.isComplexPassword()) {
            /**
             * Vérifier que le nouveau mot de passe est au moins de 8 caractàres, contient une minuscule, une majuscule,
             * un chiffre et un caractère spécial.
             */
            String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,50})";
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(newPassword);
                if(!matcher.matches()) {
                    return "The password is not complex enough. It needs at least one lowercase, one uppercase and one" +
                         "special characters and a number.";
                }
        }

        User user = this.userRepository.findByUsername(username);

        // Vérifier que le nouveau mot de passe n'est pas un ancien mot de passe.
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isNotOldPassword = user.getOldPasswords().stream()
                                        .filter( pass -> bCryptPasswordEncoder.matches(newPassword,pass))
                                        .collect(Collectors.toList())
                                        .isEmpty();
        if(bCryptPasswordEncoder.matches(newPassword,user.getPassword())) {
            isNotOldPassword = false;
        }
        if(!isNotOldPassword) {
            return "You can't use an old password.";
        }

        user.getOldPasswords().add(user.getPassword());
        user.setPassword(newPassword);

        try {
            AuthLog.write("The password of the user " + username + " has changed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.userRepository.save(user);

        return "Password changed successfully.";
    }

    /**
     * Changer le mot de passe du client
     * @param username le nom d'usager du client
     * @param newPassword le nouveau mot de passe
     * @param oldPassword l'ancien mot de passe
     * @return
     */
    public String change(String username, String newPassword, String oldPassword) {
         // Vérifier que le mot de passe actuel concorde avant de permettre le changement de mot de passe.
        User user = this.userRepository.findByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(oldPassword,user.getPassword())) {
            return "The old password entered doesn't match with the current one.";
        }

        return change(username,newPassword);
    }
}