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
public class RecipeServiceTest extends AbstractTest{
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	////Service under test-------------------------
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	//Tests----------------------------------------
	
	@Test
	public void testCreate(){
		Recipe recipe;
		recipe = recipeService.create();
		Assert.notNull(recipe);
	}
	
	@Test
	public void testSave(){
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
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La receta no puede ser nula");
		
		Recipe recipe =null;
		Recipe savedRecipe = recipeService.save(recipe);
		
		exception=ExpectedException.none();
	}
	
	@Test
	public void testSaveTickerEmpty(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El ticker no puede ser nulo");
		
		Recipe recipe = recipeService.create();
		recipe.setTicker("     ");
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
		
		exception=ExpectedException.none();
		
	}
	
//	@SuppressWarnings("unused")
//	@Test
//	public void testSaveOtherUser(){
//		exception.expect(IllegalArgumentException.class);
//		exception.expectMessage("Solo el propietario puede realizar operaciones");
//		
//		User user = userService.create();
//		user.setName("Nombre del usuario");
//		user.setSurname("Apellidos del usuario");
//		user.setEmail("email@delusuario.com");
//		Collection<Folder> folders = new ArrayList<Folder>();
//		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
//		Collection<Attend> attends = new ArrayList<Attend>();
//		
//		user.setFolders(folders);
//		user.setSocialIdentities(socialIdentities);
//		user.setAttends(attends);
//		
//		Collection<Authority> authorities = new ArrayList<Authority>();
//		Authority authority = new Authority();
//		authority.setAuthority("USER");
//		authorities.add(authority);
//		 
//		UserAccount userAcc = new UserAccount();
//		userAcc.setPassword("userTest");
//		userAcc.setUsername("userTest");
//		userAcc.setAuthorities(authorities);
//		user.setUserAccount(userAcc);
//		
//		
//		User savedUser = userService.save(user);
//
//		
//		User user2 = userService.create();
//		user2.setName("Nombre del usuario2");
//		user2.setSurname("Apellidos del usuario2");
//		user2.setEmail("email@delusuario2.com");
//		Collection<Folder> folders2 = new ArrayList<Folder>();
//		Collection<SocialIdentity> socialIdentities2 = new ArrayList<SocialIdentity>();
//		Collection<Attend> attends2 = new ArrayList<Attend>();
//		
//		user2.setFolders(folders2);
//		user2.setSocialIdentities(socialIdentities2);
//		user2.setAttends(attends2);
//		
//		Collection<Authority> authorities2 = new ArrayList<Authority>();
//		Authority authority2 = new Authority();
//		authority2.setAuthority("USER");
//		authorities2.add(authority2);
//		 
//		UserAccount userAcc2 = new UserAccount();
//		userAcc2.setPassword("otherUser");
//		userAcc2.setUsername("otherUser");
//		userAcc2.setAuthorities(authorities);
//		user2.setUserAccount(userAcc2);
//		
//		User savedUser2 = userService.save(user2);
//		
//		super.authenticate("userTest");
//		
//		Recipe recipe = recipeService.create();
//		recipe.setTicker(recipeService.createTicker());
//		recipe.setTitle("Titulo de la receta");
//		recipe.setSummary("Resumen de la receta");
//		Date date = new Date(System.currentTimeMillis()-1000000000);
//		recipe.setAuthorMoment(date);
//		Date date2 = new Date(System.currentTimeMillis()-100000);
//		recipe.setLastUpdate(date2);
//		Collection<String> pictures = new ArrayList<String>();
//		pictures.add("http://www.urldelaimagen.com/foto.jpg");
//		recipe.setPictures(pictures);
//		recipe.setIsCopy(false);
//		
//		
//		
//		Collection<Quantity> quantitiesRecipe = new ArrayList<Quantity>();
//		Collection<Belongs> belongs = new ArrayList<Belongs>();
//		Collection<RecipeHint> recipeHints = new ArrayList<RecipeHint>();
//		Collection<Qualification> qualifications = new ArrayList<Qualification>();
//		Collection<Comment> comments = new ArrayList<Comment>();
//		Collection<Step> steps = new ArrayList<Step>();
//		Collection<Qualified> qualifieds = new ArrayList<Qualified>();
//		
//
//		recipe.setQuantities(quantitiesRecipe);
//		recipe.setBelongs(belongs);
//		recipe.setRecipeHints(recipeHints);
//		recipe.setQualifications(qualifications);
//		recipe.setComments(comments);
//		recipe.setUser(savedUser);
//		recipe.setSteps(steps);
//		recipe.setQualifieds(qualifieds);
//		
//		
//		System.out.println("recipe id:   "+recipe.getId());
//		System.out.println("recipe version:   "+recipe.getVersion());
//		System.out.println("recipe ticker:  "+recipe.getTicker());
//		System.out.println("recipe title:   "+recipe.getTitle());
//		System.out.println("recipe summary:  "+recipe.getSummary());
//		System.out.println("recipe author moment:    "+recipe.getAuthorMoment());
//		System.out.println("recipe last update:    "+recipe.getLastUpdate());
//		System.out.println("recipe pictures:  "+recipe.getPictures());
//		System.out.println("recipe iscopy:   "+recipe.getIsCopy());
//		System.out.println("recipe user:   "+recipe.getUser().getUserAccount().getUsername());
//	
//		
//		Recipe savedRecipe = recipeService.save(recipe);
//		
//		System.out.println("recipe id:   "+savedRecipe.getId());
//		System.out.println("recipe version:   "+savedRecipe.getVersion());
//		System.out.println("recipe ticker:  "+savedRecipe.getTicker());
//		System.out.println("recipe title:   "+savedRecipe.getTitle());
//		System.out.println("recipe summary:  "+savedRecipe.getSummary());
//		System.out.println("recipe author moment:    "+savedRecipe.getAuthorMoment());
//		System.out.println("recipe last update:    "+savedRecipe.getLastUpdate());
//		System.out.println("recipe pictures:  "+savedRecipe.getPictures());
//		System.out.println("recipe iscopy:   "+savedRecipe.getIsCopy());
//		System.out.println("recipe user:   "+savedRecipe.getUser().getUserAccount().getUsername());
//		
//		super.unauthenticate();
//		
//		super.authenticate("otherUser");
//		
//		recipe.setTitle("Cambio de titulo");
//		
//		Recipe savedRecipe2 = recipeService.save(recipe);
//		
//		super.unauthenticate();
//		
//		
//		
//		exception=ExpectedException.none();
//		
//	}
	
	@Test
	public void testDelete(){
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
		
		Collection<Recipe> recipesBefore = recipeService.findAll();
		Assert.isTrue(recipesBefore.contains(savedRecipe));
		recipeService.delete(savedRecipe);
		Collection<Recipe> recipesAfter = recipeService.findAll();
		Assert.isTrue(!(recipesAfter.contains(savedRecipe)));
		
		super.unauthenticate();
	}
	
	@Test
	public void testDeleteNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La receta no puede ser nula");
		
		Recipe recipe = null;
		recipeService.delete(recipe);
		
		exception = ExpectedException.none();
	}
	
	@Test
	public void testDeleteIdEqualsZero(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El id de la receta no puede ser 0");
		
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
		
		recipeService.delete(recipe);
		
		exception=ExpectedException.none();
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testDeleteOtherUser(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Solo el propietario puede realizar operaciones");
		
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
		
		User user2 = userService.create();
		user2.setName("Nombre del usuario2");
		user2.setSurname("Apellidos del usuario2");
		user2.setEmail("email@delusuario2.com");
		Collection<Folder> folders2 = new ArrayList<Folder>();
		Collection<SocialIdentity> socialIdentities2 = new ArrayList<SocialIdentity>();
		Collection<Attend> attends2 = new ArrayList<Attend>();
		
		user2.setFolders(folders2);
		user2.setSocialIdentities(socialIdentities2);
		user2.setAttends(attends2);
		
		Collection<Authority> authorities2 = new ArrayList<Authority>();
		Authority authority2 = new Authority();
		authority2.setAuthority("USER");
		authorities2.add(authority2);
		 
		UserAccount userAcc2 = new UserAccount();
		userAcc2.setPassword("otherUser");
		userAcc2.setUsername("otherUser");
		userAcc2.setAuthorities(authorities);
		user2.setUserAccount(userAcc2);
		
		
		User savedUser2 = userService.save(user2);
		
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
		
		super.authenticate("otherUser");
		
		recipeService.delete(savedRecipe);
		
		super.unauthenticate();
		
		
		
		exception=ExpectedException.none();
		
	}
	@Test
	public void testDeleteRecipeFromContest(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se pueden borrar recetas inscritas en un concurso");
		
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
		recipe.setIsCopy(true);
		
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
		
		Collection<Recipe> recipesBefore = recipeService.findAll();
		Assert.isTrue(recipesBefore.contains(savedRecipe));
		recipeService.delete(savedRecipe);
		Collection<Recipe> recipesAfter = recipeService.findAll();
		Assert.isTrue(!(recipesAfter.contains(savedRecipe)));
		
		super.unauthenticate();
		
		exception=ExpectedException.none();
		
	}
	
//	@Test
//	public void testCreateCopyOfRecipe(){
//		super.authenticate("user1");
//		Recipe recipe1= recipeService.findOne(76);
//		System.out.println(recipeService.findAll().size());
//		Recipe recipe2=recipeService.createCopyOfRecipe(recipe1);
//		System.out.println("Start");
//		System.out.println(recipe2.getQualifications());
//		System.out.println(recipe1.getQualifications());
//		System.out.println(recipe2.getQuantities());
//		System.out.println(recipe1.getQuantities());
//		System.out.println(recipe2.getSteps());
//		System.out.println(recipe1.getSteps());
//		System.out.println(recipe2.getRecipeHints());
//		System.out.println(recipe1.getRecipeHints());
//		System.out.println(recipe2.getTicker());
//		System.out.println(recipe2.getParentTicker());
//		System.out.println(recipe1.getTicker());
//		
//		System.out.println(recipeService.findAll().size());
//	}
	
//	@Test
//	public void testAddIngredientNotSavedForRecipe(){
//		exception.expect(IllegalArgumentException.class);
//		exception.expectMessage("El ingrediente debe estar guardado en la bd");
//		Ingredient ingredient=ingredientService.create();
//		ingredient.setName("nombre de prueba");
//		ingredient.setDescription("Descripcion del ingrediente");
//		Collection<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
//		Collection<Quantity> quantities = new ArrayList<Quantity>();
//		ingredient.setPropertyValues(propertyValues);
//		ingredient.setQuantities(quantities);
//		
//		
//		Recipe recipe = recipeService.create();
//		recipe.setTicker(recipeService.createTicker());
//		recipe.setTitle("Titulo de la receta");
//		recipe.setSummary("Resumen de la receta");
//		Date date = new Date(System.currentTimeMillis()-1000000000);
//		recipe.setAuthorMoment(date);
//		Date date2 = new Date(System.currentTimeMillis()-100000);
//		recipe.setLastUpdate(date2);
//		Collection<String> pictures = new ArrayList<String>();
//		pictures.add("http://www.urldelaimagen.com/foto.jpg");
//		recipe.setPictures(pictures);
//		recipe.setIsCopy(false);
//		
//		Collection<Quantity> quantitiesRecipe = new ArrayList<Quantity>();
//		Collection<Belongs> belongs = new ArrayList<Belongs>();
//		Collection<RecipeHint> recipeHints = new ArrayList<RecipeHint>();
//		Collection<Qualification> qualifications = new ArrayList<Qualification>();
//		Collection<Comment> comments = new ArrayList<Comment>();
//		Collection<Step> steps = new ArrayList<Step>();
//		Collection<Qualified> qualifieds = new ArrayList<Qualified>();
//		
//		User user = userService.create();
//		user.setName("Nombre del usuario");
//		user.setSurname("Apellidos del usuario");
//		user.setEmail("email@delusuario.com");
//		Collection<Folder> folders = new ArrayList<Folder>();
//		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
//		Collection<Attend> attends = new ArrayList<Attend>();
//		
//		user.setFolders(folders);
//		user.setSocialIdentities(socialIdentities);
//		user.setAttends(attends);
//		
//		Collection<Authority> authorities = new ArrayList<Authority>();
//		Authority authority = new Authority();
//		authority.setAuthority("USER");
//		authorities.add(authority);
//		 
//		UserAccount userAcc = new UserAccount();
//		userAcc.setPassword("userTest");
//		userAcc.setUsername("userTest");
//		userAcc.setAuthorities(authorities);
//		user.setUserAccount(userAcc);
//		
//		
//		User savedUser = userService.save(user);
//		recipe.setQuantities(quantitiesRecipe);
//		recipe.setBelongs(belongs);
//		recipe.setRecipeHints(recipeHints);
//		recipe.setQualifications(qualifications);
//		recipe.setComments(comments);
//		recipe.setUser(savedUser);
//		recipe.setSteps(steps);
//		recipe.setQualifieds(qualifieds);
//		
//		super.authenticate("userTest");
//		
//		System.out.println("recipe id:   "+recipe.getId());
//		System.out.println("recipe version:   "+recipe.getVersion());
//		System.out.println("recipe ticker:  "+recipe.getTicker());
//		System.out.println("recipe title:   "+recipe.getTitle());
//		System.out.println("recipe summary:  "+recipe.getSummary());
//		System.out.println("recipe author moment:    "+recipe.getAuthorMoment());
//		System.out.println("recipe last update:    "+recipe.getLastUpdate());
//		System.out.println("recipe pictures:  "+recipe.getPictures());
//		System.out.println("recipe iscopy:   "+recipe.getIsCopy());
//		System.out.println("recipe user:   "+recipe.getUser().getUserAccount().getUsername());
//	
//		
//		Recipe savedRecipe = recipeService.save(recipe);
//		
//		System.out.println("recipe id:   "+savedRecipe.getId());
//		System.out.println("recipe version:   "+savedRecipe.getVersion());
//		System.out.println("recipe ticker:  "+savedRecipe.getTicker());
//		System.out.println("recipe title:   "+savedRecipe.getTitle());
//		System.out.println("recipe summary:  "+savedRecipe.getSummary());
//		System.out.println("recipe author moment:    "+savedRecipe.getAuthorMoment());
//		System.out.println("recipe last update:    "+savedRecipe.getLastUpdate());
//		System.out.println("recipe pictures:  "+savedRecipe.getPictures());
//		System.out.println("recipe iscopy:   "+savedRecipe.getIsCopy());
//		System.out.println("recipe user:   "+savedRecipe.getUser().getUserAccount().getUsername());
//		
//		super.unauthenticate();
//		
//		recipeService.addIngredientForRecipe(savedRecipe, ingredient);
//		
//		exception=ExpectedException.none();
//		
//		
//	}

}
