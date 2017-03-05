package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Attend;
import domain.Belongs;
import domain.Comment;
import domain.Folder;
import domain.Ingredient;
import domain.PropertyValue;
import domain.Qualification;
import domain.Qualified;
import domain.Quantity;
import domain.Recipe;
import domain.RecipeHint;
import domain.SocialIdentity;
import domain.Step;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class IngredientServiceTest extends AbstractTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//Service under test-------------------------
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuantityService quantityService;
	
	//Tests--------------------------------------
	
	@Test
	public void testCreate(){
		Ingredient ingredient;
		ingredient = ingredientService.create();
		Assert.notNull(ingredient);
	}
	
	@Test
	public void testSave(){
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient result=ingredientService.save(ingredient);
		Collection<Ingredient> ingredients = ingredientService.findAll();
		Assert.isTrue(ingredients.contains(result));
		
		System.out.println("He metido este ingrediente en la base de datos: "+result.getName()+"====="+result.getName());
		System.out.println(result.getName());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveIngredientNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El ingrediente no puede ser nulo");
		
		Ingredient ingredient=null;
		Ingredient result = ingredientService.save(ingredient);
		
		exception=ExpectedException.none();	
	}
	
	@Test
	public void testSaveIngredientNameNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre del ingrediente no puede ser nulo");
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient result=ingredientService.save(ingredient);
		Collection<Ingredient> properties = ingredientService.findAll();
		properties.contains(result);
		
		System.out.println("He metido este ingrediente en la base de datos: "+result.getName()+"====="+result.getName());
		System.out.println(result.getName());
		
		exception=ExpectedException.none();	
		
	}
	
	@Test
	public void testSaveIngredientDescriptionNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La descripción del ingrediente no puede ser nula");
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("Nombre del ingrediente");
		ingredient.setDescription("");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient result=ingredientService.save(ingredient);
		Collection<Ingredient> properties = ingredientService.findAll();
		properties.contains(result);
		
		System.out.println("He metido este ingrediente en la base de datos: "+result.getName()+"====="+result.getName());
		System.out.println(result.getName());
		
		exception=ExpectedException.none();	
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveCollection(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La colección de ingredientes no puede ser nula");
		
		Collection<Ingredient> ingredients = null;
		Collection<Ingredient> savedIngredients = ingredientService.save(ingredients);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testDelete(){
		Ingredient ingredient = ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient result=ingredientService.save(ingredient);
		Collection<Ingredient> ingredientsBefore =ingredientService.findAll();
		Assert.isTrue(ingredientsBefore.contains(result));
		ingredientService.delete(result);
		Collection<Ingredient> ingredientsAfter =ingredientService.findAll();
		Assert.isTrue(!(ingredientsAfter.contains(result)));
	}
	
	@Test
	public void testDeleteIngredientNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El ingrediente no puede ser nulo");
		
		Ingredient ingredient = null;
		ingredientService.delete(ingredient);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testDeleteIdEqualsZero(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El id del ingrediente no puede ser 0");
		
		Ingredient ingredient = ingredientService.create();
		ingredientService.delete(ingredient);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testDeleteIngredientDoesNotExists(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El ingrediente debe existir para poder borrarse de la bd");
		Ingredient ingredient = ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		ingredient.setId(24444);
		ingredient.setVersion(3);
		Ingredient result=ingredientService.save(ingredient);
		Collection<Ingredient> propertiesBefore =ingredientService.findAll();
		Assert.isTrue(propertiesBefore.contains(result));
		ingredientService.delete(ingredient);
		exception=ExpectedException.none();	
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testDeleteIngredientBelongsToARecipe(){
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El ingrediente no puede estar relacionado con una receta");
		
		
		
		Ingredient ingredient = ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient = ingredientService.save(ingredient);
		

		Recipe recipe = recipeService.create();
		recipe.setTicker(recipeService.createTicker());
		recipe.setTitle("Titulo de la receta");
		recipe.setSummary("Resumen de la receta");
		Date date = new Date(System.currentTimeMillis()-1000000000);
		recipe.setAuthorMoment(date);
		Date date2 = new Date(System.currentTimeMillis()-100000);
		recipe.setLastUpdate(date2);
		Collection<String> pictures = new ArrayList<String>();
		pictures.add("http://www.urldelaimagen.com/foto.jpg");
		recipe.setPictures(pictures);
		recipe.setIsCopy(false);
		
		Collection<Quantity> quantitiesRecipe = new ArrayList<Quantity>();
		Collection<Belongs> belongs = new ArrayList<Belongs>();
		Collection<RecipeHint> recipeHints = new ArrayList<RecipeHint>();
		Collection<Qualification> qualifications = new ArrayList<Qualification>();
		Collection<Comment> comments = new ArrayList<Comment>();
		Collection<Step> steps = new ArrayList<Step>();
		Collection<Qualified> qualifieds = new ArrayList<Qualified>();
		
		User user = userService.create();
		user.setName("Nombre del usuario");
		user.setSurname("Apellidos del usuario");
		user.setEmail("email@delusuario.com");
		Collection<Folder> folders = new ArrayList<Folder>();
		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		Collection<Attend> attends = new ArrayList<Attend>();
		
		user.setFolders(folders);
		user.setSocialIdentities(socialIdentities);
		user.setAttends(attends);
		
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("USER");
		authorities.add(authority);
		 
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("userTest");
		userAcc.setUsername("userTest");
		userAcc.setAuthorities(authorities);
		user.setUserAccount(userAcc);
		
		
		User savedUser = userService.save(user);
		recipe.setQuantities(quantitiesRecipe);
		recipe.setBelongs(belongs);
		recipe.setRecipeHints(recipeHints);
		recipe.setQualifications(qualifications);
		recipe.setComments(comments);
		recipe.setUser(savedUser);
		recipe.setSteps(steps);
		recipe.setQualifieds(qualifieds);
		
		super.authenticate("userTest");
		
		System.out.println("recipe id:   "+recipe.getId());
		System.out.println("recipe version:   "+recipe.getVersion());
		System.out.println("recipe ticker:  "+recipe.getTicker());
		System.out.println("recipe title:   "+recipe.getTitle());
		System.out.println("recipe summary:  "+recipe.getSummary());
		System.out.println("recipe author moment:    "+recipe.getAuthorMoment());
		System.out.println("recipe last update:    "+recipe.getLastUpdate());
		System.out.println("recipe pictures:  "+recipe.getPictures());
		System.out.println("recipe iscopy:   "+recipe.getIsCopy());
		System.out.println("recipe user:   "+recipe.getUser().getUserAccount().getUsername());
	
		
		Recipe savedRecipe = recipeService.save(recipe);
		
		System.out.println("recipe id:   "+savedRecipe.getId());
		System.out.println("recipe version:   "+savedRecipe.getVersion());
		System.out.println("recipe ticker:  "+savedRecipe.getTicker());
		System.out.println("recipe title:   "+savedRecipe.getTitle());
		System.out.println("recipe summary:  "+savedRecipe.getSummary());
		System.out.println("recipe author moment:    "+savedRecipe.getAuthorMoment());
		System.out.println("recipe last update:    "+savedRecipe.getLastUpdate());
		System.out.println("recipe pictures:  "+savedRecipe.getPictures());
		System.out.println("recipe iscopy:   "+savedRecipe.getIsCopy());
		System.out.println("recipe user:   "+savedRecipe.getUser().getUserAccount().getUsername());
		
		super.unauthenticate();
		
		Quantity quantity = quantityService.create();
		quantity.setIngredient(savedIngredient);
		quantity.setRecipe(savedRecipe);
		quantity.setUnit("kilograms");
		quantity.setValue(1.5);
		
		Quantity savedQuantity = quantityService.save(quantity);
		
		
		ingredientService.delete(savedIngredient);
		
		
		exception=ExpectedException.none();	
		

	}

	
	
}
