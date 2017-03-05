package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Belongs;
import domain.Category;
import domain.Recipe;

@Service
@Transactional
public class CategoryService {
	
	//Managed Repository----------------------------------
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//SupportingServices-----------------------------------
	
	@Autowired
	private RecipeService recipeService;
	
	//Constructors----------------------------------------
	
	public CategoryService(){
		super();
	}
	
	//Simple Crud methods---------------------------------
	public Category create(){
		Category result;
		
		result=new Category();
		Collection<String> tags=new LinkedList<String>();
		Collection<Category> subCategory=new LinkedList<Category>();
		Collection<Belongs> belongs=new LinkedList<Belongs>();
		result.setTags(tags);
		result.setSubCategory(subCategory);
		result.setBelongs(belongs);
		return result;
	}
	
	public Collection<Category> findAll(){
		Collection<Category> result;
		
		result= categoryRepository.findAll();
		Assert.notNull(result,"Las categorias devueltas no puede ser nulas");
		
		return result;
	}
	
	public Category findOne(int categoryId){
		Category result;
		result = categoryRepository.findOne(categoryId);
		return result;
	}
	
	public Category save(Category category){
		Assert.notNull(category,"La categoria a guardar no puede ser nula");
		if(category.getId()!=0){
					Assert.isTrue(getCategoriesNoCicleAux(category).contains(category),"La categoria a añadir no puede generar un ciclo");
		}
		Category result;
		result= categoryRepository.save(category);
		
		return result;
	}
	public  Collection<Recipe> findRecipesForCategory(Category category){
				Collection<Recipe> result = categoryRepository.findRecipesForCategoryId(category.getId());
				return result;
			}
			
			public Collection<Category> getCategoriesNoCicle(Category category){
				Collection<Category> aux= findAll();
				Collection<Category> result= new LinkedList<Category>();
				result.addAll(aux);
				result.removeAll(getFullTree(category));
				result.remove(category);
				
				return result;
			}
			
			public Collection<Category> getCategoriesNoCicleAux(Category category){
				Collection<Category> aux= findAll();
				Collection<Category> result= new LinkedList<Category>();
				result.addAll(aux);
				result.removeAll(getFullTree(category));
				
				return result;
			}
			
		
			
			public Collection<Category> getFullTree(Category category){
				Collection<Category> result= categoryRepository.findCategoriesByRecipe(category.getId());
				Collection<Category> toFill= new LinkedList<Category>();
				toFill.addAll(result);
				Collection<Category> aux= categoryRepository.findCategoriesByRecipe(category.getId());
				for(Category c: aux){
					toFill.addAll(getFullTree(c));
				}
				return toFill;
			}
	public void delete(Category category){
		

		Assert.notNull(category,"La categoria a borrar no puede ser nulo");
		Assert.isTrue(category.getId() !=0,"La categoria a borrar debe tener una id valida ");
		Collection<Recipe> recipes = new ArrayList<Recipe>();
		
		recipes=recipeService.findRecipesByCategory(category);
		
		
		
		
		Assert.isTrue(checkCategoryDelete(category),"La categoria a borrar no debe tener subcategorias con recetas");
		Assert.isTrue(recipes.isEmpty(),"La categoria a borrar no debe tener recetas");
		categoryRepository.delete(category);
		
		
	}
	
	//OtherBusinessesModels-------------------------------
	
	public Collection<Category> findCategoriesByRecipe(Recipe recipe){
		Collection<Category> result = categoryRepository.findCategoriesByRecipe(recipe.getId());
		return result;
	}
	
	public boolean checkSubCategoriesHaveRecipes(Collection<Category> categories){
		boolean res = true;
		
		for(Category c : categories){
			if(c.getBelongs().size()>0){
				res=false;
				break;
			}
		}
		
		
		return res;
	}
	
	
	public boolean checkSubCategorxiesHaveRecipes(Collection<Category> categories){
		boolean res=false;
		Collection<Recipe> recipes= new ArrayList<Recipe>();
		Collection<Category> subCategories= new ArrayList<Category>();
		
		if(categories.isEmpty()){
			res=true;
		}
			for(Category c:categories){	
			
			recipes=recipeService.findRecipesByCategory(c);
			if(recipes.isEmpty()){	
				res=true;
				break;
			}
			subCategories =categoryRepository.findSubCategoryId(c.getId());
			if(!subCategories.isEmpty()){
				res=checkSubCategoriesHaveRecipes(subCategories);
				break;
			}
				
		}
		return res;
			
			
		
	}
	
	public Boolean checkCategoryDelete(Category c){
		Boolean res=true;
		
		if(!c.getBelongs().isEmpty()){
			res=false;
			return res;
		}
		if(!c.getSubCategory().isEmpty()){
			Collection<Category> sC=categoryRepository.findSubCategoryId(c.getId());
			for(Category c2: sC){
				res=checkCategoryDelete(c2);
				if(!res){
					break;
				}
			}
		}
		
		
		return res;
	}
		
	public  Category addTag(Category category, String newTag ){
		ArrayList<String> tags = new ArrayList<String>();
		
		tags.addAll(category.getTags());
		tags.add(newTag);
		category.setTags(tags);
		return category;
	}
	public  Category removeTag(Category category, Integer tagIndex){
		ArrayList<String> tags = new ArrayList<String>();
		
		tags.addAll(category.getTags());
		String tagToDelete= tags.get(Integer.valueOf(tagIndex));
		tags.remove(tagToDelete);
		category.setTags(tags);
		return category;
	}
	

}
