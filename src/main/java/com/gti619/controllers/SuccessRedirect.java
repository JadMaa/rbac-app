package com.gti619.controllers;


import com.gti619.services.AuthLog;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SuccessRedirect {

    @GetMapping
    public String renderLogin(){
        return "login";
    }

    // Rediriger a une page successful dependamment du role (admin, residentiel ou business)
    @GetMapping("/success")
    public String redender(Authentication authentication) throws Exception {

        AuthLog.write(authentication.getName()+" connected to the system.");

        String roles = authentication.getAuthorities().toString();
        if(roles.contains("ADMIN")){
            return "redirect:/admin/param";
        } else if(roles.contains("RESIDENTIAL")){
            return "redirect:/clients/residential";
        } else {
            return "redirect:/clients/business";
        }
    }

}
