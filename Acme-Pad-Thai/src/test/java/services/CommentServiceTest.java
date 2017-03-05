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
public class CommentServiceTest extends AbstractTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//Services under test-----------------------------
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	//Tests
	
	@SuppressWarnings("unused")
	@Test
	public void testCreate(){
		Comment comment;
		comment = commentService.create();
	}
	
	@SuppressWarnings("unused")
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
		
		
		
		Comment comment = commentService.create();
		comment.setTitle("Titulo del comentario");
		comment.setText("Texto del comentario");
		comment.setStars(8);
		Date date3 = new Date(System.currentTimeMillis()-1000000000);
		comment.setDateCreation(date3);
		comment.setRecipe(savedRecipe);
		comment.setCustomer(savedUser);
		
		Comment savedComment = commentService.save(comment);
		
		super.unauthenticate();
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testModify(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se pueden modificar comentarios");
		
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
		
		
		Comment comment = commentService.create();
		comment.setTitle("Titulo del comentario");
		comment.setText("Texto del comentario");
		comment.setStars(8);
		Date date3 = new Date(System.currentTimeMillis()-1000000000);
		comment.setDateCreation(date3);
		comment.setRecipe(savedRecipe);
		comment.setCustomer(savedUser);
		
		Comment savedComment = commentService.save(comment);
		
		savedComment.setTitle("Titulo modificado");
		Comment modifiedComment = commentService.save(savedComment);
		
		super.unauthenticate();
		exception=ExpectedException.none();	
	}
	
	@Test
	public void testSaveNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El comentario no puede ser nulo");
		
		Comment comment = null;
		commentService.save(comment);
		
		exception=ExpectedException.none();	
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveTitleNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El titulo no puede ser nulo ni estar vacío");
		
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
		
		
		
		Comment comment = commentService.create();
		comment.setTitle("		");
		comment.setText("Texto del comentario");
		comment.setStars(8);
		Date date3 = new Date(System.currentTimeMillis()-1000000000);
		comment.setDateCreation(date3);
		comment.setRecipe(savedRecipe);
		comment.setCustomer(savedUser);
		
		Comment savedComment = commentService.save(comment);
		
		super.unauthenticate();
		exception=ExpectedException.none();	
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSaveOtherUser(){
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
		
		Comment comment = commentService.create();
		comment.setTitle("Titulo del comentario");
		comment.setText("Texto del comentario");
		comment.setStars(8);
		Date date3 = new Date(System.currentTimeMillis()-1000000000);
		comment.setDateCreation(date3);
		comment.setRecipe(savedRecipe);
		comment.setCustomer(savedUser);
		
		Comment savedComment = commentService.save(comment);
		
		super.unauthenticate();
		
		super.authenticate("otherUser");
		
		comment.setTitle("Cambio de titulo");
		
		Comment savedComment2 = commentService.save(comment);
		
		super.unauthenticate();
		
		
		
		exception=ExpectedException.none();
	}

}
