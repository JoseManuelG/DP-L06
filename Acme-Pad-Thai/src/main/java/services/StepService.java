package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StepRepository;
import security.LoginService;
import security.UserAccount;
import domain.Recipe;
import domain.Step;

@Service
@Transactional
public class StepService {
	

	//Managed Repository----------------------------------
	
	@Autowired
	private StepRepository stepRepository;
	
	//SupportingServices-----------------------------------
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private StepHintService stepHintService;
	
	
	//Constructors----------------------------------------
	
	public StepService(){
		super();
	}
	
	//Simple Crud methods---------------------------------
	public Step create(){
		Step result;
		
		result=new Step();
		
		return result;
	}
	
	public Collection<Step> findAll(){
		Collection<Step> result;
		
		
		result= stepRepository.findAll();
		Assert.notNull(result,"El conjunto de elementos no puede ser nulo");
		
		return result;
	}
	
	public Step findOne(int stepId){
		Step result;
		result = stepRepository.findOne(stepId);
		return result;
	}

	@SuppressWarnings("static-access")
	public Step save(Step step){
		Assert.notNull(step,"El step a guardar no puede ser nulo");
		Recipe recipe=step.getRecipe();
		Step result;
		
		UserAccount userAccount=recipe.getUser().getUserAccount();
		UserAccount loginUserAccount=loginService.getPrincipal();
		Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para añadir un step a la misma");
		if(step.getId()==0){
			Integer aux = recipeService.countStepsForRecipe(recipe);
			step.setNumber(aux+1);
		}
		result= stepRepository.save(step);
		
		return result;
	}

	@SuppressWarnings("static-access")
	public Collection<Step> save(Collection<Step> steps){
		Assert.notNull(steps,"El conjunto de steps a guardar no puede ser nulo");
		Collection<Step> result=new ArrayList<Step>();
		Step arrayStep[] =new Step[steps.size()];
		arrayStep= steps.toArray(arrayStep);
		
		System.out.println(steps);
		if(!steps.isEmpty()){
			Recipe recipe=arrayStep[0].getRecipe();
			UserAccount userAccount=recipe.getUser().getUserAccount();
			UserAccount loginUserAccount=loginService.getPrincipal();
			Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para añadir un conjunto de steps a la misma");
			Step auxStep=this.create();
			int aux = recipeService.countStepsForRecipe(recipe);
			for(Step s : steps){
				//auxStep.setDescription(s.getDescription());
				//auxStep.setPicture(s.getPicture());
				//auxStep.setRecipe(s.getRecipe());
				//auxStep.setStepHints(s.getStepHints());
				//auxStep.setNumber(aux);
				//result.add(auxStep);
				//save(auxStep);
				//aux++;
				save(s);

			}
		}
		
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Step step){
		Assert.notNull(step,"El step a borrar no puede ser nulo");
		Assert.isTrue(step.getId() !=0,"El objeto step a borrar debe tener una id valida ");
		
		UserAccount userAccount=step.getRecipe().getUser().getUserAccount();
		UserAccount loginUserAccount=loginService.getPrincipal();
		Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para borrar un conjunto de steps a la misma");
		
		
		stepRepository.delete(step.getId());
		
	}
	
	//OtherBusinessesModels-------------------------------
	
	public Collection<Step> copyStepsForCopyOfRecipe(Recipe recipe, Recipe oldRecipe){
	    Collection<Step> steps = recipeService.findStepsByRecipe(oldRecipe);
	    Collection<Step> result = new LinkedList<Step>();
	    for(Step s : steps){
	    	Step stepCloned = create();
	    	stepCloned.setDescription(s.getDescription());
	    	stepCloned.setNumber(s.getNumber());
	    	stepCloned.setPicture(s.getPicture());
	    	
	    	stepCloned.setStepHints(stepHintService.copyStepHintsForStep(s));
			stepCloned.setRecipe(recipe);
			result.add(stepCloned);
	    }
	    recipe.setSteps(result);
	    return result;
	  }
	
	public Collection<Step> findStepsByRecipe(Recipe recipe){
	    Collection<Step> result = recipeService.findStepsByRecipe(recipe);
	    return result;
	}

}
