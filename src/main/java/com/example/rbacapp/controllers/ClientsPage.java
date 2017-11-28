package com.example.rbacapp.controllers;

import com.example.rbacapp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/clients")
public class ClientsPage {

    private ClientRepository clientRepository;

    @Autowired
    public ClientsPage(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/residential")
    public String renderClientResidPage(Model model){

        model.addAttribute("type","Residential");
        model.addAttribute("clients",
                StreamSupport.stream(this.clientRepository.findAll().spliterator(),false)
                    .filter( client -> client.getType().equals("RESIDENTIAL"))
                    .collect(Collectors.toList())
        );

        return "clients";
    }

    @GetMapping("/business")
//    @PreAuthorize("hasAnyRole('ROLE_BUSINESS')")
    public String renderClientBusiPage(Model model){

        model.addAttribute("type","Business");
        model.addAttribute("clients",
                StreamSupport.stream(this.clientRepository.findAll().spliterator(),false)
                    .filter( client -> client.getType().equals("BUSINESS"))
                    .collect(Collectors.toList())
        );

        return "clients";
    }

}
