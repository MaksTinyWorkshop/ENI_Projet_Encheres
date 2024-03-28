package fr.eni.ecole.encheres.exceptions;

public class BusinessCode {

		
	// Clefs de validation ou d'erreur des BO
	
	public static final String BLL_UTILISATEUR_UPDATE_ERREUR = "update.utilisateur.error";
	
	//Articles
	public static final String ENCHERE_AUCUNE = "aucune.enchere";
	
	
	//BLL
	public static final String BLL_USER_LOGIN_BLANK = "validation.user.loginBlank";
	public static final String BLL_USER_LOGIN_FORM = "validation.user.userForm";
	public static final String BLL_USER_EMAIL_BLANK = "validation.user.emailBlank";
	public static final String BLL_USER_EMAIL_FORM = "validation.user.emailForm";
	public static final String BLL_USER_PASSWORD_BLANK = "validation.user.passwordBlank";
	public static final String BLL_USER_PASSWORD_LENGTH = "validation.user.passwordLength";
	public static final String BLL_USER_PASSWORD_FORM = "validation.user.passwordForm";
	public static final String BLL_USER_USER_EXISTS = "validation.user.usernameUnicity";
	public static final String BLL_USER_EMAIL_EXISTS = "validation.user.emailUnicity";
}
	