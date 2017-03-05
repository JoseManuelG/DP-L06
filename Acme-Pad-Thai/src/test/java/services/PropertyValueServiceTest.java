package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Ingredient;
import domain.Property;
import domain.PropertyValue;
import domain.Quantity;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class PropertyValueServiceTest extends AbstractTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//Services under test-----------------------------------------
	
	@Autowired
	private PropertyValueService propertyValueService;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private IngredientService ingredientService;
	
	//Tests-------------------------------------------------------
	
	@Test
	public void testCreate(){
		PropertyValue propertyValue;
		propertyValue = propertyValueService.create();
		Assert.notNull(propertyValue);
	}
	
	@Test
	public void testSave(){
		PropertyValue propertyValue = propertyValueService.create();
		
		Property property=propertyService.create();
		property.setName("nombre de prueba");
		Property savedProperty=propertyService.save(property);
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient=ingredientService.save(ingredient);
		
		propertyValue.setIngredient(savedIngredient);
		propertyValue.setProperty(savedProperty);
		propertyValueService.save(propertyValue);
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveForExistingPropertyValue(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Un ingrediente no puede tener dos veces la misma propiedad");
		
		PropertyValue propertyValue = propertyValueService.create();
		
		Property property=propertyService.create();
		property.setName("nombre de prueba");
		Property savedProperty=propertyService.save(property);
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient=ingredientService.save(ingredient);
		
		propertyValue.setIngredient(savedIngredient);
		propertyValue.setProperty(savedProperty);
		PropertyValue savedPropertyValue = propertyValueService.save(propertyValue);
		
		PropertyValue propertyValue2 = propertyValueService.create();
		
		propertyValue2.setIngredient(savedIngredient);
		propertyValue2.setProperty(savedProperty);
		PropertyValue savedPropertyValue2 = propertyValueService.save(propertyValue2);
		
		exception=ExpectedException.none();	
	}
	
	@Test
	public void testDelete(){
		PropertyValue propertyValue = propertyValueService.create();
		
		Property property=propertyService.create();
		property.setName("nombre de prueba");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		PropertyValue existingPropertyValue = propertyValueService.findOne(349);
		propertyValues.add(existingPropertyValue);
		property.setPropertyValues(propertyValues);
		Property savedProperty=propertyService.save(property);
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient=ingredientService.save(ingredient);
		
		propertyValue.setIngredient(savedIngredient);
		
		propertyValue.setProperty(savedProperty);
		PropertyValue savedPropertyValue = propertyValueService.save(propertyValue);
		Collection<PropertyValue> propertyValues2 =savedIngredient.getPropertyValues();
		propertyValues.add(savedPropertyValue);
		savedIngredient.setPropertyValues(propertyValues2);
		Collection<PropertyValue> propertyValues3 =savedProperty.getPropertyValues();
		propertyValues3.add(savedPropertyValue);
		savedProperty.setPropertyValues(propertyValues3);
		ingredientService.save(savedIngredient);
		propertyService.save(savedProperty);
		
		propertyValueService.delete(savedPropertyValue);
	}
	
	@Test
	public void testDeletePropertyValueNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El objeto no puede ser nulo");
		
		PropertyValue propertyValue = null;
		propertyValueService.delete(propertyValue);
		
		exception=ExpectedException.none();	
		
	}

}
