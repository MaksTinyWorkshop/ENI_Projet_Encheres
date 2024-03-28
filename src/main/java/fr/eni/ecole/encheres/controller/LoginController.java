package fr.eni.ecole.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.ecole.encheres.bll.contexte.ContexteService;
import fr.eni.ecole.encheres.bo.Utilisateur;



@Controller
public class LoginController {
	
	//Attribut
	private ContexteService service;
	
	// Constructeur
	public LoginController(ContexteService service) {
		this.service = service;
	}
	
	
	///////////// METHODE DE LOGIN
	@GetMapping("/login")
	public String login() {
		//retourne la vue de la page de connexion
		return "login";
	}
	
	@GetMapping("/login-success")
	public String login(Principal ppal,Model model, Utilisateur utilisateurEnSession) {
		String pseudoMembre = ppal.getName();
		Utilisateur user = service.charger(pseudoMembre);
		
		model.addAttribute("pseudo", user);
		
		return "redirect:/";
	}

	///////////// METHODE D'AFFICHAGE ET UPDATE DU PROFIL
	@GetMapping("/profil")
	public String afficherMonProfil(Model model, Principal ppal) {
		String pseudo = ppal.getName();
		Utilisateur user = service.charger(pseudo);
		if(user != null) {
			model.addAttribute("user" ,user);
			return "view-mon-profil";
		} else {
			System.out.println("User inconnu");
		}
		return "redirect:/profil";
	}
	
	
	//TODO faire cette méthode pour mettre à jour le profil du user connecté
	@PostMapping("/profil")
	public String mettreAJourMonProfil(
			@ModelAttribute("user") Utilisateur u) {
		
		return "view-mon-profil";
	}
	
}
