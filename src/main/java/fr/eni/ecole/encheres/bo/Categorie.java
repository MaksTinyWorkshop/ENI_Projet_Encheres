package fr.eni.ecole.encheres.bo;

public class Categorie {
	
///////////////////////////////////////////// Attributs

	public Long id;
	public String libelle;
	
/////////////////////////////////////////////Constructeurs

	public Categorie() {
	}
	public Categorie(Long id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
////////////////////////////////////////////Setters / getters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
////////////////////////////////////////////////Autres méthodes
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Categorie [id=");
		builder.append(id);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
