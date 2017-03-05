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

import utilities.AbstractTest;
import domain.Ingredient;
import domain.Property;
import domain.PropertyValue;
import domain.Quantity;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class PropertyServiceTest extends AbstractTest{
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//Service under test-------------------------
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private PropertyValueService propertyValueService;
	
	@Autowired
	private IngredientService ingredientService;
	
	//Tests----------------------------------------
	
	@Test
	public void testCreateProperty(){
		Property property;
		property = propertyService.create();
		Assert.notNull(property);
	}

	@Test
	public void testFindAll(){
		Collection<Property> result = propertyService.findAll();
		for(Property p : result){
			System.out.println(p.getName());
		}
	}	
	@Test
	public void testFindOne(){
		Property result = propertyService.findOne(134);
		System.out.println(result.getName());
	}
	
	@Test
	public void testSave(){
		Property property=propertyService.create();
		property.setName("nombre de prueba");
		Property result=propertyService.save(property);
		Collection<Property> properties = propertyService.findAll();
		Assert.isTrue(properties.contains(result));
		
		System.out.println("He metido esta propiedad en la base de datos: "+result.getName()+"====="+result.getPropertyValues());
		System.out.println(result.getPropertyValues());
	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveNameNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre de la propiedad no puede ser nulo, estar vacío o tener solo espacios");
		
		Property property =propertyService.create();
		property.setName("");
		Property result = propertyService.save(property);
		
		exception=ExpectedException.none();	
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La propiedad no puede ser nula");
		
		Property property=null;
		Property result = propertyService.save(property);
		
		exception=ExpectedException.none();	
	}
	
	@Test
	public void testSaveCollection(){
		Collection<Property> properties = new ArrayList<Property>();
		
		Property property=propertyService.create();
		property.setName("nombre de prueba");
		properties.add(property);
		
		Property property2=propertyService.create();
		property2.setName("otro nombre de prueba");
		properties.add(property2);
		
		int propertiesBefore =propertyService.findAll().size();
		propertyService.save(properties);
		int propertiesAfter =propertyService.findAll().size();
		
		System.out.println("Cantidad de propiedades antes:  "+propertiesBefore);
		System.out.println("Cantidad de propiedades después:  "+propertiesAfter);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveCollectionNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La colección de propiedades no puede ser nula");
		
		Collection<Property> properties = null;
		Collection<Property> savedProperties = propertyService.save(properties);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testDelete(){
		Property property = propertyService.create();
		property.setName("Nombre de prueba");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		property.setPropertyValues(propertyValues);
		Property result=propertyService.save(property);
		Collection<Property> propertiesBefore =propertyService.findAll();
		Assert.isTrue(propertiesBefore.contains(result));
		propertyService.delete(result);
		Collection<Property> propertiesAfter =propertyService.findAll();
		Assert.isTrue(!(propertiesAfter.contains(result)));
	}
	
	@Test
	public void testDeletePropertyNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La propiedad no puede ser nula");
		
		Property property = null;
		propertyService.delete(property);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testDeleteIdEqualsZero(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El id de la propiedad no puede ser 0");
		
		Property property = propertyService.create();
		propertyService.delete(property);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testDeletePropertyDoesNotExists(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La propiedad debe existir para poder borrarse de la bd");
		Property property = propertyService.create();
		property.setName("Nombre de prueba que no existe");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		property.setPropertyValues(propertyValues);
		property.setId(24444);
		property.setVersion(3);
		Property result=propertyService.save(property);
		Collection<Property> propertiesBefore =propertyService.findAll();
		Assert.isTrue(propertiesBefore.contains(result));
		propertyService.delete(property);
		exception=ExpectedException.none();	
	}
	
	@Test
	public void testDeletePropertyBelongsToAnIngredient(){
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La propiedad no puede estar relacionada con un ingrediente");
		Property asdfa=propertyService.findOne(134);
		System.out.println(asdfa);
		Ingredient ingredient = ingredientService.create();
		ingredient.setName("Descripcion del ingrediente");
		ingredient.setDescription("Descripcion del ingrediente");
		ingredient.setPropertyValues(new ArrayList<PropertyValue>());
		ingredient.setQuantities(new ArrayList<Quantity>());
		Ingredient savedIngredient = ingredientService.save(ingredient);
		
		Property property = propertyService.create();
		property.setName("Nombre de propiedad perteneciente a un ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		property.setPropertyValues(propertyValues);
		Property result=propertyService.save(property);
		
		
		PropertyValue propertyValue = propertyValueService.create();
		propertyValue.setIngredient(savedIngredient);
		propertyValue.setProperty(result);
		propertyValueService.save(propertyValue);
		
		
		propertyService.delete(result);
		
		exception=ExpectedException.none();	

	}


}
