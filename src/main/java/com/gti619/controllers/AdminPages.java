package com.gti619.controllers;

import com.gti619.configurations.AuthParam;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(path = "/admin")
public class AdminPages {

    @GetMapping("/param")
    public String getParam(Model model, Authentication authentication){
        //protect, only user with admin role can access. If someone else, he get redirect to the dinied page
        if(!authentication.getAuthorities().toString().contains("ADMIN")){ return "redirect:/denied";}

        model.addAttribute("lockTime", AuthParam.getLockTime());
        model.addAttribute("maxAttempt", AuthParam.getMaxAttempt());
        model.addAttribute("complexPassword", AuthParam.isComplexPassword());
        return "admin/param";
    }

    @PostMapping("/param")
    //When changed the static class AuthParam is affected form the form values
    public String getParam(Model model,Authentication authentication,
                           @RequestParam long lockTime,
                           @RequestParam long maxAttempt,
                           @RequestParam( value = "complexPassword", required = false) String complexPassword){
        //protect, only user with admin role can access. If someone else, he get redirect to the dinied page
        if(!authentication.getAuthorities().toString().contains("ADMIN")){ return "redirect:/denied";}

        AuthParam.setLockTime(lockTime);
        if ( complexPassword == null){
            AuthParam.setComplexPassword(false);
        } else {
            AuthParam.setComplexPassword(true);
        }
        AuthParam.setMaxAttempt(maxAttempt);
        model.addAttribute("lockTime", AuthParam.getLockTime());
        model.addAttribute("maxAttempt", AuthParam.getMaxAttempt());
        model.addAttribute("complexPassword", AuthParam.isComplexPassword());
        return "admin/param";
    }
}
