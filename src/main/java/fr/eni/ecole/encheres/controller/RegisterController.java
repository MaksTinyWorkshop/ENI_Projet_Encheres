package fr.eni.ecole.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

    private final UtilisateurService utilisateurService;

    public RegisterController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerFormObject", new Utilisateur());
        return "view-register-form";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid Utilisateur formObject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "view-register-form";
        }
        // Enregistrer l'utilisateur
        utilisateurService.enregistrerUtilisateur(formObject.getPseudo(), formObject.getNom(), formObject.getPrenom(), formObject.getTelephone(), formObject.getEmail(), formObject.getRue(), formObject.getCodePostal(), formObject.getVille(), formObject.getMotDePasse());
       
        return "redirect:/"; // Redirigez l'utilisateur vers la page d'accueil
    }
}


