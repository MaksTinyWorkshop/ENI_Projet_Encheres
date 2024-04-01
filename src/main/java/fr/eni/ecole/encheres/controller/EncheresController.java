package fr.eni.ecole.encheres.controller;


import java.security.Principal;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.ecole.encheres.bll.EnchereService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;


@Controller
@RequestMapping("/articles")
public class EncheresController {

	private final UtilisateurService utilisateurService;
	private final EnchereService enchereService;

	public EncheresController(UtilisateurService utilisateurService, EnchereService enchereService) {
		this.utilisateurService = utilisateurService;
		this.enchereService = enchereService;
	}

	@PostMapping("/articleDetail/{id}")
	public String faireUneEnchere(
			@ModelAttribute("articleSelect") ArticleAVendre article, 
			Utilisateur user,
			Principal ppal) {
		
		// Récup des infos du user
		String pseudo = ppal.getName();
		user = utilisateurService.consulterProfil(pseudo);
				
		if (user.getCredit() > article.getPrixVente()) {
			try {
				enchereService.placerUneEnchere(user, article);
				
			} catch (BusinessException be) {
				be.add(BusinessCode.UTILISATEUR_MONTANT_INSUFFISANT);
				throw be;
			}
		}
		
		return "redirect:/view-article-detail";
	}

	
}
