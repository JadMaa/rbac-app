package com.gti619.controllers;

import com.gti619.services.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/changePassword")
public class ChangePassword {

    private PasswordManager passwordManager;

    @Autowired
    public ChangePassword(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @GetMapping
    public String renderPage(){
        return "change-password";
    }

    @PostMapping
    @ResponseBody
    public String changPassword(@RequestParam String newPassword, @RequestParam String oldPassword, Authentication authentication){
        return this.passwordManager.change(authentication.getName(),newPassword,oldPassword);
    }
}