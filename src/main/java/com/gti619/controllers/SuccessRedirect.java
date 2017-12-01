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

    // This redirect to the good page, if you are admin, you go to admins page, if you are a residential , you go to residential
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
