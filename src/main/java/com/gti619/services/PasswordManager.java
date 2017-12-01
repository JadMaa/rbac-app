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
    public PasswordManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String change(String username, String newPassword) {

        if(AuthParam.isComplexPassword()){
            // Verifie que le nouveau mdp est au moins 8 caracteres, contient une minuscule, une majuscule, un chiffre et un caractere special
            String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,50})";
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(newPassword);
                if(!matcher.matches()){
                 return "The password is not complex enough. It needs at least one lowercase, one uppercase and one special characters and a number.";
                }
        }

        User user = this.userRepository.findByUsername(username);

        // Verifie que le nouveau mdp n'est pas un ancien mdp
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isNotOldPassword = user.getOldPasswords().stream()
                                        .filter( pass -> bCryptPasswordEncoder.matches(newPassword,pass))
                                        .collect(Collectors.toList())
                                        .isEmpty();
        if(bCryptPasswordEncoder.matches(newPassword,user.getPassword())){
            isNotOldPassword = false;
        }

        if(!isNotOldPassword){
            return "You can't use an old password.";
        }

        user.getOldPasswords().add(user.getPassword());
        user.setPassword(newPassword);

        try {
            AuthLog.write("The password of the user "+ username+" has changed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.userRepository.save(user);

        return "Password changed successfully.";
    }

    public String change(String username, String newPassword, String oldPassword) {
         // Verifie que le mdp actuel match avant le changement de mdp
        User user = this.userRepository.findByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(oldPassword,user.getPassword())){
            return "The password entered doesn't match with the current one.";
        }

        return change(username,newPassword);
    }



}
