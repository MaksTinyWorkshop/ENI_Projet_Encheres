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
	
	/////////////////////////////////////// les attributs
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemp;
	
	//!!!! NB !!!!! notice des statu :  0 : PAS COMMENCEE, 1 : EN COURS, 2 : CLOTUREE, 100 : ANNULEE
	private final String FIND_ACTIVE = "SELECT nom_article, prix_vente, date_fin_encheres, id_utilisateur FROM ARTICLES_A_VENDRE WHERE statu_enchere = 20";
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
		// TODO Auto-generated method stub
		
	}
}
