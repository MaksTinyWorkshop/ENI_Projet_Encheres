package fr.eni.ecole.encheres.exceptions;

public class BusinessCode {

		
	// Clefs de validation ou d'erreur des BO
		
	//Clés de Validation
	public static final String VALIDATION_USER_NULL= "validation.user.null";
	public static final String VALIDATION_USER_LOGIN_BLANK = "Notblank.user.pseudo";
	public static final String VALIDATION_USER_LOGIN_FORM = "Pattern.user.pseudo";
	public static final String VALIDATION_USER_EMAIL_BLANK = "Notblank.user.email";
	public static final String VALIDATION_USER_EMAIL_FORM = "Email.user.email";
	public static final String VALIDATION_USER_PASSWORD_BLANK = "Notblank.user.motDePasse";
	public static final String VALIDATION_USER_PASSWORD_LENGTH = "Size.user.motDePasse";
	public static final String VALIDATION_USER_PASSWORD_FORM = "Pattern.user.motDePasse";
	public static final String VALIDATION_USER_PASSWORD_DIFFERENT = "Different.user.motDePasse";
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
	public static final String BLL_VALIDATION_ARTICLE_DATE_FIN_NULL = "NotNull.article.dateFinEncheres";
	public static final String BLL_VALIDATION_ARTICLE_DATE_FIN_LOW = "Past.article.dateFinEncheres";
	public static final String BLL_VALIDATION_ARTICLE_DATE_INIT_NULL = "NotNull.article.dateDebutEncheres";
	public static final String BLL_VALIDATION_ARTICLE_DATE_INIT_HIGH = "Past.article.dateDebutEncheres";
	public static final String BLL_VALIDATION_ARTICLE_PRIX = "NotNull.article.prixInitial";
	public static final String BLL_VALIDATION_ARTICLE_DESCR_BLANK = "NotBlank.article.description";
	public static final String BLL_VALIDATION_ARTICLE_NOM_BLANK = "NotBlank.article.nom";
	public static final String BLL_VALIDATION_ARTICLE_NULL = "bll.validation.null";
	
	// 4. Placement d'enchère
	public static final String BLL_UTILISATEUR_PLACEMENT_ENCHERE_ERREUR = "bll.placement.enchere.error";
	public static final String VALIDATION_ENCHERE_CREDIT = "placement.enchere.credit.insuffisant";
	public static final String VALIDATION_ENCHERE_USER_EQUALS_VENDEUR = "placement.enchere.user.equals.vendeur";
	public static final String VALIDATION_ENCHERE_MONTANT_INSUFFISANT = "placement.enchere.montant.insuffisant";
	public static final String VALIDATION_ENCHERE_USER_EQUALS_ENCHERISSEUR = "placement.enchere.user.equals.encherisseur";
}


	
