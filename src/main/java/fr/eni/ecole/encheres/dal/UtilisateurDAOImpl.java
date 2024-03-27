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

	private static final String FIND_BY_PSEUDO = "select pseudo, nom, prenom, email, credit, administrateur from UTILISATEURS where pseudo = :pseudo";
	private static final String INSERT_USER_QUERY = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe) "
													+ "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse)";
	
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
	
	 @Override
	    public void save(Utilisateur utilisateur) {
	        MapSqlParameterSource params = new MapSqlParameterSource();
	        params.addValue("pseudo", utilisateur.getPseudo());
	        params.addValue("nom", utilisateur.getNom());
	        params.addValue("prenom", utilisateur.getPrenom());
	        params.addValue("email", utilisateur.getEmail());
	        params.addValue("telephone", utilisateur.getTelephone());
	        params.addValue("motDePasse", utilisateur.getMotDePasse());
	        
	        jdbcTemplate.update(INSERT_USER_QUERY, params);
	    }
	
	
}
