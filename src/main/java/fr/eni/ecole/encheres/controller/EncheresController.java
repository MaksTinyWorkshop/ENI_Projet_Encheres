package fr.eni.ecole.encheres.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticleService;
import fr.eni.ecole.encheres.bll.EnchereService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class EncheresController {

	private final UtilisateurService utilisateurService;
	private final EnchereService enchereService;
	private final ArticleService articleService;

	public EncheresController(UtilisateurService utilisateurService, EnchereService enchereService,
			ArticleService articleService) {
		this.utilisateurService = utilisateurService;
		this.enchereService = enchereService;
		this.articleService = articleService;
	}

	@PostMapping("/articles/articleDetail/{id}")
	public String faireUneEnchere(
			@ModelAttribute("articleSelect") ArticleAVendre article, 
			@RequestParam(name="enchere") int montantEnchere,
			Principal ppal) {

		// Récup du pseudo user et idArticle
		String pseudoUser = ppal.getName();
		long idArticle = article.getId();
		
		// Récup des infos en base pour savoir si l'utilisateur a les moyens d'enchérir
		Utilisateur user = utilisateurService.consulterProfil(pseudoUser);
		ArticleAVendre articleAUpdate = articleService.consulterArticleById(idArticle);

		if (user.getCredit() > articleAUpdate.getPrixVente()) {
			enchereService.placerUneEnchere(pseudoUser, idArticle, montantEnchere);
			return "redirect:/";

		} else {
			return "redirect:/";
		}

	}

}
