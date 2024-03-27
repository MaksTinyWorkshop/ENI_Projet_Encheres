package fr.eni.ecole.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @GetMapping("/register-form")
    public String showRegisterForm() {
        return "register-form";
    }
    
    @PostMapping("/register-form")
    public String registerUser(
    		@RequestParam("pseudo") String pseudo,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("telephone") String telephone,
            @RequestParam("email") String email,
            @RequestParam("rue") String rue,
            @RequestParam("codePostal") String codePostal,
            @RequestParam("ville") String ville,
            @RequestParam("motDePasse") String motDePasse) {  

			if (pseudo.isEmpty() || nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() ||
                    rue.isEmpty() || codePostal.isEmpty() || email.isEmpty() || ville.isEmpty() || motDePasse.isEmpty()) {
                // Prévient en cas de champ vide
                return "redirect:/register-form?error=emptyFields";
            }
            
            return "redirect:/index";
        
    }
}
  
    


