package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
@Entity
@Access(AccessType.PROPERTY)
public class Ingredient extends DomainEntity {
	//Attributes
	private String name;
	private String description;
	private String picture;
	
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

	//Relationships
	private Collection<PropertyValue> propertyValues;
	private Collection<Quantity> quantities;
	
	@OneToMany(mappedBy = "ingredient", cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})	
	public Collection<PropertyValue> getPropertyValues() {
		return propertyValues;
	}
	public void setPropertyValues(Collection<PropertyValue> propertyValues) {
		this.propertyValues = propertyValues;
	}
	@OneToMany(mappedBy = "ingredient")
	public Collection<Quantity> getQuantities() {
		return quantities;
	}
	public void setQuantities(Collection<Quantity> quantities) {
		this.quantities = quantities;
	}
}
