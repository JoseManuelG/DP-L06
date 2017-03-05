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
public class QualificationServiceTest extends AbstractTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//Services under test----------------------------------------
	
	@Autowired
	private QualificationService qualificationService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	//Tests-----------------------------------------------------
	
	@Test
	public void testCreate(){
		Qualification qualification;
		qualification = qualificationService.create();
		Assert.notNull(qualification);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSave(){
		Qualification qualification = qualificationService.create();
		qualification.setOpinion(false);
		
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
		user2.setName("Nombre del usuario");
		user2.setSurname("Apellidos del usuario");
		user2.setEmail("email@delusuario.com");
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
		
		Recipe savedRecipe = recipeService.save(recipe);
		
		super.unauthenticate();
		super.authenticate("otherUser");
		
		qualification.setCustomer(savedUser);
		qualification.setRecipe(savedRecipe);
		
		Qualification savedQualification = qualificationService.save(qualification);
		Collection<Qualification> qualifications2 = qualificationService.findAll();
		Assert.isTrue(qualifications2.contains(savedQualification));
		
		super.unauthenticate();
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El objeto no puede ser nulo");
		
		Qualification qualification;
		qualification = null;
		Qualification savedQualification = qualificationService.save(qualification);
		
		exception = ExpectedException.none();
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveSameCutomer(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Un usuario no puede dar like a su propia receta");
		
		Qualification qualification = qualificationService.create();
		qualification.setOpinion(true);
		
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
		user2.setName("Nombre del usuario");
		user2.setSurname("Apellidos del usuario");
		user2.setEmail("email@delusuario.com");
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
		
		Recipe savedRecipe = recipeService.save(recipe);
		
		
		qualification.setCustomer(savedUser);
		qualification.setRecipe(savedRecipe);
		
		Qualification savedQualification = qualificationService.save(qualification);
		Collection<Qualification> qualifications2 = qualificationService.findAll();
		Assert.isTrue(qualifications2.contains(savedQualification));
		
		super.unauthenticate();
		
		
		exception = ExpectedException.none();
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testDelete(){
		Qualification qualification = qualificationService.create();
		qualification.setOpinion(false);
		
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
		user2.setName("Nombre del usuario");
		user2.setSurname("Apellidos del usuario");
		user2.setEmail("email@delusuario.com");
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
		
		Recipe savedRecipe = recipeService.save(recipe);
		
		super.unauthenticate();
		super.authenticate("otherUser");
		
		qualification.setCustomer(savedUser);
		qualification.setRecipe(savedRecipe);
		
		Qualification savedQualification = qualificationService.save(qualification);
		Collection<Qualification> qualifications2 = qualificationService.findAll();
		Assert.isTrue(qualifications2.contains(savedQualification));
		
		super.unauthenticate();
		
		super.authenticate("userTest");
		Assert.isTrue(qualifications2.contains(savedQualification));
		qualificationService.delete(savedQualification);
		qualifications2 = qualificationService.findAll();
		Assert.isTrue(!(qualifications2.contains(savedQualification)));
		
	}

}
