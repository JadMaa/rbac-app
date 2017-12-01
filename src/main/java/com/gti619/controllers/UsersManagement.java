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
        //protect, only user with admin role can access. If someone else, he get redirect to the dinied page
        if(!authentication.getAuthorities().toString().contains("ADMIN")){ return "redirect:/denied";}

        model.addAttribute("users", this.userRepository.findAll());
        return "/admin/users-management";
    }

    // The admin can change the password of any user. When we got a post on this path, the service passwordManager change the password
    @PostMapping("/password")
    @ResponseBody
    public String changePassword(@RequestParam String username,@RequestParam String newPassword, Authentication authentication){
        //protect, only user with admin role can access. If someone else, he get redirect to the dinied page
        if(!authentication.getAuthorities().toString().contains("ADMIN")){ return "redirect:/denied";}

        return this.passwordManager.change(username,newPassword);
    }

    // when the admin click on unlock, the user get unlock
    @PostMapping("/unlock")
    @ResponseBody
    public String unlock(@RequestParam String username, Authentication authentication){
        //protect, only user with admin role can access. If someone else, he get redirect to the dinied page
        if(!authentication.getAuthorities().toString().contains("ADMIN")){ return "redirect:/denied";}

        User user = this.userRepository.findByUsername(username);
        user.setUnlockDate(null);
        user.setAccountNonLocked(true);
        this.userRepository.save(user);
        return "The account have been unlock.";
    }

}
