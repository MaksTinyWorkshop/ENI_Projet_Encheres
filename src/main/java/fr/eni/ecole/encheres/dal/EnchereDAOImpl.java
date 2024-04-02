package fr.eni.ecole.encheres.dal;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

	private static final String INSERT_ENCHERE = "INSERT into ENCHERES (id_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:pseudo, :no_article, :date_enchere, :montant_enchere)";
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void placerUneEnchere(String pseudoUser, long idArticle, int montantEnchere) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		
		//Date d'enchère = moment de l'insertion
		LocalDateTime dateEnchere = LocalDateTime.now();

		
		namedParam.addValue("pseudo", pseudoUser);
		namedParam.addValue("no_article", idArticle);
		namedParam.addValue("montant_enchere", montantEnchere);
		namedParam.addValue("date_enchere", dateEnchere);

		jdbcTemplate.update(INSERT_ENCHERE, namedParam);
		
	}

}
