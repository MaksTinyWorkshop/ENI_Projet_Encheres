package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EnchereDAO {
	void placerUneEnchere(Utilisateur user, ArticleAVendre article);
	
}
