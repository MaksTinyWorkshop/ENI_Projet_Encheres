package fr.eni.ecole.encheres.bo;

public class Adresse {
	
///////////////////////////////////////////// Attributs

	public Long id;
	public String complement;
	public String rue;
	public String codePostal;
	public String ville;
	
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
