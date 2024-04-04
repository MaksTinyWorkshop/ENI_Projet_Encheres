package fr.eni.ecole.encheres.bo;

import java.io.Serializable;
import java.util.Objects;

public class Categorie implements Serializable{
	
///////////////////////////////////////////// num Série

private static final long serialVersionUID = 1L;

///////////////////////////////////////////// Attributs

	private Long id;
	private String libelle;
	
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
	public int hashCode() {
		return Objects.hash(id, libelle);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		return Objects.equals(id, other.id) && Objects.equals(libelle, other.libelle);
	}
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
	public void setNoCategorie(int int1) {
		
	}
}
