package fr.eni.ecole.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bll.contexte.ContexteService;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UtilisateurService utilisateurService;


	public UserController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("registerFormObject", new Utilisateur());
		return "view-register-form";
	}

	@PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerFormObject") Utilisateur formObject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "view-register-form";
        }
        
        // Transmettre l'objet utilisateur à la couche de service pour enregistrement
        utilisateurService.enregistrerUtilisateur(formObject);
        
        return "redirect:/"; // Redirigez l'utilisateur vers la page d'accueil
    }

	///////// METHODE D'AFFICHAGE ET UPDATE DU PROFIL
	@GetMapping("/profil")
	public String afficherMonProfil(Model model, Principal ppal) {
		String pseudo = ppal.getName();
		Utilisateur user = utilisateurService.consulterProfil(pseudo);
		if (user != null) {
			model.addAttribute("formObject", user);
			return "view-mon-profil";
		} else {
			System.out.println("User inconnu");
		}
		return "redirect:/profil";
	}

	//TODO faire cette méthode pour mettre à jour le profil du user connecté
	@PostMapping("/profil")
	public String mettreAJourMonProfil(@ModelAttribute("user") Utilisateur u) {

		return "view-mon-profil";
	}
}
