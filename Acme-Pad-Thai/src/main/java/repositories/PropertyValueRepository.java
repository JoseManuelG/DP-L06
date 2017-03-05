package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PropertyValue;

@Repository
public interface PropertyValueRepository extends JpaRepository<PropertyValue, Integer> {
	
	//Return all the property values for a property id
	@Query("select pv from PropertyValue pv where pv.property.id = ?1")
	Collection<PropertyValue> findByPropertyId(int propertyId);
	
	//Finds a unique property value from a given ingredient and a property
	@Query("select pv from PropertyValue pv where pv.ingredient.id = ?1 and pv.property.id = ?2")
	PropertyValue findByIngredientIdAndPropertyId(int ingredientId, int PropertyId);
	
	//Select all the property values from a given ingredient
	@Query("select pv from PropertyValue pv where pv.ingredient.id=?1")
	public Collection<PropertyValue> findPropertyValueByIngredient(int ingredientId);

}
