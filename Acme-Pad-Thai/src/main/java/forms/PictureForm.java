package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.Recipe;


public class PictureForm {
	
	private String text;
	private Recipe recipe;
	
//Constructor
	public PictureForm(){
		super();
	}
	
//attributes------------
	@NotBlank
	@URL
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	
	
}
