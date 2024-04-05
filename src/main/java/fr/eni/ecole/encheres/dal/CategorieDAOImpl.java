package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Categorie;


@Repository
public class CategorieDAOImpl implements CategorieDAO {

///////////////////////////////////////////// Attributs
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private final String FIND_ALL = "SELECT no_categorie, libelle "
									+ " FROM CATEGORIES ";
	
	private final String FIND_BY_ID = "SELECT no_categorie, libelle "
									+ " FROM CATEGORIES where no_categorie = :id ";
	
///////////////////////////////////////////// Les méthodes
	
	@Override
	public List<Categorie> getAllCategories() {								//récupère la liste des catégories
		return jdbcTemplate.query(FIND_ALL, new CategorieRowMapper());
	}
	
	@Override
	public Categorie getCategorieById(long idCategorie) {					// récupères une catégorie par ID
		MapSqlParameterSource namedParam = new MapSqlParameterSource();
		namedParam.addValue("id",idCategorie);
		return jdbcTemplate.queryForObject(FIND_BY_ID, namedParam, new CategorieRowMapper());
	}

///////////////////////////////////////////////////////////////////////// ROWMAPPERS CUSTOM

	class CategorieRowMapper implements RowMapper<Categorie> {

		@Override
		public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
			Categorie c = new Categorie();
			c.setId(rs.getLong("no_categorie"));
			c.setLibelle(rs.getString("libelle"));
			return c;
		}
	}
}
