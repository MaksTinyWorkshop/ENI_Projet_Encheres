package fr.eni.ecole.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.ecole.encheres.bll.ArticleService;
import fr.eni.ecole.encheres.bo.ArticleAVendre;

@Controller
public class ArticleController {

///////////////////////////////////////////// Attributs
	
	private ArticleService articleService;//dépendance
	
///////////////////////////////////////////// Constructeurs
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
////////////////////////////////////////////Méthodes

	@GetMapping("/")
	public String accueil(Model model) {
		//appel du service pour charger la liste
		List<ArticleAVendre> articles = articleService.charger();
		//intégration des articles au modèle pour thymleaf
		model.addAttribute("articles", articles);
		//retour à l'index
		return "index";
	}
}
