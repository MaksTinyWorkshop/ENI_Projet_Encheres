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
public class AdresseDAOImpl implements AdresseDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	//requête de récupération de l'adresse
	private static final String FIND_BY_PSEUDO = " SELECT U.pseudo, A.rue, A.complement, A.code_postal, A.ville, A.no_adresse "
												+ " FROM UTILISATEURS U INNER JOIN ADRESSES A ON U.no_adresse = A.no_adresse "
												+ " WHERE U.pseudo = :pseudo ";
	//requête de modification de l'adresse
	private static final String UPDATE_ADRESSE = "UPDATE ADRESSES "
												+ " SET complement= :complement, rue= :rue, code_postal= :codePostal, ville= :ville "
												+ " WHERE no_adresse= :no_adresse ";
	//requête de création de l'adresse
	private static final String INSERT_ADRESSE_QUERY = "INSERT INTO ADRESSES "
														+ " (rue, ville, code_postal, complement) "
														+ " VALUES (:rue, :ville, :codePostal, :complement) ";
	//requête de récupération de la dernière adresse
	private static final String RECUP_ADRESSE = "SELECT MAX(no_adresse) "
												+ " FROM ADRESSES ";
	
//////////////////////////////////////////// les méthodes 
	
	@Override
	public Adresse read(String pseudo) {											// recherche l'adresse par pseudo
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("pseudo", pseudo);
		
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParam, new AdresseRowMapper());
	}

	@Override
	public void update(Utilisateur utilisateur, long idAdresse) {					// modifie une adresse existante
		// Update des infos de son adresse
		MapSqlParameterSource namedParamA = new MapSqlParameterSource();
		Adresse adresse = utilisateur.getAdresse();

		namedParamA.addValue("complement", adresse.getComplement());
		namedParamA.addValue("rue", adresse.getRue());
		namedParamA.addValue("codePostal", adresse.getCodePostal());
		namedParamA.addValue("ville", adresse.getVille());
		namedParamA.addValue("no_adresse", idAdresse);

		jdbcTemplate.update(UPDATE_ADRESSE, namedParamA);
	}
	
	
	@Override
	public long readLast() {														// recherche la dernière adresse de la table
		
		 Long dernierAdresseId = jdbcTemplate.queryForObject(RECUP_ADRESSE, new MapSqlParameterSource(), Long.class);
		 return dernierAdresseId != null ? dernierAdresseId : -1;
	}

	@Override
	public void saveAddress(Adresse adresse) {										// crée une nouvelle adresse
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("rue", adresse.getRue());
	    params.addValue("ville", adresse.getVille());
	    params.addValue("codePostal", adresse.getCodePostal());
	    params.addValue("complement", adresse.getComplement());

	    // Insert l'adresse en BDD
	    jdbcTemplate.update(INSERT_ADRESSE_QUERY, params);
	}

/////////////////////////////////////////////////////////////////// les ROWMAPPER customs
	
	class AdresseRowMapper implements RowMapper<Adresse> {

		@Override
		public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
			Adresse a = new Adresse();
			a.setId(rs.getLong("no_adresse"));
			a.setRue(rs.getString("rue"));
			a.setComplement(rs.getString("complement"));
			a.setCodePostal(rs.getString("code_postal"));
			a.setVille(rs.getString("ville"));
			
			return a;
		}
	}
}
