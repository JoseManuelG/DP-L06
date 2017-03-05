package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
@Entity
@Access(AccessType.PROPERTY)
public class PropertyValue extends DomainEntity {
	//Atributes
	
	//Relationships
	private Property property;
	private Ingredient ingredient;

	@NotNull
	@ManyToOne(optional = false)
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
	@NotNull
	@ManyToOne(optional = false)
	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

}
