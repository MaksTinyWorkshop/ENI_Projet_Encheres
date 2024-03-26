package fr.eni.ecole.encheres.bll;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Utilisateur;

@Service
public class UtilisateurService {

    private final List<Utilisateur> utilisateurs = new ArrayList<>();

    public void enregistrerUtilisateur(String pseudo, String email, String motDePasse) {
        // Validation des données d'inscription
        if (pseudo == null || pseudo.isBlank()) {
            throw new IllegalArgumentException("Le pseudo est obligatoire.");
        }
        if (!pseudo.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Le pseudo ne doit contenir que des caractères alphanumériques et '_'.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("L'email est obligatoire.");
        }
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("L'email n'est pas valide.");
        }
        if (motDePasse == null || motDePasse.isBlank()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }
        if (motDePasse.length() < 8 || motDePasse.length() > 20) {
            throw new IllegalArgumentException("Le mot de passe doit avoir une longueur comprise entre 8 et 20 caractères.");
        }
        if (!motDePasse.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre, un caractère spécial et avoir une longueur comprise entre 8 et 20 caractères.");
        }

        // Vérification de l'unicité du pseudo et de l'email
        if (existsByPseudo(pseudo)) {
            throw new IllegalArgumentException("Ce pseudo est déjà utilisé.");
        }
        if (existsByEmail(email)) {
            throw new IllegalArgumentException("Cet email est déjà enregistré.");
        }

        PasswordEncoder passwordEncoder = null;
		// Chiffrer le mot de passe
        @SuppressWarnings("null")
		String motDePasseHash = passwordEncoder.encode(motDePasse);

        // Créer un nouvel utilisateur et l'ajouter à la liste
        Utilisateur utilisateur = new Utilisateur(pseudo, email, motDePasseHash);
        utilisateur.setCredit(10); // Initialiser le crédit
        utilisateurs.add(utilisateur);
    }

    public boolean existsByPseudo(String pseudo) {
        return utilisateurs.stream().anyMatch(u -> u.getPseudo().equals(pseudo));
    }

    public boolean existsByEmail(String email) {
        return utilisateurs.stream().anyMatch(u -> u.getEmail().equals(email));
    }
}