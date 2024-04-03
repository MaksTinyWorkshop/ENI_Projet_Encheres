package fr.eni.ecole.encheres.bll;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.dal.ArticleDAOImpl;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;


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
	public List<ArticleAVendre> charger(Principal user){										//appel la méthode de chargement de la liste des articles actifs via la DAO
		List<ArticleAVendre> listeArticles = new ArrayList<>();
		listeArticles = articleDAO.getActiveArticles();
		if (user != null) {
			List<ArticleAVendre> listeArticlesUser = new ArrayList<>();
			listeArticlesUser = articleDAO.getUserAndActiveArticles(user.getName());
			for (ArticleAVendre e : listeArticlesUser) {
				listeArticles.add(e);
			}
		}
		BusinessException be = new BusinessException();											//création d'une instance de la classe d'exception
		if (listeArticles == null || listeArticles.isEmpty()) {
			be.add(BusinessCode.ENCHERE_AUCUNE);												//ajout de la clé erreur
			throw be;																			//propage l'exception 
		}
		return listeArticles;
	}
	
	@Override
	public ArticleAVendre consulterArticleById(Long articleId) {
		
		return articleDAO.getArticleById(articleId);
	}
	@Override
	public void supprArticleById(Long id) {
		articleDAO.supprArticleById(id);
	}
	
	@Override
	public Adresse getAdress(String pseudo) {				//récupère l'adresse du Principal pour le formulaire de créa d'article
		Adresse adress = articleDAO.getAdress(pseudo);		//tranfère la demande à la DAL
		return adress;
	}
	
	
	@Override
	@Transactional
	public void creerArticle(ArticleAVendre newArticle, boolean create) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerArticle(newArticle, be);
		isValid &= validerNom(newArticle.getNom(), be);
		isValid &= validerDescription(newArticle.getDescription(), be);
		isValid &= validerPrix(newArticle.getPrixInitial(), be);
		isValid &= validerDateInit(newArticle.getDateDebutEncheres(), newArticle.getDateFinEncheres(), be);
		isValid &= validerDateFin(newArticle.getDateDebutEncheres(), newArticle.getDateFinEncheres(), be);
		
		if (isValid) {
			try {
				if (create) {
					articleDAO.creerArticle(newArticle);
				}else {
					articleDAO.modifierArticle(newArticle);
				}
			}catch (BusinessException e) {
				be.add(BusinessCode.BLL_CREER_ARTICLE_ERROR);
				throw be;
			}
		}else {
			be.printStackTrace();
			throw be;
		}
	}
	
	//////////////////////////////// VALIDATIONS BLL /////////////////////////////////////////////
	
	////// 1 // Création d'article ///////////
	
	private boolean validerDateFin(LocalDate dI, LocalDate dF, BusinessException be) {
		if (dF == null) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_DATE_FIN_NULL);
			return false;
		}else if (dI.compareTo(dF) > 0) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_DATE_FIN_LOW);
			return false;
		}
		return true;
	}
	
	private boolean validerDateInit(LocalDate dI, LocalDate dF, BusinessException be) {
		if (dI == null) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_DATE_INIT_NULL);
			return false;
		}else if (dI.compareTo(dF) > 0) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_DATE_INIT_HIGH);
			return false;
		}
		return true;
	}
	
	private boolean validerPrix(int p, BusinessException be) {
		if (p <= 0) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_PRIX);
			return false;
		}
		return true;
	}
	
	private boolean validerDescription(String d, BusinessException be) {
		if (d == null || d.isBlank()) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_DESCR_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerNom(String n, BusinessException be) {
		if (n == null || n.isBlank()) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_NOM_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean validerArticle(ArticleAVendre a, BusinessException be) {
		if (a == null) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_NULL);
			return false;
		}
		return true;
	}

	/////////// FILTRES ARTICLES
	@Override
	public List<ArticleAVendre> getArticlesByName(String nomArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categorie getCategorieByName(String categorie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleAVendre> getArticlesByCategorie(Categorie categorieObj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 public List<Categorie> getAllCategories() {
	        return articleDAO.getAllCategories();
	    }

	
}
