package com.gti619.controllers;

import com.gti619.domain.Client;
import com.gti619.repositories.ClientRepository;
import com.gti619.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/clients")
public class ClientsPages {

    private ClientRepository clientRepository;
    private UserRepository userRepository;

    @Autowired
    public ClientsPages(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/residential")
    public String renderClientResidPage(Model model, Authentication authentication){

        if(!authentication.getAuthorities().toString().contains("RESIDENTIAL")){ return "redirect:/denied";}

        model.addAttribute("type","RESIDENTIAL");
        //Render Only residential Clients
        model.addAttribute("clients",
                StreamSupport.stream(this.clientRepository.findAll().spliterator(),false)
                    .filter( client -> client.getType().equalsIgnoreCase("RESIDENTIAL"))
                    .collect(Collectors.toList())
        );

        return "clients";
    }


    @GetMapping("/business")
    public String renderClientBusiPage(Model model, Authentication authentication){

        if(!authentication.getAuthorities().toString().contains("BUSINESS")){ return "redirect:/denied";}

        model.addAttribute("type","BUSINESS");
        //Render Only business Clients
        model.addAttribute("clients",
                StreamSupport.stream(this.clientRepository.findAll().spliterator(),false)
                    .filter( client -> client.getType().equalsIgnoreCase("BUSINESS"))
                    .collect(Collectors.toList())
        );

        return "clients";
    }

    @PostMapping
    public String renderClientBusiPage(Model model, Authentication authentication, @ModelAttribute Client client, @RequestParam String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(password,this.userRepository.findByUsername(authentication.getName()).getPassword())){
            if( authentication.getAuthorities().toString().contains(client.getType().toUpperCase())){
                this.clientRepository.save(client);
            }
        }
        return "redirect:/clients/"+client.getType().toLowerCase();
    }

}
