/* AdministratorController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.BelongsService;
import services.CampaignService;
import services.CategoryService;
import services.CommentService;
import services.CustomerService;
import services.QualificationService;
import services.QualifiedService;
import services.QuantityService;
import services.RecipeHintService;
import services.RecipeService;
import services.StepService;
import services.UserService;
import domain.Banner;
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
import forms.CategoryForm;
import forms.PictureForm;
import forms.StringForm;

@Controller
@RequestMapping("/recipe")
public class RecipeController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private QuantityService quantityService;
	
	@Autowired
	private QualifiedService qualifiedService;
	
	@Autowired
	private RecipeHintService recipeHintService;
	
	@Autowired
	private StepService stepService;
	
	@Autowired
	private BelongsService belongsService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private QualificationService qualificationService;
	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public RecipeController() {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
//		ModelAndView result;
//		Collection<Recipe> recipes;
//		recipes = recipeService.findOriginalRecipes();
//		result = new ModelAndView("recipe/list");
//		result.addObject("requestURI","recipe/list.do");
//		result.addObject("recipes",recipes);
//		
		ModelAndView result;
		Collection<Category> categories;
		Collection<Recipe> recipes = recipeService.findOriginalRecipes();
		CategoryForm categoryForm= new CategoryForm();
		categories = categoryService.findAll();
		
		result = new ModelAndView("category/listrecipe");
		result.addObject("requestURI","category/listrecipefinal.do");
		result.addObject("categories",categories);
		result.addObject("categoryForm",categoryForm);
		result.addObject("recipes", recipes);
		
		return result;
	}
	
//	@RequestMapping(value = "/listByUser", method = RequestMethod.GET)
//	public ModelAndView list(@RequestParam(required = false) Integer userId) {
//		ModelAndView result;
//		Collection<Recipe> recipes;
//		if(userId == null){
//			User user = userService.findByPrincipal();
//			recipes = recipeService.findRecipesByUser(user);
//		}else{
//			User user = userService.findOne(userId);
//			recipes = recipeService.findRecipesByUser(user);
//		}
//		result = new ModelAndView("recipe/list");
//		result.addObject("requestURI","recipe/list.do");
//		result.addObject("recipes",recipes);
//		
//		return result;
//	}
//	
	@RequestMapping(value = "/user/listcontest", method = RequestMethod.GET)
	public ModelAndView listcontest() {
		ModelAndView result;
		Collection<Recipe> recipes;
		
		User user = userService.findByPrincipal();
		recipes = recipeService.findRecipesInContestByUser(user);
		result = new ModelAndView("recipe/list");
		result.addObject("booleanlistcontest",true);
		result.addObject("recipes",recipes);
		result.addObject("user",user);
		result.addObject("requestURI","category/listrecipefinal.do");
		result.addObject("recipes", recipes);
		return result;
	}
	
	
	@RequestMapping(value = "/listByUser", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer userId) {
		ModelAndView result;
		Collection<Recipe> recipes;
		User user = userService.findOne(userId);
		recipes = recipeService.findRecipesByUser(user);
		result = new ModelAndView("recipe/list");
		result.addObject("requestURI","recipe/listByUser.do");
		result.addObject("recipes",recipes);
		
		return result;
	}

	@RequestMapping(value = "/user/myRecipes", method = RequestMethod.GET)
	  public ModelAndView myRecipes() {
	    ModelAndView result;
	    Collection<Recipe> recipes;
	    int id =0;
	    User user = userService.findByPrincipal();
	   
	    recipes = recipeService.findRecipesByUser(user);
	    result = new ModelAndView("recipe/list");
	    result.addObject("requestURI","recipe/user/myRecipes.do");
	    result.addObject("recipes",recipes);
	    result.addObject("id",id);
	    return result;
	  }
	
	
	
	@RequestMapping(value = "/latestRecipes", method = RequestMethod.GET)
	  public ModelAndView latestRecipes() {
	    ModelAndView result;
	    Collection<Recipe> recipes;
	    Customer customer = customerService.findActorByPrincial();
	   
	    recipes = recipeService.findFollowedsLastRecipes(customer);
	    result = new ModelAndView("recipe/list");
	    result.addObject("requestURI","recipe/latestRecipes.do");
	    result.addObject("recipes",recipes);
	    return result;
	  }

	// Search ---------------------------------------------------------------
	
	@RequestMapping("/search")
	public ModelAndView search() {
		ModelAndView result;
		StringForm sf = new StringForm();
		sf.setText("text");
		result = new ModelAndView("recipe/search");
		result.addObject("stringForm",sf);
		return result;
	}
	@RequestMapping(value = "/search",method=RequestMethod.POST,params="Accept" )
	public ModelAndView search(StringForm stringForm ) {
		ModelAndView result;
		String text= stringForm.getText();
		Collection<Recipe> recipesReturned = recipeService.searchForRecipe(text);
		result = new ModelAndView("recipe/list");
		result.addObject("recipes",recipesReturned);
		return result;
	}

	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer recipeId) {
		ModelAndView result;
		Recipe recipe = recipeService.findOne(recipeId);
		Collection<Qualification> qualifications = qualificationService.findQualificationsByRecipe(recipe);
		int likes =0;
		int dislikes =0;
		for(Qualification q: qualifications){
			if(q.getOpinion()==Boolean.TRUE){
				likes++;
			}else{
				dislikes++;
			}
		}
		Collection<Comment> comments =commentService.findCommentsForRecipe(recipe);
		Collection<Quantity> quantities = quantityService.findQuantitiesForRecipe(recipe);
		Collection<Belongs> belongs =belongsService.findBelongsByRecipe(recipe);
		Collection<Step> steps = stepService.findStepsByRecipe(recipe);
		Collection<RecipeHint> recipeHints = recipeHintService.findRecipeHintsByRecipe(recipe);
		Collection<String> pictures =recipe.getPictures();
		Banner banner = bannerService.randomBanner();
		campaignService.bannerDisplayed(banner);
		result = new ModelAndView("recipe/view");
		result.addObject("likes",likes);
		result.addObject("dislikes",dislikes);
		result.addObject("comments",comments);
		result.addObject("recipe",recipe);
		result.addObject("quantities",quantities);
		result.addObject("belongs",belongs);
		result.addObject("steps",steps);
		result.addObject("recipeHints",recipeHints);
		result.addObject("pictures",pictures);
		result.addObject("banner",banner);
		Integer like = 1;
		Customer customer = customerService.findActorByPrincial();
		if(customer!=null){
			for (Qualification q: recipe.getQualifications()){
				if (q.getCustomer().equals(customer) && q.getOpinion()==true) {
					like = 2;
					break;
				}else if(q.getCustomer().equals(customer) && q.getOpinion()==false){
					like=3;
					break;
				}
			}
		}
		result.addObject("like",like);
		boolean esMiReceta;
		if(customerService.findActorByPrincial()!=null){
			esMiReceta= customerService.findActorByPrincial().equals(recipe.getUser());
		}else{
			esMiReceta = true;
		}
		result.addObject("esMiReceta",esMiReceta);
		return result;
	}
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Recipe recipe;

		recipe = recipeService.create();
		recipe.setAuthorMoment(new Date(System.currentTimeMillis()-1000));
		recipe.setLastUpdate(new Date(System.currentTimeMillis()-1000));
		recipe.setTicker(recipeService.createTicker());
		recipe.setUser(userService.findByPrincipal());
		result = createEditModelAndView(recipe);
		result.addObject("requestURI","recipe/user/edit.do");

		return result;
	}
		
	// Edit ---------------------------------------------------------------
	@RequestMapping(value="/user/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int recipeId){
		ModelAndView result;
		Recipe recipe;
		recipe = recipeService.findOne(recipeId);
		Collection<Qualification> qualifications = qualificationService.findQualificationsByRecipe(recipe);
		int likes =0;
		int dislikes =0;
		for(Qualification q: qualifications){
			if(q.getOpinion()==Boolean.TRUE){
				likes++;
			}else{
				dislikes++;
			}
		}
		Collection<String> pictures =recipe.getPictures();
		Collection<Quantity> quantities = quantityService.findQuantitiesForRecipe(recipe);
		Collection<Belongs> belongs =belongsService.findBelongsByRecipe(recipe);
		Collection<Step> steps = stepService.findStepsByRecipe(recipe);
		Collection<RecipeHint> recipeHints = recipeHintService.findRecipeHintsByRecipe(recipe);
		 List<Integer> uno =new ArrayList<Integer>();
		    uno.add(5);
		Assert.notNull(recipe);
		result = createEditModelAndView(recipe);
		result.addObject("uno",uno);
		result.addObject("qualifications",qualifications);
		result.addObject("likes",likes);
		result.addObject("dislikes",dislikes);
		result.addObject("pictures",pictures);
		result.addObject("quantities",quantities);
		result.addObject("belongs",belongs);
		result.addObject("steps",steps);
		result.addObject("recipeHints",recipeHints);
		result.addObject("requestURI","recipe/user/edit.do");
		
		return result;
	}
	
	// Like ---------------------------------------------------------------
	
	@RequestMapping(value="/user/like", method=RequestMethod.GET)
	public ModelAndView like(@RequestParam int recipeId){
		ModelAndView result;
		Recipe recipe;
		recipe = recipeService.findOne(recipeId);
		Qualification qualification = null;
		List<Qualification> everyQualificationOfRecipe = new ArrayList<Qualification>(qualificationService.findQualificationsByRecipe(recipe));
		List<Qualification> qualifications = new ArrayList<Qualification>();
		for(Qualification q: everyQualificationOfRecipe){
			if(q.getCustomer().equals(customerService.findActorByPrincial()))
				qualifications.add(q);
		}
		if(qualifications.size()==1){
			qualification=qualifications.get(0);
		}else if(qualifications.size()==0){
			qualification=qualificationService.create();
			qualification.setCustomer(customerService.findActorByPrincial());
			qualification.setRecipe(recipe);
		}
		qualification.setOpinion(true);
		qualificationService.save(qualification);
		result = new ModelAndView("redirect:../view.do?recipeId="+recipeId);
		result.addObject("requestURI","recipe/user/like.do");
		
		return result;
	}
	
	// Dislike ---------------------------------------------------------------
	
	@RequestMapping(value="/user/dislike", method=RequestMethod.GET)
	public ModelAndView dislike(@RequestParam int recipeId){
		ModelAndView result;
		Recipe recipe;
		recipe = recipeService.findOne(recipeId);
		Qualification qualification = null;
		List<Qualification> everyQualificationOfRecipe = new ArrayList<Qualification>(qualificationService.findQualificationsByRecipe(recipe));
		List<Qualification> qualifications = new ArrayList<Qualification>();
		for(Qualification q: everyQualificationOfRecipe){
			if(q.getCustomer().equals(customerService.findActorByPrincial()))
				qualifications.add(q);
		}
		if(qualifications.size()==1){
			qualification=qualifications.get(0);
		}else if(qualifications.size()==0){
			qualification=qualificationService.create();
			qualification.setCustomer(customerService.findActorByPrincial());
			qualification.setRecipe(recipe);
		}
		qualification.setOpinion(false);
		qualificationService.save(qualification);

		result = new ModelAndView("redirect:../view.do?recipeId="+recipeId);
		result.addObject("requestURI","recipe/user/dislike.do");
		
		return result;
	}
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid Recipe recipe, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(recipe);
		} else {
			try {
				recipe.setLastUpdate(new Date(System.currentTimeMillis()-100));
				recipeService.save(recipe);		
				result = new ModelAndView("redirect:myRecipes.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(recipe, "recipe.commit.error");				
			}
		}

		return result;
	}
		
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Recipe recipe, BindingResult binding) {
		ModelAndView result;

		try {			
			recipeService.delete(recipe);
			result = new ModelAndView("redirect:myRecipes.do");
			//result.addObject("requestURI","recipe/user/delete.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(recipe, "recipe.commit.error");
		}

		return result;
	}
	
	// ----------------------------------------Picture -----------------------
	
	// Create --------------------------------------------------------------
	@RequestMapping("/user/createpicture")
	public ModelAndView createpicture(String recipeId) {
		ModelAndView result;
		PictureForm pf = new PictureForm();
		pf.setText("your pic url here");
		Recipe recipe = recipeService.findOne(Integer.valueOf(recipeId));
		pf.setRecipe(recipe);
		result = new ModelAndView("recipe/user/createpicture");
		result.addObject("pictureForm",pf);
		return result;
	}
	
	@RequestMapping(value = "/user/createpicture",method=RequestMethod.POST,params="Accept" )
	public ModelAndView createpicture(PictureForm pictureForm, BindingResult binding) {
		ModelAndView result;
		if (pictureForm.getText().isEmpty()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(pictureForm.getRecipe());	
		} else {
		Recipe recipe = pictureForm.getRecipe();
		List<String> pictures = new ArrayList<String>();
		pictures.addAll(recipe.getPictures());
		String pictureToAdd =pictureForm.getText();
		pictures.add(pictureToAdd);
		recipe.setPictures(pictures);
		Date currentTime=new Date(System.currentTimeMillis()-10000);
		recipe.setLastUpdate(currentTime);
		recipeService.save(recipe);
		result = new ModelAndView("redirect:../user/edit.do?recipeId="+recipe.getId());
		
		}
		return result;
	}
	
	// Delete --------------------------------------------------------------
	@RequestMapping(value = "/user/deletePicture")
	public 	ModelAndView deletePicture(String recipeId, Integer pictureIndex) {
		ModelAndView result;
		Recipe recipe = recipeService.findOne(Integer.valueOf(recipeId));
		try {
			
			List<String> pictures = new LinkedList<String>();
			pictures.addAll(recipe.getPictures());
			String pictureToDelete= pictures.get(Integer.valueOf(pictureIndex));
			pictures.remove(pictureToDelete);
			recipe.setPictures(pictures);
			Date currentTime=new Date(System.currentTimeMillis()-10000);
			recipe.setLastUpdate(currentTime);
			recipeService.save(recipe);
			result = new ModelAndView("redirect:../user/edit.do?recipeId="+recipe.getId());
		} catch (Throwable oops) {
			result = new ModelAndView("recipe.commit.error");
		}

		return result;
	}
	
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Recipe recipe) {
		ModelAndView result;

		result = createEditModelAndView(recipe, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Recipe recipe, String message) {
		ModelAndView result;
		
		User user = userService.findByPrincipal();
		Collection<Comment> comments =commentService.findCommentsForRecipe(recipe);
		Collection<Quantity> quantities = quantityService.findQuantitiesForRecipe(recipe);
		Collection<Qualified> qualifieds = qualifiedService.findQualifiedsByRecipe(recipe);
		Collection<RecipeHint> recipehints = recipeHintService.findRecipeHintsByRecipe(recipe);
		Collection<Step> steps = stepService.findStepsByRecipe(recipe);
		Collection<Belongs> belongs = belongsService.findBelongsByRecipe(recipe);
		Collection<Qualification> qualifications = qualificationService.findQualificationsByRecipe(recipe);
		
		result = new ModelAndView("recipe/user/edit");
		result.addObject("user",user);
		result.addObject("comments",comments);
		result.addObject("quantities",quantities);
		result.addObject("qualifieds",qualifieds);
		result.addObject("recipehints",recipehints);
		result.addObject("steps",steps);
		result.addObject("belongs",belongs);
		result.addObject("qualifications",qualifications);
		result.addObject("recipe", recipe);
		result.addObject("message", message);

		return result;
	}
}