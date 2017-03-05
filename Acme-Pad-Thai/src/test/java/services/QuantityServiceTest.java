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
public class QuantityServiceTest extends AbstractTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//Service under test--------------------------
	
	@Autowired
	private QuantityService quantityService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	//Tests--------------------------------------
	
	@Test
	public void testCreate(){
		Quantity quantity;
		quantity = quantityService.create();
		Assert.notNull(quantity);
	}
	
	@Test
	public void testSave(){
		Quantity quantity=quantityService.create();
		quantity.setUnit("kilograms");
		quantity.setValue(1.3);
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient=ingredientService.save(ingredient);
		Collection<Ingredient> properties = ingredientService.findAll();
		properties.contains(savedIngredient);
		
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
		
			
		Recipe savedRecipe = recipeService.save(recipe);
		
		super.unauthenticate();
		
		quantity.setIngredient(savedIngredient);
		quantity.setRecipe(savedRecipe);
		
		Quantity savedQuantity = quantityService.save(quantity);
		
		Collection<Quantity> quantities2 = quantityService.findAll();
		quantities2.contains(savedQuantity);
	}
	
	@Test
	public void testSaveQuantityNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La cantidad no puede ser nula");
		
		Quantity quantity = null;
		quantityService.save(quantity);
		
		exception=ExpectedException.none();
		
	}
	
	@Test
	public void testSaveUnitNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La unidad no puede ser nula");
		
		Quantity quantity=quantityService.create();
		quantity.setUnit(null);
		quantity.setValue(1.3);
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient=ingredientService.save(ingredient);
		Collection<Ingredient> properties = ingredientService.findAll();
		properties.contains(savedIngredient);
		
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
		
			
		Recipe savedRecipe = recipeService.save(recipe);
		
		super.unauthenticate();
		
		quantity.setIngredient(savedIngredient);
		quantity.setRecipe(savedRecipe);
		
		Quantity savedQuantity = quantityService.save(quantity);
		
		Collection<Quantity> quantities2 = quantityService.findAll();
		quantities2.contains(savedQuantity);
		
		exception=ExpectedException.none();
		
	}
	
	@Test
	public void testSaveValueNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El valor no puede ser nulo");
		
		Quantity quantity=quantityService.create();
		quantity.setUnit("kilograms");
		quantity.setValue(null);
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient=ingredientService.save(ingredient);
		Collection<Ingredient> properties = ingredientService.findAll();
		properties.contains(savedIngredient);
		
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
		
			
		Recipe savedRecipe = recipeService.save(recipe);
		
		super.unauthenticate();
		
		quantity.setIngredient(savedIngredient);
		quantity.setRecipe(savedRecipe);
		
		Quantity savedQuantity = quantityService.save(quantity);
		
		Collection<Quantity> quantities2 = quantityService.findAll();
		quantities2.contains(savedQuantity);
		
		exception=ExpectedException.none();
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveCollection(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La colección de cantidades no puede ser nula");
		
		Collection<Quantity> quantities = null;
		Collection<Quantity> savedQuantities = quantityService.save(quantities);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testDelete(){
		Quantity quantity=quantityService.create();
		quantity.setUnit("kilograms");
		quantity.setValue(1.3);
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient=ingredientService.save(ingredient);
		Collection<Ingredient> properties = ingredientService.findAll();
		properties.contains(savedIngredient);
		
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
		
			
		Recipe savedRecipe = recipeService.save(recipe);
		
		super.unauthenticate();
		
		quantity.setIngredient(savedIngredient);
		quantity.setRecipe(savedRecipe);
		
		Quantity savedQuantity = quantityService.save(quantity);
		
		Collection<Quantity> quantities2 = quantityService.findAll();
		quantities2.contains(savedQuantity);
		
		quantityService.delete(savedQuantity);
		Collection<Quantity> quantitiesAfter = quantityService.findAll();
		Assert.isTrue(!(quantitiesAfter.contains(savedQuantity)));
		
	}
	
	@Test
	public void testDeleteQuantityNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La cantidad no puede ser nula");
		
		Quantity quantity = null;
		quantityService.delete(quantity);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testDeleteIdEqualsZero(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El id de la cantidad no puede ser 0");
		
		Quantity quantity = quantityService.create();
		quantityService.delete(quantity);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testFindByRecipeNullAndIngredient(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La receta no puede ser nula");
		
		Recipe recipe=null;
		Ingredient ingredient=null;
		quantityService.findByRecipeAndIngredient(recipe, ingredient);
		
		exception=ExpectedException.none();
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveForExistingQuantity(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Una receta no puede tener dos veces el mismo ingrediente");
		
		Quantity quantity = quantityService.create();
		
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
		Recipe savedRecipe = recipeService.save(recipe);
		super.unauthenticate();
		
		Ingredient ingredient=ingredientService.create();
		ingredient.setName("nombre de prueba");
		ingredient.setDescription("Descripcion del ingrediente");
		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		ingredient.setPropertyValues(propertyValues);
		ingredient.setQuantities(quantities);
		Ingredient savedIngredient=ingredientService.save(ingredient);
		
		quantity.setIngredient(savedIngredient);
		quantity.setRecipe(savedRecipe);
		quantity.setUnit("kilograms");
		quantity.setValue(1.1);
		Quantity savedQuantity = quantityService.save(quantity);
		
		Quantity quantity2 = quantityService.create();
		
		quantity2.setIngredient(savedIngredient);
		quantity2.setRecipe(savedRecipe);
		quantity2.setUnit("kilograms");
		quantity2.setValue(1.1);
		Quantity savedPropertyValue2 = quantityService.save(quantity2);
		
		exception=ExpectedException.none();	
	}
	
	

}
