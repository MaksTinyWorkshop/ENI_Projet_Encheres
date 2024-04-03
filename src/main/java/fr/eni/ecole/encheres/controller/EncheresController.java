package fr.eni.ecole.encheres.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	private final MessageSource messageSource;

	public EncheresController(EnchereService enchereService, ArticleService articleService, MessageSource messageSource) {
		this.enchereService = enchereService;
		this.articleService = articleService;
		this.messageSource = messageSource;
	}

	@PostMapping("/articles/articleDetail/{id}")
	public String faireUneEnchere(@ModelAttribute("articleSelect") ArticleAVendre article,
			@RequestParam(name = "enchere") int montantEnchere, Principal ppal, RedirectAttributes ra, Locale locale) {
		// Création  de "coquille" pour transporter les infos"
		Enchere enchere = new Enchere();
		Utilisateur user = new Utilisateur();
		article = articleService.consulterArticleById(article.getId());
		user.setPseudo(ppal.getName());
		enchere.setAcquereur(user);
		enchere.setArticleAVendre(article);
		enchere.setMontant(montantEnchere);
		enchere.setDate(LocalDate.now());
		System.out.println(article);
		
		try {
			enchereService.placerUneEnchere(enchere);

		} catch (BusinessException be) {
			List<String> messagesDErreur = be	.getClefsExternalisations()
												.stream()
												.map(key -> messageSource.getMessage(key, null, locale))
												.collect(Collectors.toList());
			ra.addFlashAttribute("messageDErreur", messagesDErreur);
			return "redirect:/articles/articleDetail/" + article.getId();
		}
		return "redirect:/";
	}
}
