package fr.eni.ecole.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.dal.ArticleDAOImpl;
import fr.eni.ecole.encheres.exceptions.BusinessException;
import fr.eni.ecole.encheres.exceptions.BusinessCode;


@Service
public class ArticleServiceImpl implements ArticleService {
	
///////////////////////////////////////////// Attributs
	
	private ArticleDAOImpl articleDAO;//dépendance
	
///////////////////////////////////////////// Constructeurs
	
	public ArticleServiceImpl(ArticleDAOImpl articleDAO) {
		this.articleDAO = articleDAO;
	}

//////////////////////////////////////////// Méthodes
	
	@Override
	public List<ArticleAVendre> charger(){//appel la méthode de chargement de la liste des articles actifs via la DAO
		List<ArticleAVendre> ListeArticles = articleDAO.getActiveArticles();
		if (ListeArticles.isEmpty()) {
			BusinessException be = new BusinessException();//création d'une instance de la classe d'exception
			be.add(BusinessCode.ENCHERE_AUCUNE);// ajout de la clé erreur
			throw be;//propage l'exception 
		}
		return ListeArticles;
	}
	
	public void creerArticle(ArticleAVendre newArticle) {
		articleDAO.creerArticle(newArticle);
	}
}
