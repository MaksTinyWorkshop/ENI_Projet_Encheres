package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleAVendre;

public interface ArticleDAO {
	
	List<ArticleAVendre> getActiveArticles();//pour afficher tous les articles par date
	//List<ArticleAVendre> getArticlesByName(String boutNom);//pour rechercher l'article par élément contenu dans le nom
	//List<ArticleAVendre> getArticlesByCategorie(Categorie categorie);//pour afficher tous les articles par catégories
}
