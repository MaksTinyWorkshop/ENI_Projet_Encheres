package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface UtilisateurService {
    void enregistrerUtilisateur(String pseudo, String nom, String prenom, String telephone, String email, String rue, String codePostal, String ville, String motDePasse);
    boolean existsByPseudo(String pseudo);
    boolean existsByEmail(String email);
    
    Utilisateur consulterProfil(String pseudo);
    
    void update(Utilisateur utilisateur);
}