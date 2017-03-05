package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StepHintRepository;
import security.LoginService;
import security.UserAccount;
import domain.Step;
import domain.StepHint;

@Service
@Transactional
public class StepHintService {
	

	//Managed Repository----------------------------------
	
	@Autowired
	private StepHintRepository stepHintRepository;
	
	//SupportingServices-----------------------------------
	
	@Autowired
	private LoginService loginService;
	
	//Constructors----------------------------------------
	
	public StepHintService(){
		super();
	}
	
	//Simple Crud methods---------------------------------
	
	public StepHint create(){
		StepHint result;
		
		result=new StepHint();
		
		return result;
	}
	
	public Collection<StepHint> findAll(){
		Collection<StepHint> result;
		
		result= stepHintRepository.findAll();
		Assert.notNull(result,"El conjunto de elementos no puede ser nulo");
		
		return result;
	}
	
	public StepHint findOne(int stepHintId){
		StepHint result;
		result = stepHintRepository.findOne(stepHintId);
		return result;
	}

	@SuppressWarnings("static-access")
	public StepHint save(StepHint stepHint){
		Assert.notNull(stepHint,"El stepHint a guardar no puede ser nulo");
		
		UserAccount userAccount=stepHint.getStep().getRecipe().getUser().getUserAccount();
		UserAccount loginUserAccount=loginService.getPrincipal();
		Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para añadir un stepHint a la misma");
		
		StepHint result;
		result= stepHintRepository.save(stepHint);
		
		return result;
	}

	@SuppressWarnings("static-access")
	public Collection<StepHint> save(Collection<StepHint> stepHints){
		Assert.notNull(stepHints,"El conjunto de stepHints a guardar no puede ser nulo");
		Collection<StepHint> result=new ArrayList<StepHint>();
		StepHint arrayStepHint[] =new StepHint[stepHints.size()];
		arrayStepHint= stepHints.toArray(arrayStepHint);
		
		if(!stepHints.isEmpty()){
			StepHint stepHint=arrayStepHint[0];
			
			UserAccount userAccount=stepHint.getStep().getRecipe().getUser().getUserAccount();
			UserAccount loginUserAccount=loginService.getPrincipal();
			Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para añadir un conjunto de stepHints a la misma");
			
			
			for(StepHint sh : stepHints){
				result.add(save(sh));
			}
			
		}
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(StepHint stepHint){
		Assert.notNull(stepHint,"El objeto stepHint a borrar no debe ser nulo");
		Assert.isTrue(stepHint.getId() !=0,"El objeto stepHint a borrar debe tener una id valida ");
		assert stepHint.getId() !=0;
		

		UserAccount userAccount=stepHint.getStep().getRecipe().getUser().getUserAccount();
		UserAccount loginUserAccount=loginService.getPrincipal();
		Assert.isTrue(userAccount==loginUserAccount,"El usuario debe ser el autor de la receta para eliminar un stepHint de la misma");
		
		stepHintRepository.delete(stepHint.getId());
		
	}
	
	//OtherBusinessesModels-------------------------------
	
	public Collection<StepHint> copyStepHintsForCopyOfRecipe(Collection<Step> steps){
		List<StepHint> result = new LinkedList<StepHint>(); 
		for(Step s : steps){
			result.addAll(copyStepHintsForStep(s));
		}
		return result;
	}
	
	public Collection<StepHint> copyStepHintsForStep(Step step){
		Collection<StepHint> stepHints = stepHintRepository.findStepHintByStep(step.getId());
		Collection<StepHint> result = new LinkedList<StepHint>();
		for(StepHint sh : stepHints){
			StepHint stepHintCloned =create();
			stepHintCloned.setHint(sh.getHint());
			stepHintCloned.setStep(step);
			result.add(stepHintCloned);
		}
		return result;
	}

}
