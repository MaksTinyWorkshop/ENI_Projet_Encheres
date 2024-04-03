package fr.eni.ecole.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


	
}
