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

	ArticleAVendre consulterArticleById(Long articleId);
	void supprArticleById(Long id);

	static List<Categorie> getAllCategories() {
		return null;
	}
	void creerArticle(ArticleAVendre newArticle, boolean create);

	Object findArticlesByCategorie(String categorie);

	Object findAllArticles();

	
}
