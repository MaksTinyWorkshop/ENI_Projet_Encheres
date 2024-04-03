package fr.eni.ecole.encheres.bll;


import fr.eni.ecole.encheres.bo.Utilisateur;

public interface UtilisateurService {

    Utilisateur consulterProfil(String pseudo);
    
	void save(Utilisateur utilisateur);
    
    void update(Utilisateur user);

	void updatePassword(String pseudo, String nouveauMdp);

	
}