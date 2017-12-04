package com.gti619.controllers;

import com.gti619.domain.User;
import com.gti619.repositories.UserRepository;
import com.gti619.services.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class UsersManagement {

    private UserRepository userRepository;
    private PasswordManager passwordManager;

    @Autowired
    /**
     * Constructeur par copie d'attributs
     * @param userRepository la table des types d'utilisateur
     * @param passwordManager le gestionnaire de mot de passe
     */
    public UsersManagement(UserRepository userRepository, PasswordManager passwordManager) {
        this.userRepository = userRepository;
        this.passwordManager = passwordManager;
    }

    @GetMapping
    /**
     * Après vérification, donner accès à un utilisateur avec le rôle d'administrateur. Sinon, le rediriger vers la page
     * denied.html.
     * @param model le modèle actuel
     * @param authentication les paramètres d'authentification
     * @return "/admin/users-management" l'accès au service de gestion des mots de passe
     */
    public String renderPage(Model model, Authentication authentication){
        if(!authentication.getAuthorities().toString().contains("ADMIN")) {
            return "redirect:/denied";
        }
        model.addAttribute("users", this.userRepository.findAll());
        return "/admin/users-management";
    }

    /**
     * Après vérification, l'administrateur peut changer le mot de passe d'un client avec le service de
     * gestion des mots de passe
     * @param username le nom du client
     * @param newPassword le nouveau mot de passe
     * @param authentication les paramètres d'authentification
     * @return
     */
    @PostMapping("/password")
    @ResponseBody
    public String changePassword(@RequestParam String username,@RequestParam String newPassword,
                                 Authentication authentication){
        if(!authentication.getAuthorities().toString().contains("ADMIN")) {
            return "redirect:/denied";
        }
        return this.passwordManager.change(username,newPassword);
    }

    /**
     * Après vérification, l'administrateur peut dévérouiller un compte de client.
     * @param username le nom du client
     * @param authentication les paramètres d'authentification
     * @return "The account have been unlock." le message de dévérouillage du compte client
     */
    // Admin peut unlock des utilisateurs bloques
    @PostMapping("/unlock")
    @ResponseBody
    public String unlock(@RequestParam String username, Authentication authentication){
        // Donner acces seulement a un utilisateur avec role admin. Sinon le rediriger vers denied
        if(!authentication.getAuthorities().toString().contains("ADMIN")){ return "redirect:/denied";}

        User user = this.userRepository.findByUsername(username);
        user.setUnlockDate(null);
        user.setAccountNonLocked(true);
        this.userRepository.save(user);
        return "The account has been unlocked.";
    }
}