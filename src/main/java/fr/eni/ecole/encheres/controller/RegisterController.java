package fr.eni.ecole.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register-form")
    public String showRegisterForm() {
        return "register-form";
    }
    
}