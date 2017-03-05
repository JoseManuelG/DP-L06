package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Entity
@Access(AccessType.PROPERTY)
public class Quantity extends DomainEntity {
	//Attributes
	private String unit;
	private Double value;
	
	@NotNull
	@Pattern(regexp = "^grams$|^kilograms$|^ounces$|^pounds$|^millilitres$|^litres$|^spoons$|^cups$|^pieces$")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@NotNull
	@Min((long) 1.0)
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}


	//Relationships
	private Ingredient ingredient;
	private Recipe recipe;
	
	@NotNull
	@ManyToOne(optional = false)
	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	@NotNull
	@ManyToOne(optional = false)
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
