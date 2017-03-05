package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Qualified extends DomainEntity {
	//Attributes
	private Boolean winner;
	
	
	public Boolean getWinner() {
		return winner;
	}
	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
	
	//Relationships
	private Contest contest;
	private Recipe recipe;
	
	@ManyToOne(optional=false)
	@Valid
	public Contest getContest() {
		return contest;
	}
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	@ManyToOne(optional=false,cascade={CascadeType.REMOVE,CascadeType.DETACH,CascadeType.REFRESH})
	@Valid
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
}
