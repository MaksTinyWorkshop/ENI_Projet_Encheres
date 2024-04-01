package fr.eni.ecole.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.eni.ecole.encheres.bll.ArticleService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleAVendre;

@Controller
public class EncheresController {

	private final UtilisateurService utilisateurService;
	private final ArticleService articleService;
	
	
	public EncheresController(UtilisateurService utilisateurService, ArticleService articleService) {
		this.utilisateurService = utilisateurService;
		this.articleService = articleService;
	}
	
	@GetMapping("/article/{id}")
	public String detailsArticle(
			@PathVariable(name="id", required = false)int idArticle) {
		ArticleAVendre articleEnBase = articleService.
		
		return "";
	}
	
	
}
