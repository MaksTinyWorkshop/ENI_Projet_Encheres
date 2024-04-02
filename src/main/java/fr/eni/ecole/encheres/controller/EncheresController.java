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
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;

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
		
		// Récup des infos en base pour savoir si l'utilisateur a les moyens d'enchérir et s'il n'est pas le vendeur
		Utilisateur user = utilisateurService.consulterProfil(pseudoUser);
		ArticleAVendre articleEnBase = articleService.consulterArticleById(idArticle);
		String pseudoVendeur = articleEnBase.getVendeur().getPseudo();

		if (user.getCredit() > montantEnchere && !pseudoUser.equals(pseudoVendeur)) {
			try {
			enchereService.placerUneEnchere(pseudoUser, idArticle, montantEnchere);
			return "redirect:/";
			} catch (BusinessException be) {
				be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_ERREUR);
				throw be;
			}	
		} else {
			BusinessException be = new BusinessException();
			be.add(BusinessCode.BLL_UTILISATEUR_PLACEMENT_ENCHERE_ERREUR);
			throw be;
		}

	}

}
