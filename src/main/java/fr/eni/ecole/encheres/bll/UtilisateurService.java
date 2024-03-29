package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface UtilisateurService {

    Utilisateur consulterProfil(String pseudo);
    
    void update(Utilisateur user, Utilisateur userEnBase);

	void enregistrerUtilisateur(Utilisateur formObject);
	
	void updatePassword(String pseudo, String nouveauMdp);


}