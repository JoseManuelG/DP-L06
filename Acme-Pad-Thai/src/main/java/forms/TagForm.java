package forms;

import domain.Category;


public class TagForm {
	
	private String text;
	private Category category;
	
//Constructor
	public TagForm(){
		super();
	}
	
//attributes------------
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
}
