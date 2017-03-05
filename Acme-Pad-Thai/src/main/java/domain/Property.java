package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
public class Property extends DomainEntity {
	//Attributes
	private String name;

	@NotBlank
	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//Relationships
	private Collection<PropertyValue> propertyValues;

	@OneToMany(mappedBy = "property")	
	public Collection<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(Collection<PropertyValue> propertyValues) {
		this.propertyValues = propertyValues;
	}
	

}
