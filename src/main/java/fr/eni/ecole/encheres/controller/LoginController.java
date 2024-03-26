package fr.eni.ecole.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.ecole.encheres.bll.contexte.ContexteService;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes({ "utilisateurEnSession"})
public class LoginController {
	
	private ContexteService service;

	public LoginController(ContexteService service) {
		this.service = service;
	}
	
	@GetMapping("/login")
	public String login() {
		//retourne la vue de la page de connexion
		return "login";
	}
	
	@GetMapping("/login-success")
	public String login(Principal ppal, HttpSession session, Utilisateur utilisateurEnSession) {
		String pseudoMembre = ppal.getName();
		Utilisateur aCharger = service.charger(pseudoMembre);
		
		session.setAttribute("utilisateurEnSession", aCharger);
		
		return "redirect:/";
	}

	
	
}
