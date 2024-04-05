package fr.eni.ecole.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.ecole.encheres.bll.contexte.ContexteService;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class LoginController {
	
///////////////////////////////////////////// Attributs
	
	private ContexteService service;
	
///////////////////////////////////////////// Constructeurs

	public LoginController(ContexteService service) {
		this.service = service;
	}
	
////////////////////////////////////////////Méthodes

	@GetMapping("/login")																	// permet d'atteindre la page de login
	public String login() {
		//retourne la vue de la page de connexion
		return "login";
	}
	
	@GetMapping("/login-success")
	public String login(Principal ppal,Model model, Utilisateur utilisateurEnSession) {		// permet de s'identifier
		String pseudoMembre = ppal.getName();
		Utilisateur user = service.charger(pseudoMembre);
		
		model.addAttribute("pseudo", user);
		
		return "redirect:/";
	}


	
}
