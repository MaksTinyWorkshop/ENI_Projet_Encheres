package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;


@Repository
public class EnchereDAOImpl implements EnchereDAO {

	private static final String INSERT_ENCHERE = "INSERT into ENCHERES "
			+ " (id_utilisateur, no_article, date_enchere, montant_enchere) "
			+ " VALUES (:pseudo, :no_article, :date_enchere, :montant_enchere)";
	private static final String FIND_ENCHERISSEUR_BY_ID_ARTICLE = "SELECT id_utilisateur, montant_enchere FROM ENCHERES where no_article = :no_article";
	private static final String DELETE_ENCHERE_BY_ID = "DELETE FROM ENCHERES WHERE no_article = :no_article";
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void placerUneEnchere(Enchere enchere) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		
		namedParam.addValue("pseudo", enchere.getAcquereur().getPseudo());
		namedParam.addValue("no_article", enchere.getArticleAVendre().getId());
		namedParam.addValue("montant_enchere", enchere.getMontant());
		namedParam.addValue("date_enchere", enchere.getDate());

		jdbcTemplate.update(INSERT_ENCHERE, namedParam);
		
	}

	@Override
	public Utilisateur lireEncherisseur(Enchere enchere) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("no_article", enchere.getArticleAVendre().getId());
		
		try {
			return jdbcTemplate.queryForObject(FIND_ENCHERISSEUR_BY_ID_ARTICLE, namedParam, new UtilisateurTemporaireRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	
	@Override
	public void supprimerEncherePrecedente(Enchere enchere) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("no_article", enchere.getArticleAVendre().getId());
		
		jdbcTemplate.update(DELETE_ENCHERE_BY_ID, namedParam);
		
	}

	/**
	* Classe de mapping pour gérer le mapping à remonter
	*/
	
	class UtilisateurTemporaireRowMapper implements RowMapper<Utilisateur> {

		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur uTemp = new Utilisateur();
			uTemp.setPseudo(rs.getString("id_utilisateur"));
			uTemp.setCredit(rs.getInt("montant_enchere"));
			return uTemp;
		}
		
		
	}

}
