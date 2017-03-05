package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BelongsRepository;
import security.LoginService;
import security.UserAccount;
import domain.Belongs;
import domain.Recipe;

@Service
@Transactional
public class BelongsService {
	
	//Managed Repository----------------------------------
	
	@Autowired
	private BelongsRepository belongsRepository;
	
	//SupportingServices-----------------------------------
	@Autowired
	private LoginService loginService;
	
	//Constructors----------------------------------------
	
	public BelongsService(){
		super();
	}
	
	//Simple Crud methods---------------------------------
	
	public Belongs create(){
		Belongs result;
		result= new Belongs();
		return result;
	}
	
	public Collection<Belongs> findAll() {
		Collection<Belongs> result;
		
		Assert.notNull(belongsRepository);
		result= belongsRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public Belongs findOne(int belongsId){
		Belongs result;
		result = belongsRepository.findOne(belongsId);
		return result;
	}
	
	public Belongs save(Belongs belongs){
		Assert.notNull(belongs);
		
		Belongs result;
		result= belongsRepository.save(belongs);
		return result;
	}
	@SuppressWarnings("static-access")
	public Collection<Belongs> save(Collection<Belongs> belongs) {
		Assert.notNull(belongs,"El conjunto de belongs a guardar no puede ser nulo");
		Collection<Belongs> result=new ArrayList<Belongs>();
		Belongs arrayBelongs[] =new Belongs[belongs.size()];
		arrayBelongs= belongs.toArray(arrayBelongs);
		
		if(!belongs.isEmpty()){
			Recipe recipe=arrayBelongs[0].getRecipe();
			UserAccount userAccount=recipe.getUser().getUserAccount();
			UserAccount loginUserAccount=loginService.getPrincipal();
			Assert.isTrue(userAccount.equals(loginUserAccount),"El usuario debe ser el autor de la receta para añadir un conjunto de belongs a la misma");
			
			
			
			for(Belongs s : belongs){
				result.add(save(s));
			}
		}
		
		return result;
	}
	
	
	public void delete(Belongs belongs){
		Assert.isTrue(belongs !=null);
		Assert.isTrue(belongs.getId() !=0);
		
		belongsRepository.delete(belongs);
	}
	
	
	
	
	//OtherBusinessesModels-------------------------------
	
	public Collection<Belongs> findBelongsByRecipe(Recipe recipe){
		Collection<Belongs> result = belongsRepository.findBelongsByRecipe(recipe.getId());
		return result;
	}
	public Collection<Belongs> copyBelongssForCopyOfRecipe(Recipe recipe, Recipe oldRecipe){
		Collection<Belongs> belongs = belongsRepository.findBelongsByRecipe(oldRecipe.getId());
		List<Belongs> result = new LinkedList<Belongs>();
		for(Belongs b : belongs){
			Belongs belongCloned = this.create();
			belongCloned.setCategory(b.getCategory());
			belongCloned.setRecipe(recipe);
			result.add(belongCloned);
		}
		recipe.setBelongs(result);
		return result;
	}

}
