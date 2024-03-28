package fr.eni.ecole.encheres.bll;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.AdresseDAO;
import fr.eni.ecole.encheres.dal.UtilisateurDAO;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;



@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	//Injection des repository
	private UtilisateurDAO utilisateurDAO;
	private AdresseDAO adresseDAO;

   


    private void validerDonneesInscription(String pseudo, String email, String motDePasse) {
        BusinessException be = new BusinessException();
        if (pseudo == null || pseudo.isBlank()) {
            be.add(BusinessCode.BLL_USER_LOGIN_BLANK);
        }
        if (!pseudo.matches("[a-zA-Z0-9_]+")) {
            be.add(BusinessCode.BLL_USER_LOGIN_FORM);
        }
        if (email == null || email.isBlank()) {
            be.add(BusinessCode.BLL_USER_EMAIL_BLANK);
        }
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            be.add(BusinessCode.BLL_USER_EMAIL_FORM);
        }
        if (motDePasse == null || motDePasse.isBlank()) {
            be.add(BusinessCode.BLL_USER_PASSWORD_BLANK);
        }
        if (motDePasse.length() < 8 || motDePasse.length() > 20) {
            be.add(BusinessCode.BLL_USER_PASSWORD_LENGTH);
        }
        if (!motDePasse.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {
            be.add(BusinessCode.BLL_USER_PASSWORD_FORM);
        }

        if (!be.isValid()) {
            throw be;
        }
    }

    private void verifierUnicitePseudo(String pseudo) {
        if (existsByPseudo(pseudo)) {
            throw new BusinessException(BusinessCode.BLL_USER_USER_EXISTS);
        }
    }

    private void verifierUniciteEmail(String email) {
        if (existsByEmail(email)) {
            throw new BusinessException(BusinessCode.BLL_USER_EMAIL_EXISTS);
        }
    }
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO) {
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
	}

	
	
	@Override
	public boolean existsByPseudo(String pseudo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void enregistrerUtilisateur(@Valid Utilisateur formObject) {
		// TODO Auto-generated method stub
		
	}

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
	 * Méthode privée pour centraliser l'association entre 
	 * un user et son adresse  
	 * @param user
	 */
	private void chargerAdresse(Utilisateur u) {
		Adresse adresse = adresseDAO.read(u.getPseudo());
		u.setAdresse(adresse);
	}



	@Override
	public void update(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		BusinessException be = new BusinessException();
		try {
			
		}catch (DataAccessException e) {
			be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_ERREUR);
		}
		
	}



	
	
//    @Override
//    public void enregistrerUtilisateur(String pseudo, String nom, String prenom, String telephone, String email, String rue, String codePostal, String ville, String motDePasse) {
//        validerDonneesInscription(pseudo, email, motDePasse); // Assuming validation for other fields are done elsewhere
//        verifierUnicitePseudo(pseudo);
//        verifierUniciteEmail(email);
//        String motDePasseHash = encoderMotDePasse(motDePasse);
//        creerUtilisateur(pseudo, nom, prenom, telephone, email, rue, codePostal, ville, motDePasseHash);
//    }


//    
//        
//    @Override
//    public boolean existsByPseudo(String pseudo) {
//        return utilisateurs.stream().anyMatch(u -> u.getPseudo().equals(pseudo));
//    }
//
//    @Override
//    public boolean existsByEmail(String email) {
//        return utilisateurs.stream().anyMatch(u -> u.getEmail().equals(email));
//    }
//
//    private String encoderMotDePasse(String motDePasse) {
//        return passwordEncoder.encode(motDePasse);
//    }
//
//    private void creerUtilisateur(String pseudo, String nom, String prenom, String telephone, String email, String rue, String codePostal, String ville, String motDePasseHash) {
//        Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, telephone, email, rue, codePostal, ville, motDePasseHash);
//        utilisateur.setCredit(10);
//        utilisateurs.add(utilisateur);
//    }
//
//    private void validerDonneesInscription(String pseudo, String email, String motDePasse) {
//        if (pseudo == null || pseudo.isBlank()) {
//            throw new IllegalArgumentException(messageSource.getMessage("validation.user.loginBlank", null, null));
//        }
//        if (!pseudo.matches("[a-zA-Z0-9_]+")) {
//            throw new IllegalArgumentException(messageSource.getMessage("validation.user.userForm", null, null));
//        }
//        if (email == null || email.isBlank()) {
//            throw new IllegalArgumentException(messageSource.getMessage("validation.user.emailBlank", null, null));
//        }
//        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
//            throw new IllegalArgumentException(messageSource.getMessage("validation.user.emailForm", null, null));
//        }
//        if (motDePasse == null || motDePasse.isBlank()) {
//            throw new IllegalArgumentException(messageSource.getMessage("validation.user.passwordBlank", null, null));
//        }
//        if (motDePasse.length() < 8 || motDePasse.length() > 20) {
//            throw new IllegalArgumentException(messageSource.getMessage("validation.user.passwordLength", null, null));
//        }
//        if (!motDePasse.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {
//            throw new IllegalArgumentException(messageSource.getMessage("validation.user.passwordForm", null, null));
//        }
//    }
//    private void verifierUnicitePseudo(String pseudo) {
//        if (existsByPseudo(pseudo)) {
//            throw new IllegalArgumentException("Ce pseudo est déjà utilisé");
//        }
//    }
//
//    private void verifierUniciteEmail(String email) {
//        if (existsByEmail(email)) {
//            throw new IllegalArgumentException("Cet email est déjà enregistré");
//     }
//  }
//

 

}



