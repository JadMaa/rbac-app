package com.example.rbacapp.controllers;


import com.example.rbacapp.commons.AuthLog;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginPage {

    @GetMapping
    public String renderLogin(){
        return "login";
    }

    @GetMapping("/success")
    public String redender(Authentication authentication) throws Exception {

        AuthLog.write(authentication.getName()+" connected to the system.");

        String roles = authentication.getAuthorities().toString();
        if(roles.contains("ROLE_ADMIN")){
            return "redirect:/clients/residential";
        } else if(roles.contains("ROLE_RESIDENTIAL")){
            return "redirect:/clients/residential";
        } else {
            return "redirect:/clients/business";
        }
    }

}
