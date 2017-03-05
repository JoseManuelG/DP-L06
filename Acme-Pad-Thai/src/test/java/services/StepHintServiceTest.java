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
public class StepHintServiceTest extends AbstractTest {

			
			//Service under test-------------------------
			@Autowired
			private StepService stepService;
			@Autowired
			private RecipeService recipeService;
			@Autowired
			private UserService userService;
			
			@Autowired
			private StepHintService stepHintService;
					
			//Tests----------------------------------------
			@Rule
			  public ExpectedException exception = ExpectedException.none();
			
			//Tests de creates----------------------------------------
			@Test
			public void testCreateStepHint(){
				StepHint stepHint;
				stepHint = stepHintService.create();
				Assert.notNull(stepHint);
				Assert.isNull(stepHint.getHint());
				Assert.isNull(stepHint.getStep());

			}
			
			//Tests de saves----------------------------------------
			@Test
			public void testSaveSingleStepHint(){
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
				
				StepHint stepHint = stepHintService.create();
			    stepHint.setHint("StepHintTest");
			    stepHint.setStep(saved);
			    StepHint stepHintSaved=stepHintService.save(stepHint);
			    step.setStepHint(stepHintSaved);
			    Assert.isTrue(step.getStepHints().contains(stepHintSaved));

			}
			
			
			
			
			
			@Test
			public void testSaveStepHintCollection(){
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
				
				StepHint stepHint1 = stepHintService.create();
			    stepHint1.setHint("StepHintTest1");
			    stepHint1.setStep(saved);
			    stepHints.add(stepHint1);
			    
			    StepHint stepHint2 = stepHintService.create();
			    stepHint2.setHint("StepHintTest2");
			    stepHint2.setStep(saved);
			    stepHints.add(stepHint2);
			    
			    Collection<StepHint> stepHintsSaved=stepHintService.save(stepHints);
			    step.setStepHints(stepHintsSaved);
			    Assert.isTrue(step.getStepHints().containsAll(stepHintsSaved));

			}
			
			@Test
			public void testSaveSingleNullStepHint(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El stepHint a guardar no puede ser nulo");
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
				
				StepHint stepHint = null;
			    
			   
			    StepHint stepHintSaved=stepHintService.save(stepHint);
			    step.setStepHint(stepHintSaved);
			    Assert.isTrue(step.getStepHints().contains(stepHintSaved));

			}
			
			
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveStepHintNullCollection(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El conjunto de stepHints a guardar no puede ser nulo");
				
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
		        
		        Collection<StepHint> stepHints = null;
			
				
			    
			    Collection<StepHint> stepHintsSaved=stepHintService.save(stepHints);
			    

			}
			
			
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveSingleStepHintAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para añadir un stepHint a la misma");
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
				
				super.authenticate("userTest2");
				
				StepHint stepHint = stepHintService.create();
			    stepHint.setHint("StepHintTest");
			    stepHint.setStep(saved);
			    StepHint stepHintSaved=stepHintService.save(stepHint);
			    step.setStepHint(stepHintSaved);
			    Assert.isTrue(step.getStepHints().contains(stepHintSaved));

			}
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveStepHintCollectionAnotherUser(){			
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para añadir un conjunto de stepHints a la misma");
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
								
				Step saved=stepService.save(step);
				steps = stepService.findAll();
				Assert.isTrue(steps.contains(saved));
				
				StepHint stepHint1 = stepHintService.create();
			    stepHint1.setHint("StepHintTest1");
			    stepHint1.setStep(saved);
			    stepHints.add(stepHint1);
			    
			    StepHint stepHint2 = stepHintService.create();
			    stepHint2.setHint("StepHintTest2");
			    stepHint2.setStep(saved);
			    stepHints.add(stepHint2);
			    
			    super.authenticate("userTest2");
			    
			    Collection<StepHint> stepHintsSaved=stepHintService.save(stepHints);
			    step.setStepHints(stepHintsSaved);
			    Assert.isTrue(step.getStepHints().containsAll(stepHintsSaved));

			}
			
			//Tests de deletes----------------------------------------
			public void testDeleteStepHint(){
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
		        Collection<StepHint> stepHintsPostDelete = new ArrayList<StepHint>();
			
				Step step = stepService.create();
				step.setDescription("TestDescription");
				step.setPicture("http://www.example.com");
				step.setRecipe(savedRecipe);
				step.setStepHints(stepHints);
								
				Step saved=stepService.save(step);
				steps = stepService.findAll();
				Assert.isTrue(steps.contains(saved));
				
				StepHint stepHint = stepHintService.create();
			    stepHint.setHint("StepHintTest");
			    stepHint.setStep(saved);
			    StepHint stepHintSaved=stepHintService.save(stepHint);
			    step.setStepHint(stepHintSaved);
			    Assert.isTrue(step.getStepHints().contains(stepHintSaved));
			    
			    stepHintService.delete(stepHintSaved);
			    stepHintsPostDelete =stepHintService.findAll();
			    Assert.isTrue(!stepHintsPostDelete.contains(stepHintSaved));

				
			}
			
			@Test
			public void testDeleteStepHintnull(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El objeto stepHint a borrar no debe ser nulo");
				StepHint stepHint;
				stepHint=null;
				stepHintService.delete(stepHint);
				exception=ExpectedException.none();
			}
			@Test
			public void testDeleteStepHintId0(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El objeto stepHint a borrar debe tener una id valida ");
				StepHint stepHint;
				stepHint = stepHintService.create();
				stepHintService.delete(stepHint);
				exception=ExpectedException.none();
			}
			
			@SuppressWarnings("unused")
			public void testDeleteStepHintAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para eliminar un stepHint de la misma");

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
		        Collection<StepHint> stepHintsPostDelete = new ArrayList<StepHint>();
			
				Step step = stepService.create();
				step.setDescription("TestDescription");
				step.setPicture("http://www.example.com");
				step.setRecipe(savedRecipe);
				step.setStepHints(stepHints);
								
				Step saved=stepService.save(step);
				steps = stepService.findAll();
				Assert.isTrue(steps.contains(saved));
				
				StepHint stepHint = stepHintService.create();
			    stepHint.setHint("StepHintTest");
			    stepHint.setStep(saved);
			    StepHint stepHintSaved=stepHintService.save(stepHint);
			    step.setStepHint(stepHintSaved);
			    Assert.isTrue(step.getStepHints().contains(stepHintSaved));
			    
			    
			    super.authenticate("userTest2");
			    
			    stepHintService.delete(stepHintSaved);
			    stepHintsPostDelete =stepHintService.findAll();
			    Assert.isTrue(!stepHintsPostDelete.contains(stepHintSaved));

				
			}
}
