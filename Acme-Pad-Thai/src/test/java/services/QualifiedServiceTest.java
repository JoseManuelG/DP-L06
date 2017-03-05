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
import domain.Contest;
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
public class QualifiedServiceTest extends AbstractTest {
	
	//Service under test-------------------------
			@Autowired
			private QualifiedService qualifiedService;
			@Autowired
			private RecipeService recipeService;
			@Autowired
			private ContestService contestService;
			@Autowired
			private UserService userService;
			
			//Tests----------------------------------------
			@Rule
			  public ExpectedException exception = ExpectedException.none();
			
			//Tests de creates----------------------------------------
			@SuppressWarnings("unused")
			@Test
			public void testCreateQualified(){
				//Creamos la recipe para el qualified
				Recipe recipe = recipeService.create();
		        recipe.setTicker(recipeService.createTicker());
		        recipe.setTitle("Titulo de la receta");
		        recipe.setSummary("Resumen de la receta");
		        Date date = new Date();
		        recipe.setAuthorMoment(date);
		        Collection<String> pictures = new ArrayList<String>();
		        recipe.setIsCopy(false);
		        Collection<Quantity> quantitiesRecipe = new ArrayList<Quantity>();
		        Collection<Belongs> belongs = new ArrayList<Belongs>();
		        Collection<RecipeHint> recipeHints = new ArrayList<RecipeHint>();
		        Collection<Qualification> qualifications = new ArrayList<Qualification>();
		        Collection<Comment> comments = new ArrayList<Comment>();
		        Collection<Step> steps = new ArrayList<Step>();
		        Collection<Qualified> qualifieds = new ArrayList<Qualified>();
		        
		        //Creamos el contest para el qualified
		        Contest contest =contestService.create();
		        Date closeFutureTime= new Date(System.currentTimeMillis()+1000000000);
		        Date futureTime= new Date(System.currentTimeMillis()+1800000000);
		        contest.setOpeningTime(closeFutureTime);
		        contest.setClosingTime(futureTime);
		        contest.setQualifieds(qualifieds);
		        contest.setTittle("RecipeTittle");
		            
				Qualified qualified;
						
				qualified = qualifiedService.create();
				qualified.setContest(contest);
				qualified.setRecipe(recipe);
				Assert.notNull(qualified);
				Assert.isNull(qualified.getWinner());
		
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testCreateQualifiedNullRecipe(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El recipe a relacionar con el qualified debe ser valido");
				//Creamos la recipe para el qualified
				Recipe recipe = null;
		        
		        Collection<Qualified> qualifieds = new ArrayList<Qualified>();
		        
		        //Creamos el contest para el qualified
		        Contest contest =contestService.create();
		        Date closeFutureTime= new Date(System.currentTimeMillis()+1000000000);
		        Date futureTime= new Date(System.currentTimeMillis()+1800000000);
		        contest.setOpeningTime(closeFutureTime);
		        contest.setClosingTime(futureTime);
		        contest.setQualifieds(qualifieds);
		        contest.setTittle("RecipeTittle");
		            
				Qualified qualified;
						
				qualified = qualifiedService.create();
				qualified.setContest(contest);
				qualified.setRecipe(recipe);
				Assert.notNull(qualified);
				Assert.isNull(qualified.getWinner());
				
				Qualified savedQualified=qualifiedService.save(qualified);
			}
			@SuppressWarnings("unused")
			@Test
			public void testCreateQualifiedNullContest(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El contest a relacionar con el qualified debe ser valido");
				//Creamos la recipe para el qualified
				Recipe recipe = recipeService.create();
		        recipe.setTicker(recipeService.createTicker());
		        recipe.setTitle("Titulo de la receta");
		        recipe.setSummary("Resumen de la receta");
		        Date date = new Date();
		        recipe.setAuthorMoment(date);
		        Collection<String> pictures = new ArrayList<String>();
		        recipe.setIsCopy(false);
		        Collection<Quantity> quantitiesRecipe = new ArrayList<Quantity>();
		        Collection<Belongs> belongs = new ArrayList<Belongs>();
		        Collection<RecipeHint> recipeHints = new ArrayList<RecipeHint>();
		        Collection<Qualification> qualifications = new ArrayList<Qualification>();
		        Collection<Comment> comments = new ArrayList<Comment>();
		        Collection<Step> steps = new ArrayList<Step>();
		        Collection<Qualified> qualifieds = new ArrayList<Qualified>();
		        
		        //Creamos el contest para el qualified
		        Contest contest =null;
		            
				Qualified qualified;
						
				qualified = qualifiedService.create();
				qualified.setContest(contest);
				qualified.setRecipe(recipe);
				Assert.notNull(qualified);
				Assert.isNull(qualified.getWinner());
				
				Qualified savedQualified=qualifiedService.save(qualified);
			}
			
			//Tests de saves----------------------------------------
			@Test
			public void testSaveQualified(){
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
		        
		           
		        
 		        
		        //Creamos el contest para el qualified
		        Contest contest =contestService.create();
		        Date closeFutureTime= new Date(System.currentTimeMillis()+100000000);
		        Date futureTime= new Date(System.currentTimeMillis()+180000000);
		        contest.setOpeningTime(closeFutureTime);
		        contest.setClosingTime(futureTime);
		        contest.setQualifieds(qualifieds);
		        contest.setTittle("RecipeTittle");
		        
		        Contest savedContest = contestService.save(contest);
		        
		            
				Qualified qualified = qualifiedService.create();
				qualified.setContest(savedContest);
				qualified.setRecipe(savedRecipe);
						
				 
				
				Qualified savedQualified=qualifiedService.save(qualified);
				Assert.isTrue(savedQualified.getId()!=0);
				Qualified quali = qualifiedService.findOne(savedQualified.getId());
				Assert.isTrue(quali==savedQualified);
				
				super.authenticate(null);
			}
			
			
			@Test
			public void testSaveNullQualified(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El objeto a guardar no debe ser nulo");
				Qualified qualified =null;
						
				 
				
				Qualified savedQualified=qualifiedService.save(qualified);
				Assert.isTrue(savedQualified.getId()!=0);
				Qualified quali = qualifiedService.findOne(savedQualified.getId());
				Assert.isTrue(quali==savedQualified);
				
				super.authenticate(null);
			}
			
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveQualifiedAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El usuario debe ser el autor de la receta para presentarla a concurso");
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
		        
		           
		        
 		        
		        //Creamos el contest para el qualified
		        Contest contest =contestService.create();
		        Date closeFutureTime= new Date(System.currentTimeMillis()+100000000);
		        Date futureTime= new Date(System.currentTimeMillis()+180000000);
		        contest.setOpeningTime(closeFutureTime);
		        contest.setClosingTime(futureTime);
		        contest.setQualifieds(qualifieds);
		        contest.setTittle("RecipeTittle");
		        
		        Contest savedContest = contestService.save(contest);
		        
		        
		        super.authenticate("userTest2");
		        
		        Qualified qualified = qualifiedService.create();
				qualified.setContest(savedContest);
				qualified.setRecipe(savedRecipe);
						
				 
				
				Qualified savedQualified=qualifiedService.save(qualified);
				Assert.isTrue(savedQualified.getId()!=0);
				Qualified quali = qualifiedService.findOne(savedQualified.getId());
				Assert.isTrue(quali==savedQualified);
				
				super.authenticate(null);
			}
			
			//Tests de deletes----------------------------------------
			@Test
			public void testDeleteQualified(){
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
	        
	           
	        
		        
	        //Creamos el contest para el qualified
	        Contest contest =contestService.create();
	        Date closeFutureTime= new Date(System.currentTimeMillis()+100000000);
	        Date futureTime= new Date(System.currentTimeMillis()+180000000);
	        contest.setOpeningTime(closeFutureTime);
	        contest.setClosingTime(futureTime);
	        contest.setQualifieds(qualifieds);
	        contest.setTittle("RecipeTittle");
	        
	        Contest savedContest = contestService.save(contest);
	        
	            
			Qualified qualified = qualifiedService.create();
			qualified.setContest(savedContest);
			qualified.setRecipe(savedRecipe);
					
			 
			
			Qualified qualifiedSaved=qualifiedService.save(qualified);
			//Collection<Qualified> qualifiedsPostSave =qualifiedService.findAll();
			Qualified savedQualified = qualifiedService.findOne(qualifiedSaved.getId());
			Assert.isTrue(savedQualified==qualifiedSaved);
			//Assert.isTrue(qualifiedsPostSave.contains(qualifiedSaved));
			
			
			qualifiedService.delete(qualifiedSaved);
			Qualified exists=qualifiedService.findOne(qualifiedSaved.getId());
			Assert.isNull(exists);
//			Collection<Qualified> qualifiedsPostDelete =qualifiedService.findAll();
//			Assert.isTrue(!qualifiedsPostDelete.contains(qualifiedSaved));
			
			
			super.authenticate(null);
			
			}
			
			@Test
			public void testDeleteQualifiednull(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El objeto a borrar no debe ser nulo");				          
				Qualified qualified =null;
				qualifiedService.delete(qualified);
				exception=ExpectedException.none();
			}
			
			
			@Test
			public void testDeleteQualifiedId0(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("La id del objeto a borrar debe ser valida");
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
		        
		           
		        
			        
		        //Creamos el contest para el qualified
		        Contest contest =contestService.create();
		        Date closeFutureTime= new Date(System.currentTimeMillis()+100000000);
		        Date futureTime= new Date(System.currentTimeMillis()+180000000);
		        contest.setOpeningTime(closeFutureTime);
		        contest.setClosingTime(futureTime);
		        contest.setQualifieds(qualifieds);
		        contest.setTittle("RecipeTittle");
		        
		        Contest savedContest = contestService.save(contest);
		        
		            
		        Qualified qualified = qualifiedService.create();
				qualified.setContest(savedContest);
				qualified.setRecipe(savedRecipe);
				qualifiedService.delete(qualified);
				exception=ExpectedException.none();
			}
}
