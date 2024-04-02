package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	Utilisateur read(String pseudo);

	void save(Utilisateur utilisateur);
	
	void update(Utilisateur utilisateur);
	
	void updateMdp(String pseudo, String nouveauMdp);
		
	int uniqueEmail(String email);
	
	int uniquePseudo(String pseudo);

	Long saveAddress(Adresse adresse);
	
	void crediter(Utilisateur utilisateur);
	
	void debiter(String pseudo, int montant);
	
	}
