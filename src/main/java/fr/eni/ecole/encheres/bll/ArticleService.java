package fr.eni.ecole.encheres.bll;

import java.security.Principal;
import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;

public interface ArticleService {
	List<ArticleAVendre> charger();
	
	void creerArticle(ArticleAVendre newArticle);

	Adresse getAdress(String pseudo);
}
