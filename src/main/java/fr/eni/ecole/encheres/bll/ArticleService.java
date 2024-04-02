package fr.eni.ecole.encheres.bll;



import java.security.Principal;
import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;

public interface ArticleService {
	List<ArticleAVendre> charger(Principal user);

	Adresse getAdress(String pseudo);

	List<ArticleAVendre> getArticlesByName(String nomArticle);

	List<ArticleAVendre> getArticlesByCategorie(Categorie categorieObj);

	Categorie getCategorieByName(String categorie);
	ArticleAVendre consulterArticleById(Long articleId);
	void supprArticleById(Long id);

	void creerArticle(ArticleAVendre newArticle, boolean create);

	
}
