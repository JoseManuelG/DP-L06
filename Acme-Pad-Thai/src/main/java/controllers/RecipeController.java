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
import java.util.Collection;
import java.util.Date;

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
		ModelAndView result;
		
		Collection<Category> categories = categoryService.findAll();
		Collection<Recipe> recipes = recipeService.findOriginalRecipes();
		CategoryForm categoryForm= new CategoryForm();
		
		result = new ModelAndView("category/listrecipe");
		result.addObject("requestURI","category/listrecipefinal.do");
		result.addObject("categories",categories);
		result.addObject("categoryForm",categoryForm);
		result.addObject("recipes", recipes);
		
		return result;
	}

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
	    User user = userService.findByPrincipal();
	   
	    recipes = recipeService.findRecipesByUser(user);
	    result = new ModelAndView("recipe/list");
	    result.addObject("requestURI","recipe/user/myRecipes.do");
	    result.addObject("recipes",recipes);
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
		result.addObject("requestURI","recipe/search.do");
		result.addObject("recipes",recipesReturned);
		return result;
	}

	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer recipeId) {
		ModelAndView result;
		Recipe recipe = recipeService.findOne(recipeId);
		Collection<Qualification> qualifications = qualificationService.findQualificationsByRecipe(recipe);
		int likes = qualificationService.calculateLikesForRecipe(qualifications);
		int dislikes = qualificationService.calculateDislikesForRecipe(qualifications);
		
		Collection<Comment> comments =commentService.findCommentsForRecipe(recipe);
		Collection<Quantity> quantities = quantityService.findQuantitiesForRecipe(recipe);
		Collection<Belongs> belongs =belongsService.findBelongsByRecipe(recipe);
		Collection<Step> steps = stepService.findStepsByRecipe(recipe);
		Collection<RecipeHint> recipeHints = recipeHintService.findRecipeHintsByRecipe(recipe);
		Collection<String> pictures =recipe.getPictures();
		Banner banner = bannerService.randomBanner();
		if(banner!=null){
			campaignService.bannerDisplayed(banner);
		}
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
		Customer customer = customerService.findActorByPrincial();
		Integer like = qualificationService.likeButtonsToShow(customer, recipe);
		
		result.addObject("like",like);
		boolean esMiReceta = recipeService.checkIfRecipeIsMine(recipe);
		result.addObject("esMiReceta",esMiReceta);
		return result;
	}
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Recipe recipe;

		recipe = recipeService.create();
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
		int likes = qualificationService.calculateLikesForRecipe(qualifications);
		int dislikes = qualificationService.calculateDislikesForRecipe(qualifications);
		Collection<String> pictures =recipe.getPictures();
		Collection<Quantity> quantities = quantityService.findQuantitiesForRecipe(recipe);
		Collection<Belongs> belongs =belongsService.findBelongsByRecipe(recipe);
		Collection<Step> steps = stepService.findStepsByRecipe(recipe);
		Collection<RecipeHint> recipeHints = recipeHintService.findRecipeHintsByRecipe(recipe);
		

		Assert.notNull(recipe);
		result = createEditModelAndView(recipe);
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
		qualificationService.like(recipeId);
		ModelAndView result;
		result = new ModelAndView("redirect:../view.do?recipeId="+recipeId);
		result.addObject("requestURI","recipe/user/like.do");
		
		return result;
	}
	
	// Dislike ---------------------------------------------------------------
	
	@RequestMapping(value="/user/dislike", method=RequestMethod.GET)
	public ModelAndView dislike(@RequestParam int recipeId){
		qualificationService.dislike(recipeId);
		ModelAndView result;
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
			recipeService.createpicture(pictureForm);
		result = new ModelAndView("redirect:../user/edit.do?recipeId="+pictureForm.getRecipe().getId());
		
		}
		return result;
	}
	
	// Delete --------------------------------------------------------------
	@RequestMapping(value = "/user/deletePicture")
	public 	ModelAndView deletePicture(String recipeId, Integer pictureIndex) {
		ModelAndView result;
		
		try {
			recipeService.deletePicture(recipeId,pictureIndex);
			result = new ModelAndView("redirect:../user/edit.do?recipeId="+recipeId);
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