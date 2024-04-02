package fr.eni.ecole.encheres.bll;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ArticleDAO;
import fr.eni.ecole.encheres.dal.EnchereDAO;
import fr.eni.ecole.encheres.dal.UtilisateurDAO;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;

@Service
public class EnchereServiceImpl implements EnchereService {	// Enlever $$ acquéreur et recréditer précedent enchérisseur verifier en base
	// Les vérifs sont à faire dans cette couche-ci
	
	//Injection des repositories
	private EnchereDAO enchereDAO;
	private ArticleDAO articleDAO;
	private UtilisateurDAO utilisateurDAO;
	
	public EnchereServiceImpl(EnchereDAO enchereDAO, ArticleDAO articleDAO, UtilisateurDAO utilisateurDAO) {
		this.enchereDAO = enchereDAO;
		this.articleDAO = articleDAO;
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	@Transactional
	public void placerUneEnchere(String pseudoUser, long idArticle, int montantEnchere) {
		BusinessException be = new BusinessException();
		// Méthodes de vérifiaction
		boolean isValid = true;
		isValid &= validerCreditUtilisateur(pseudoUser, idArticle, be);
		isValid &= validerUserNotVendeur(pseudoUser, idArticle, be);
		isValid &= validerMontantEnchere(montantEnchere, idArticle, be);

		if (!isValid) {
			throw be;
		}
			//// 1- Vérification de l'existence d'une enchère présente
			//Remontée d'un user de la base qui ne contient que le pseudo et le "crédit" (montant_enchère), s'il existe
		
			Utilisateur previousEncherisseur = null;
			try {
				previousEncherisseur = enchereDAO.lireEncherisseur(idArticle);
				//Remboursement de l'enchérisseur précédent s'il existe
				if (previousEncherisseur != null) {
					enchereDAO.supprimerEncherePrecedente(idArticle);
					utilisateurDAO.crediter(previousEncherisseur);
				}
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Aucune enchère précédente");
			}
					
			// 2 : Placer enchère user connecté
			enchereDAO.placerUneEnchere(pseudoUser, idArticle, montantEnchere);
			articleDAO.updatePrix(idArticle, montantEnchere);
			utilisateurDAO.debiter(pseudoUser, montantEnchere);
	}

	/**
	 * Méthodes de validation 
	 */
	
	// User a assez de crédit / User != vendeur / montantEnchere > prixVente
	
	private boolean validerCreditUtilisateur(String pseudoUser, long idArticle, BusinessException be) {
		int creditUtilisateur = utilisateurDAO.read(pseudoUser).getCredit();
		int prixVente = articleDAO.getArticleById(idArticle).getPrixVente();
		if (creditUtilisateur < prixVente) {
			be.add(BusinessCode.VALIDATION_ENCHERE_CREDIT);
			return false;
		}
		return true;
	}
	
	private boolean validerUserNotVendeur(String pseudoUser, long idArticle, BusinessException be) {
		String pseudoVendeur = articleDAO.getArticleById(idArticle).getVendeur().getPseudo();
		if (pseudoUser.equals(pseudoVendeur)) {
			be.add(BusinessCode.VALIDATION_ENCHERE_USER_EQUALS_VENDEUR);
			return false;
		}
		return true;
	}
	
	private boolean validerMontantEnchere(int montantEnchere, long idArticle, BusinessException be) {
		int prixVente = articleDAO.getArticleById(idArticle).getPrixVente();
		if (montantEnchere <= prixVente) {
			be.add(BusinessCode.VALIDATION_ENCHERE_MONTANT_INSUFFISANT);
			return false;
		}
		return true;
	}
	
}
