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
import domain.Category;
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
public class CategoryServiceTest extends AbstractTest {
	
	//Service under test-------------------------
			@Autowired
			private CategoryService categoryService;
			@Autowired
			private BelongsService belongsService;
			
			@Autowired
			private RecipeService recipeService;
			
			@Autowired
			private UserService userService;
			
			//Tests----------------------------------------
			@Rule
			  public ExpectedException exception = ExpectedException.none();
			//Tests de creates----------------------------------------
			@Test
			public void testCreateCategory(){
				Category category;
				category = categoryService.create();
				Assert.notNull(category);
				Assert.isNull(category.getBelongs());
				Assert.isNull(category.getDescription());
				Assert.isNull(category.getName());
				Assert.isNull(category.getParentCategory());
				Assert.isNull(category.getSubCategory());
				Assert.isNull(category.getPicture());
				Assert.isNull(category.getTags());
			}
			
			//Tests de saves----------------------------------------
			@Test
			public void testSaveCategory(){
				Category category = categoryService.create();
				Collection<String> tags = new ArrayList<String>();
				Collection<Category> subCategories = new ArrayList<Category>();
				Collection<Belongs> belongs = new ArrayList<Belongs>();
				
				category.setName("category name");
				category.setPicture("http://www.example.com");
				category.setTags(tags);
				category.setDescription("description");
				category.setSubCategory(subCategories);
				category.setBelongs(belongs);
				
				
				Category savedCategory=categoryService.save(category);
				Collection<Category> categories= categoryService.findAll();
				Assert.isTrue(categories.contains(savedCategory));
				
				
				
			}
			
			@Test
			public void testSaveNullCategory(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("La categoria a guardar no puede ser nula");
				Category category = null;
				
				
				Category savedCategory=categoryService.save(category);
				Collection<Category> categories= categoryService.findAll();
				Assert.isTrue(categories.contains(savedCategory));
				
				exception=ExpectedException.none();
				
				
			}
			
			//Tests de deletes----------------------------------------
			@Test
			public void testDeleteCategory(){
				Category category = categoryService.create();
				Collection<String> tags = new ArrayList<String>();
				Collection<Category> subCategories = new ArrayList<Category>();
				Collection<Belongs> belongs = new ArrayList<Belongs>();
				
				
				category.setName("category name");
				category.setPicture("http://www.example.com");
				category.setTags(tags);
				category.setDescription("description");
				category.setSubCategory(subCategories);
				category.setBelongs(belongs);
				
				
				Category savedCategory=categoryService.save(category);
				Collection<Category> categories= categoryService.findAll();
				Assert.isTrue(categories.contains(savedCategory));
				
				categoryService.delete(savedCategory);
				Collection<Category> categoriesPostDelete =categoryService.findAll();
				Assert.isTrue(!categoriesPostDelete.contains(savedCategory));
				
				
			}
			
			@Test
			public void testDeleteCategorynull(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("La categoria a borrar no puede ser nulo");
				Category category;
				category=null;
				categoryService.delete(category);
				exception=ExpectedException.none();
			}
			@Test
			public void testDeleteCategoryId0(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("La categoria a borrar debe tener una id valida ");
				Category category;
				category = categoryService.create();
				categoryService.delete(category);
				exception=ExpectedException.none();
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testDeleteCategoryWithRecipe(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("La categoria a borrar no debe tener recetas");
		
				
				Category category = categoryService.create();
				Collection<String> tags = new ArrayList<String>();
				Collection<Category> subCategories = new ArrayList<Category>();
				Collection<Belongs> belongs = new ArrayList<Belongs>();
				
				
				category.setName("category name");
				category.setPicture("http://www.example.com");
				category.setTags(tags);
				category.setDescription("description");
				category.setSubCategory(subCategories);
				category.setBelongs(belongs);
				
				
				Category savedCategory=categoryService.save(category);
				Collection<Category> categories= categoryService.findAll();
				Assert.isTrue(categories.contains(savedCategory));
				
				Category category2 = categoryService.create();
				Collection<String> tags2 = new ArrayList<String>();
				Collection<Category> subCategories2 = new ArrayList<Category>();
				Collection<Belongs> belongs2 = new ArrayList<Belongs>();
				subCategories2.add(category);
				
				category2.setName("category name2");
				category2.setPicture("http://www.example2.com");
				category2.setTags(tags2);
				category2.setDescription("description2");
				category2.setSubCategory(subCategories2);
				category2.setBelongs(belongs);
				
				
				Category savedCategory2=categoryService.save(category);
				Collection<Category> categories2= categoryService.findAll();
				Assert.isTrue(categories2.contains(savedCategory2));
				
				
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
		        Collection<Belongs> belongsRec = new ArrayList<Belongs>();
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
		        recipe.setBelongs(belongsRec);
		        recipe.setRecipeHints(recipeHints);
		        recipe.setQualifications(qualifications);
		        recipe.setComments(comments);
		        recipe.setUser(savedUser);
		        recipe.setSteps(steps);
		        recipe.setQualifieds(qualifieds);
		        
		        super.authenticate("userTest");
		        
		        
		        Recipe savedRecipe = recipeService.save(recipe);
		        
		        Belongs belongCreated=belongsService.create();
		        belongCreated.setCategory(savedCategory);
		        belongCreated.setRecipe(savedRecipe);
		        Belongs belongSaved=belongsService.save(belongCreated);
		        
				
				
				categoryService.delete(savedCategory);
				Collection<Category> categoriesPostDelete2 =categoryService.findAll();
				Assert.isTrue(!categoriesPostDelete2.contains(savedCategory2));
				exception=ExpectedException.none();
				
				
			}
			
//			@Test
//			public void testDeleteCategoryWithSubCategoryWithRecipe(){
//				exception.expect(IllegalArgumentException.class);
//				exception.expectMessage("La categoria a borrar no debe tener recetas");
//				
//				//Creamos la recipe para el qualified
//				Recipe recipe = recipeService.create();
//		        recipe.setTicker(recipeService.createTicker());
//		        recipe.setTitle("Titulo de la receta");
//		        recipe.setSummary("Resumen de la receta");
//		        Date date = new Date(System.currentTimeMillis()-1000000000);
//		        recipe.setAuthorMoment(date);
//		        Date date2 = new Date(System.currentTimeMillis()-100000);
//		        recipe.setLastUpdate(date2);
//		        Collection<String> pictures = new ArrayList<String>();
//		        pictures.add("http://www.urldelaimagen.com/foto.jpg");
//		        recipe.setPictures(pictures);
//		        recipe.setIsCopy(false);
//		        
//		        Collection<Quantity> quantitiesRecipe = new ArrayList<Quantity>();
//		        Collection<Belongs> belongsRec = new ArrayList<Belongs>();
//		        Collection<RecipeHint> recipeHints = new ArrayList<RecipeHint>();
//		        Collection<Qualification> qualifications = new ArrayList<Qualification>();
//		        Collection<Comment> comments = new ArrayList<Comment>();
//		        Collection<Step> steps = new ArrayList<Step>();
//		        Collection<Qualified> qualifieds = new ArrayList<Qualified>();
//		        
//		        User user = userService.create();
//		        user.setName("Nombre del usuario");
//		        user.setSurname("Apellidos del usuario");
//		        user.setEmail("email@delusuario.com");
//		        Collection<Folder> folders = new ArrayList<Folder>();
//		        Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
//		        Collection<Attend> attends = new ArrayList<Attend>();
//		        
//		        user.setFolders(folders);
//		        user.setSocialIdentities(socialIdentities);
//		        user.setAttends(attends);
//		        
//		        Collection<Authority> authorities = new ArrayList<Authority>();
//		        Authority authority = new Authority();
//		        authority.setAuthority("USER");
//		        authorities.add(authority);
//		         
//		        UserAccount userAcc = new UserAccount();
//		        userAcc.setPassword("userTest");
//		        userAcc.setUsername("userTest");
//		        userAcc.setAuthorities(authorities);
//		        user.setUserAccount(userAcc);
//		        
//		        
//		        User savedUser = userService.save(user);
//		        recipe.setQuantities(quantitiesRecipe);
//		        recipe.setBelongs(belongsRec);
//		        recipe.setRecipeHints(recipeHints);
//		        recipe.setQualifications(qualifications);
//		        recipe.setComments(comments);
//		        recipe.setUser(savedUser);
//		        recipe.setSteps(steps);
//		        recipe.setQualifieds(qualifieds);
//		        
//		        super.authenticate("userTest");
//		        
//		        
//		        Recipe savedRecipe = recipeService.save(recipe);
//		
//				
//				Category category = categoryService.create();
//				Collection<String> tags = new ArrayList<String>();
//				Collection<Category> subCategories = new ArrayList<Category>();
//				Collection<Belongs> belongs = new ArrayList<Belongs>();
//				
//				
//				category.setName("category name");
//				category.setPicture("http://www.example.com");
//				category.setTags(tags);
//				category.setDescription("description");
//				category.setSubCategory(subCategories);
//				category.setBelongs(belongs);
//				
//				
//				Category savedCategory=categoryService.save(category);
//				Collection<Category> categories= categoryService.findAll();
//				Assert.isTrue(categories.contains(savedCategory));
//				
//				Belongs belongCreated=belongsService.createBelongs(savedCategory, savedRecipe);
//			    Belongs belongSaved=belongsService.save(belongCreated);
//			    
//			    Collection<Belongs> belongsToAdd=new ArrayList<Belongs>();
//			     
//				
//				
//				Recipe savedRecipe2= recipeService.save(savedRecipe);
//				
//				Category savedCategory2= categoryService.save(savedCategory);
//			    
//				Category category2 = categoryService.create();
//				Collection<String> tags2 = new ArrayList<String>();
//				Collection<Category> subCategories2 = new ArrayList<Category>();
//				Collection<Belongs> belongs2 = new ArrayList<Belongs>();
//				subCategories2.add(savedCategory);
//				
//				category2.setName("category name2");
//				category2.setPicture("http://www.example2.com");
//				category2.setTags(tags2);
//				category2.setDescription("description2");
//				category2.setSubCategory(subCategories2);
//				category2.setBelongs(belongs);
//				
//				
//				Category savedCategory3=categoryService.save(category2);
//				Collection<Category> categories2= categoryService.findAll();
//				Assert.isTrue(categories2.contains(savedCategory));
//				
//			
//				categoryService.delete(savedCategory3);
//				Collection<Category> categoriesPostDelete2 =categoryService.findAll();
//				Assert.isTrue(!categoriesPostDelete2.contains(savedCategory3));
//				
//				
//			}
			/*@Test
			public void testDeleteCategoryWithSubCategoryWithRecipe(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("La categoria a borrar no debe tener subcategorias con recetas");
				
				
				Category category = categoryService.create();
				Collection<String> tags = new ArrayList<String>();
				Collection<Belongs> belongs = new ArrayList<Belongs>();
				Collection<Category> subCategories=new ArrayList<Category>();
		
				subCategories.add(categoryService.findOne(73));
				System.out.println(categoryService.findOne(73).getBelongs().size());
				
				category.setName("category name");
				category.setPicture("http://www.example.com");
				category.setTags(tags);
				category.setDescription("description");
				category.setBelongs(belongs);
				category.setSubCategory(subCategories);
				
				Category savedCategory=categoryService.save(category);
				
				System.out.println(savedCategory.getSubCategory());
				for(Category c : savedCategory.getSubCategory()){
					System.out.println(c.getName());
					System.out.println(c.getBelongs());
				}
				categoryService.delete(savedCategory);
				exception=ExpectedException.none();
				

			}*/
			

}
