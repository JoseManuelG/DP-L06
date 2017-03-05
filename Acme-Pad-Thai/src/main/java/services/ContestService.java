package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContestRepository;
import domain.Contest;
import domain.Qualification;
import domain.Qualified;
import domain.Recipe;

@Service
@Transactional
public class ContestService {
	

	//Managed Repository----------------------------------
	
	@Autowired
	private ContestRepository contestRepository;
	
	//SupportingServices-----------------------------------
	@Autowired
	private QualifiedService qualifiedService;
	
	//Constructors----------------------------------------
	
	public ContestService(){
		super();
	}
	
	//Simple Crud methods---------------------------------
	public Contest create(){
		Contest result;
		
		result=new Contest();
		
		return result;
	}
	
	public Collection<Contest> findAll(){
		Collection<Contest> result;
		
		result= contestRepository.findAll();
		Assert.notNull(result,"El conjunto de elementos no puede ser nulo");
		
		return result;
	}
	
	public Contest findOne(int contestId){
		Contest result;
		result = contestRepository.findOne(contestId);
		return result;
	}
	
	public Contest save(Contest contest){
		Assert.notNull(contest,"El concurso a guardar no puede ser nulo");
		if(contest.getId()!=0){
		contestRepository.findOne(contest.getId());
		
		Assert.isTrue(checkContestTime(contest),"El concurso no puede ser modificado de esta forma");
		}
		Contest result = contestRepository.save(contest);
		
		
		return result;
	}
	
	public void delete(Contest contest){
		Assert.notNull(contest,"El objeto contest no debe ser nulo");
		Assert.isTrue(contest.getId() !=0,"El objeto constest a borrar debe tener una id valida ");
		Collection<Qualified> qualifieds=  qualifiedService.findByContest(contest);
		Assert.isTrue(qualifieds.isEmpty(),"No deben haber qualifieds, o el contest no debe ser borrado");
		contestRepository.delete(contest);
	}
	
	
	//OtherBusinessesModels-------------------------------
	public boolean checkContestTime(Contest contest){
		boolean result=false;
		Date currentMoment = new Date(System.currentTimeMillis()-100);
		
			if(contest.getOpeningTime().after(currentMoment)){
				result=true;
			}else{		
			Contest oldContest=contestRepository.findOne(contest.getId());
						
			Assert.isTrue(oldContest.getTittle().equals(contest.getTittle()),"El titulo no debe variar en un concurso en marcha");
			
			Assert.isTrue(oldContest.getOpeningTime().compareTo(contest.getOpeningTime())==0,"La fecha de inicio no debe variar en un concurso en marcha");
						
			Assert.isTrue(oldContest.getQualifieds().containsAll(contest.getQualifieds()),"Los qualifieds no debe variar en un concurso en marcha");
			result=true;
			}
		
		return result;
		
	}
	
	public Collection<Recipe> getWinners(Contest contest){
		Collection<Recipe> qualifiedRecipes=contestRepository.findRecipesByContestId(contest.getId());
		Collection<Recipe> result = new ArrayList<Recipe>();
		for(int i=0;i<3;i++){
			Recipe recipe = max(qualifiedRecipes);
			result.add(recipe);
			qualifiedRecipes.remove(recipe);
			
		}
		return result;
		
	}
	public void setWinners(Contest contest){
		Collection<Recipe> qualifiedRecipes=contestRepository.findRecipesByContestId(contest.getId());
		Collection<Recipe> result = new ArrayList<Recipe>();
		for(int i=0;i<3;i++){
			if(qualifiedRecipes.isEmpty()){
				break;
			}
			Recipe recipe = max(qualifiedRecipes);
			result.add(recipe);
			qualifiedRecipes.remove(recipe);
			
		}
		for(Recipe r:result){
			for(Qualified q: r.getQualifieds()){
				q.setWinner(true);
				qualifiedService.save(q);
			}
		}
		
	}
	private Recipe max(Collection<Recipe> recipes) {
		Recipe[] recipesArray = new Recipe[recipes.size()];
		recipesArray = recipes.toArray(recipesArray);
		Recipe result = recipesArray[0];
		for(Recipe r : recipes){
			if(calculateScoreForRecipe(r)>calculateScoreForRecipe(result)){
				result =r;
			}
		}
	   return result;
	}
	
	public int calculateScoreForRecipe(Recipe recipe){
		int result = 0;
		if(!(recipe.getQualifications().isEmpty()) || !(recipe.getQualifications()==null)){
			for(Qualification q : recipe.getQualifications()){
				if(q.getOpinion()==true){
					result++;
				}else{
					result--;
				}
			}
		}
		return result;
	}
	
	public Collection<Contest> getPopularContest(){
		Collection<Contest> result;
		Integer aux;
		aux =contestRepository.findMaxRecipesForContest();
		result = contestRepository.findContestBySize(aux);
		return result;
	}
	
	public int findMinRecipesForContests(){
		int result=contestRepository.findMinRecipesForContests();
		return result;
	}
	
	public double findAvgRecipesForContests(){
		double result=contestRepository.findAvgRecipesForContests();
		return result;
	}
	
	public int findMaxRecipesForContests(){
		int result=contestRepository.findMaxRecipesForContests();
		return result;
	}
	public Collection<Contest> findContestsWithMoreRecipes(){
		Collection<Contest> result;
		result=contestRepository.findContestsWithMoreRecipes();
		return result;
	}
}
