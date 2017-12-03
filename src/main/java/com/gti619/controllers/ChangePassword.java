package com.gti619.controllers;

import com.gti619.services.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/changePassword")
public class ChangePassword {

    private PasswordManager passwordManager;

    @Autowired
    /**
     * Constructeur par copie d'attribut
     * @param passwordManager le nouveau gestionnaire de mot de passe
     */
    public ChangePassword(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @GetMapping
    /**
     * Retourne la page change-password.html
     * @return "change-password" la page change-password.html
     */
    public String renderPage() {
        return "change-password";
    }

    @PostMapping
    @ResponseBody
    /**
     * Changer le mot de passe de l'utilisateur en utilisant le service PasswordManager
     * @param newPassword le nouveau mot de passe
     * @param oldPassword l'ancien mot de passe
     * @param authentication les paramètres d'authentification
     */
    public String changPassword(@RequestParam String newPassword, @RequestParam String oldPassword,
                                Authentication authentication) {
        return this.passwordManager.change(authentication.getName(),newPassword,oldPassword);
    }
}