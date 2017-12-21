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
import jp.co.rakus.merucari.domain.Item;

@Repository
@Transactional
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedjdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** Item型のRowMapper */
	private static final RowMapper<Item> RowMapper = (rs, i) -> {
		Item item = new Item();

		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		// item.setCategory(rs.getInt("category"));
		item.setCategory(rs.getString("category"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getDouble("price"));
		item.setDescription(rs.getString("description"));

		return item;
	};

	/**
	 * DBに登録されているItemsのidの最大値を取得する
	 * 
	 * @return maxId
	 */
	public Integer getMaxId() {
		SqlParameterSource param = new MapSqlParameterSource();
		Integer maxId = namedjdbcTemplate.queryForObject("SELECT max(id) from items", param, Integer.class);
		return maxId;
	}

	/**
	 * 引数のitemを追加
	 * 
	 * @param Item
	 */
	public void save(Item item) {

		String sql = "insert into items(id,name,condition,category,brand,price,shipping,description)"
				+ " values(:id,:name,:condition,:categoryId,:brand,:price,:shipping,:description)";

		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", item.getId())
				.addValue("name", item.getName())
				.addValue("condition", item.getCondition())
				.addValue("categoryId", item.getCategoryId())
				.addValue("brand", item.getBrand())
				.addValue("price", item.getPrice())
				.addValue("shipping", item.getShipping())
				.addValue("description", item.getDescription());

		namedjdbcTemplate.update(sql, param);

	}
	
	
	/**
	 * Update用
	 * 引数のitemで既存データを書き換え
	 * 
	 * @param Item
	 */
	public void update(Item item) {

		String sql = "update items set name = :name, condition = :condition, category= :category, brand = :brand, price = :price, description = :description where id = :id";

		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", item.getId())
				.addValue("name", item.getName())
				.addValue("condition", item.getCondition())
				.addValue("category", item.getCategoryId())
				.addValue("brand", item.getBrand())
				.addValue("price", item.getPrice())
				//.addValue("shipping", item.getShipping())
				.addValue("description", item.getDescription());

		namedjdbcTemplate.update(sql, param);

	}
	
	
	

	/**
	 * test用 itemテーブルの頭から30件取得
	 */
	public List<Item> findTo30() {

		String sql = "select items.id,items.name,items.condition,category.name_all as \"category\",items.brand,items.price,items.shipping,items.description"
				+ " from items left outer join category on items.category = category.id order by items.id limit  30";

		List<Item> itemList = namedjdbcTemplate.query(sql, RowMapper);

		return itemList;
	}

	/**
	 * Itemテーブルのデータ総数を返す
	 * 
	 * @return int count(*)で取得した総数
	 * 
	 */
	public Integer getNumOfTotalData() {

		int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM items", Integer.class);

		return count;
	}

	/**
	 * ページネーション時にselectするメソッド
	 * 
	 * @param Limit
	 *            , Offset
	 * 
	 * @return Item
	 * 
	 */
	public List<Item> findByLimitAndOffset(int limit, int offset) {

		String sql = "select items.id,items.name,items.condition,category.name_all as \"category\",items.brand,items.price,items.shipping,items.description"
				+ " from items left outer join category on items.category = category.id order by items.id limit :limit offset :offset";

		SqlParameterSource param = new MapSqlParameterSource().addValue("limit", limit).addValue("offset", offset);

		List<Item> itemList = namedjdbcTemplate.query(sql, param, RowMapper);

		return itemList;

	}

	/**
	 * 商品詳細ページ用 引数のidの商品をselect
	 * 
	 * @param int
	 *            itemId
	 * @return Item item
	 * 
	 */
	public Item findById(int itemId) {

		String sql = "select items.id,items.name,items.condition,category.name_all as \"category\",items.brand,items.price,items.shipping,items.description"
				+ " from items left outer join category on items.category = category.id where items.id = :itemId";

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);

		Item item = namedjdbcTemplate.queryForObject(sql, param, RowMapper);

		return item;

	}

	/**
	 * - 検索フォーム用 - 引数の孫idを元に検索してブランドを返す
	 * 
	 * @param int
	 *            id
	 * @return Item item
	 */
	public List<Item> findBrandByParent(int id) {

		String sql = "select * from items where category = :id order by id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		List<Item> itemList = namedjdbcTemplate.query(sql, param, RowMapper);

		return itemList;
	}

	/**
	 * - 検索フォーム用 - 引数の孫idとブランド名を元に検索してヒットしたItemを返す
	 * 
	 * @param int
	 *            id
	 * @param String
	 *            brandName
	 * 
	 * @return Item item
	 * 
	 */
	public List<Item> findSearchedItem(int grandChildId, String brandName) {

		String sql = "select items.id,items.name,items.condition,category.name_all as \"category\",items.brand,items.price,items.shipping,items.description"
				+ " from items left outer join category on items.category = category.id  where items.category = :grandChildId and brand = :brandName order by items.id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("grandChildId", grandChildId)
				.addValue("brandName", brandName);

		List<Item> itemList = namedjdbcTemplate.query(sql, param, RowMapper);

		return itemList;
	}
	
	
	/**
	 * - 検索フォーム用 - 引数の名前と孫idとブランド名を元に検索してヒットしたItemを返す
	 * 
	 * @param int
	 *            id
	 * @param String
	 *            brandName
	 * @param String
	 * 				name
	 * 
	 * @return Item item
	 * 
	 */
	public List<Item> findSearchedItemExistName(int grandChildId, String brandName,String name) {

		String sql = "select items.id,items.name,items.condition,category.name_all as \"category\",items.brand,items.price,items.shipping,items.description"
				+ " from items left outer join category on items.category = category.id  where items.category = :grandChildId and brand = :brandName and items.name like :name order by items.id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("grandChildId", grandChildId)
				.addValue("brandName", brandName).addValue("name", "%" + name + "%");

		List<Item> itemList = namedjdbcTemplate.query(sql, param, RowMapper);

		return itemList;
	}
	
	
	/**
	 * - 検索フォーム用 - 引数の名前で検索してヒットしたItemを返す
	 * 
	 *@param String
	 * 				name
	 * 
	 * @return Item item
	 * 
	 */
	public List<Item> findSearchedItemOnlyName(String name) {

		String sql = "select items.id,items.name,items.condition,category.name_all as \"category\",items.brand,items.price,items.shipping,items.description"
				+ " from items left outer join category on items.category = category.id  where items.name like :name order by items.id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");

		List<Item> itemList = namedjdbcTemplate.query(sql, param, RowMapper);

		return itemList;
	}


}
