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
public class RecipeHintServiceTest extends AbstractTest {
	//Service under test-------------------------
			@Autowired
			private RecipeHintService recipeHintService;
			
			@Autowired
			private RecipeService recipeService;
			
			@Autowired
			private UserService userService;
			
			//Tests----------------------------------------
			@Rule
			  public ExpectedException exception = ExpectedException.none();
			
			//Tests de creates----------------------------------------
			@Test
			public void testCreateRecipeHint(){
				RecipeHint recipeHint;
				recipeHint = recipeHintService.create();
				Assert.notNull(recipeHint);
				Assert.isNull(recipeHint.getRecipe());
				Assert.isNull(recipeHint.getHint());
				
			}
			
			//Tests de saves----------------------------------------
			@Test
			public void testSaveRecipeHint(){
				//Creamos la recipe para el qualified
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
		        
		        Collection<RecipeHint> recipeHintsRet = new ArrayList<RecipeHint>();
			
				RecipeHint recipeHint = recipeHintService.create();
				recipeHint.setRecipe(savedRecipe);
				recipeHint.setHint("test recipeHint");
				
								
				RecipeHint savedRecipeHint=recipeHintService.save(recipeHint);
				recipeHintsRet = recipeHintService.findAll();
				Assert.isTrue(recipeHintsRet.contains(savedRecipeHint));
				
				
				
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveNullRecipeHint(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El recipeHint a guardar no puede ser nulo");
				//Creamos la recipe para el qualified
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
		        
		        Collection<RecipeHint> recipeHintsRet = new ArrayList<RecipeHint>();
			
				RecipeHint recipeHint = null;
				
								
				RecipeHint savedRecipeHint=recipeHintService.save(recipeHint);
				recipeHintsRet = recipeHintService.findAll();
				Assert.isTrue(recipeHintsRet.contains(savedRecipeHint));
				
				
				
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveRecipeHintAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para añadir un recipeHint a la misma");
				//Creamos la recipe para el qualified
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
		        
		        User user2 = userService.create();
		        user2.setName("Nombre del usuario2");
		        user2.setSurname("Apellidos del usuario2");
		        user2.setEmail("email@delusuario.com2");
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
		        userAcc2.setPassword("userTest2");
		        userAcc2.setUsername("userTest2");
		        userAcc2.setAuthorities(authorities2);
		        user2.setUserAccount(userAcc2);
		        
		        
		        User savedUser2 = userService.save(user2);
		        
		        
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
		        
		        Collection<RecipeHint> recipeHintsRet = new ArrayList<RecipeHint>();
			
				RecipeHint recipeHint = recipeHintService.create();
				recipeHint.setRecipe(savedRecipe);
				recipeHint.setHint("test recipeHint");
				
				 super.authenticate("userTest2");	
				 
				 
				RecipeHint savedRecipeHint=recipeHintService.save(recipeHint);
				recipeHintsRet = recipeHintService.findAll();
				Assert.isTrue(recipeHintsRet.contains(savedRecipeHint));
				
				
				
			}
			
			//Tests de deletes----------------------------------------
			@Test
			public void testDeleteRecipeHint(){
				//Creamos la recipe para el qualified
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
		        
		        Collection<RecipeHint> recipeHintsRet = new ArrayList<RecipeHint>();
		        Collection<RecipeHint> recipeHintsPostDelete = new ArrayList<RecipeHint>();
			
				RecipeHint recipeHint = recipeHintService.create();
				recipeHint.setRecipe(savedRecipe);
				recipeHint.setHint("test recipeHint");
				
								
				RecipeHint recipeHintSaved=recipeHintService.save(recipeHint);
				recipeHintsRet = recipeHintService.findAll();
				Assert.isTrue(recipeHintsRet.contains(recipeHintSaved));
				
				recipeHintService.delete(recipeHintSaved);
				recipeHintsPostDelete =recipeHintService.findAll();
				Assert.isTrue(!recipeHintsPostDelete.contains(recipeHintSaved));
				
				
				
			}
			
			@Test
			public void testDeleteRecipeHintnull(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El recipeHint a guardar no puede ser nulo");
				RecipeHint recipeHint;
				recipeHint=null;
				recipeHintService.delete(recipeHint);
				exception=ExpectedException.none();
			}
			@Test
			public void testDeleteRecipeHintId0(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El objeto recipeHint a borrar debe tener una id valida ");
				RecipeHint recipeHint;
				recipeHint = recipeHintService.create();
				recipeHintService.delete(recipeHint);
				exception=ExpectedException.none();
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testDeleteRecipeHintAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para borrar un recipeHint a la misma");
				//Creamos la recipe para el qualified
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
		        
		        User user2 = userService.create();
		        user2.setName("Nombre del usuario2");
		        user2.setSurname("Apellidos del usuario2");
		        user2.setEmail("email@delusuario.com2");
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
		        userAcc2.setPassword("userTest2");
		        userAcc2.setUsername("userTest2");
		        userAcc2.setAuthorities(authorities2);
		        user2.setUserAccount(userAcc2);
		        
		        
		        User savedUser2 = userService.save(user2);
		        
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
		        
		        Collection<RecipeHint> recipeHintsRet = new ArrayList<RecipeHint>();
		        Collection<RecipeHint> recipeHintsPostDelete = new ArrayList<RecipeHint>();
			
				RecipeHint recipeHint = recipeHintService.create();
				recipeHint.setRecipe(savedRecipe);
				recipeHint.setHint("test recipeHint");
				
								
				RecipeHint recipeHintSaved=recipeHintService.save(recipeHint);
				recipeHintsRet = recipeHintService.findAll();
				Assert.isTrue(recipeHintsRet.contains(recipeHintSaved));
				
				super.authenticate("userTest2");
				
				recipeHintService.delete(recipeHintSaved);
				recipeHintsPostDelete =recipeHintService.findAll();
				Assert.isTrue(!recipeHintsPostDelete.contains(recipeHintSaved));
				
				
				
			}
			
}
