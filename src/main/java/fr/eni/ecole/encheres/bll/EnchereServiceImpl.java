package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ArticleDAO;
import fr.eni.ecole.encheres.dal.EnchereDAO;
import fr.eni.ecole.encheres.dal.UtilisateurDAO;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;

@Service
public class EnchereServiceImpl implements EnchereService { 

///////////////////////////////////////////// Attributs
	
	private EnchereDAO enchereDAO;
	private ArticleDAO articleDAO;
	private UtilisateurDAO utilisateurDAO;
	
///////////////////////////////////////////// Constructeurs

	public EnchereServiceImpl(EnchereDAO enchereDAO, ArticleDAO articleDAO, UtilisateurDAO utilisateurDAO) {
		this.enchereDAO = enchereDAO;
		this.articleDAO = articleDAO;
		this.utilisateurDAO = utilisateurDAO;
	}

///////////////////////////////////////////// Méthodes
	
	@Override
	@Transactional
	public void placerUneEnchere(Enchere enchere) {								// permet de faire une enchère
		BusinessException be = new BusinessException();
		// Méthodes de vérifiaction
		boolean isValid = true;
		isValid &= validerUserNotVendeur(enchere, be);
		isValid &= validerTopEncherisseur(enchere, be);
		isValid &= validerMontantEnchere(enchere, be);
		isValid &= validerCreditUtilisateur(enchere, be);

		if (!isValid) {
			throw be;
		}
		//// 1- Vérification de l'existence d'une enchère présente
		// Remontée d'un user de la base qui ne contient que le pseudo et le "crédit"
		//// (montant_enchère), s'il existe
		Utilisateur previousEncherisseur = null;
		previousEncherisseur = enchereDAO.lireEncherisseur(enchere);
		
		// Remboursement de l'enchérisseur précédent s'il existe
		if (previousEncherisseur != null) {
			enchereDAO.supprimerEncherePrecedente(enchere);
			utilisateurDAO.crediter(previousEncherisseur);
		}
		// 2 : Placer enchère user connecté
		enchereDAO.placerUneEnchere(enchere);
		articleDAO.updatePrix(enchere);
		utilisateurDAO.debiter(enchere);

	}


	//////////////////////////////// LES VALIDATIONS ////////////////////////////////


	private boolean validerCreditUtilisateur(Enchere enchere, BusinessException be) {
		Utilisateur u = enchere.getAcquereur();
		ArticleAVendre a = enchere.getArticleAVendre();

		int creditUtilisateur = utilisateurDAO	.read(u.getPseudo())
												.getCredit();
		int prixVente = a.getPrixVente();

		if (creditUtilisateur < prixVente) {
			be.add(BusinessCode.VALIDATION_ENCHERE_CREDIT);
			return false;
		}
		return true;
	}

	private boolean validerUserNotVendeur(Enchere enchere, BusinessException be) {
		Utilisateur u = enchere.getAcquereur();
		ArticleAVendre a = enchere.getArticleAVendre();

		String pseudoVendeur = a.getVendeur()
								.getPseudo();
		String pseudoAcquereur = u.getPseudo();
		if (pseudoAcquereur.equals(pseudoVendeur)) {
			be.add(BusinessCode.VALIDATION_ENCHERE_USER_EQUALS_VENDEUR);
			return false;
		}
		return true;
	}

	private boolean validerMontantEnchere(Enchere enchere, BusinessException be) {
		ArticleAVendre a = enchere.getArticleAVendre();
		int montantEnchere = enchere.getMontant();

		if ((Integer) montantEnchere == null) {
			return false;
		}

		int prixVente = a.getPrixVente();
		if (montantEnchere <= prixVente) {
			be.add(BusinessCode.VALIDATION_ENCHERE_MONTANT_INSUFFISANT);
			return false;
		}
		return true;
	}

	private boolean validerTopEncherisseur(Enchere enchere, BusinessException be) {
		String encherisseur = enchere	.getAcquereur()
										.getPseudo();
		Utilisateur precedentEncherisseurUser = enchereDAO.lireEncherisseur(enchere);
		if (precedentEncherisseurUser != null) {
			String precedentEncherisseur = precedentEncherisseurUser.getPseudo();
			
			if (encherisseur.equals(precedentEncherisseur)) {
				be.add(BusinessCode.VALIDATION_ENCHERE_USER_EQUALS_ENCHERISSEUR);
				return false;
			}
		}										
		return true;
	}
}
