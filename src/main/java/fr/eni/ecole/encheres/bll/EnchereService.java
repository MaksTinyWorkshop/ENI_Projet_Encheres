package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EnchereService {
	void placerUneEnchere(String pseudoUser, long idArticle, int montantEnchere);
}
