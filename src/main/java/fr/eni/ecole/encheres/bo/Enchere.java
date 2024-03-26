package fr.eni.ecole.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Enchere implements Serializable{
	
///////////////////////////////////////////// num Série
	
	private static final long serialVersionUID = 1L;

///////////////////////////////////////////// Attributs
	//Locaux
	private LocalDate date;
	private int montant;
	//Liens
	private Utilisateur acquereur;
	private ArticleAVendre articleAVendre;
	
/////////////////////////////////////////////Constructeurs
	
	public Enchere() {
	}
	//Locaux
	public Enchere(LocalDate date, int montant) {
		this.date = date;
		this.montant = montant;
	}
	//All
	public Enchere(LocalDate date, int montant, Utilisateur acquereur, ArticleAVendre articleAVendre) {
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
	public int hashCode() {
		return Objects.hash(acquereur, articleAVendre, date, montant);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enchere other = (Enchere) obj;
		return Objects.equals(acquereur, other.acquereur) && Objects.equals(articleAVendre, other.articleAVendre)
				&& Objects.equals(date, other.date) && montant == other.montant;
	}
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
