package fr.eni.ecole.encheres.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import fr.eni.ecole.encheres.bll.EnchereService;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.exceptions.BusinessException;

@Controller
public class EncheresController {


	private final EnchereService enchereService;
	private final MessageSource messageSource;


	public EncheresController(EnchereService enchereService, MessageSource messageSource) {
		this.enchereService = enchereService;
		this.messageSource = messageSource;
	}

	@PostMapping("/articles/articleDetail/{id}")
	public String faireUneEnchere(
			@ModelAttribute("articleSelect") ArticleAVendre article, 
			@RequestParam(name="enchere") int montantEnchere,
			Principal ppal,
			RedirectAttributes ra, Locale locale) {
		
		// Récup du pseudo user et idArticle
		String pseudoUser = ppal.getName();
		long idArticle = article.getId();
		
			try {
			enchereService.placerUneEnchere(pseudoUser, idArticle, montantEnchere);
			
			} catch (BusinessException be) {
				List<String> messagesDErreur = be.getClefsExternalisations()
						.stream()
						.map(key-> messageSource.getMessage(key, null, locale))
						.collect(Collectors.toList());
				ra.addFlashAttribute("messageDErreur", messagesDErreur);
				return "redirect:/articles/articleDetail/" + idArticle;
			}	
			return "redirect:/";


	}

}
