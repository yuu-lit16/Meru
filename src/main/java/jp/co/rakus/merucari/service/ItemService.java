package jp.co.rakus.merucari.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.merucari.domain.Item;
import jp.co.rakus.merucari.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository repository;
	
	/**
	 * ItemRepositoryのfindBrandByParentから null と 重複を弾いたメソッド
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
	
}
