package jp.co.rakus.merucari.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jp.co.rakus.merucari.domain.Category;

@Repository
@Transactional
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedjdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** Category型のRowMapper */
	private static final RowMapper<Category> RowMapper = (rs, i) -> {

		Category category = new Category();

		category.setId(rs.getInt("id"));
		category.setParent(rs.getInt("parent"));
		category.setName(rs.getString("name"));
		category.setNameAll(rs.getString("name_all"));

		return category;
	};

	/**
	 * - 検索フォーム用 - 親カテゴリを返す
	 * 
	 * @return Category
	 */
	public List<Category> findParent() {

		String sql = "select id,parent,name,name_all from category where parent is null order by name asc";

		SqlParameterSource param = new MapSqlParameterSource();

		List<Category> categoryList = namedjdbcTemplate.query(sql, param, RowMapper);

		return categoryList;
	}

	/**
	 * - 検索フォーム用 - 引数の親idを元に検索して子カテゴリを返す
	 * 
	 * @param int
	 *            id
	 * @return Category category
	 */
	public List<Category> findChildByParent(int id) {

		String sql = "select id,parent,name,name_all from category where parent = :id order by name asc";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		List<Category> categoryList = namedjdbcTemplate.query(sql, param, RowMapper);

		return categoryList;
	}

	/**
	 * category table の id検索用
	 * 
	 * @param String
	 *            nameAll
	 * @return Category
	 * 
	 */
	public Integer findIdByNameAll(String nameAll) {

		String sql = "select id from category where name_all = :nameAll";

		SqlParameterSource param = new MapSqlParameterSource().addValue("nameAll", nameAll);

		return namedjdbcTemplate.queryForObject(sql, param, Integer.class);
	}
}
