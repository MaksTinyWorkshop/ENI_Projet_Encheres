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

	private static final String FIND_BY_PSEUDO = "select pseudo, nom, prenom, telephone, email, credit, administrateur, no_adresse "
			+ " from UTILISATEURS where pseudo = :pseudo";
	private static final String INSERT_USER_QUERY = "INSERT INTO UTILISATEURS "
			+ " (pseudo, nom, prenom, email, telephone, mot_de_passe, no_adresse) "
			+ " VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, :no_adresse )";
	
	private static final String UPDATE_USER= "UPDATE UTILISATEURS SET "
			+ " nom= :nom, prenom= :prenom, email= :email, telephone= :telephone "
			+ " WHERE pseudo= :pseudo";
	private static final String UPDATE_MOT_DE_PASSE= "UPDATE utilisateurs "
			+ " SET mot_de_passe = :nouveauMdp WHERE pseudo = :pseudo";
	
	private static final String COUNT_EMAIL= "SELECT count(email) "
			+ " FROM UTILISATEURS WHERE email = :email";
	private static final String COUNT_PSEUDO= "SELECT count(pseudo) "
			+ " FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String CREDIT_PRECEDENT_ENCHERISSEUR= "UPDATE UTILISATEURS SET"
			+ " credit = credit + :credit WHERE pseudo= :pseudo";
	private static final String DEBIT_PRECEDENT_ENCHERISSEUR= "UPDATE UTILISATEURS SET"
			+ " credit = credit - :credit WHERE pseudo= :pseudo";

	
	@Autowired 
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Utilisateur read(String pseudo) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", pseudo);

		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParam, new UtilisateurRowMapper());
	}

	
	@Override
    public void save(Utilisateur utilisateur, long idAdresse) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", utilisateur.getPseudo());
        params.addValue("nom", utilisateur.getNom());
        params.addValue("prenom", utilisateur.getPrenom());
        params.addValue("email", utilisateur.getEmail());
        params.addValue("telephone", utilisateur.getTelephone());
        params.addValue("motDePasse", utilisateur.getMotDePasse());
        params.addValue("no_adresse", idAdresse);


        jdbcTemplate.update(INSERT_USER_QUERY, params);
    }


	@Override
	public void update(Utilisateur utilisateur) {
		//Update des infos de l'utilisateur
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", utilisateur.getPseudo());
		namedParam.addValue("nom", utilisateur.getNom());
		namedParam.addValue("prenom", utilisateur.getPrenom());
		namedParam.addValue("email",utilisateur.getEmail());
		namedParam.addValue("telephone", utilisateur.getTelephone());
				
		jdbcTemplate.update(UPDATE_USER, namedParam);
	}
	
	@Override
	public void updateMdp(String pseudo, String nouveauMdp) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", pseudo);
		namedParam.addValue("nouveauMdp", nouveauMdp);
		
		jdbcTemplate.update(UPDATE_MOT_DE_PASSE, namedParam);
	}
	
	@Override
	public void crediter(Utilisateur utilisateur) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", utilisateur.getPseudo());
		namedParam.addValue("credit", utilisateur.getCredit());
		
		jdbcTemplate.update(CREDIT_PRECEDENT_ENCHERISSEUR, namedParam);
	}
	
	@Override
	public void debiter(String pseudo, int montant) {
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", pseudo);
		namedParam.addValue("credit", montant);
		
		jdbcTemplate.update(DEBIT_PRECEDENT_ENCHERISSEUR, namedParam);
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
			u.setTelephone(rs.getString("telephone"));

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
