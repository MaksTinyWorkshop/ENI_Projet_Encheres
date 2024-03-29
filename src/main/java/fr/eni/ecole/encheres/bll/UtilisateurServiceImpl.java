package fr.eni.ecole.encheres.bll;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.AdresseDAO;
import fr.eni.ecole.encheres.dal.UtilisateurDAO;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;
	


@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	// Injection des repository
	private UtilisateurDAO utilisateurDAO;
	private AdresseDAO adresseDAO;

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO) {
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
	}

	@Override
	public Utilisateur consulterProfil(String pseudo) {
		// Récup du user par son pseudo
		Utilisateur u = utilisateurDAO.read(pseudo);
		if (u != null) {
			// Chargement de son adresse
			chargerAdresse(u);
		}
		return u;
	}
	
	

	/**
	 * Méthode privée pour centraliser l'association entre un user et son adresse
	 * 
	 * @param user
	 */
	private void chargerAdresse(Utilisateur u) {
		Adresse adresse = adresseDAO.read(u.getPseudo());
		u.setAdresse(adresse);
	}

	

	

	@Override
	public void enregistrerUtilisateur(Utilisateur user) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));

	}

	/**
	 * Méthodes de validation des BO
	 */

	private boolean validerUtilisateur(Utilisateur u, BusinessException be) {
		if (u == null) {
			be.add(BusinessCode.VALIDATION_USER_NULL);
			return false;
		}
		return true;
	}

	private boolean validerUniquePseudo(String pseudo, BusinessException be) {
		int count = utilisateurDAO.uniquePseudo(pseudo);
		if (count == 1) {
			be.add(BusinessCode.VALIDATION_USER_USER_EXISTS);
			return false;
		}

		return true;
	}

	private boolean validerUniqueMail(String email, BusinessException be) {
		int count = utilisateurDAO.uniqueEmail(email);
		if (count == 1) {
			be.add(BusinessCode.VALIDATION_USER_EMAIL_EXISTS);
			return false;
		}

		return true;
	}

	private boolean validerPseudo(String pseudo, BusinessException be) {
		if (pseudo == null || pseudo.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_LOGIN_BLANK);
			return false;
		}
		if (!pseudo.matches("[a-zA-Z0-9_]+")) {
			be.add(BusinessCode.VALIDATION_USER_LOGIN_FORM);
			return false;
		}
		return true;
	}

	private boolean validerEmail(String email, BusinessException be) {
		if (email == null || email.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_EMAIL_BLANK);
			return false;
		}
		if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
			be.add(BusinessCode.VALIDATION_USER_EMAIL_FORM);
			return false;
		}
		return true;
	}

	private boolean validerMotDePasse(String motDePasse, BusinessException be) {
		if (motDePasse == null || motDePasse.isBlank()) {
			be.add(BusinessCode.VALIDATION_USER_PASSWORD_BLANK);
			return false;
		}
		if (motDePasse.length() < 8 || motDePasse.length() > 20) {
			be.add(BusinessCode.VALIDATION_USER_PASSWORD_LENGTH);
			return false;
		}
		if (!motDePasse.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {
			be.add(BusinessCode.VALIDATION_USER_PASSWORD_FORM);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public void update(Utilisateur utilisateur) {
	    BusinessException be = new BusinessException();
	    boolean isValid = true;
	    isValid &= validerUtilisateur(utilisateur, be);
	    isValid &= validerEmail(utilisateur.getEmail(), be);
	    isValid &= validerPseudo(utilisateur.getPseudo(), be);
	    isValid &= validerMotDePasse(utilisateur.getMotDePasse(), be);

	    if (isValid) {
	        // Encode le mot de passe avant de l'enregistrer
	        utilisateur.setMotDePasse(utilisateur.getMotDePasse());
	        try {
	            utilisateurDAO.update(utilisateur);
	            System.out.println("Success");
	        } catch (DataAccessException e) {
	            be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_ERREUR);
	            throw be;
	        }
	    } else {
	        throw be;
	    }
	}

	

}
