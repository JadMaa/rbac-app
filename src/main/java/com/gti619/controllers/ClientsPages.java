package com.gti619.controllers;

import com.gti619.domain.Client;
import com.gti619.repositories.ClientRepository;
import com.gti619.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    /**
     * Constructeur par copie d'attributs
     * @param clientRepository l'entrée du client dans la table
     * @param userRepository l'entrée du type d'utilisateur dans la table
     */
    public ClientsPages(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/residential")
    /**
     * Après vérification, autoriser l'utilisateur de type "préposé aux clients résidentiels" à consulter la page pour
     * un préposé aux clients résidentiels.
     * @param model model le modèle actuel
     * @param authentication les paramètres d'authentification
     */
    public String renderClientResidPage(Model model, Authentication authentication){
        // Donner acces seulement a un utilisateur avec role residential. Sinon le rediriger vers denied
        if(!authentication.getAuthorities().toString().contains("RESIDENTIAL")) {
            return "redirect:/denied";
        }
        model.addAttribute("type","RESIDENTIAL");
        //Render les clients residentiels seulement
        model.addAttribute("clients",
                StreamSupport.stream(this.clientRepository.findAll().spliterator(),false)
                    .filter( client -> client.getType().equalsIgnoreCase("RESIDENTIAL"))
                    .collect(Collectors.toList()));
        return "clients";
    }


    @GetMapping("/business")
    /**
     * Après vérification, autoriser l'utilisateur de type "préposé aux clients résidentiels" à consulter la page pour
     * un préposé aux clients résidentiels.
     * @param model model le modèle actuel
     * @param authentication les paramètres d'authentification
     */
    public String renderClientBusiPage(Model model, Authentication authentication){
        // Donner acces seulement a un utilisateur avec role business. Sinon le rediriger vers denied
        if(!authentication.getAuthorities().toString().contains("BUSINESS")) {
            return "redirect:/denied";
        }
        model.addAttribute("type","BUSINESS");
        //Render les clients business seulement
        model.addAttribute("clients",
                StreamSupport.stream(this.clientRepository.findAll().spliterator(),false)
                    .filter( client -> client.getType().equalsIgnoreCase("BUSINESS"))
                    .collect(Collectors.toList()));
        return "clients";
    }

    @PostMapping
    /**
     * Après la vérification du mot de passe, ajouter un nouveau client.
     * @param model le modèle actuel
     * @param authentication les paramètres d'authentification
     * @param client les paramètres du nouveau client
     * @param password le mot de passe spécifié par le nouveua client
     */
    // Ajouter un nouveau client
    public String renderClientBusiPage(Model model, Authentication authentication, @ModelAttribute Client client,
                                       @RequestParam String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(password,this.userRepository.findByUsername(authentication.getName()).
                getPassword())) {
            if(authentication.getAuthorities().toString().contains(client.getType().toUpperCase())) {
                this.clientRepository.save(client);
            }
        }
        return "redirect:/clients/"+client.getType().toLowerCase();
    }
}