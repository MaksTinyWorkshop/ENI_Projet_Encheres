package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EnchereDAO {
	void placerUneEnchere(String pseudoUser, long idArticle, int montantEnchere);
	
	Utilisateur lireEncherisseur(long idArticle);
	
	void supprimerEncherePrecedente(long idArticle);
}
