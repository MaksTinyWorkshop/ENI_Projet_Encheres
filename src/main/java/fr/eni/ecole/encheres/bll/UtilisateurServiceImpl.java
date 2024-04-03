package fr.eni.ecole.encheres.bll;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public void save(Utilisateur utilisateur) {
	    BusinessException be = new BusinessException();

	    if (utilisateur == null) {
	        be.add(BusinessCode.VALIDATION_USER_NULL);
	        throw be;
	    }

	    if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().isEmpty()) {
	        be.add(BusinessCode.VALIDATION_USER_PASSWORD_BLANK);
	    }

	    // Validate the user object and collect validation errors
	    boolean isValid = true;
	    isValid &= validerUtilisateur(utilisateur, be);
	    isValid &= validerUniquePseudo(utilisateur.getPseudo(), be);
	    isValid &= validerUniqueMail(utilisateur.getEmail(), be);
	    isValid &= validerPseudo(utilisateur.getPseudo(), be);
	    isValid &= validerEmail(utilisateur.getEmail(), be);
	    isValid &= validerMotDePasse(utilisateur.getMotDePasse(), be);

	    if (!isValid) {
	        throw be; // Throw exception if any validation fails
	    }

	    // Encode the password
	    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(utilisateur.getMotDePasse());
	    utilisateur.setMotDePasse(encodedPassword);

	    // Save the user and address details
	    try {
	        adresseDAO.saveAddress(utilisateur.getAdresse());
	        long idAdresse = adresseDAO.readLast();
	        utilisateurDAO.save(utilisateur, idAdresse);
	    } catch (DataAccessException e) {
	        be.add(BusinessCode.SAVE_USER_ERROR);
	        throw be;
	    }
	}
	
	
	
	@Override
	@Transactional
	public void update(Utilisateur update) {
		BusinessException be = new BusinessException();
		
		
		// Méthodes de vérification
		boolean isValid = true;
		isValid &= validerUtilisateur(update, be);
		isValid &= validerEmail(update.getEmail(), be);

		if (isValid) {
			try {
				// Récupération de l'idAdresse pour update redirigé vers adresseDAO
				long idAdresse = update	.getAdresse()
											.getId();

				utilisateurDAO.update(update);
				adresseDAO.update(update, idAdresse);

			} catch (DataAccessException e) {
				be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_ERREUR);
				throw be;
			}
		} else {
			throw be;
		}
	}
	

	@Override
	@Transactional
	public void updatePassword(String pseudo, String nouveauMdp) {
		// Récup du user par son pseudo
		BusinessException be = new BusinessException();
		Utilisateur u = utilisateurDAO.read(pseudo);

		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		u.setMotDePasse(passwordEncoder.encode(nouveauMdp));

		try {
			utilisateurDAO.updateMdp(pseudo, u.getMotDePasse());
		} catch (DataAccessException e) {
			be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_MDP_ERREUR);
			throw be;
		}

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


}
