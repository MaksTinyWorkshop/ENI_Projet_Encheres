package fr.eni.ecole.encheres.bo;

import java.util.List;

public class Utilisateur {
	
///////////////////////////////////////////// Attributs
	//Locaux
	public String pseudo;
	public String nom;
	public String prenom;
	public String email;
	public String telephone;
	public String motDePasse;
	public int credit;
	public boolean admin;
	//Liens
	public Adresse adresse;
	public List<ArticleAVendre> articlesAVendre;
	
/////////////////////////////////////////////Constructeurs
	
	public Utilisateur() {
	}
	//Locaux
	public Utilisateur(	String pseudo, String nom, String prenom, String email,
						String telephone, String motDePasse, int credit, boolean admin) {
		
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.admin = admin;
	}
	//All
	public Utilisateur(	String pseudo, String nom, String prenom, String email,
						String telephone, String motDePasse, int credit, boolean admin,
						Adresse adresse, List<ArticleAVendre> articlesAVendre) {
		
		
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.admin = admin;
		this.adresse = adresse;
		this.articlesAVendre = articlesAVendre;
	}
	
////////////////////////////////////////////Setters / getters
	
	//Locaux
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	//Liens
	
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public List<ArticleAVendre> getArticlesAVendre() {
		return articlesAVendre;
	}
	public void setArticlesAVendre(List<ArticleAVendre> articlesAVendre) {
		this.articlesAVendre = articlesAVendre;
	}
	
////////////////////////////////////////////////Autres méthodes
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Utilisateur [pseudo=");
		builder.append(pseudo);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", prenom=");
		builder.append(prenom);
		builder.append(", email=");
		builder.append(email);
		builder.append(", telephone=");
		builder.append(telephone);
//		builder.append(", motDePasse=");
//		builder.append(motDePasse);
		builder.append(", credit=");
		builder.append(credit);
		builder.append(", admin=");
		builder.append(admin);
		builder.append(", adresse=");
		builder.append(adresse);
		builder.append(", articlesAVendre=");
		builder.append(articlesAVendre);
		builder.append("]");
		return builder.toString();
	}		
}
