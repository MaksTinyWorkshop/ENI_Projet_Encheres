package fr.eni.ecole.encheres.controller;

import org.springframework.stereotype.Controller;

import fr.eni.ecole.encheres.bll.ArticleService;
import fr.eni.ecole.encheres.bll.UtilisateurService;

@Controller
public class EncheresController {

	private final UtilisateurService utilisateurService;
	private final ArticleService articleService;
	
	
	public EncheresController(UtilisateurService utilisateurService, ArticleService articleService) {
		this.utilisateurService = utilisateurService;
		this.articleService = articleService;
	}
	
}
