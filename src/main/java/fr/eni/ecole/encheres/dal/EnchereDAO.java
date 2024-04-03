package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EnchereDAO {
	void placerUneEnchere(Enchere enchere);
	
	Utilisateur lireEncherisseur(Enchere enchere);
	
	void supprimerEncherePrecedente(Enchere enchere);
}
