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
import domain.StepHint;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class StepServiceTest extends AbstractTest {
			//Service under test-------------------------
			@Autowired
			private StepService stepService;
			@Autowired
			private RecipeService recipeService;
			@Autowired
			private UserService userService;

			//Tests----------------------------------------
			@Rule
			  public ExpectedException exception = ExpectedException.none();
			
			
			//Tests de creates----------------------------------------
			@Test
			public void testCreatestep(){
				Step step;
				step = stepService.create();
				Assert.notNull(step);
				Assert.isNull(step.getDescription());
				Assert.isNull(step.getNumber());
				Assert.isNull(step.getPicture());
				Assert.isNull(step.getRecipe());
				Assert.isNull(step.getStepHints());
			}
			
			//Tests de saves----------------------------------------
			@Test
			public void testSaveSingleStep(){
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
		        
		        Collection<StepHint> stepHints = new ArrayList<StepHint>();
			
				Step step = stepService.create();
				step.setDescription("TestDescription");
				step.setPicture("http://www.example.com");
				step.setRecipe(savedRecipe);
				step.setStepHints(stepHints);
				
				
				
				Step saved=stepService.save(step);
				steps = stepService.findAll();
				Assert.isTrue(steps.contains(saved));
				
				
			}
			
			@Test
			public void testSaveStepCollection(){
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
		        
		        Collection<StepHint> stepHints1 = new ArrayList<StepHint>();
		        Collection<StepHint> stepHints2 = new ArrayList<StepHint>();
		        Collection<Step> stepsTo = new ArrayList<Step>();
			
				Step step1 = stepService.create();
				step1.setDescription("TestDescription");
				step1.setPicture("http://www.example.com");
				step1.setRecipe(savedRecipe);
				step1.setStepHints(stepHints1);
				
				
				Step step2 = stepService.create();
				step2.setDescription("TestDescription");
				step2.setPicture("http://www.example.com");
				step2.setRecipe(savedRecipe);
				step2.setStepHints(stepHints2);
				
				stepsTo.add(step1);
				stepsTo.add(step2);
					
				
				Collection<Step> saved=stepService.save(stepsTo);
				steps = stepService.findAll();
				Assert.isTrue(steps.containsAll(saved));
				
				
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveSingleNullStep(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El step a guardar no puede ser nulo");
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
		        
		        
				Collection<StepHint> stepHints = new ArrayList<StepHint>();
			
				Step step = null;
				
				
				
				Step saved=stepService.save(step);
				steps = stepService.findAll();
				Assert.isTrue(steps.contains(saved));
				
				
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveStepNullCollection(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El conjunto de steps a guardar no puede ser nulo");
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
		        
		        Collection<StepHint> stepHints1 = new ArrayList<StepHint>();
		        Collection<StepHint> stepHints2 = new ArrayList<StepHint>();
		        Collection<Step> stepsTo = null;
				
				Collection<Step> saved=stepService.save(stepsTo);
				steps = stepService.findAll();
				Assert.isTrue(steps.containsAll(saved));
				
				
			}
			
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveSingleStepAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para añadir un step a la misma");
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
		        
		        Collection<StepHint> stepHints = new ArrayList<StepHint>();
			
				Step step = stepService.create();
				step.setDescription("TestDescription");
				step.setPicture("http://www.example.com");
				step.setRecipe(savedRecipe);
				step.setStepHints(stepHints);
				
				super.authenticate("userTest2");
				
				Step saved=stepService.save(step);
				steps = stepService.findAll();
				Assert.isTrue(steps.contains(saved));
				
				
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveStepCollectionAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para añadir un conjunto de steps a la misma");
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
		        
		        Collection<StepHint> stepHints1 = new ArrayList<StepHint>();
		        Collection<StepHint> stepHints2 = new ArrayList<StepHint>();
		        Collection<Step> stepsTo = new ArrayList<Step>();
			
				Step step1 = stepService.create();
				step1.setDescription("TestDescription");
				step1.setPicture("http://www.example.com");
				step1.setRecipe(savedRecipe);
				step1.setStepHints(stepHints1);
				
				
				Step step2 = stepService.create();
				step2.setDescription("TestDescription");
				step2.setPicture("http://www.example.com");
				step2.setRecipe(savedRecipe);
				step2.setStepHints(stepHints2);
				
				stepsTo.add(step1);
				stepsTo.add(step2);
					
				super.authenticate("userTest2");
				
				Collection<Step> saved=stepService.save(stepsTo);
				steps = stepService.findAll();
				Assert.isTrue(steps.containsAll(saved));
				
				
			}
			
			//Tests de deletes----------------------------------------
			@Test
			public void testDeleteStep(){
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
		        
		        Collection<StepHint> stepHints = new ArrayList<StepHint>();
			
				Step step = stepService.create();
				step.setDescription("TestDescription");
				step.setPicture("http://www.example.com");
				step.setRecipe(savedRecipe);
				step.setStepHints(stepHints);
				
				
				
				Step stepSaved=stepService.save(step);
				steps = stepService.findAll();
				Assert.isTrue(steps.contains(stepSaved));
				
				stepService.delete(stepSaved);
				Collection<Step> stepsPostDelete =stepService.findAll();
				Assert.isTrue(!stepsPostDelete.contains(stepSaved));
				
				
				super.authenticate(null);
				
				
				
				
			}
			
			@Test
			public void testDeleteStepnull(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El step a borrar no puede ser nulo");
				Step step;
				step=null;
				stepService.delete(step);
				exception=ExpectedException.none();
			}
			@Test
			public void testDeleteStepId0(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El objeto step a borrar debe tener una id valida ");
				Step step;
				step = stepService.create();
				stepService.delete(step);
				exception=ExpectedException.none();
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testDeleteStepAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para borrar un conjunto de steps a la misma");

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
		        
		        Collection<StepHint> stepHints = new ArrayList<StepHint>();
			
				Step step = stepService.create();
				step.setDescription("TestDescription");
				step.setPicture("http://www.example.com");
				step.setRecipe(savedRecipe);
				step.setStepHints(stepHints);
				
				
				
				Step stepSaved=stepService.save(step);
				steps = stepService.findAll();
				Assert.isTrue(steps.contains(stepSaved));
				
				
				super.authenticate("userTest2");
				
				stepService.delete(stepSaved);
				Collection<Step> stepsPostDelete =stepService.findAll();
				Assert.isTrue(!stepsPostDelete.contains(stepSaved));
				
				
				super.authenticate(null);
				
				
				
				
			}
			
			
			
}
