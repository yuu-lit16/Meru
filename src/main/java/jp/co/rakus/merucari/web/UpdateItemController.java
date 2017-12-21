package jp.co.rakus.merucari.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jp.co.rakus.merucari.domain.Item;
import jp.co.rakus.merucari.service.ItemService;

@Controller
@EnableAutoConfiguration
public class UpdateItemController {

	@Autowired
	private ItemService itemService;


	/** Formをセット（商品追加用） */
	@ModelAttribute
	public UpdateItemForm setUpForm() {
		return new UpdateItemForm();
	}

	/** 商品追加 - 商品追加画面を表示する - */
	@RequestMapping("/addItem")
	public String displaryAddItem() {
		return "add";
	}

	/** 商品追加 - Formから受け取ったItemをsaveする - */
	@RequestMapping("/executeAddItem")
	public String saveAddItem(@Validated UpdateItemForm form, BindingResult result) {

		if (result.hasErrors()) {
			return "add";
		}

		Item item = new Item();
		item.setName(form.getName());
		item.setPrice(Integer.parseInt(form.getPrice()));
		item.setCategoryId(Integer.parseInt(form.getGrandChild()));
		item.setBrand(form.getBrand());
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setDescription(form.getDescription());

		itemService.save(item);

		return "list";
	}

	/** 商品編集 - 商品編集画面を表示する - */
	@RequestMapping("/editItem")
	public String displayUpdateItem(@RequestParam String id, Model model) {

		Item item = itemService.findById(Integer.parseInt(id));
		UpdateItemForm form = new UpdateItemForm();

		form.setUpdateItemId(id);
		form.setName(item.getName());
		form.setPrice(String.valueOf(item.getPrice()));
		form.setBrand(item.getBrand());
		form.setDescription(item.getDescription());
		form.setCondition(String.valueOf(item.getCondition()));

		model.addAttribute("updateItemForm", form);
		model.addAttribute("id",id);

		return "edit";
	}

	/** 商品編集 - Formから受け取ったItemで既存商品をupdateする - */
	@RequestMapping("/executeEditItem")
	public String updateEditItem(@Validated UpdateItemForm form, BindingResult result) {

		if (result.hasErrors()) {
			return "edit";
		}

		Item item = new Item();
		item.setId(Integer.parseInt(form.getUpdateItemId()));
		item.setName(form.getName());
		item.setPrice(Double.parseDouble(form.getPrice()));
		item.setCategoryId(Integer.parseInt(form.getGrandChild()));
		item.setBrand(form.getBrand());
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setDescription(form.getDescription());

		itemService.update(item);

		return "list";
	}

}
