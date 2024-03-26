package fr.eni.ecole.encheres.bo;

import java.io.Serializable;
import java.util.Objects;

public class Adresse implements Serializable{
	
///////////////////////////////////////////// num Série

private static final long serialVersionUID = 1L;

///////////////////////////////////////////// Attributs

	private Long id;
	private String complement;
	private String rue;
	private String codePostal;
	private String ville;
	
///////////////////////////////////////////// Constructeurs
	
	public Adresse() {
	}
	public Adresse(Long id, String complement, String rue, String codePostal, String ville) {
		this.id = id;
		this.complement = complement;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	
///////////////////////////////////////////// Getters/Setters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComplement() {
		return complement;
	}
	public void setComplement(String complement) {
		this.complement = complement;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
///////////////////////////////////////////// Autre méthodes
	
	@Override
	public int hashCode() {
		return Objects.hash(codePostal, complement, id, rue, ville);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		return Objects.equals(codePostal, other.codePostal) && Objects.equals(complement, other.complement)
				&& Objects.equals(id, other.id) && Objects.equals(rue, other.rue) && Objects.equals(ville, other.ville);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adresse [id=");
		builder.append(id);
		builder.append(", complement=");
		builder.append(complement);
		builder.append(", rue=");
		builder.append(rue);
		builder.append(", codePostal=");
		builder.append(codePostal);
		builder.append(", ville=");
		builder.append(ville);
		builder.append("]");
		return builder.toString();
	}
}
