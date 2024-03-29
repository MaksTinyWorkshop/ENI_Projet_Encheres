package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static final String FIND_BY_PSEUDO = "select pseudo, nom, prenom, email, credit, administrateur, no_adresse from UTILISATEURS where pseudo = :pseudo";
	private static final String INSERT_USER_QUERY = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe) "
													+ "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse)";
	private static final String UPDATE_USER= "UPDATE UTILISATEURS SET nom= :nom, prenom= :prenom, email= :email, telephone= :telephone WHERE pseudo= :pseudo";
	private static final String UPDATE_MOT_DE_PASSE= "UPDATE utilisateurs SET mot_de_passe = :nouveauMdp WHERE pseudo = :pseudo";
	
	private static final String COUNT_EMAIL= "SELECT count(email) FROM UTILISATEURS WHERE email = :email";
	private static final String COUNT_PSEUDO= "SELECT count(pseudo) FROM UTILISATEURS WHERE pseudo = :pseudo";
	
	
	@Autowired 
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Utilisateur read(String pseudo) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParam, new UtilisateurRowMapper());
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
	

	@Override
	public void update(Utilisateur utilisateur) {
		//Update des infos de l'utilisateur
		MapSqlParameterSource namedParamU = new MapSqlParameterSource();
		namedParamU.addValue("pseudo", utilisateur.getPseudo());
		namedParamU.addValue("nom", utilisateur.getNom());
		namedParamU.addValue("prenom", utilisateur.getPrenom());
		namedParamU.addValue("email",utilisateur.getEmail());
		namedParamU.addValue("telephone", utilisateur.getTelephone());
				
		jdbcTemplate.update(UPDATE_USER, namedParamU);
			
	}
	
	@Override
	public void updateMdp(String pseudo, String nouveauMdp) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("nouveauMdp", nouveauMdp);
		
		jdbcTemplate.update(UPDATE_MOT_DE_PASSE, namedParam);
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

			//Association pour l'adresse 
			Adresse adresse = new Adresse();
			adresse.setId(rs.getLong("no_adresse"));
			u.setAdresse(adresse);
		
			return u;
		}
		
	}



	@Override
	public int uniqueEmail(String email) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("email", email);
		return jdbcTemplate.queryForObject(COUNT_EMAIL, namedParam, Integer.class);
	}


	@Override
	public int uniquePseudo(String pseudo) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(COUNT_PSEUDO, namedParam, Integer.class);
	}



	



	
	
}
