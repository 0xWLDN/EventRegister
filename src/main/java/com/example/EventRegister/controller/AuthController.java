package com.example.EventRegister.controller;

import com.example.EventRegister.model.User;
import com.example.EventRegister.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UserRepository userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepo.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect(org.springframework.security.core.Authentication auth) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")))
            return "redirect:/admin/dashboard";
        else
            return "redirect:/participant/dashboard";
    }
}

