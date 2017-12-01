package com.gti619.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/denied")
public class AccessDeniedCtrl {

    // Envoie la page denied.html
    @RequestMapping
    public String denied(){
        return "denied";
    }
}
