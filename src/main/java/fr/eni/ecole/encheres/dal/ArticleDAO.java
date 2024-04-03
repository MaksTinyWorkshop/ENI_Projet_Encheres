package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Enchere;

public interface ArticleDAO {
	//1.les méthodes permettant de consulter les Articles
	
	List<ArticleAVendre> getUserAndActiveArticles(String pseudo);
	
	List<ArticleAVendre> getActiveArticles();//pour afficher tous les articles par date
	
	//2. les méthodes permettant de créer ou modifier des articles
	void creerArticle(ArticleAVendre newArticle);

	List<ArticleAVendre> getArticlesByName(String boutNom);
	
	ArticleAVendre getArticleById(Long articleId);
	
	void supprArticleById(Long articleId);

	List<ArticleAVendre> getArticlesByCategorie(Categorie categorie);

	Adresse getAdress(String pseudo);
	
	void updatePrix(Enchere enchere);

	void modifierArticle(ArticleAVendre newArticle);

	

	

}
