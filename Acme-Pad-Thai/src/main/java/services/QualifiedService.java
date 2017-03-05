package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QualifiedRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Contest;
import domain.Qualified;
import domain.Recipe;

@Service
@Transactional
public class QualifiedService {
	

	//Managed Repository----------------------------------
	
	@Autowired
	private QualifiedRepository qualifiedRepository;
	
	//SupportingServices-----------------------------------
	
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private LoginService loginService;
	
	//Constructors----------------------------------------
	
	public QualifiedService(){
		super();
	}
	
	//Simple Crud methods---------------------------------
	
	public Qualified create(){
		Qualified result;
		result = new Qualified();
		return result;
	}
	public Qualified create(Contest contest,Recipe recipe,Boolean boolean1 ){		
		Qualified result;
		result=new Qualified();
		result.setContest(contest);
		result.setRecipe(recipe);
		result.setWinner(false);
		return result;
	}
	
	public Collection<Qualified> findAll(){
		Collection<Qualified> result =qualifiedRepository.findAll();
		
		Assert.notNull(result,"El conjunto de elementos no puede ser nulo");
		
		return result;
	}
	
	public Qualified findOne(int qualifiedId){
		Qualified result;
		result = qualifiedRepository.findOne(qualifiedId);
		return result;
	}

	@SuppressWarnings("static-access")
	public Qualified save(Qualified qualified){
		
		UserAccount userAccount=qualified.getRecipe().getUser().getUserAccount();
		UserAccount loginUserAccount=loginService.getPrincipal();
		ArrayList<Authority> authorities = new ArrayList<Authority>();
				authorities.addAll(loginUserAccount.getAuthorities());
		String loginAuthority = authorities.get(0).getAuthority();
		Assert.notNull(qualified,"El objeto a guardar no debe ser nulo");
		Assert.notNull(qualified.getContest(),"El contest a relacionar con el qualified debe ser valido");
		Assert.notNull(qualified.getRecipe(),"El recipe a relacionar con el qualified debe ser valido");
		Assert.isTrue(qualified.getContest().getOpeningTime().before(new Date(System.currentTimeMillis())), "El Concurso no ha empezado aún");
		Assert.isTrue(qualified.getContest().getClosingTime().after(new Date(System.currentTimeMillis()))||loginAuthority.equals(Authority.ADMIN), "El Concurso ya ha empezado aún");
		Qualified result;
		
		
		Assert.isTrue(userAccount.equals(loginUserAccount)||loginAuthority.equals(Authority.ADMIN),"El usuario debe ser el autor de la receta para presentarla a concurso");
		
		Assert.isTrue(recipeIsNotInContest(qualified.getRecipe(), qualified.getContest()), "Esta receta ya esta inscrita en este concurso");
		if(!loginAuthority.equals(Authority.ADMIN)){
		Recipe copyOfRecipe= recipeService.createCopyOfRecipe(qualified.getRecipe());
		qualified.setRecipe(copyOfRecipe);
		}
		
		
		result= qualifiedRepository.save(qualified);
		
		
		return result;
	}
	
	public void delete(Qualified qualified){
		Assert.notNull(qualified,"El objeto a borrar no debe ser nulo");
		Assert.isTrue(qualified.getId() !=0,"La id del objeto a borrar debe ser valida");
		
		qualifiedRepository.delete(qualified);
	}
	
	
	//OtherBusinessesModels-------------------------------
	public Collection<Qualified> findByContest(Contest contest){
		Collection<Qualified> qualifieds= qualifiedRepository.findByContestId(contest.getId());
		return qualifieds;
		
	}
	
	public Qualified findByContestAndRecipe(Contest contest, Recipe recipe){
		Qualified qualified;
		qualified=qualifiedRepository.findQualifiedByRecipeAndContest(recipe.getId(), contest.getId());
		return qualified;
		
	}
	
	public Collection<Qualified> findQualifiedsByRecipe(Recipe recipe){
		Collection<Qualified> qualifieds= qualifiedRepository.findByRecipeId(recipe.getId());
		return qualifieds;
		
	}
	
	public Boolean recipeIsNotInContest(Recipe recipe, Contest contest){
		Boolean res=true;
		
		Collection<Qualified> qualifieds=qualifiedRepository.findByContestId(contest.getId());
		for(Qualified q: qualifieds){
			if(q.getRecipe().getParentTicker().equals(recipe.getTicker())){
				res=false;
			}
		}
		
		return res;
	}
	
	public Boolean recipeIsNotInContest2(Recipe recipe, Contest contest){
		Boolean res=true;
		
		Recipe rec=qualifiedRepository.findQualifiedByRecipeParentTickerAndContest(recipe.getTicker(), contest.getId());
		if(rec==null){
			res=false;
		}
		return res;
	}
	
	public Recipe getParentRecipeOfQualified(Qualified qualified){
		Recipe res;
		Recipe aux=qualified.getRecipe();
		res=recipeService.findRecipeByParentTicker(aux.getParentTicker());
		return res;
	}
	
	



}
