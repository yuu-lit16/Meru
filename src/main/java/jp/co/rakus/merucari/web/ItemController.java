package jp.co.rakus.merucari.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.rakus.merucari.domain.Category;
import jp.co.rakus.merucari.domain.Item;
import jp.co.rakus.merucari.service.CategoryService;
import jp.co.rakus.merucari.service.ItemService;

@Controller
@EnableAutoConfiguration
public class ItemController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	HttpSession session;

	/** 【ajax】最初にアクセスされた時にItemをJSONで返す */
	@RequestMapping(value = "/getJsonOfIndexItemList")
	@ResponseBody
	public List<Item> sendJsonIndex() {
		return itemService.findTo30();
	}
	
	/** 【ajax】最初にアクセスされた時に * 親Category * をJSONで返す */
	@RequestMapping(value = "/getJsonOfParentCategory")
	@ResponseBody
	public List<Category> sendCategoryIndex() {
		return categoryService.findParent();
	}
	
	/** 【ajax】検索機能 - 親カテゴリーのidを元に子カテゴリーを返す - */
	@RequestMapping(value = "/getJsonOfChildCategory")
	@ResponseBody
	public List<Category> getJsonOfChildCategory(@RequestParam String parentId) {
		return categoryService.findChildByParent(Integer.parseInt(parentId));
	}
	
	/** 【ajax】検索機能 - 子カテゴリーのidを元に孫カテゴリーを返す - */
	@RequestMapping(value = "/getJsonOfGrandChildCategory")
	@ResponseBody
	public List<Category> getJsonOfGrandChildCategory(@RequestParam String parentId) {
		return categoryService.findChildByParent(Integer.parseInt(parentId));
	}
	
	/** 【ajax】検索機能 - 孫カテゴリーのidを元にブランドを返す - */
	@RequestMapping(value = "/getJsonOfBrand")
	@ResponseBody
	public List<Item> getJsonOfBrand(@RequestParam String parentId) {
		return itemService.findBrandByParentDistinct(Integer.parseInt(parentId));
	}
	
	/** 【ajax】検索機能 - 孫idとブランド名で検索した結果のItemListを返す */
	@RequestMapping(value = "/getItemOfSerched")
	@ResponseBody
	public List<Item> getItemOfSerched(
			@RequestParam String selectGrandChildValue,
			@RequestParam String selectBrandValue
			) {

		List<Item> itemList = itemService.findSearchedItem(
				Integer.parseInt(selectGrandChildValue), 
				selectBrandValue
				);
		
		return itemList;
	}
	
	/** 【ajax】検索機能 - 孫idとブランド名と名前で検索した結果のItemListを返す */
	@RequestMapping(value = "/getItemOfSerchedExistName")
	@ResponseBody
	public List<Item> getItemOfSerched(
			@RequestParam String selectNameValue,
			@RequestParam String selectGrandChildValue,
			@RequestParam String selectBrandValue
			) {
		
				List<Item> itemList = itemService.findSearchedItemExistName(
				Integer.parseInt(selectGrandChildValue), 
				selectBrandValue,
				selectNameValue
				);
		
		return itemList;
	}
	
	/** 【ajax】検索機能 - 名前だけで検索した結果のItemListを返す */
	@RequestMapping(value = "/getItemOfSerchedOnlyName")
	@ResponseBody
	public List<Item> getItemOfSerched(
			@RequestParam String selectNameValue
			) {
		
		List<Item> itemList = itemService.findSearchedItemOnlyName(selectNameValue);
		
		return itemList;
	}


	
	/** 【ajax】ページネーション機能 - DBのItem総数を返す */
	@RequestMapping(value = "/getTotalItem")
	@ResponseBody
	public Integer getTotalItem() {
		return itemService.getNumOfTotalData();
	}

	/** 【ajax】ページネーション機能 - ブラウザからクリックされたページを受け取り、そこで表示するItemListを返す */
	@RequestMapping(value = "/paging")
	@ResponseBody
	public List<Item> clickPagingNum(@RequestParam String counter) {

		int limit = 30;
		int offset = 0;

		try {
			offset = limit * (Integer.parseInt(counter) - 1);
		} catch (Exception e) {
			offset = 0;
		}

		List<Item> itemList = itemService.findByLimitAndOffset(limit, offset);

		return itemList;
	}
	
	/** 【ajax】ページネーション機能 - ブラウザからクリックされたページを受け取り、そこで表示するItemListを返す */
	@RequestMapping(value = "/selectPaging")
	@ResponseBody
	public List<Item> selectPaging(@RequestParam String selectPageNum) {

		int limit = 30;
		int offset = 0;

		try {
			offset = limit * (Integer.parseInt(selectPageNum) - 1);
		} catch (Exception e) {
			offset = 0;
		}

		List<Item> itemList = itemService.findByLimitAndOffset(limit, offset);

		return itemList;
	}

	
	/** 一覧リストを表示する */
	@RequestMapping("/")
	public String index() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// user 情報を取得
		String username = "";
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		session.setAttribute("userName", username);
		
		
		return "list";
	}
	
	
	/** 商品詳細画面を表示する */
	@RequestMapping("/detail")
	public String displayDetail(@RequestParam String id , Model model) {
		
		Item item = itemService.findById(Integer.parseInt(id));
		model.addAttribute("itemList",item);
		
		return "detail";
	}

}
