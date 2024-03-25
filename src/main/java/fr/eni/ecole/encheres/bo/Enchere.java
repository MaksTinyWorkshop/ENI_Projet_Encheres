package fr.eni.ecole.encheres.bo;

import java.time.LocalDate;

public class Enchere {
	
///////////////////////////////////////////// Attributs
	//Locaux
	public LocalDate date;
	public int montant;
	//Liens
	public Utilisateur acquereur;
	public ArticleAVendre articleAVendre;
	
/////////////////////////////////////////////Constructeurs
	
	public Enchere() {
		super();
	}
	//Locaux
	public Enchere(LocalDate date, int montant) {
		super();
		this.date = date;
		this.montant = montant;
	}
	//All
	public Enchere(LocalDate date, int montant, Utilisateur acquereur, ArticleAVendre articleAVendre) {
		super();
		this.date = date;
		this.montant = montant;
		this.acquereur = acquereur;
		this.articleAVendre = articleAVendre;
	}
	
//////////////////////////////////////////// Setters / getters
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getMontant() {
		return montant;
	}
	public void setMontant(int montant) {
		this.montant = montant;
	}
	public Utilisateur getAcquereur() {
		return acquereur;
	}
	public void setAcquereur(Utilisateur acquereur) {
		this.acquereur = acquereur;
	}
	public ArticleAVendre getArticleAVendre() {
		return articleAVendre;
	}
	public void setArticleAVendre(ArticleAVendre articleAVendre) {
		this.articleAVendre = articleAVendre;
	}
	
////////////////////////////////////////////////Autres méthodes
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [date=");
		builder.append(date);
		builder.append(", montant=");
		builder.append(montant);
		builder.append(", acquereur=");
		builder.append(acquereur);
		builder.append(", articleAVendre=");
		builder.append(articleAVendre);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
