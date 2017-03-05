package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
	
	//Find a collection of properties from a given ingredient
	@Query("select distinct pv.property from Ingredient i join i.propertyValues pv where i.id=?1")
	Collection<Property> findByIngredientId(int ingredientId);
	
	//Find a property from a given propertyValue
	@Query("select pv.property from PropertyValue pv where pv.id=?1")
	public Property findPropertyByPropertyValue(int propertyValueId);
	
	@Query("select p from Property p where p.id not in (select pv.property from PropertyValue pv where pv.ingredient.id =?1)")
	public Collection<Property> findPropertiesNotInIngredient(int ingredientId);

}
