package jp.co.rakus.merucari.service;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.rakus.merucari.domain.Category;
import jp.co.rakus.merucari.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	
	/**
	 * - 検索フォーム用 -
	 * 親カテゴリを返す
	 * 
	 * @return Category
	 * */
	public List<Category> findParent() {
		
		return repository.findParent();
	}
	
	/**
	 * - 検索フォーム用 -
	 * 引数の親idを元に検索して子カテゴリを返す
	 * 	@param int id
	 * @return Category category
	 * */
	public List<Category> findChildByParent(int id) {

		return repository.findChildByParent(id);
	}
	
	/**
	 * category id 取得用
	 * */
	public Integer findIdByNameAll(String nameAll) {
		return repository.findIdByNameAll(nameAll);
	}
	
}
