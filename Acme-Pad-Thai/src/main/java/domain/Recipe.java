package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
public class Recipe extends DomainEntity {
	//Attributes
	private String ticker;
	private String title;
	private String summary;
	private Date authorMoment;
	private Date lastUpdate;
	private Collection<String> pictures;
	private boolean isCopy;
	private String parentTicker;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp ="^([0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])-[A-Z|a-z][A-Z|a-z][A-Z|a-z][A-Z|a-z]$")
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@NotBlank
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@NotNull	
	@Temporal(TemporalType.DATE)
	@Past
	public Date getAuthorMoment() {
		return authorMoment;
	}
	public void setAuthorMoment(Date authorMoment) {
		this.authorMoment = authorMoment;
	}
	@Temporal(TemporalType.DATE)
	@Past
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	@ElementCollection
	@NotNull
	public Collection<String> getPictures() {
		return pictures;
	}
	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}
	@NotNull
	public boolean getIsCopy() {
		return isCopy;
	}
	
	public void setIsCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}
	@Pattern(regexp ="^$|^([0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])-[A-Z|a-z][A-Z|a-z][A-Z|a-z][A-Z|a-z]$")
	public String getParentTicker() {
		return parentTicker;
	}
	public void setParentTicker(String parentTicker) {
		this.parentTicker = parentTicker;
	}

	//Relationships
	private Collection<Quantity> quantities;
	private Collection<Belongs> belongs;
	private Collection<RecipeHint> recipeHints;
	private Collection<Qualification> qualifications;
	private Collection<Comment> comments;
	private User user;
	private Collection<Step> steps;
	private Collection<Qualified> qualifieds;
	
	@NotNull
	@OneToMany(mappedBy = "recipe",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})
	public Collection<Quantity> getQuantities() {
		return quantities;
	}
	public void setQuantities(Collection<Quantity> quantities) {
		this.quantities = quantities;
	}
	@NotNull
	@OneToMany(mappedBy = "recipe",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})	
	public Collection<Belongs> getBelongs() {
		return belongs;
	}
	public void setBelongs(Collection<Belongs> belongs) {
		this.belongs = belongs;
	}
	@NotNull
	@OneToMany(mappedBy = "recipe",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})	
	public Collection<RecipeHint> getRecipeHints() {
		return recipeHints;
	}
	public void setRecipeHints(Collection<RecipeHint> recipeHints) {
		this.recipeHints = recipeHints;
	}
	@NotNull
	@OneToMany(mappedBy = "recipe",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})	
	public Collection<Qualification> getQualifications() {
		return qualifications;
	}
	public void setQualifications(Collection<Qualification> qualifications) {
		this.qualifications = qualifications;
	}
	@NotNull
	@OneToMany(mappedBy = "recipe",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})	
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	@NotNull
	@ManyToOne(optional = false,cascade={CascadeType.REFRESH})
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@NotNull
	@OneToMany(mappedBy = "recipe",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})	
	public Collection<Step> getSteps() {
		return steps;
	}
	public void setSteps(Collection<Step> steps) {
		this.steps = steps;
	}
	@NotNull
	@OneToMany(mappedBy = "recipe",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})	
	public Collection<Qualified> getQualifieds() {
		return qualifieds;
	}
	public void setQualifieds(Collection<Qualified> qualifieds) {
		this.qualifieds = qualifieds;
	}
}
