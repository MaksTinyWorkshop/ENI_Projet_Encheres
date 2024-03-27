package fr.eni.ecole.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Utilisateur;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final List<Utilisateur> utilisateurs = new ArrayList<>();
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource; // Ajout de MessageSource

    // Injection de MessageSource via le constructeur
    public UtilisateurServiceImpl(PasswordEncoder passwordEncoder, MessageSource messageSource) {
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @Override
    public void enregistrerUtilisateur(String pseudo, String nom, String prenom, String telephone, String email, String rue, String codePostal, String ville, String motDePasse) {
        validerDonneesInscription(pseudo, email, motDePasse); // Assuming validation for other fields are done elsewhere
        verifierUnicitePseudo(pseudo);
        verifierUniciteEmail(email);
        String motDePasseHash = encoderMotDePasse(motDePasse);
        creerUtilisateur(pseudo, nom, prenom, telephone, email, rue, codePostal, ville, motDePasseHash);
    }

    @Override
    public boolean existsByPseudo(String pseudo) {
        return utilisateurs.stream().anyMatch(u -> u.getPseudo().equals(pseudo));
    }

    @Override
    public boolean existsByEmail(String email) {
        return utilisateurs.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    private String encoderMotDePasse(String motDePasse) {
        return passwordEncoder.encode(motDePasse);
    }

    private void creerUtilisateur(String pseudo, String nom, String prenom, String telephone, String email, String rue, String codePostal, String ville, String motDePasseHash) {
        Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, telephone, email, rue, codePostal, ville, motDePasseHash);
        utilisateur.setCredit(10);
        utilisateurs.add(utilisateur);
    }

    private void validerDonneesInscription(String pseudo, String email, String motDePasse) {
        if (pseudo == null || pseudo.isBlank()) {
            throw new IllegalArgumentException(messageSource.getMessage("validation.user.loginBlank", null, null));
        }
        if (!pseudo.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException(messageSource.getMessage("validation.user.userForm", null, null));
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(messageSource.getMessage("validation.user.emailBlank", null, null));
        }
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException(messageSource.getMessage("validation.user.emailForm", null, null));
        }
        if (motDePasse == null || motDePasse.isBlank()) {
            throw new IllegalArgumentException(messageSource.getMessage("validation.user.passwordBlank", null, null));
        }
        if (motDePasse.length() < 8 || motDePasse.length() > 20) {
            throw new IllegalArgumentException(messageSource.getMessage("validation.user.passwordLength", null, null));
        }
        if (!motDePasse.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {
            throw new IllegalArgumentException(messageSource.getMessage("validation.user.passwordForm", null, null));
        }
    }
    private void verifierUnicitePseudo(String pseudo) {
        if (existsByPseudo(pseudo)) {
            throw new IllegalArgumentException("Ce pseudo est déjà utilisé");
        }
    }

    private void verifierUniciteEmail(String email) {
        if (existsByEmail(email)) {
            throw new IllegalArgumentException("Cet email est déjà enregistré");
     }
  }
 

}

