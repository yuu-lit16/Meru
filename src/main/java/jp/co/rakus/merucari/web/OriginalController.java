package jp.co.rakus.merucari.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.merucari.domain.Category;
import jp.co.rakus.merucari.repository.OriginalRepository;
import jp.co.rakus.merucari.service.OriginalService;

@Controller
@RequestMapping(value = "/")
public class OriginalController {

	@Autowired
	private OriginalService service;

	@Autowired
	private OriginalRepository repository;

	/** categoryテーブル登録処理 */
	@RequestMapping(value = "/saveCategoryTable")
	public String createFirst() {

		// categoryNameの全件取得
		List<String> categoryNameList = service.findAllDistinct();

		/** 1. 一番目を取得する */
		// String[] categoryName = null;

		List<String> firstList = new ArrayList<>();

		// List<String> secondNameList = new ArrayList<>();

		for (int i = 0; i < categoryNameList.size(); i++) {
			try {
				String[] categoryName = categoryNameList.get(i).split("/", 0);
				firstList.add(categoryName[0]);
			} catch (Exception e) {
			}
		}

		// 重複を弾く
		List<String> firstListDistinct = new ArrayList<String>(new HashSet<>(firstList));

		// 1stをsaveする -------------------------------------------------

		Category category1 = new Category();

		for (int i = 0; i < firstListDistinct.size(); i++) {
			category1.setName(firstListDistinct.get(i));
			/** -------------------- save処理 ----------------------- */
			//repository.saveCategory1st(category1);
			/** -------------------- save処理 ----------------------- */
		}
		// -------------------------------------------------------

		/** 2. 2番目を取得する */

		// 本来の処理
		// List<Category> category1stList = repository.findAllCategoryTable();

		// 仮置きの処理
		List<Category> category1stList = repository.findFirstCategoryTable();

		for (int i = 0; i < category1stList.size(); i++) {
			System.out.println(i + "番目のループを実行中 (second登録処理)");

			List<String> secondNameList = new ArrayList<>();

			for (int j = 0; j < categoryNameList.size(); j++) {

				try {
					String[] categoryAllName = categoryNameList.get(j).split("/", 0);

					if (categoryAllName[0].equals(category1stList.get(i).getName())) {
						secondNameList.add(categoryAllName[1]);
					}

				} catch (Exception e) {
				}
			}
			// 重複を弾く
			List<String> secondNameListDistinct = new ArrayList<String>(new HashSet<>(secondNameList));

			for (int k = 0; k < secondNameListDistinct.size(); k++) {

				Category category = new Category();
				category.setName(secondNameListDistinct.get(k));
				category.setParent(category1stList.get(i).getId());

				/** category2 を save --------------------------------- */
				//repository.saveCategorySecond(category);
				/** ----------------------------------------------------- */


			}
		}

		/** 3. 3番目を登録する */
		// 親だけのリスト
		List<Category> firstCategoryList = repository.findFirstCategoryTable();

		// 子だけのリスト
		List<Category> secondCategoryList = repository.findSecondCategoryTable();

		for (Category firstCategory : firstCategoryList) {
			System.out.println("ループを実行中 (thrid登録処理)");
			List<Category> secondCategoryObjectList = new ArrayList<>();
			for (Category secondCategory : secondCategoryList) {
				if (firstCategory.getId() == secondCategory.getParent()) {
					secondCategoryObjectList.add(secondCategory);
				}
			}
			// List<String> thirdCategoryNameList = new ArrayList<>();
			for (Category secondCategoryObject : secondCategoryObjectList) {
				List<String> thirdCategoryNameList = new ArrayList<>();
				for (String categoryName : categoryNameList) {
					try {
						String[] categoryAllName = categoryName.split("/");
						if (categoryAllName[0].equals(firstCategory.getName())) {
							if (categoryAllName[1].equals(secondCategoryObject.getName())) {
								thirdCategoryNameList.add(categoryAllName[2]);
							}
						}
					} catch (NullPointerException e) {
					}
				}
				List<String> thirdNameListDistinct = new ArrayList<>(new LinkedHashSet<>(thirdCategoryNameList));
				for (String thirdCategoryName : thirdNameListDistinct) {
					Category category = new Category();
					category.setName(thirdCategoryName);
					category.setParent(secondCategoryObject.getId());
					
					// nameAllも追加 -----------------------------------------------------------------------------------------------------------
					category.setNameAll(firstCategory.getName() +  "/" + secondCategoryObject.getName() + "/" + thirdCategoryName);
					// ---------------------------------------------------------------------------------------------------------------------------
					
					/** save 処理 ---------------------------------- */
					// repository.saveCategorySecond(category);    --- カラム nameAll 追加前   
					// repository.saveCategoryNameAll(category);   --- 追加後
 					/** ------------------------------------------- */
					
				}
			}

		}
		// for (int i = 0; i < firstCategoryList.size(); i++) {
		//
		// // コピペ開始
		//
		// System.out.println(i + "番目のループを実行中 (thrid登録処理)");
		//
		// List<Category> secondCategoryObjectList = new ArrayList<Category>();
		//
		// // for 追加
		// for (int a = 0; a < secondCategoryList.size(); a++) {
		//
		// if (firstCategoryList.get(i).getId() ==
		// secondCategoryList.get(a).getParent()) {
		// secondCategoryObjectList.add(secondCategoryList.get(a));
		// System.out.println("secondCategoryObjectList"+
		// secondCategoryList.get(a).getName());
		// }
		// }
		//
		// for (int j = 0; j < secondCategoryObjectList.size(); j++) {
		//
		// List<String> thirdCategoryNameList = new ArrayList<>();
		//
		// // child List
		// for (int b = 0; b < categoryNameList.size(); b++) {
		//
		// try {
		// String[] categoryAllName = categoryNameList.get(j).split("/");
		//
		// if (categoryAllName[0].equals(firstCategoryList.get(i).getName())) {
		// //&& categoryAllName[1].equals(secondCategoryObjectList.get(j).getName())) {
		// if (categoryAllName[1].equals(secondCategoryObjectList.get(j).getName())) {
		// thirdCategoryNameList.add(categoryAllName[2]);
		// }
		// }
		//
		// } catch (Exception e) {
		// }
		//
		// }
		// System.out.println();
		//
		// // 重複を弾く
		// List<String> thirdNameListDistinct = new ArrayList<String>(new
		// HashSet<>(thirdCategoryNameList));
		//
		// for (int k = 0; k < thirdNameListDistinct.size(); k++) {
		//
		// Category category = new Category();
		// category.setName(thirdNameListDistinct.get(k));
		// category.setParent(secondCategoryObjectList.get(j).getId());
		//
		// /** category3 を save --------------------------------- */
		// repository.saveCategorySecond(category);
		// System.out.println("saveするcategory = " + category);
		// /** ----------------------------------------------------- */
		//
		// }
		// }
		return "index";
	}

}