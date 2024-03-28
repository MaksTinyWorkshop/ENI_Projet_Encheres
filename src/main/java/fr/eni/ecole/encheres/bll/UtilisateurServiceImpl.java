package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

   


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
}