package fr.eni.ecole.encheres.bll;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ArticleDAO;
import fr.eni.ecole.encheres.dal.EnchereDAO;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;

@Service
public class EnchereServiceImpl implements EnchereService {
	
	//Injection des repositories
	private EnchereDAO enchereDAO;
	private ArticleDAO articleDAO;
	
	public EnchereServiceImpl(EnchereDAO enchereDAO, ArticleDAO articleDAO) {
		this.enchereDAO = enchereDAO;
		this.articleDAO = articleDAO;
	}

	@Override
	@Transactional
	public void placerUneEnchere(Utilisateur user, ArticleAVendre article) {
		BusinessException be = new BusinessException();

		try {
			enchereDAO.placerUneEnchere(user, article);
			articleDAO.updatePrix(article);
			
		} catch (DataAccessException e) {
			be.add(BusinessCode.BLL_UTILISATEUR_PLACEMENT_ENCHERE_ERREUR);
			throw be;
		}
		
	}

}
