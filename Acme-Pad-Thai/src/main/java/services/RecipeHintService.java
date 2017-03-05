package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecipeHintRepository;
import security.LoginService;
import security.UserAccount;
import domain.Recipe;
import domain.RecipeHint;

@Service
@Transactional
public class RecipeHintService {
	

	//Managed Repository----------------------------------
	
	@Autowired
	private RecipeHintRepository recipeHintRepository;
	
	//SupportingServices-----------------------------------
	
	@Autowired
	private LoginService loginService;
	
	//Constructors----------------------------------------
	
	public RecipeHintService(){
		super();
	}
	
	//Simple Crud methods---------------------------------
	public RecipeHint create(){
		RecipeHint result;
		
		result=new RecipeHint();
		
		return result;
	}
	
	public Collection<RecipeHint> findAll(){
		Collection<RecipeHint> result;
		
		result= recipeHintRepository.findAll();
		Assert.notNull(result,"El conjunto de elementos no puede ser nulo");
		
		return result;
	}
	
	public RecipeHint findOne(int recipeHintId){
		RecipeHint result;
		result = recipeHintRepository.findOne(recipeHintId);
		return result;
	}

	@SuppressWarnings("static-access")
	public RecipeHint save(RecipeHint recipeHint){
		Assert.notNull(recipeHint,"El recipeHint a guardar no puede ser nulo");

		
		UserAccount userAccount=recipeHint.getRecipe().getUser().getUserAccount();
		UserAccount loginUserAccount=loginService.getPrincipal();
		Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para añadir un recipeHint a la misma");
		
		RecipeHint result;
		result= recipeHintRepository.save(recipeHint);
		
		return result;
	}

	@SuppressWarnings("static-access")
	public Collection<RecipeHint> save(Collection<RecipeHint> recipeHints){
		Assert.notNull(recipeHints,"El conjunto de recipeHints a guardar no puede ser nulo");
		Collection<RecipeHint> result=new ArrayList<RecipeHint>();
		RecipeHint arrayRecipeHint[] =new RecipeHint[recipeHints.size()];
		arrayRecipeHint= recipeHints.toArray(arrayRecipeHint);
		
		if(!recipeHints.isEmpty()){
			Recipe recipe=arrayRecipeHint[0].getRecipe();
			UserAccount userAccount=recipe.getUser().getUserAccount();
			UserAccount loginUserAccount=loginService.getPrincipal();
			Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para añadir un conjunto de recipeHints a la misma");
			
			
			
			for(RecipeHint s : recipeHints){
				result.add(save(s));
			}
		}
		
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(RecipeHint recipeHint){
		Assert.notNull(recipeHint,"El recipeHint a guardar no puede ser nulo");
		Assert.isTrue(recipeHint.getId() !=0,"El objeto recipeHint a borrar debe tener una id valida ");
		
		UserAccount userAccount=recipeHint.getRecipe().getUser().getUserAccount();
		UserAccount loginUserAccount=loginService.getPrincipal();
		Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para borrar un recipeHint a la misma");
		
		recipeHintRepository.delete(recipeHint.getId());
	}
	
	//OtherBusinessesModels-------------------------------
	
	public Collection<RecipeHint> copyRecipeHintsForCopyOfRecipe(Recipe recipe, Recipe oldRecipe){
	    Collection<RecipeHint> recipeHints = recipeHintRepository.findRecipeHintsByRecipe(oldRecipe.getId());
	    Collection<RecipeHint> result = new LinkedList<RecipeHint>();
	    for(RecipeHint rh : recipeHints){
	    	RecipeHint recipeHintCloned = create();
	    	recipeHintCloned.setHint(rh.getHint());
			
			recipeHintCloned.setRecipe(recipe);
			result.add(recipeHintCloned);
	    }
	    recipe.setRecipeHints(result);
	    return result;
	  }

	public Collection<RecipeHint> findRecipeHintsByRecipe(Recipe recipe){
		Collection<RecipeHint> result = recipeHintRepository.findRecipeHintsByRecipe(recipe.getId());
		return result;
	}
}
