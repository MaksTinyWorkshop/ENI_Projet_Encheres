package fr.eni.ecole.encheres.bll;


public interface UtilisateurService {
    void enregistrerUtilisateur(String pseudo, String nom, String prenom, String telephone, String email, String rue, String codePostal, String ville, String motDePasse);
    boolean existsByPseudo(String pseudo);
    boolean existsByEmail(String email);
}