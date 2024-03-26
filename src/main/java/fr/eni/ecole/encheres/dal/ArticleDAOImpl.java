package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class ArticleDAOImpl implements ArticleDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemp;
	
	
	private final String FIND_ACTIVE = "SELECT nom_article, prix_vente, date_fin_encheres, id_utilisateur FROM ARTICLES_A_VENDRE WHERE statu_enchere >= 1";
	//private final String FIND_BY_NAME = " ";	
	//private final String FIND_BY_CATEGORIE = " ";
	
	/*
	@Override
	public List<ArticleAVendre> getArticlesByName(String boutNom) {
		return null;
	}
	@Override
	public List<ArticleAVendre> getArticlesByCategorie(Categorie categorie) {
		return null;
	}
	*/
	
	@Override
	public List<ArticleAVendre> getActiveArticles() {
		return jdbcTemp.query(FIND_ACTIVE, new ArticleRowMapper());
	}
	
	///////////////////////////// ROWMAPPER CUSTOM
	
	class ArticleRowMapper implements RowMapper<ArticleAVendre> {
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleAVendre a = new ArticleAVendre();
			a.setNom(rs.getString("nom_article"));
			a.setPrixVente(rs.getInt("prix_vente"));
			
			//date finale
			//récupératio nde la date provenant de la BDD
	        String dateFromBDD = rs.getString("date_fin_encheres");
	        // Définition du format de date de la BDD
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        // Conversion 
	        LocalDate date = LocalDate.parse(dateFromBDD, formatter);
			a.setDateFinEncheres(date);
			
			// Association pour le vendeur
			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("id_utilisateur"));
			a.setVendeur(vendeur);
			
			return a;
		}
	}
}
