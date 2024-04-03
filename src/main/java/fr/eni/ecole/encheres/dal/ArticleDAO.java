package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Enchere;

public interface ArticleDAO {
	
	//1.les méthodes permettant de récupérer des listes les Articles
	List<ArticleAVendre> getUserAndActiveArticles(String pseudo);
	List<ArticleAVendre> getActiveArticles();//pour afficher tous les articles par date
	List<ArticleAVendre> getArticlesByName(String boutNom);
	List<ArticleAVendre> getArticlesByCategorie(Categorie categorie);
	
	//2. récupération d'un article complet
	ArticleAVendre getArticleById(Long articleId);
	
	//3. les méthodes permettant de créer ou modifier des articles
	void creerArticle(ArticleAVendre newArticle);
	void supprArticleById(Long articleId);
<<<<<<< HEAD
=======
	
>>>>>>> 37761271372efd572c7dd50561ff664092ddf9f5
	void updatePrix(Enchere enchere);
	void modifierArticle(ArticleAVendre newArticle);
	
	List<Categorie> getAllCategories();

}