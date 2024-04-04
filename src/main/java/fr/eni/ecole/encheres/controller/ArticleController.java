package fr.eni.ecole.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticleService;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

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
	public String accueil(Model model, Principal user) {								//Gère l'affichage des articles en cours de vente sur l'accueil.
		try {
			List<ArticleAVendre> articles = articleService.charger(user);				//appel du service pour charger la liste
			model.addAttribute("articlesList", articles);
			return "index";																//retour à l'index
		}catch (BusinessException e){													//ici récupération de la BusinessException chargée dans le service
			model.addAttribute("listArticleError", e.getClefsExternalisations());		//transfer au model de la liste codes erreurs retournée
			return "index";
		}
	}
	
	
	@GetMapping("/Creer-Article")														//Prépare un nouvel article à remplir, avec l'adresse pré-Remplie.
	public String creerArticle(Model model, Principal user) {
		if (user != null) {																//vérifie le USer
			ArticleAVendre newArticle = new ArticleAVendre(); 
			Utilisateur vendeur  = new Utilisateur();
			Categorie categorie  = new Categorie();
			Adresse adresse = articleService.getAdress(user.getName());					//Ajout d'une instance adresse qui va récupérer en base l'adresse du Principal
			newArticle.setVendeur(vendeur);
			newArticle.getVendeur().setPseudo(user.getName());
			newArticle.setCategorie(categorie);
			newArticle.setRetrait(adresse);
			model.addAttribute("article", newArticle);
			System.out.println("la je crée un nouvel article !!!!!");
			System.out.println(newArticle.getId());
			model.addAttribute("titre", false);
			return "view-article-creation";
		}else {
			return "redirect:/";
		}
	}
	@GetMapping("/modifier-Article/{id}")												//Prépare un nouvel article à remplir, avec l'adresse pré-Remplie.
	public String modifierArticle(@PathVariable(name="id", required = false) Long id, Model model) {
		ArticleAVendre newArticle = articleService.consulterArticleById(id);
		model.addAttribute("article", newArticle);
		model.addAttribute("titre", true);
		return "view-article-creation";
	}
	
	@PostMapping("/Creer-Article")														// Permet d'enregistrer un nouvel article
	public String newArticle(
			@Valid @ModelAttribute("article") ArticleAVendre newArticle, BindingResult br) {
		boolean create = false;
		if (newArticle.getId() == null) {												// si l'ID est null, l'article est nouveau
			create = true;
		}
		if (!br.hasErrors()) {
			try {
				articleService.creerArticle(newArticle, create);
				return "redirect:/";
			} catch (BusinessException e) {
				e.getClefsExternalisations().forEach(key -> {
					ObjectError error = new ObjectError ("globalError", key);
					br.addError(error);
				});
				return "view-article-creation";
			}
		}
		return "view-article-creation";
	}
	

	/////// GET MAPPING DES FILTRES DU FOMRULAIRE HTML 
	@GetMapping("/filtrer-par-nom-article")
	public String filtrerParNomArticle(Model model, @RequestParam("nomArticle") String nomArticle) {
	    try {
	        List<ArticleAVendre> articlesFiltres = articleService.getArticlesByName(nomArticle);
	        model.addAttribute("articlesList", articlesFiltres);
	        return "index"; // Retourne la vue de la page d'accueil avec les articles filtrés par nom
	    } catch (BusinessException e) {
	        model.addAttribute("listArticleError", e.getClefsExternalisations());
	        return "index"; // Retourne la vue de la page d'accueil en cas d'erreur
	    }
	}




	
	@GetMapping("/articles/articleDetail/{id}")												//Permet d'atteindre la page d'affichage de détail de l'article sélectionné
	public String articleDetail(
			@PathVariable(name="id", required = false)Long articleId, Model model) {
		ArticleAVendre articleAVoir = articleService.consulterArticleById(articleId);		//création d'un coquille que la requête va compléter avec le paramètre passé
		model.addAttribute("articleSelect", articleAVoir);									// ajoute l'objet chargé pour l'exploitation avec thymleaf
		System.out.println(articleAVoir);
		return "view-article-detail";														// nous envoie sur la page de détail de l'article
	}
	
	
	@GetMapping("/articles/supprimer/{id}")
	public String supprimerArticle(@PathVariable(name="id", required = false)Long articleId) {
		articleService.supprArticleById(articleId);
		return "redirect:/";
	}
	
	//////// filtre par catégorie et mapping vers la vue :
	@GetMapping("/fragment-liste-articles-connecte")
    public String getAllCategories(Model model) {
        List<Categorie> categories = ArticleService.getAllCategories();
        model.addAttribute("categories", categories);
        return "fragment-liste-articles-connecte";
    }
}
