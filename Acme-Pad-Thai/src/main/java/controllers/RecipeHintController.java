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

import services.RecipeHintService;
import services.RecipeService;
import services.UserService;
import domain.Recipe;
import domain.RecipeHint;
import domain.User;

@Controller
@RequestMapping("/recipehint")
public class RecipeHintController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecipeHintService recipeHintService;
	
	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public RecipeHintController() {
		super();
	}
	
	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer recipeHintId) {
		ModelAndView result;
		RecipeHint recipeHint = recipeHintService.findOne(recipeHintId);
	
		result = new ModelAndView("recipeHint/view");
		result.addObject("recipeHint",recipeHint);
		return result;
	}
		
	
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer recipeId) {
		ModelAndView result;
		RecipeHint recipeHint = recipeHintService.create();
		Recipe recipe = recipeService.findOne(recipeId);
		Collection<RecipeHint> recipeHints = recipeHintService.findAll();
		
		
		recipeHint.setRecipe(recipe);
		result = createEditModelAndView(recipeHint);
		result.addObject("recipeHints",recipeHints);
		result.addObject("requestURI","recipehint/user/create.do");
		result.addObject("urlID",recipeHint.getRecipe().getId());
		return result;
	}
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView create(@Valid RecipeHint recipeHint, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(recipeHint);
			result.addObject("requestURI","recipehint/user/create.do");
			result.addObject("recipeHint",recipeHint);
			result.addObject("urlID",recipeHint.getRecipe().getId());

		} else {
			try {
				Assert.isTrue(!recipeHint.getRecipe().getIsCopy());

				recipeHintService.save(recipeHint);
				result = new ModelAndView("redirect:../../recipe/user/edit.do?recipeId="+recipeHint.getRecipe().getId());
				result.addObject("recipeHint",recipeHint);
			} catch (Throwable oops) {
				result = createEditModelAndView(recipeHint, "recipe.commit.error");	
				result.addObject("requestURI","recipehint/user/create.do");
				result.addObject("recipeHint",recipeHint);
				result.addObject("urlID",recipeHint.getRecipe().getId());
			}
		}

		return result;
	}
	
	
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value="/user/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int recipeHintId){
		ModelAndView result;
		RecipeHint recipeHint;
		recipeHint = recipeHintService.findOne(recipeHintId);
		result = createEditModelAndView(recipeHint);
		result.addObject("requestURI","recipehint/user/edit.do");
		result.addObject("recipeHint",recipeHint);
		result.addObject("urlID",recipeHint.getRecipe().getId());

		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid RecipeHint recipeHint, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {		
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(recipeHint);
			result.addObject("urlID",recipeHint.getRecipe().getId());

		} else {
			try {
				Assert.isTrue(!recipeHint.getRecipe().getIsCopy());

				recipeHintService.save(recipeHint);		
				result = new ModelAndView("redirect:../../recipe/user/edit.do?recipeId="+recipeHint.getRecipe().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(recipeHint, "recipe.commit.error");
				result.addObject("urlID",recipeHint.getRecipe().getId());

			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(RecipeHint recipeHint, BindingResult binding) {
		ModelAndView result;

		try {			
			Assert.isTrue(!recipeHint.getRecipe().getIsCopy());

			recipeHintService.delete(recipeHint);
			result = new ModelAndView("redirect:../../recipe/user/edit.do?recipeId="+recipeHint.getRecipe().getId());
			result.addObject("requestURI","recipehint/user/delete.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(recipeHint, "recipe.commit.error");
			result.addObject("urlID",recipeHint.getRecipe().getId());

		}

		return result;
	}
	
	
	
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(RecipeHint recipeHint) {
			ModelAndView result;

			result = createEditModelAndView(recipeHint, null);
			
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(RecipeHint recipeHint, String message) {
			ModelAndView result;
			
			User user = userService.findByPrincipal();
			
			result = new ModelAndView("recipehint/user/edit");
			result.addObject("user",user);
			result.addObject("recipeHint", recipeHint);
			result.addObject("message", message);

			return result;
		}
}