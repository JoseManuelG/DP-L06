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

import services.IngredientService;
import services.QuantityService;
import services.RecipeService;
import services.UserService;
import domain.Ingredient;
import domain.Quantity;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/quantity")
public class QuantityController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private QuantityService quantityService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeService recipeService;
	

	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public QuantityController() {
		super();
	}
	
	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer quantityId) {
		ModelAndView result;
		Quantity quantity = quantityService.findOne(quantityId);
	
		result = new ModelAndView("quantity/view");
		result.addObject("quantity",quantity);
		return result;
	}
		
	
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer recipeId) {
		ModelAndView result;
		Quantity quantity = quantityService.create();
		Recipe recipe = recipeService.findOne(recipeId);
		Collection<Ingredient> ingredients = ingredientService.findAll();
		Collection<Ingredient> ingredientsAdded =ingredientService.findIngredentsByRecipe(recipe);
		ingredients.removeAll(ingredientsAdded);
		
		quantity.setRecipe(recipe);
		result = createEditModelAndView(quantity);
		result.addObject("ingredients",ingredients);
		result.addObject("ingredient",quantity.getIngredient());
		result.addObject("requestURI","quantity/user/create.do");
		result.addObject("urlID",quantity.getRecipe().getId());
		
		return result;
	}
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView create(@Valid Quantity quantity, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(quantity);
			result.addObject("requestURI","quantity/user/create.do");
			result.addObject("ingredient",quantity.getIngredient());
			result.addObject("urlID",quantity.getRecipe().getId());

		} else {
			try {
				Assert.isTrue(!quantity.getRecipe().getIsCopy());

				quantityService.save(quantity);
				result = new ModelAndView("redirect:/recipe/user/edit.do?recipeId=".concat(String.valueOf(quantity.getRecipe().getId())));
				result.addObject("ingredient",quantity.getIngredient());
			} catch (Throwable oops) {
				result = createEditModelAndView(quantity, "recipe.commit.error");	
				result.addObject("requestURI","quantity/user/create.do");
				result.addObject("ingredient",quantity.getIngredient());
				result.addObject("urlID",quantity.getRecipe().getId());

			}
		}

		return result;
	}
	
	
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value="/user/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int quantityId){
		ModelAndView result;
		Quantity quantity;
		quantity = quantityService.findOne(quantityId);
		Ingredient ingredient=quantity.getIngredient();
		result = createEditModelAndView(quantity);
		result.addObject("requestURI","quantity/user/edit.do");
		result.addObject("ingredient",ingredient);
		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save( Quantity quantity, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			Collection<String> units =new ArrayList<String>();
			System.out.println(binding.getAllErrors());
			String[] unitsArray = { "grams","kilograms","ounces", "pounds", "millilitres", "litres", "spoons", "cups", "pieces" };
			for(int i =  0; i < unitsArray.length; i++){
				units.add(unitsArray[i]);  
			}
			result = createEditModelAndView(quantity, "quantity.commit.error");
			result.addObject("units",units);
		} else {
			try {
				Assert.isTrue(!quantity.getRecipe().getIsCopy());
				quantityService.save(quantity);		
				result = new ModelAndView("redirect:/recipe/user/myRecipes.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(quantity, "quantity.commit.error");				
			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Quantity quantity, BindingResult binding) {
		ModelAndView result;

		try {			
			Assert.isTrue(!quantity.getRecipe().getIsCopy());
			quantityService.delete(quantity);
			result = new ModelAndView("redirect:../../recipe/user/edit.do?recipeId="+quantity.getRecipe().getId());
			//result.addObject("requestURI","quantity/user/delete.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(quantity, "recipe.commit.error");
		}

		return result;
	}
	
	
	
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(Quantity quantity) {
			ModelAndView result;

			result = createEditModelAndView(quantity, null);
			
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(Quantity quantity, String message) {
			ModelAndView result;
			
			User user = userService.findByPrincipal();
			Collection<Ingredient> ingredients = ingredientService.findAll();
			Collection<Ingredient> ingredientsAdded =ingredientService.findIngredentsByRecipe(quantity.getRecipe());
			ingredients.removeAll(ingredientsAdded);
			Collection<String> units =new ArrayList<String>();
			String[] unitsArray = { "grams","kilograms","ounces", "pounds", "millilitres", "litres", "spoons", "cups", "pieces" };
			for(int i =  0; i < unitsArray.length; i++){
				units.add(unitsArray[i]);  
			}
			result = new ModelAndView("quantity/user/edit");
			result.addObject("units",units);
			result.addObject("user",user);
			result.addObject("ingredients",ingredients);
			result.addObject("quantity", quantity);
			result.addObject("message", message);

			return result;
		}
}