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
			List<ArticleAVendre> articles = articleService.charger();//appel du service pour charger la liste
			model.addAttribute("articles", articles);//intégration des articles au modèle pour thymleaf
			return "index";//retour à l'index
			
		}catch (BusinessException e){//ici récupération de la Business Exception chargée dans le service
			//model.addAttribute("empty", e);//TODO ne marche pas, voir plus tard...
			return "index";
		}
		//TODO gestion de l'exception en cas d'abscence d'enchère
	}
}
