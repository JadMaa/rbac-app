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

    public String change(String username, String newPassword){

        if(AuthParam.isComplexPassword()){
            String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,50})";
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(newPassword);
                if(!matcher.matches()){
                 return "The password is not enought complex.";
                }
        }

        User user = this.userRepository.findByUsername(username);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isNotOldPassword = user.getOldPasswords().stream()
                                        .filter( pass -> bCryptPasswordEncoder.matches(newPassword,pass))
                                        .collect(Collectors.toList())
                                        .isEmpty();
        if(bCryptPasswordEncoder.matches(newPassword,user.getPassword())){
            isNotOldPassword = false;
        }

        if(!isNotOldPassword){
            return "You can't use old password.";
        }

        user.getOldPasswords().add(user.getPassword());
        user.setPassword(newPassword);
        this.userRepository.save(user);

        return "Password changed.";
    }

    public String change(String username, String newPassword, String oldPassword){

        User user = this.userRepository.findByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(oldPassword,user.getPassword())){
            return "The password entered dosn't match with the current one.";
        }

        return change(username,newPassword);
    }



}
