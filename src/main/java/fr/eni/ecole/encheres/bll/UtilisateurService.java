package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

public interface UtilisateurService {

    Utilisateur consulterProfil(String pseudo);
    
    void update(Utilisateur utilisateur);

	void enregistrerUtilisateur(@Valid Utilisateur formObject);
	
	void updatePassword(String pseudo, String nouveauMdp);


}