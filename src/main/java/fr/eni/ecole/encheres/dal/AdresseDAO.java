package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface AdresseDAO {

	Adresse read(String pseudo);
	
	void update(Utilisateur utilisateur, long idAdresse);

	void save(Adresse adresse);

	Long saveAddress(Adresse adresse);
}
