package jp.co.rakus.merucari.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.rakus.merucari.domain.Category;

@Repository
@Transactional
public class OriginalRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;

	private static final RowMapper<Category> categoryRowMapper = (rs, i) -> {

		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setParent(rs.getInt("parent"));
		category.setName(rs.getString("name"));

		return category;
	};

	/** category_nameの全件取得 */
	public List<String> findAllDistinct() {
		String sql = "SELECT category_name from original order by id";

		List<String> categoryNameList = jdbcTemplate.queryForList(sql, String.class);

		return categoryNameList;
	}

	/** save 1st */
	public void saveCategory1st(Category category) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(category);

		namedTemplate.update("INSERT INTO category (name) values (:name)", param);
	}

	/** save second or third*/
	//public int saveCategorySecond(Category category) {
	public void saveCategorySecond(Category category) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(category);

		namedTemplate.update("INSERT INTO category (parent,name) values (:parent,:name)", param);

		//return jdbcTemplate.queryForObject("select max(id) from category", Integer.class);
	}




	/** categoryテーブルのfindAll */
	public List<Category> findAllCategoryTable() {

		String sql = "select * from category order by id;";
		List<Category> categoryList = namedTemplate.query(sql, categoryRowMapper);
		return categoryList;
	}




	/** categoryテーブルの親だけを取ってくる*/
	public List<Category> findFirstCategoryTable() {

		String sql = "select * from category where parent is null order by id";
		List<Category> categoryList = namedTemplate.query(sql, categoryRowMapper);
		return categoryList;
	}


	/** categoryテーブルの子だけ(孫が登録されていない状況で)を取ってくる */
	public List<Category> findSecondCategoryTable() {

		String sql = "select * from category where parent is not null order by id";
		List<Category> categoryList = namedTemplate.query(sql, categoryRowMapper);
		return categoryList;
	}


	/** categoryテーブルにname_allもsaveする */
	//public void saveCategoryNameAll(Category category, int id) {
	public void saveCategoryNameAll(Category category) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(category);

		namedTemplate.update("INSERT INTO category (parent,name,name_all) values (:parent,:name,:nameAll)", param);
		//namedTemplate.update("update set category parent = :parent,name = :name,nameAll = :nameAll where id =" + id, param);
	}

}
