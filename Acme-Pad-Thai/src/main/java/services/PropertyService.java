package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PropertyRepository;
import domain.Ingredient;
import domain.Property;
import domain.PropertyValue;

@Service
@Transactional
public class PropertyService {
	
	//Managed Repository-----------------------------
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	//Supporting services-----------------------------
	
	@Autowired
	private PropertyValueService propertyValueService;
	
	
	//Constructors------------------------------------
	
	public PropertyService(){
		super();
	}
	
	//Simple CRUD methods----------------------------
	
	public Property create() {
		Property result;

		result = new Property();
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		result.setPropertyValues(propertyValues);
		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;

		result = propertyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Property findOne(int propertyId) {
		Property result;

		result = propertyRepository.findOne(propertyId);
		Assert.notNull(result);

		return result;
	}

	public Property save(Property property) {
		Assert.notNull(property,"La propiedad no puede ser nula");
		Assert.hasText(property.getName(),"El nombre de la propiedad no puede ser nulo, estar vacío o tener solo espacios");
		
		Property result;

		result = propertyRepository.save(property);
		
		return result;
	}
	
	public Collection<Property> save(Collection<Property> properties){
		Assert.notNull(properties,"La colección de propiedades no puede ser nula");
		Collection<Property> result=new ArrayList<Property>();
		for(Property p : properties){
			result.add(save(p));
		}
		return result;
	}

	public void delete(Property property) {
		Assert.notNull(property,"La propiedad no puede ser nula");
		Assert.isTrue(property.getId() != 0,"El id de la propiedad no puede ser 0");
		
		Assert.isTrue(propertyRepository.exists(property.getId()),"La propiedad debe existir para poder borrarse de la bd");
		Assert.isTrue(!propertyValueService.existsPropertyValueForProperty(property),"La propiedad no puede estar relacionada con un ingrediente");
		
		propertyRepository.delete(property);
	}
	
	
	//Other bussiness methods------------------------
	
	public Collection<Property> findPropertiesNotInIngredient(Ingredient ingredient){
		Collection<Property> result = new ArrayList<Property>();
		result = propertyRepository.findPropertiesNotInIngredient(ingredient.getId());
		return result;
	}

}
