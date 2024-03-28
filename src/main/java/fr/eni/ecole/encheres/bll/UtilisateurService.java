package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;
<<<<<<< HEAD
import jakarta.validation.Valid;
=======
>>>>>>> 18da06ed1c5259ca8550305613a0a134891312b5

public interface UtilisateurService {
    boolean existsByPseudo(String pseudo);
    boolean existsByEmail(String email);
<<<<<<< HEAD
	void enregistrerUtilisateur(@Valid Utilisateur formObject);
=======
    
    Utilisateur consulterProfil(String pseudo);
>>>>>>> 18da06ed1c5259ca8550305613a0a134891312b5
}