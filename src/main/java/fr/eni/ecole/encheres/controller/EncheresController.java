package fr.eni.ecole.encheres.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import fr.eni.ecole.encheres.bll.ArticleService;
import fr.eni.ecole.encheres.bll.EnchereService;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exceptions.BusinessException;

@Controller
public class EncheresController {

	private final EnchereService enchereService;
	private final ArticleService articleService;


	public EncheresController(EnchereService enchereService, ArticleService articleService) {
		this.enchereService = enchereService;
		this.articleService = articleService;

	}
 
	
	@PostMapping("/articles/articleDetail/{id}")
	public String faireUneEnchere(@ModelAttribute("articleSelect") ArticleAVendre article,
			@RequestParam(name = "enchere", required = true) int montantEnchere, Principal ppal,
			Model model) {

		// Création de "coquille" pour transporter les infos
		Enchere enchere = new Enchere();
		Utilisateur user = new Utilisateur();
		article = articleService.consulterArticleById(article.getId());
		user.setPseudo(ppal.getName());
		enchere.setAcquereur(user);
		enchere.setArticleAVendre(article);
		enchere.setMontant(montantEnchere);
		enchere.setDate(LocalDate.now());

		try {
			enchereService.placerUneEnchere(enchere);

		} catch (BusinessException be) {
			List<String> messagesDErreur = be	.getClefsExternalisations()
												.stream()
												.collect(Collectors.toList());
			model.addAttribute("messageDErreur", messagesDErreur);

			// Re-préparation du modèle pour la vue en cas d'erreur
			model.addAttribute("articleSelect", articleService.consulterArticleById(article.getId()));
			return "view-article-detail";

		}
		return "redirect:/";
	}
}
