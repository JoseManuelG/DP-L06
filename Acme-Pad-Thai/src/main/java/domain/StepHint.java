package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class StepHint extends DomainEntity {
	//Attributes-----------------------
	private String hint;
	@NotBlank
	public String getHint() {
		return hint;
	}
	
	public void setHint(String hint) {
		this.hint= hint;
	}
	
	//Relationships--------------------

	//private Recipe recipe;
	private Step step;

//	@Valid
//	@ManyToOne(optional=false)
//	public Recipe getRecipe() {
//		return recipe;
//	}
//
//	public void setRecipe(Recipe recipe) {
//		this.recipe = recipe;
//	}
	
	@Valid
	@ManyToOne(optional=false)
	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

}
