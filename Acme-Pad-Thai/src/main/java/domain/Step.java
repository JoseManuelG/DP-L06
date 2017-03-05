package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
@Entity
@Access(AccessType.PROPERTY)
public class Step extends DomainEntity{
	//Attributes-----------------------------
		private String description;
		private Integer number;
		private String picture;
		
		@NotBlank
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		@URL
		public String getPicture() {
			return picture;
		}
		public void setPicture(String picture) {
			this.picture = picture;
		}

		//Relationships--------------------------
		private Collection<StepHint> stepHints;
		private Recipe recipe;
		
		@OneToMany(mappedBy = "step",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})
		@NotNull
		public Collection<StepHint> getStepHints() {
			return stepHints;
		}
		public void setStepHints(Collection<StepHint> stepHints) {
			this.stepHints = stepHints;
		}
		public void setStepHint(StepHint stepHint){
			this.stepHints.add(stepHint);
		}
		
		@Valid
		@NotNull
		@ManyToOne(optional= false)
		public Recipe getRecipe() {
			return recipe;
		}
		public void setRecipe(Recipe recipes) {
			this.recipe = recipes;
		
		}
		
}
