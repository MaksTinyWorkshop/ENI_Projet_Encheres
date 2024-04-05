package fr.eni.ecole.encheres.bll;

import java.security.Principal;
import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;


public interface ArticleService {
	List<ArticleAVendre> charger(Principal user);

	Adresse getAdress(String pseudo);

	ArticleAVendre consulterArticleById(Long articleId);

	void supprArticleById(Long id);

<<<<<<< HEAD
	List<Categorie> getAllCategories();
	
	void creerArticle(ArticleAVendre newArticle, boolean create);

=======
	void creerArticle(ArticleAVendre newArticle, boolean create);
	
	List<ArticleAVendre> chargerArticleFiltre(ArticleAVendre data);
	
	List<Categorie> chargerCategories();

	Categorie chargerCategorie(long idCategorie);
	
	List<ArticleAVendre> chargerArticlesParCategorie(long idCategorie);
>>>>>>> cb01855c81f91396a548fe1db9f437f12863c42a
}
