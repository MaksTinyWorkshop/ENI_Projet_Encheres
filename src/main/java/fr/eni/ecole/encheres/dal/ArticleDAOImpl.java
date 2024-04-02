package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class ArticleDAOImpl implements ArticleDAO {
	
	/////////////////////////////////////// les attributs
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemp;
	
	//!!!! NB !!!!! notice des statu :  0 : PAS COMMENCEE, 1 : EN COURS, 2 : CLOTUREE, 100 : ANNULEE
	private final String FIND_ACTIVE = "SELECT no_article, nom_article, prix_vente, date_fin_encheres, id_utilisateur FROM ARTICLES_A_VENDRE WHERE statu_enchere = 1";
	//private final String FIND_BY_NAME = " ";	
	//private final String FIND_BY_CATEGORIE = " ";
	
	//requête de récupération de tout l'article
	private final String FIND_ARTICLE_BY_ID = "SELECT a.*, d.* FROM ARTICLES_A_VENDRE a"
												+ " INNER JOIN ADRESSES d ON a.no_adresse_retrait = d.no_adresse"
												+ " WHERE a.no_article = :articleId";
	//requête de suppression d'un article
	private final String SUPPR_ARTICLE_BY_ID = "DELETE FROM ARTICLES_A_VENDRE "
													+ " WHERE no_article = :articleId";
	
	//requêtes de création d'article
	private final String FIND_ADRESS_PSEUDO = "SELECT a.no_adresse, a.rue, a.code_postal, a.ville" 
													+ " FROM ADRESSES a "
													+ "INNER JOIN UTILISATEURS u ON a.no_adresse = u.no_adresse "
													+ "WHERE u.pseudo = :pseudo";
	
	private final String INSERT_ARTICLE = "INSERT INTO ARTICLES_A_VENDRE "
										+ "(nom_article, description, date_debut_encheres, date_fin_encheres, statu_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait)"
										+ " VALUES (:nom, :description, :dateDebutEncheres, :dateFinEncheres, :statu, :prixInitial, :prixVente, :vendeur, :categorie, :retrait)";

	private static final String UPDATE_PRIX = "UPDATE ARTICLES_A_VENDRE SET prix_vente= :prix_vente where no_article= :no_article";
	
	///////// METHODES DE FILTRES PAR NOM ET PAS CATEGORIE
	@Override
	public List<ArticleAVendre> getArticlesByName(String boutNom) {
	    String query = "SELECT nom_article, prix_vente, date_fin_encheres, id_utilisateur FROM ARTICLES_A_VENDRE WHERE nom_article LIKE :boutNom";
	    MapSqlParameterSource params = new MapSqlParameterSource().addValue("boutNom", "%" + boutNom + "%");
	    return jdbcTemp.query(query, params, new ArticleRowMapper());
	}

	@Override
	public List<ArticleAVendre> getArticlesByCategorie(Categorie categorie) {
	    String query = "SELECT nom_article, prix_vente, date_fin_encheres, id_utilisateur FROM ARTICLES_A_VENDRE WHERE no_categorie = :idCategorie";
	    MapSqlParameterSource params = new MapSqlParameterSource().addValue("idCategorie", categorie.getId());
	    return jdbcTemp.query(query, params, new ArticleRowMapper());
	}
	
	@Override
	public List<ArticleAVendre> getActiveArticles() {									//rempli la liste des Articles actif
		return jdbcTemp.query(FIND_ACTIVE, new ArticleRowMapper());
	}
	
	@Override
	public ArticleAVendre getArticleById(Long articleId) {							//récupère un objet Article par son ID
		MapSqlParameterSource np = new MapSqlParameterSource();
		np.addValue("articleId", articleId);
		
		return jdbcTemp.queryForObject(FIND_ARTICLE_BY_ID, np, new FullArticleRowMapper());
	}
	
	@Override
	public void supprArticleById(Long articleId) {
		MapSqlParameterSource np = new MapSqlParameterSource();
		np.addValue("articleId", articleId);
		
		jdbcTemp.update(SUPPR_ARTICLE_BY_ID, np);
	}
	
	@Override 																			//récupère une adresse par le pseudo User
	public Adresse getAdress(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		
		return jdbcTemp.queryForObject(FIND_ADRESS_PSEUDO,namedParameters, new ArticleAdressRowMapper());
	}

	
	@Override
	public void creerArticle(ArticleAVendre newArticle) {								//crée un nouvel article à vendre
		
		KeyHolder keyHolder = new GeneratedKeyHolder();//keyholder à identification unique
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();//mappeur SQL pour le remplissage des colonnes
		
		//remplissage de chaque catégorie (image abscente)
		namedParameters.addValue("nom", newArticle.getNom());
		namedParameters.addValue("description", newArticle.getDescription());
		namedParameters.addValue("dateDebutEncheres", newArticle.getDateDebutEncheres());
		namedParameters.addValue("dateFinEncheres", newArticle.getDateFinEncheres());
		namedParameters.addValue("statu", "1");
		namedParameters.addValue("prixInitial", newArticle.getPrixInitial());
		namedParameters.addValue("prixVente", null);
		namedParameters.addValue("vendeur", newArticle.getVendeur().getPseudo());
		namedParameters.addValue("categorie", newArticle.getCategorie().getId());
		namedParameters.addValue("retrait", newArticle.getRetrait().getId());
		
		jdbcTemp.update(INSERT_ARTICLE, namedParameters, keyHolder);//injection dans la BDD
		
		if (keyHolder != null && keyHolder.getKey() != null) {
			// Mise à jour de l'identifiant du film auto-généré par la base
			newArticle.setId(keyHolder.getKey().longValue());
		}
	}
	
	@Override
	public void updatePrix(long idArticle, int montantEnchere) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		
		namedParam.addValue("prix_vente", montantEnchere);
		namedParam.addValue("no_article", idArticle);
		
		jdbcTemp.update(UPDATE_PRIX, namedParam);
		
	}
	
	
	///////////////////////////////////////////////////////////////////////// ROWMAPPERS CUSTOM
	
	class ArticleRowMapper implements RowMapper<ArticleAVendre> {
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleAVendre a = new ArticleAVendre();
			a.setId(rs.getLong("no_article"));
			a.setNom(rs.getString("nom_article"));
			a.setPrixVente(rs.getInt("prix_vente"));
			//date finale
			a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());//dte finale convertie en LocalDate
			
			// Association pour le vendeur
			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("id_utilisateur"));
			a.setVendeur(vendeur);
			
			return a;
		}
	}
	
	class ArticleAdressRowMapper implements RowMapper<Adresse> {
		@Override
		public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
			Adresse a = new Adresse();
			a.setId(rs.getLong("no_adresse"));
			a.setRue(rs.getString("rue"));
			a.setCodePostal(rs.getString("code_postal"));
			a.setVille(rs.getString("ville"));
			
			return a;
		}
	}
	
	class FullArticleRowMapper implements RowMapper<ArticleAVendre> {
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleAVendre a = new ArticleAVendre();
			a.setId(rs.getLong("no_article"));
			a.setNom(rs.getString("nom_article"));
			a.setDescription(rs.getString("description"));
			a.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());//dte début convertie en LocalDate
			a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());//dte finale convertie en LocalDate
			a.setStatu(rs.getInt("statu_enchere"));
			a.setPrixInitial(rs.getInt("prix_initial"));
			a.setPrixVente(rs.getInt("prix_vente"));
			
			Utilisateur user = new Utilisateur();
			user.setPseudo(rs.getString("id_utilisateur"));
			a.setVendeur(user);
			
			Categorie cat = new Categorie();
			cat.setId(rs.getLong("no_categorie"));
			a.setCategorie(cat);
			
			Adresse adr = new Adresse();
			adr.setId(rs.getLong("no_adresse"));
			adr.setRue(rs.getString("rue"));
			adr.setCodePostal(rs.getString("code_postal"));
			adr.setVille(rs.getString("ville"));
			a.setRetrait(adr);
			
			return a;
		}
	}


}
