package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PropertyValueRepository;
import domain.Ingredient;
import domain.Property;
import domain.PropertyValue;

@Service
@Transactional
public class PropertyValueService {
	
	
	//Managed Repository-----------------------------
	
	@Autowired
	private PropertyValueRepository propertyValueRepository;
	
	//Supporting services-----------------------------
	
	//Constructors------------------------------------
	
	public PropertyValueService(){
		super();
	}
	
	//Simple CRUD methods----------------------------
	
	public PropertyValue create() {
		PropertyValue result;

		result = new PropertyValue();

		return result;
	}


	public Collection<PropertyValue> findAll() {
		Collection<PropertyValue> result;

		result = propertyValueRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public PropertyValue findOne(int propertyValueId) {
		PropertyValue result;

		result = propertyValueRepository.findOne(propertyValueId);
		Assert.notNull(result);

		return result;
	}

	public PropertyValue save(PropertyValue propertyValue) {
		Assert.notNull(propertyValue,"El objeto no puede ser nulo");
		Assert.notNull(propertyValue.getIngredient(),"El ingrediente no puede ser nulo");
		Assert.notNull(propertyValue.getProperty(),"La propiedad no puede ser nula");
		Assert.isTrue(!(findByIngredientAndProperty(propertyValue.getIngredient(), propertyValue.getProperty())),"Un ingrediente no puede tener dos veces la misma propiedad");
		
		PropertyValue result;

		result = propertyValueRepository.save(propertyValue);
		
		return result;
	}
	
	public Collection<PropertyValue> save(Collection<PropertyValue> propertyValues){
		Assert.notNull(propertyValues);
		Collection<PropertyValue> result=new ArrayList<PropertyValue>();
		for(PropertyValue pv : propertyValues){
			result.add(save(pv));
		}
		return result;
	}

	public void delete(PropertyValue propertyValue) {
		Assert.notNull(propertyValue,"El objeto no puede ser nulo");
		Assert.isTrue(propertyValue.getId() != 0,"El objeto debe estar antes en la base de datos");

		propertyValueRepository.delete(propertyValue);
	}
	
	
	//Other bussiness methods------------------------
	

	
	public boolean existsPropertyValueForProperty(Property property) {
		boolean result;
		Collection<PropertyValue> propertyValues;
		
		propertyValues = propertyValueRepository.findByPropertyId(property.getId()); 
		result = !propertyValues.isEmpty();
		
		return result;
	}
	
	public boolean findByIngredientAndProperty(Ingredient ingredient, Property property){
		boolean result=false;
		Assert.notNull(ingredient,"El ingrediente no puede ser nulo");
		Assert.notNull(property,"La propiedad no puede ser nula");
		result=(propertyValueRepository.findByIngredientIdAndPropertyId(ingredient.getId(), property.getId()))!=null;
		return result;
	}
	
//	public Collection<PropertyValue> copyPropertyValuesForCopyOfRecipe(Ingredient ingredient){
//		Collection<PropertyValue> propertyValues = propertyValueRepository.findPropertyValueByIngredient(ingredient.getId());
//		Collection<PropertyValue> result = new LinkedList<PropertyValue>(propertyValues);
//		for(PropertyValue pv : result){
//			pv.setIngredient(ingredient);
//		}
//		return result;
//	}
//
//	public Collection<PropertyValue> copyPropertyValuesForCopyOfRecipe(Collection<Ingredient> ingredients){
//		Collection<PropertyValue> result = new ArrayList<PropertyValue>();
//		for(Ingredient i: ingredients){
//			result.addAll(copyPropertyValuesForCopyOfRecipe(i));
//		}
//		return result;
//	}
//	


}
