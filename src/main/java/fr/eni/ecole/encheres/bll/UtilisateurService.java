package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface UtilisateurService {

    Utilisateur consulterProfil(String pseudo);
    
    void update(Utilisateur user, Utilisateur userEnBase);

	void updatePassword(String pseudo, String nouveauMdp);

	void save(Utilisateur utilisateur);

	void saveAddress(String pseudo, Adresse adresse);



}