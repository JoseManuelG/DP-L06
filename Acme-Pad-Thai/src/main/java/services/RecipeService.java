package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecipeRepository;
import security.LoginService;
import domain.Belongs;
import domain.Category;
import domain.Comment;
import domain.Customer;
import domain.Qualification;
import domain.Qualified;
import domain.Quantity;
import domain.Recipe;
import domain.RecipeHint;
import domain.Step;
import domain.User;

@Service
@Transactional
public class RecipeService {
	
	
	//Managed Repository-----------------------------
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	//Supporting services-----------------------------
	@Autowired
	private QuantityService quantityService;

	@Autowired
	private QualificationService qualificationService;
	
	@Autowired
	private StepService stepService;
	
	@Autowired
	private RecipeHintService recipeHintService;
	
	@Autowired
	private BelongsService belongService;
	
	@Autowired
	private LoginService loginService;
	
	//Constructors------------------------------------
	
	public RecipeService(){
		super();
	}
	
	//Simple CRUD methods----------------------------
	
	public Recipe create() {
		Recipe result;
		result = new Recipe();

		return result;
	}

	public Collection<Recipe> findAll() {
		Collection<Recipe> result;

		result = recipeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Recipe findOne(int recipeId) {
		Recipe result;

		result = recipeRepository.findOne(recipeId);
		Assert.notNull(result);

		return result;
	}

	@SuppressWarnings("static-access")
	public Recipe save(Recipe recipe) {
		Assert.notNull(recipe,"La receta no puede ser nula");
		Assert.hasText(recipe.getTicker(),"El ticker no puede ser nulo");
		Assert.hasText(recipe.getTitle(),"El titulo no puede ser nulo");
		Assert.hasText(recipe.getSummary(),"El resumen no puede ser nulo");
		Assert.notNull(recipe.getAuthorMoment(),"La fecha de autoría no puede ser nula");
		Assert.notNull(recipe.getPictures(),"La coleccion de fotos no puede ser nula");
		Assert.notNull(recipe.getIsCopy(),"El boolean iscopy debe ser true o false");
		Assert.isTrue(!recipe.getIsCopy()||recipe.getId()==0,"El boolean iscopy debe ser true o false");
		Assert.notNull(recipe.getUser().getUserAccount(),"La useraccount no puede ser nula");
		Assert.notNull(loginService.getPrincipal(),"El getprincipal no puede ser nulo");
		
		//Assert.isTrue(recipe.getUser().getUserAccount()==loginService.getPrincipal(),"Solo el propietario puede realizar operaciones");
		Recipe result;

		result = recipeRepository.save(recipe);
		
		return result;
	}


	@SuppressWarnings("static-access")
	public void delete(Recipe recipe) {
		Assert.notNull(recipe,"La receta no puede ser nula");
		Assert.isTrue(recipe.getId() != 0,"El id de la receta no puede ser 0");
		Assert.isTrue(recipe.getUser().getUserAccount().equals(loginService.getPrincipal()),"Solo el propietario puede realizar operaciones");
		Assert.isTrue(recipe.getIsCopy()==false,"No se pueden borrar recetas inscritas en un concurso");

		recipeRepository.delete(recipe);
	}
	
	
	//Other bussiness methods------------------------
	

	public Collection<Recipe> findRecipesByUser(User user) {
		Collection<Recipe> result = recipeRepository.findRecipesByUser(user.getId());
		return result;
	}
	public Collection<Recipe> findRecipesByCategory(Category category) {
		Collection<Recipe> result =recipeRepository.findRecipesByCategory(category.getId());
		return result;
	}
	
	public String createTicker(){
		String result="";
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		Date date=new  Date();
		result=result.concat(dateFormat.format(date));
		int A=65;
		int Z=90;
		result=result.concat("-");
		for (int i=1; i<=4; i++){
			int numAleatorio = (int)Math.floor(Math.random()*(Z -A)+A);
			char letra=(char)numAleatorio;
			result=result.concat(Character.toString(letra));
		}
		return result;
		
	}
	
	public Recipe createCopyOfRecipe(Recipe recipe){
		Recipe result = new Recipe();
		
		result.setTicker(createTicker());
		result.setTitle(recipe.getTitle());
		result.setSummary(recipe.getSummary());
		result.setAuthorMoment(recipe.getAuthorMoment());
		result.setLastUpdate(recipe.getLastUpdate());
		Collection<String> pictures = recipe.getPictures();
		System.out.println(pictures);
		result.setPictures(pictures);
//		result.setPictures(recipe.getPictures());
		result.setUser(recipe.getUser());
		result.setQualifications(new ArrayList<Qualification>());
		result.setQuantities(new ArrayList<Quantity>());
		result.setRecipeHints(new ArrayList<RecipeHint>());
		result.setSteps(new ArrayList<Step>());
		result.setBelongs(new ArrayList<Belongs>());
		result.setQualifieds(new ArrayList<Qualified>());
		result.setComments(new ArrayList<Comment>());
		
		Recipe saved = recipeRepository.save(result);

//		Collection<Qualification> qualifications = qualificationService.copyQualificationsForCopyOfRecipe(saved,recipe);

		Collection<Quantity> quantities = quantityService.copyQuantitiesForCopyOfRecipe(saved,recipe);

		Collection<Step> steps =stepService.copyStepsForCopyOfRecipe(saved,recipe);
	
		Collection<RecipeHint> recipeHints = recipeHintService.copyRecipeHintsForCopyOfRecipe(saved, recipe);
		
		Collection<Belongs> belongs = belongService.copyBelongssForCopyOfRecipe(saved, recipe);
		
		saved.setIsCopy(true);
		saved.setParentTicker(recipe.getTicker());
		
		recipeRepository.save(saved);
		
//		Collection<Qualification> qualificationsSaved = qualificationService.save(qualifications);

		Collection<Quantity> quantitiesSaved = quantityService.save(quantities);
		
		Collection<Step> stepsSaved = stepService.save(steps);
		
		Collection<RecipeHint> recipeHintsSaved = recipeHintService.save(recipeHints);
		
		belongService.save(belongs);
//		saved.setQualifications(qualificationsSaved);
		saved.setQuantities(quantitiesSaved);
		saved.setSteps(stepsSaved);
		saved.setRecipeHints(recipeHintsSaved);
		
		Recipe finalRecipeFinal = recipeRepository.save(saved);
		//Los saves a continuacion en el orden de las dependencias
		
		return finalRecipeFinal;
		
	}
	
	
	
	public boolean isValidToQualify(Recipe recipe){
		boolean result=false;
		Collection<Qualification> qualifications = qualificationService.findQualificationsByRecipe(recipe);
		Integer numLikes=0;
		Integer numDislikes=0;
		for(Qualification q: qualifications){
			if(q.getOpinion()){
				numLikes++;
			}else{
				numDislikes++;
			}
		}
		result = (numLikes>=5) && (numDislikes==0);
		return result;
		
	}
	
	public Collection<Recipe> recipesValidToQualify(Collection<Recipe> recipes){
		Collection<Recipe> result=new LinkedList<Recipe>();
		for(Recipe rec:recipes){
			Collection<Qualification> qualifications = qualificationService.findQualificationsByRecipe(rec);
			Integer numLikes=0;
			Integer numDislikes=0;
			for(Qualification q: qualifications){
				if(q.getOpinion()){
					numLikes++;
				}else{
					numDislikes++;
				}
			}
			if((numLikes>=5) && (numDislikes==0)){
				result.add(rec);
			
			}
		}
		return result;
			
		
		
		
	}
	
	public Double findAvgOfStepsForRecipes(){
		Double result = recipeRepository.findAvgOfStepsForRecipes();
		return result;
	}
	
	public Double findStdDevOfStepsForRecipes(){
		Double result = recipeRepository.findStdDevOfStepsForRecipes();
		return result;
	}
	
	public Double findAvgOfIngredientsPerRecipe(){
		Double result = recipeRepository.findAvgOfIngredientsPerRecipe();
		return result;
	}
	
	public Double findStdDevOfIngredientsPerRecipe(){
		Double result = recipeRepository.findStdDevOfIngredientsPerRecipe();
		return result;
	}
	public Collection<Qualification> findQualificationsByRecipe(Recipe recipe){
		Collection<Qualification> result =recipeRepository.findQualificationsByRecipeId(recipe.getId());
		return result;
	}
	
	public Collection<Step> findStepsByRecipe(Recipe recipe){
		Collection<Step> result = recipeRepository.findStepsByRecipeId(recipe.getId());
		return result;
	}
	
	public int countStepsForRecipe(Recipe recipe){
		int result = recipeRepository.countStepsForRecipeId(recipe.getId());
		return result;
	}
	public Collection<Recipe> searchForRecipe(String searchTerm){
		Collection<Recipe> result = recipeRepository.searchForRecipe(searchTerm);
		return result;
	}
	
	public Collection<Recipe> findRecipesByParentTicker(String parentTicker){
		Collection<Recipe> result = recipeRepository.findRecipesByParentTicker(parentTicker);
		return result;
	}
	public Collection<Recipe> findRecipesInContestByUser(User user){
			Collection<Recipe> result = recipeRepository.findRecipesInContestByUser(user.getId());
			return result;
	}
	public Collection<Recipe> findOriginalRecipes(){
		Collection<Recipe> result = recipeRepository.findOriginalRecipes();
		return result;
	}
	public Recipe findRecipeByParentTicker(String parentTicker){
		Recipe result = recipeRepository.findRecipeByParentTicker(parentTicker);
		return result;
	}

	public Collection<Recipe> findFollowedsLastRecipes(Customer customer){
		Collection<Recipe> result = recipeRepository.findFollowedsLastRecipes(customer.getId());
		return result;
	}

}
