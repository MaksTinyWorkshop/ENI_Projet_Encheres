package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private final String FIND_BY_PSEUDO = "select pseudo, nom, prenom, email, credit, administrateur from UTILISATEURS where pseudo = :pseudo";
	
	@Autowired NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Utilisateur read(String pseudo) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParam, new UtilisateurRowMapper());
	}

	
	
	/**
	* Classe de mapping pour gérer les noms des colonnes
	*/
	class UtilisateurRowMapper implements RowMapper<Utilisateur> {

		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur u = new Utilisateur();
			u.setNom(rs.getString("nom"));
			u.setPrenom(rs.getString("prenom"));
			u.setEmail(rs.getString("email"));
			u.setCredit(rs.getInt("credit"));
			u.setAdmin(rs.getBoolean("administrateur"));
			u.setPseudo(rs.getString("pseudo"));
			u.setAdresse(u.getAdresse());
			return u;
		}
		
	}
	
	
}
