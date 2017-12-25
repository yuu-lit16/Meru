package jp.co.rakus.merucari.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import jp.co.rakus.merucari.domain.Item;
import jp.co.rakus.merucari.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository repository;

	/**
	 * ItemRepositoryのfindBrandByParentから null と 重複を弾く
	 * 
	 * */
	public List<Item> findBrandByParentDistinct(int id) {

		List<Item> itemList = repository.findBrandByParent(id);
		
		// 重複を弾く
		List<Item> itemListDistinct = new ArrayList<Item>(new LinkedHashSet<>(itemList));
		
		// nullを弾く
		List<Item> itemListDistinctAndNotNull = new ArrayList<Item>();
		for(Item item : itemListDistinct) {
			if (item.getBrand() != null) {
				itemListDistinctAndNotNull.add(item);
			}
		}
		return itemListDistinctAndNotNull;
	}
	
	
	/**
	 * brandをString型のListで返す
	 * 
	 * */
	public List<String> findBrandOfStringByParent(int id) {
		return repository.findBrandOfStringByParent(id);
	}
	
	
	
	/**
	 * DBに登録されているItemsのidの最大値を取得する
	 * 
	 * @return maxId
	 */
	public Integer getMaxId() {
		return repository.getMaxId();
	}

	/**
	 * 引数のitemを追加
	 * (idとshippingはで初期値が id = maxId +1 , sihpping = 0)
	 * 
	 * @param Item
	 */
	public void save(Item item) {

		item.setId(getMaxId() + 1);
		item.setShipping(0);

		repository.save(item);
	}
	
	
	/**
	 * Update用
	 * 引数のitemで既存データを書き換え
	 * 
	 * @param Item
	 */
	public void update(Item item) {
		repository.update(item);
	}

	/**
	 * test用 itemテーブルの頭から30件取得
	 */
	public List<Item> findTo30() {
		return repository.findTo30();
	}

	/**
	 * Itemテーブルのデータ総数を返す
	 * 
	 * @return int count(*)で取得した総数
	 * 
	 */
	public Integer getNumOfTotalData() {
		return repository.getNumOfTotalData();
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
		return repository.findByLimitAndOffset(limit, offset);
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
		return repository.findById(itemId);
	}

	/**
	 * - 検索フォーム用 - 引数の孫idを元に検索してブランドを返す
	 * 
	 * @param int
	 *            id
	 * @return Item item
	 */
	public List<Item> findBrandByParent(int id) {
		return repository.findBrandByParent(id);
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
		return repository.findSearchedItem(grandChildId, brandName);
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
		return repository.findSearchedItemExistName(grandChildId, brandName, name);
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
		
		String[] arrayNameList = name.replaceAll("　", " ").split("[\\s]",0);
		String addName = " and items.name like :name";
		String createdAddName = "";
		
		for (int i = 1; i < arrayNameList.length; i++) {
			createdAddName += addName +String.valueOf(i) + " ";
		}
		
		String sql = 
				"select items.id,items.name,items.condition,category.name_all as \"category\",items.brand,items.price,items.shipping,items.description"
				+ " from items left outer join category on items.category = category.id  where items.name like :name"
				+ createdAddName
				+ " order by items.id";

		return repository.findSearchedItemOnlyName(sql,arrayNameList);
	}
}