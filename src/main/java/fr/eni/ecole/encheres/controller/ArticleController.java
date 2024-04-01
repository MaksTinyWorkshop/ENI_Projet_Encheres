package fr.eni.ecole.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String accueil(Model model) {											//Gère l'affichage des articles en cours de vente sur l'accueil.
		try {
			List<ArticleAVendre> articles = articleService.charger();				//appel du service pour charger la liste
			model.addAttribute("articlesList", articles);							//intégration des articles au model
			return "index";															//retour à l'index
		}catch (BusinessException e){												//ici récupération de la BusinessException chargée dans le service
			model.addAttribute("listArticleError", e.getClefsExternalisations());	//transfer au model de la liste codes erreurs retournée
			return "index";
		}
	}
	
	
	@GetMapping("/Creer-Article")												//Prépare un nouvel article à remplir, avec l'adresse pré-Remplie.
	public String creerArticle(Model model, Principal user) {
		if (user != null) {														//vérifie le USer
			ArticleAVendre newArticle = new ArticleAVendre(); 
			Utilisateur vendeur  = new Utilisateur();
			Categorie categorie  = new Categorie();
			Adresse adresse = articleService.getAdress(user.getName());			//Ajout d'une instance adresse qui va récupérer en base l'adresse du Principal
			newArticle.setVendeur(vendeur);
			newArticle.getVendeur().setPseudo(user.getName());
			newArticle.setCategorie(categorie);
			newArticle.setRetrait(adresse);
			model.addAttribute("article", newArticle);
			return "view-article-creation";
		}else {
			return "redirect:/";
		}
	}
	
	@PostMapping("/Creer-Article")
	public String newArticle(@Valid @ModelAttribute("article") ArticleAVendre newArticle, BindingResult br) {
		if (!br.hasErrors()) {
			try {
				articleService.creerArticle(newArticle);
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
}
