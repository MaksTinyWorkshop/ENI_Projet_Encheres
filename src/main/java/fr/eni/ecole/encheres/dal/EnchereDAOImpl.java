package fr.eni.ecole.encheres.dal;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

	private static final String INSERT_ENCHERE = "INSERT into ENCHERES (id_utilisateur, no_article, date_enchere, montant_enchere) "
			+ "VALUES (:pseudo, :no_article, :date_enchere, :montant_enchere)";
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void placerUneEnchere(Utilisateur user, ArticleAVendre article) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		
		//Date d'enchère = moment de l'insertion
		LocalDate dateEnchere = LocalDate.now();
		//Conversion de date format SQL ??
		//Date sqlDate = Date.valueOf(dateEnchere);
		
		namedParam.addValue("pseudo", user.getPseudo());
		namedParam.addValue("no_article", article.getId());
		namedParam.addValue("montant_enchere", article.getPrixVente());
		namedParam.addValue("date_enchere", dateEnchere);

		jdbcTemplate.update(INSERT_ENCHERE, namedParam);
		
	}

}
