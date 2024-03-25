package fr.eni.ecole.encheres.bo;

import java.time.LocalDate;

public class ArticleAVendre {
	
///////////////////////////////////////////// Attributs
	//Locaux
	public Long id;
	public String nom;
	public String description;
	public String photo;
	public LocalDate dateDebutEncheres;
	public LocalDate dateFinEncheres;
	public int statu;
	public int prixInitial;
	public int prixVente;
	//Liens
	public Adresse retrait;
	public Utilisateur vendeur;
	public Categorie categorie;
	
/////////////////////////////////////////////Constructeurs

	public ArticleAVendre() {
	}
	//Locaux
	public ArticleAVendre(	Long id, String nom, String description, String photo, LocalDate dateDebutEncheres,
							LocalDate dateFinEncheres, int statu, int prixInitial, int prixVente) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.photo = photo;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.statu = statu;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
	}
	//All
	public ArticleAVendre(	Long id, String nom, String description, String photo, LocalDate dateDebutEncheres,
							LocalDate dateFinEncheres, int statu, int prixInitial, int prixVente, Adresse retrait,
							Utilisateur vendeur, Categorie categorie) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.photo = photo;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.statu = statu;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.retrait = retrait;
		this.vendeur = vendeur;
		this.categorie = categorie;
	}
//////////////////////////////////////////// Setters / getters
	//Locaux
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}
	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}
	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}
	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}
	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}
	public int getPrixInitial() {
		return prixInitial;
	}
	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}
	public int getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	
	//Liens
	
	public Adresse getRetrait() {
		return retrait;
	}
	public void setRetrait(Adresse retrait) {
		this.retrait = retrait;
	}
	public Utilisateur getVendeur() {
		return vendeur;
	}
	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
////////////////////////////////////////////////Autres méthodes
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleAVendre [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", description=");
		builder.append(description);
		builder.append(", photo=");
		builder.append(photo);
		builder.append(", dateDebutEncheres=");
		builder.append(dateDebutEncheres);
		builder.append(", dateFinEncheres=");
		builder.append(dateFinEncheres);
		builder.append(", statu=");
		builder.append(statu);
		builder.append(", prixInitial=");
		builder.append(prixInitial);
		builder.append(", prixVente=");
		builder.append(prixVente);
		builder.append(", retrait=");
		builder.append(retrait);
		builder.append(", vendeur=");
		builder.append(vendeur);
		builder.append(", categorie=");
		builder.append(categorie);
		builder.append("]");
		return builder.toString();
	}	
}
