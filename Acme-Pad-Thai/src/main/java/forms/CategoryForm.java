package forms;

import domain.Category;


public class CategoryForm {
	
	private int categoryId;
	
	private Category category;
//Constructor
	public CategoryForm(){
		super();
	}
	
//attributes------------
	
	
	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
}
