package com.example.rbacapp.controllers;

import com.example.rbacapp.domain.Client;
import com.example.rbacapp.domain.User;
import com.example.rbacapp.repositories.ClientRepository;
import com.example.rbacapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/init")
public class InitCtrl {

    private UserRepository userRepository;
    private ClientRepository clientRepository;

    @Autowired
    public InitCtrl(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/users")
    public Iterable<User> initDB(){

        this.userRepository.deleteAll();

        List<String> adminRoles = new ArrayList<>();
        adminRoles.add("ROLE_ADMIN");
        adminRoles.add("ROLE_RESIDENTIAL");
        adminRoles.add("ROLE_BUSINESS");
        this.userRepository.save(new User("admin","secret",adminRoles));

        List<String> residRoles = new ArrayList<>();
        residRoles.add("ROLE_RESIDENTIAL");
        this.userRepository.save(new User("resid","secret", residRoles));

        List<String> busiRoles = new ArrayList<>();
        busiRoles.add("ROLE_BUSINESS");
        this.userRepository.save(new User("busi","secret", busiRoles));

        return this.userRepository.findAll();
    }

    @GetMapping("/clients")
    public Iterable<Client> initClients(){

        this.clientRepository.deleteAll();

        this.clientRepository.save(new Client("Jeff Bezos","This is a rich client","BUSINESS"));
        this.clientRepository.save(new Client("Jad Maa","This is a poor client","BUSINESS"));
        this.clientRepository.save(new Client("Marco Polo","This is a rich client","BUSINESS"));
        this.clientRepository.save(new Client("Francis Coco","This is a rich client","BUSINESS"));
        this.clientRepository.save(new Client("Ben Coribou","This is a rich client","RESIDENTIAL"));
        this.clientRepository.save(new Client("Salim Bobo","This is a rich client","RESIDENTIAL"));

        return this.clientRepository.findAll();
    }

}
