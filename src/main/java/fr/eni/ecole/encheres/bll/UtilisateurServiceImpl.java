package fr.eni.ecole.encheres.bll;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.AdresseDAO;
import fr.eni.ecole.encheres.dal.UtilisateurDAO;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

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
	public void update(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerUtilisateur(utilisateur, be);
		isValid &= validerEmail(utilisateur.getEmail(), be);
		isValid &= validerPseudo(utilisateur.getPseudo(), be);
		isValid &= validerMotDePasse(utilisateur.getMotDePasse(), be);

		if (isValid) {
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

	@Override
	@Transactional
	public void updatePassword(String pseudo, String nouveauMdp) {
		// Récup du user par son pseudo
		BusinessException be = new BusinessException();
		Utilisateur u = utilisateurDAO.read(pseudo);
		String mdpCrypt = passwordEncoder.encode(nouveauMdp);
		u.setMotDePasse(mdpCrypt);
		try {
			utilisateurDAO.updateMdp(pseudo, mdpCrypt);
			
		} catch (DataAccessException e){
			be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_MDP_ERREUR);
			throw be;
		}

	}

	@Override
	public void enregistrerUtilisateur(@Valid Utilisateur user) {
		// TODO Auto-generated method stub

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
