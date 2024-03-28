package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

public interface UtilisateurService {
    boolean existsByPseudo(String pseudo);
    boolean existsByEmail(String email);
	void enregistrerUtilisateur(@Valid Utilisateur formObject);
}