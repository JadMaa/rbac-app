package com.gti619.controllers;

import com.gti619.domain.User;
import com.gti619.repositories.UserRepository;
import com.gti619.services.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    public UsersManagement(UserRepository userRepository, PasswordManager passwordManager) {
        this.userRepository = userRepository;
        this.passwordManager = passwordManager;
    }

    @GetMapping
    public String renderPage(Model model, Authentication authentication){
        // Donner acces seulement a un utilisateur avec role admin. Sinon le rediriger vers denied
        if(!authentication.getAuthorities().toString().contains("ADMIN")){ return "redirect:/denied";}

        model.addAttribute("users", this.userRepository.findAll());
        return "/admin/users-management";
    }

    // L'admin peut changer le password d'un autre utilisateur. Password est changer avec le service passwordManager
    @PostMapping("/password")
    @ResponseBody
    public String changePassword(@RequestParam String username,@RequestParam String newPassword, Authentication authentication){
        // Donner acces seulement a un utilisateur avec role admin. Sinon le rediriger vers denied
        if(!authentication.getAuthorities().toString().contains("ADMIN")){ return "redirect:/denied";}

        return this.passwordManager.change(username,newPassword);
    }

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
        return "The account have been unlock.";
    }

}
