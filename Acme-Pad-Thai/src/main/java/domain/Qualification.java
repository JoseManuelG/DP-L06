package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
@Entity
@Access(AccessType.PROPERTY)
public class Qualification extends DomainEntity {
	
	//Attributes
	private boolean opinion;
	

	@NotNull
	public boolean getOpinion() {
		return opinion;
	}
	public void setOpinion(boolean opinion) {
		this.opinion = opinion;
	}
	
	//Relationships
	private Customer customer;
	private Recipe recipe;
	
	@NotNull
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
