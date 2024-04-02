package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	Utilisateur read(String pseudo);

	void save(Utilisateur utilisateur, long idAdresse);
	
	void update(Utilisateur utilisateur);
	
	void updateMdp(String pseudo, String nouveauMdp);
		
	int uniqueEmail(String email);
	
	int uniquePseudo(String pseudo);
	
	void crediter(Utilisateur utilisateur);
	
	void debiter(String pseudo, int montant);
	
	}
