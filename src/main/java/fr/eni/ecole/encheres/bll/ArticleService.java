package fr.eni.ecole.encheres.bll;

import java.security.Principal;
import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;


public interface ArticleService {
	List<ArticleAVendre> charger(Principal user);
	
	List<ArticleAVendre> chargerArticlesParCategorie(long idCategorie);
	
	List<ArticleAVendre> chargerArticlesParNom(String nom);
	
	List<ArticleAVendre> chargerArticlesByFiltres(long idCategorie, String nom);
	
	List<ArticleAVendre> chargerArticlesActifs();
	
	List<Categorie> chargerCategories();

	Adresse getAdress(String pseudo);

	ArticleAVendre consulterArticleById(Long articleId);

	void supprArticleById(Long id);
	
	void creerArticle(ArticleAVendre newArticle, boolean create);
		
	Categorie chargerCategorie(long idCategorie);
	

}
