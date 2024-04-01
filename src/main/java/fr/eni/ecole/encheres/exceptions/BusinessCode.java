package fr.eni.ecole.encheres.exceptions;

public class BusinessCode {

		
	// Clefs de validation ou d'erreur des BO
		
	//Clés de Validation
	public static final String VALIDATION_USER_NULL= "validation.user.null";
	public static final String VALIDATION_USER_LOGIN_BLANK = "validation.user.loginBlank";
	public static final String VALIDATION_USER_LOGIN_FORM = "validation.user.userForm";
	public static final String VALIDATION_USER_EMAIL_BLANK = "validation.user.emailBlank";
	public static final String VALIDATION_USER_EMAIL_FORM = "validation.user.emailForm";
	public static final String VALIDATION_USER_PASSWORD_BLANK = "validation.user.passwordBlank";
	public static final String VALIDATION_USER_PASSWORD_LENGTH = "validation.user.passwordLength";
	public static final String VALIDATION_USER_PASSWORD_FORM = "validation.user.passwordForm";
	public static final String VALIDATION_USER_USER_EXISTS = "validation.user.usernameUnicity";
	public static final String VALIDATION_USER_EMAIL_EXISTS = "validation.user.emailUnicity";
	public static final String VALIDATION_ADDRESS_INVALID = "validation.adress.invalid";
	
	
	// clefs de validation BLL //////////////////////////////////
	
	// 1. profil Utilisateur
	public static final String BLL_UTILISATEUR_UPDATE_ERREUR = "update.utilisateur.error";
	public static final String BLL_UTILISATEUR_UPDATE_MDP_ERREUR= "update.utilisateur.mdp.error";
	
	public static final String SAVE_USER_ERROR = "save.user.error";
	public static final String SAVE_USER_VALID = "save.user.valid";
	public static final String ERROR_SAVING_ADDRESS = "error.saving.adress";
	public static final String VALIDATION_USER_NOT_FOUND = "user.not.found";

		// 2. Affichage des articles en cours de vente
		
	// 2. Affichage des articles en cours de vente
	public static final String ENCHERE_AUCUNE = "aucune.enchere";
	
	// 3. Création d'article
	public static final String BLL_CREER_ARTICLE_ERROR = "bll.creer.article.error";
	public static final String BLL_VALIDATION_ARTICLE_DATE_FIN_NULL = "bll.validation.article.date.fin.null";
	public static final String BLL_VALIDATION_ARTICLE_DATE_FIN_LOW = "bll.validation.article.date.fin.low";
	public static final String BLL_VALIDATION_ARTICLE_DATE_INIT_NULL = "bll.validation.article.date.init.null";
	public static final String BLL_VALIDATION_ARTICLE_DATE_INIT_HIGH = "bll.validation.article.date.init.high";
	public static final String BLL_VALIDATION_ARTICLE_PRIX = "bll.validation.article.prix";
	public static final String BLL_VALIDATION_ARTICLE_DESCR_BLANK = "bll.validation.article.descr.blank";
	public static final String BLL_VALIDATION_ARTICLE_NOM_BLANK = "bll.validation.article.nom.blank";
	public static final String BLL_VALIDATION_ARTICLE_NULL = "bll.validation.null";
	
	// 4. Placement d'enchère
	public static final String BLL_UTILISATEUR_PLACEMENT_ENCHERE_ERREUR = "bll.placement.enchere.error";
	public static final String UTILISATEUR_MONTANT_INSUFFISANT = "placement.enchere.montant.insuffisant";
}


	
