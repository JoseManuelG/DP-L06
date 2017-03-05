package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity{
	//Attributes--------------------------
	private String name;
	private String description;
	private String picture;
	private Collection<String> tags;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@URL
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	

	@ElementCollection
	public Collection<String> getTags() {
		return tags;
	}
	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}
	//Relationships-----------------------------------------
	private Category parentCategory;
	private Collection<Category> subCategory;
	private Collection<Belongs> Belongs;
	
	@ManyToOne(optional=true, cascade={CascadeType.REFRESH})
	public Category getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}
	
	@NotNull
	@OneToMany(mappedBy = "parentCategory",cascade={CascadeType.REFRESH})
	public Collection<Category> getSubCategory() {
		return subCategory;
		
	}
	public void setSubCategory(Collection<Category> SubCategory) {
		this.subCategory = SubCategory;
	}

	
	@NotNull
	@OneToMany(mappedBy = "category")
	public Collection<Belongs> getBelongs() {
		return Belongs;
	}
	public void setBelongs(Collection<Belongs> belongs) {
		Belongs = belongs;
	}
	public void setBelongs(Belongs belongs) {
		Belongs.add(belongs);
	}
	

}
