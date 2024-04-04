package fr.eni.ecole.encheres.bll;

import java.security.Principal;
import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;


public interface ArticleService {
	List<ArticleAVendre> charger(Principal user);

	Adresse getAdress(String pseudo);

	ArticleAVendre consulterArticleById(Long articleId);

	void supprArticleById(Long id);

	void creerArticle(ArticleAVendre newArticle, boolean create);
	
	List<ArticleAVendre> chargerArticleFiltre(ArticleAVendre data);


}
