package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

public interface UtilisateurService {
    boolean existsByPseudo(String pseudo);
   
    boolean existsByEmail(String email);
    
    Utilisateur consulterProfil(String pseudo);
    
    void update(Utilisateur utilisateur);

	void enregistrerUtilisateur(@Valid Utilisateur formObject);


}