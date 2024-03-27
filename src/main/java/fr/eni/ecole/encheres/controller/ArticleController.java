package fr.eni.ecole.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.ecole.encheres.bll.ArticleService;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.exceptions.BusinessException;

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
		try {
			//appel du service pour charger la liste
			List<ArticleAVendre> articles = articleService.charger();
			//intégration des articles au modèle pour thymleaf
			System.out.println("ma liste d'article n'est pas nulle, elle contient :");
			System.out.println(articles);
			model.addAttribute("articles", articles);
			//retour à l'index
			return "index";
		}catch (BusinessException e){//ici le catch de la Business Exception
			model.addAttribute("empty", e.getClefsExternalisations());
			return "index";
		}
		//TODO gestion de l'exception en cas d'abscence d'enchère
	}
}
