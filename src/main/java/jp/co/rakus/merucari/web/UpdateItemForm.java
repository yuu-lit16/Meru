package jp.co.rakus.merucari.web;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public class UpdateItemForm {
	
	@NotBlank(message="【name】を入力してください")
	private String name;
	@NotBlank(message="【price】を入力してください")
	private String price;
	
	private String parentCategory;
	
	private String childCategory;
	
	@NotBlank(message="【Category】を選択してください")
	@NotNull(message="【Category】を選択してください")
	private String grandChild;
	
	@NotBlank(message="【brand】を入力してください")
	private String brand;
	
	@NotNull(message="【condititon】を選択してください")
	private String condition;
	@NotBlank(message="【description】を入力してください")
	private String description;
	
	private String updateItemId;
	
	public String getUpdateItemId() {
		return updateItemId;
	}
	public void setUpdateItemId(String updateItemId) {
		this.updateItemId = updateItemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}
	public String getChildCategory() {
		return childCategory;
	}
	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}
	public String getGrandChild() {
		return grandChild;
	}
	public void setGrandChild(String grandChild) {
		this.grandChild = grandChild;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
