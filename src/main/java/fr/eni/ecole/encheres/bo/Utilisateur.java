package fr.eni.ecole.encheres.bo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.validation.constraints.*;

public class Utilisateur implements Serializable{
	
///////////////////////////////////////////// num Série
	
	private static final long serialVersionUID = 1L;
	
///////////////////////////////////////////// Attributs
	//Locaux
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]+$")
	private String pseudo;
	
	@NotBlank	
	private String nom;
	
	@NotBlank
	private String prenom;
	
	@Email
	private String email;
	
	private String telephone;
	
	@NotBlank
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$")
	private String motDePasse;
	
	@PositiveOrZero
	private int credit;
	
	private boolean admin;
	//Liens
	
	private Adresse adresse;
	
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
						Adresse adresse) {
		
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.admin = admin;
		this.adresse = adresse;
	}
	
	
	
	public Utilisateur(String pseudo2, String email2, String motDePasseHash) {
		this.getPseudo();
		this.getEmail();
		this.getMotDePasse();
	}
	
	public Utilisateur(String pseudo2, String nom2, String prenom2, String telephone2, String email2, String rue,
			String codePostal, String ville, String motDePasseHash) {
		super();
		
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
	
////////////////////////////////////////////////Autres méthodes
	
	@Override
	public int hashCode() {
		return Objects.hash(admin, adresse, credit, email, motDePasse, nom, prenom, pseudo, telephone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return admin == other.admin && Objects.equals(adresse, other.adresse) && credit == other.credit
				&& Objects.equals(email, other.email) && Objects.equals(motDePasse, other.motDePasse)
				&& Objects.equals(nom, other.nom) && Objects.equals(prenom, other.prenom)
				&& Objects.equals(pseudo, other.pseudo) && Objects.equals(telephone, other.telephone);
	}
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
		builder.append(", credit=");
		builder.append(credit);
		builder.append(", admin=");
		builder.append(admin);
		builder.append(", adresse=");
		builder.append(adresse);
		builder.append("]");
		return builder.toString();
	}
	
	public String getRue() {
	return adresse != null ? adresse.getRue() : null;
	    }

	public String getCodePostal() {
	return adresse != null ? adresse.getCodePostal() : null;
	    }

	public String getVille() {
	return adresse != null ? adresse.getVille() : null;
	
	
	   }
		
}
