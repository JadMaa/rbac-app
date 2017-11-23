package com.rbacapp.rbacapp.configurations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class loginCtrl {

    @GetMapping
    public String login(){
        return "login";
    }
}
