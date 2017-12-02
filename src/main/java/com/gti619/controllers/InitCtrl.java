package com.gti619.controllers;

import com.gti619.domain.Client;
import com.gti619.domain.User;
import com.gti619.repositories.ClientRepository;
import com.gti619.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// Initialisation des utilisateurs et des clients (classe pour les dev)

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
        adminRoles.add("ADMIN");
        adminRoles.add("RESIDENTIAL");
        adminRoles.add("BUSINESS");
        this.userRepository.save(new User("admin","secret",adminRoles));

        List<String> residRoles = new ArrayList<>();
        residRoles.add("RESIDENTIAL");
        this.userRepository.save(new User("resid","secret", residRoles));

        List<String> busiRoles = new ArrayList<>();
        busiRoles.add("BUSINESS");
        this.userRepository.save(new User("busi","secret", busiRoles));

        return this.userRepository.findAll();
    }

    @GetMapping("/clients")
    public Iterable<Client> initClients(){

        this.clientRepository.deleteAll();

        this.clientRepository.save(new Client("Jeff Bezos","The CEO of Amazon","BUSINESS"));
        this.clientRepository.save(new Client("Jad Maarabouni","This is a poor client","BUSINESS"));
        this.clientRepository.save(new Client("Michel Sarkis","This is a rich client","BUSINESS"));
        this.clientRepository.save(new Client("Samnang Eang","This is a rich client","BUSINESS"));
        this.clientRepository.save(new Client("John Smith","This is a very rich client","RESIDENTIAL"));
        this.clientRepository.save(new Client("Tony Bobo","This is another client","RESIDENTIAL"));

        return this.clientRepository.findAll();
    }

}
