package fr.eni.ecole.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
			model.addAttribute("articlesList", articles);//intégration des articles au modèle pour thymleaf
			return "index";//retour à l'index
			
		}catch (BusinessException e){//ici récupération de la Business Exception chargée dans le service
			model.addAttribute("listArticleError", e.getClefsExternalisations());
			return "index";
		}
	}
	
	
	@GetMapping("/Creer-Article")
	public String creerArticle() {
		
		return "view-article-creation";
	}
	
	@PostMapping("/Creer-Article/creer")
	public String newArticle() {
		
		return "view-article-creation";
		
	}
	//TODO 1. ajouter un lien "Vendre un Objet" dans la navBar.
	//TODO 2. Ajouter accès roles dans le security controller.
	//TODO 3. créer un fragment de succès de création d'objet, avec une redirection vers l'accueil.
}
