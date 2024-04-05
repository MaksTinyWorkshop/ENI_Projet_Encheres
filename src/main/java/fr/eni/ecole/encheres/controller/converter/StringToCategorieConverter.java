package fr.eni.ecole.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.ecole.encheres.bll.ArticleService;
import fr.eni.ecole.encheres.bo.Categorie;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {

///////////////////////////////////////////// Attributs
	
	private ArticleService articleService;
	
///////////////////////////////////////////// Constructeurs
	
	public StringToCategorieConverter(ArticleService articleService) {
		this.articleService = articleService;
	}
	
////////////////////////////////////////////Méthodes

	@Override
	public Categorie convert(String idCategorie) {
		Long theId = Long.parseLong(idCategorie);
		
			return articleService.chargerCategorie(theId);
	}
}
