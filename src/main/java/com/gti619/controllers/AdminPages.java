package com.gti619.controllers;

import com.gti619.configurations.AuthParam;
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
    /**
     * Après vérification, autoriser l'accès à la configuration des paramètres de sécurité d'un compte utilisateur.
     * @param model le modèle actuel
     * @param authentication les paramètres d'authentification
     */
    public String getParam(Model model, Authentication authentication) {
        /**
         * Laisser seulement un utilisateur de type administrateur avoir accès aux fonctions de la page
         * d'administration. Un autre type d'utilisateur sera redirigé vers la page denied.html.
         */
        if(!authentication.getAuthorities().toString().contains("ADMIN")) {
            return "redirect:/denied";
        }
        model.addAttribute("lockTime", AuthParam.getLockTime());
        model.addAttribute("maxAttempt", AuthParam.getMaxAttempt());
        model.addAttribute("complexPassword", AuthParam.isComplexPassword());
        return "admin/param";
    }

    @PostMapping("/param")
    /**
     * Après vérification, autoriser l'administrateur à modifier les paramètres de sécurité d'un compte utilisateur.
     * @param model le modèle actuel
     * @param authentication les paramètres d'authentification
     */
    public String getParam(Model model, Authentication authentication,
                           @RequestParam long lockTime,
                           @RequestParam long maxAttempt,
                           @RequestParam(value = "complexPassword", required = false) String complexPassword) {
        /**
         * Laisser seulement un utilisateur de type administrateur avoir accès aux fonctions de la page
         * d'administration. Un autre type d'utilisateur sera redirigé vers la page denied.html.
         */
        if(!authentication.getAuthorities().toString().contains("ADMIN")) {
            return "redirect:/denied";
        }
        AuthParam.setLockTime(lockTime);
        if ( complexPassword == null) {
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