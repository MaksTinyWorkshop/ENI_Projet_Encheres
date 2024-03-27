package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class ArticleDAOImpl implements ArticleDAO {
	
	/////////////////////////////////////// les attributs
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemp;
	
	//!!!! NB !!!!! notice des statu :  0 : PAS COMMENCEE, 1 : EN COURS, 2 : CLOTUREE, 100 : ANNULEE
	private final String FIND_ACTIVE = "SELECT nom_article, prix_vente, date_fin_encheres, id_utilisateur FROM ARTICLES_A_VENDRE WHERE statu_enchere = 20";
	//private final String FIND_BY_NAME = " ";	
	//private final String FIND_BY_CATEGORIE = " ";
	
	//requête de création d'article
	private final String INSERT_ARTICLE = "INSERT INTO ARTICLES_A_VENDRE "
										+ "(no_article, nom_article, description, date_debut_encheres, date_fin_encheres, statu_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait"
										+ " VALUES (:id, :nom, :description, :dateDebutEncheres, :dateFinEncheres, :statu, :prixInitial, :prixVente, :vendeur, :categorie, :retrait";

	
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
	
	////////////////////////////////////////////////
	
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
			//récupération de la date provenant de la BDD
	        String dateFromBDD = rs.getString("date_fin_encheres");
	        // Définition du format de date de la BDD
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        // Conversion 
	        LocalDate date = LocalDate.parse(dateFromBDD, formatter);
			a.setDateFinEncheres(date);
			a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());//dte finale convertie en LocalDate
			
			// Association pour le vendeur
			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("id_utilisateur"));
			a.setVendeur(vendeur);
			
			return a;
		}
	}

	@Override
	public void creerArticle(ArticleAVendre newArticle) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();//keyholder à identification unique
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();//mappeur SQL pour le remplissage des colonnes
		
		//remplissage de chaque catégorie (image abscente)
		namedParameters.addValue("id", newArticle.getId());
		namedParameters.addValue("nom", newArticle.getNom());
		namedParameters.addValue("description", newArticle.getDescription());
		namedParameters.addValue("dateDebutEncheres", newArticle.getDateDebutEncheres());
		namedParameters.addValue("dateFinEncheres", newArticle.getDateFinEncheres());
		namedParameters.addValue("statu", newArticle.getStatu());
		namedParameters.addValue("prixInitial", newArticle.getPrixInitial());
		namedParameters.addValue("prixVente", newArticle.getPrixVente());
		namedParameters.addValue("vendeur", newArticle.getVendeur().getPseudo());
		namedParameters.addValue("categorie", newArticle.getCategorie().getId());
		namedParameters.addValue("retrait", newArticle.getRetrait().getId());
		
		jdbcTemp.update(INSERT_ARTICLE, namedParameters, keyHolder);//injection dans la BDD
	}
}
