package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class ArticleDAOImpl implements ArticleDAO {
	
//////////////////////////////////////////////////////////////// les attributs
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemp;
	
	//!!!! NB !!!!! notice des status :  0 : PAS COMMENCEE, 1 : EN COURS, 2 : CLOTUREE, 100 : ANNULEE
	private final String FIND_ACTIVE = "SELECT no_article, nom_article, prix_vente, date_fin_encheres, id_utilisateur "
										+ " FROM ARTICLES_A_VENDRE "
										+ " WHERE statu_enchere = 1";
	//requête de récupération de tout l'article
	private final String FIND_ARTICLE_BY_PSEUDO = "SELECT no_article, nom_article, prix_vente, date_fin_encheres, id_utilisateur "
													+ " FROM ARTICLES_A_VENDRE "
													+ " WHERE id_utilisateur = :pseudo AND statu_enchere != 1";
	//requête de récupération de tout l'article
	private final String FIND_ARTICLE_BY_ID = "SELECT a.*, d.* "
												+ " FROM ARTICLES_A_VENDRE a "
												+ " INNER JOIN ADRESSES d ON a.no_adresse_retrait = d.no_adresse "
												+ " WHERE a.no_article = :articleId";
	//requête de suppression d'un article
	private final String SUPPR_ARTICLE_BY_ID = "DELETE"
												+ " FROM ARTICLES_A_VENDRE "
												+ " WHERE no_article = :articleId ";	
	
	//requête de création d'article
	private final String INSERT_ARTICLE = "INSERT "
											+ "INTO ARTICLES_A_VENDRE "
											+ " (nom_article, description, date_debut_encheres, date_fin_encheres, statu_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait) "
											+ " VALUES (:nom, :description, :dateDebutEncheres, :dateFinEncheres, :statu, :prixInitial, :prixVente, :vendeur, :categorie, :retrait) ";
	
	//resquête de mise à jour du prix final si une enchère a été faite
	private static final String MODIF_ARTICLE = "UPDATE ARTICLES_A_VENDRE "
													+ " SET nom_article = :nom, description = :description, date_debut_encheres = :startEnchere, date_fin_encheres = :endEnchere "
													+ " WHERE no_article = :no_article";
	
	//resquête de mise à jour du prix final si une enchère a été faite
	private static final String UPDATE_PRIX = "UPDATE ARTICLES_A_VENDRE "
												+ " SET prix_vente= :prix_vente "
												+ " WHERE no_article= :no_article ";
	
///////////////////////////////////////////////////////////////// les méthodes
	
	@Override																		//filtre par nom
	public List<ArticleAVendre> getArticlesByName(String boutNom) {
	    
		String query = "SELECT nom_article, prix_vente, date_fin_encheres, id_utilisateur FROM ARTICLES_A_VENDRE WHERE nom_article LIKE :boutNom";
	    MapSqlParameterSource params = new MapSqlParameterSource().addValue("boutNom", "%" + boutNom + "%");
	    return jdbcTemp.query(query, params, new ArticleRowMapper());
	}




	@Override																		//filtre par catégories
	public List<ArticleAVendre> getArticlesByCategorie(Categorie categorie) {
	    
		String query = "SELECT nom_article, prix_vente, date_fin_encheres, id_utilisateur FROM ARTICLES_A_VENDRE WHERE no_categorie = :idCategorie";
	    MapSqlParameterSource params = new MapSqlParameterSource().addValue("idCategorie", categorie.getId());
	    return jdbcTemp.query(query, params, new ArticleRowMapper());
	}
	
	@Override
	public List<ArticleAVendre> getUserAndActiveArticles(String pseudo) {			//remplit la liste des Articles du User
		MapSqlParameterSource np = new MapSqlParameterSource();
		np.addValue("pseudo", pseudo);
		
		return jdbcTemp.query(FIND_ARTICLE_BY_PSEUDO, np, new ArticleRowMapper());
	}
	
	@Override
	public List<ArticleAVendre> getActiveArticles() {								//remplit la liste des Articles actif
		
		return jdbcTemp.query(FIND_ACTIVE, new ArticleRowMapper());
	}
	
	@Override
	public ArticleAVendre getArticleById(Long articleId) {							//récupère un objet Article par son ID
		
		MapSqlParameterSource np = new MapSqlParameterSource();
		np.addValue("articleId", articleId);
		
		return jdbcTemp.queryForObject(FIND_ARTICLE_BY_ID, np, new FullArticleRowMapper());
	}
	
	@Override
	public void supprArticleById(Long articleId) {									//supprime un article
		
		MapSqlParameterSource np = new MapSqlParameterSource();
		np.addValue("articleId", articleId);
		jdbcTemp.update(SUPPR_ARTICLE_BY_ID, np);
	}
	
	@Override
	public void creerArticle(ArticleAVendre newArticle) {							//crée un nouvel article à vendre
		
		KeyHolder keyHolder = new GeneratedKeyHolder();								//keyholder pour générer l'ID
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();		//mappeur SQL pour le remplissage des colonnes
		//remplissage de chaque catégorie (image abscente)
		namedParameters.addValue("nom", newArticle.getNom());
		namedParameters.addValue("description", newArticle.getDescription());
		namedParameters.addValue("dateDebutEncheres", newArticle.getDateDebutEncheres());
		namedParameters.addValue("dateFinEncheres", newArticle.getDateFinEncheres());
		namedParameters.addValue("prixInitial", newArticle.getPrixInitial());
		namedParameters.addValue("prixVente", null);
		namedParameters.addValue("vendeur", newArticle.getVendeur().getPseudo());
		namedParameters.addValue("categorie", newArticle.getCategorie().getId());
		namedParameters.addValue("retrait", newArticle.getRetrait().getId());
		
		if (newArticle.getDateDebutEncheres().isAfter(LocalDate.now())) {			//assignation du statu en fonction de la date de début d'enchère
			namedParameters.addValue("statu", "0");
		}else {
			namedParameters.addValue("statu", "1");
		}
				
		jdbcTemp.update(INSERT_ARTICLE, namedParameters, keyHolder);				//injection dans la BDD
		if (keyHolder != null && keyHolder.getKey() != null) {
			newArticle.setId(keyHolder.getKey().longValue());						//Injection de l'identifiant par le Keyholder
		}
	}
	
	@Override
	public void modifierArticle(ArticleAVendre newArticle) {						//Modifier un article
		
		MapSqlParameterSource np = new MapSqlParameterSource();
		
		np.addValue("no_article", newArticle.getId());
		np.addValue("nom", newArticle.getNom());
		np.addValue("description", newArticle.getDescription());
		np.addValue("categorie", newArticle.getCategorie().getId());
		np.addValue("prixDep",newArticle.getPrixInitial());
		np.addValue("startEnchere", newArticle.getDateDebutEncheres());
		np.addValue("endEnchere", newArticle.getDateFinEncheres());
				
		jdbcTemp.update(MODIF_ARTICLE, np);
	}
	
	@Override
	public void updatePrix(Enchere enchere) {					// Mise à jour du prix final
		
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		
		namedParam.addValue("prix_vente", enchere.getMontant());
		namedParam.addValue("no_article", enchere.getArticleAVendre().getId());
		
		jdbcTemp.update(UPDATE_PRIX, namedParam);
	}
	
	
///////////////////////////////////////////////////////////////////////// ROWMAPPERS CUSTOM
	
	class ArticleRowMapper implements RowMapper<ArticleAVendre> {							// 1. pour l'affichage de la liste des articles
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleAVendre a = new ArticleAVendre();
			a.setId(rs.getLong("no_article"));
			a.setNom(rs.getString("nom_article"));
			a.setPrixVente(rs.getInt("prix_vente"));
			//date finale
			a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());//date finale convertie en LocalDate
			
			// Association pour le vendeur
			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("id_utilisateur"));
			a.setVendeur(vendeur);
			
			return a;
		}
	}
	
	class FullArticleRowMapper implements RowMapper<ArticleAVendre> {						// 2. pour la création d'un nouvel article
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

	@Override
	public List<Categorie> getAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

}
