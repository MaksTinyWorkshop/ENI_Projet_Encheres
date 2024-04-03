package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;

public interface ArticleDAO {
	
	//1.les méthodes permettant de récupérer des listes les Articles
	List<ArticleAVendre> getUserAndActiveArticles(String pseudo);
	List<ArticleAVendre> getActiveArticles();//pour afficher tous les articles par date
	List<ArticleAVendre> getArticlesByName(String boutNom);
	List<ArticleAVendre> getArticlesByCategorie(Categorie categorie);
	
	//3. récupération d'un article complet
	ArticleAVendre getArticleById(Long articleId);
	
	//2. les méthodes permettant de créer ou modifier des articles
	void creerArticle(ArticleAVendre newArticle);
	void supprArticleById(Long articleId);
	void updatePrix(long idArticle, int montantEnchere);
<<<<<<< HEAD
	void modifierArticle(ArticleAVendre newArticle);

}
=======


	List<Categorie> getAllCategories();

	void modifierArticle(ArticleAVendre newArticle);

	

	


}
>>>>>>> bb468532d3785da2c0ff1fe751bb9bfb4396ad25
