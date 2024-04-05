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
import fr.eni.ecole.encheres.dal.AdresseDAOImpl;
import fr.eni.ecole.encheres.dal.ArticleDAOImpl;
import fr.eni.ecole.encheres.dal.CategorieDAOImpl;
import fr.eni.ecole.encheres.exceptions.BusinessCode;
import fr.eni.ecole.encheres.exceptions.BusinessException;

@Service
public class ArticleServiceImpl implements ArticleService {

///////////////////////////////////////////// Attributs

	private ArticleDAOImpl articleDAO;// dépendance
	private AdresseDAOImpl adresseDAO;
	private CategorieDAOImpl categorieDAO;

///////////////////////////////////////////// Constructeurs

	public ArticleServiceImpl(ArticleDAOImpl articleDAO, AdresseDAOImpl adresseDAO, CategorieDAOImpl categorieDAO) {
		this.articleDAO = articleDAO;
		this.adresseDAO = adresseDAO;
		this.categorieDAO = categorieDAO;
	}

//////////////////////////////////////////// Méthodes

	@Override
	public List<ArticleAVendre> charger(Principal user) { 						// appel la méthode de chargement de la liste des articles actifs via la DAO
		List<ArticleAVendre> listeArticles = new ArrayList<>();
		listeArticles = articleDAO.getActiveArticles();
		if (user != null) {
			List<ArticleAVendre> listeArticlesUser = new ArrayList<>();
			listeArticlesUser = articleDAO.getUserAndActiveArticles(user.getName());
			for (ArticleAVendre e : listeArticlesUser) {
				listeArticles.add(e);
			}
		}
		BusinessException be = new BusinessException(); 						// création d'une instance de la classe d'exception
		if (listeArticles == null || listeArticles.isEmpty()) {
			be.add(BusinessCode.ENCHERE_AUCUNE); 								// ajout de la clé erreur
			throw be; 															// propage l'exception
		}
		return listeArticles;
	}

	@Override
	public ArticleAVendre consulterArticleById(Long articleId) {				// récupération d'un article par son ID
		return articleDAO.getArticleById(articleId);
	}

	@Override
	public void supprArticleById(Long id) {										// suppression d'un article par son ID
		articleDAO.supprArticleById(id);
	}

	@Override
	public Adresse getAdress(String pseudo) { 									// récupère l'adresse du Principal pour le formulaire de créa d'article
		Adresse adress = adresseDAO.read(pseudo);
		return adress;
	}

	@Override
	@Transactional
	public void creerArticle(ArticleAVendre newArticle, boolean create) {		// crée un nouvel article / édite un article existant
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
				} else {
					articleDAO.modifierArticle(newArticle);
				}
			} catch (BusinessException e) {
				be.add(BusinessCode.BLL_CREER_ARTICLE_ERROR);
				throw be;
			}
		} else {
			be.printStackTrace();
			throw be;
		}
	}
	
	@Override
	public List<Categorie> chargerCategories() {								// charge la liste des catégories
		List<Categorie> listeCategories = categorieDAO.getAllCategories();
		return listeCategories;
	}

	@Override
	public Categorie chargerCategorie(long idCategorie) {						// charge une catégorie par ID
		return categorieDAO.getCategorieById(idCategorie);
	}

	@Override
	public List<ArticleAVendre> chargerArticlesParCategorie(long idCategorie) {	// charge une liste d'article par catégtorie
		return articleDAO.getArticlesByCategorie(idCategorie);
	}

	@Override
	public List<ArticleAVendre> chargerArticlesParNom(String nom) {				// charge une liste d'article par nom
		return articleDAO.getArticlesByName(nom);
	}

	@Override
	public List<ArticleAVendre> chargerArticlesByFiltres(long idCategorie, String nom) { // charge une liste d'article par nom et catégorie
		return articleDAO.getArticleByFiltres(idCategorie, nom);

	}

	@Override
	public List<ArticleAVendre> chargerArticlesActifs() {						// charge la liste des articles actifs
		return articleDAO.getActiveArticles();
	}
	
	//////////////////////////////// LES VALIDATIONS ////////////////////////////////


	private boolean validerDateFin(LocalDate dI, LocalDate dF, BusinessException be) {
		if (dF == null) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_DATE_FIN_NULL);
			return false;
		} else if (dI.compareTo(dF) > 0) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_DATE_FIN_LOW);
			return false;
		}
		return true;
	}

	private boolean validerDateInit(LocalDate dI, LocalDate dF, BusinessException be) {
		if (dI == null) {
			be.add(BusinessCode.BLL_VALIDATION_ARTICLE_DATE_INIT_NULL);
			return false;
		} else if (dI.compareTo(dF) > 0) {
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
}
