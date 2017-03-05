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

import services.RecipeService;
import services.StepService;
import services.UserService;
import domain.Recipe;
import domain.Step;
import domain.StepHint;
import domain.User;

@Controller
@RequestMapping("/step")
public class StepController extends AbstractController {
	
	// Services --------------------------------------------------------------
	

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private StepService stepService;
	
	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public StepController() {
		super();
	}
	
	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer stepId) {
		ModelAndView result;
		Step step = stepService.findOne(stepId);
		Collection<StepHint> stepHints= step.getStepHints();
	
		result = new ModelAndView("step/view");
		result.addObject("step",step);
		result.addObject("stepHints", stepHints);
		return result;
	}
		
	
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer recipeId) {
		ModelAndView result;
		Step step = stepService.create();
		Assert.notNull(step);
		Recipe recipe = recipeService.findOne(recipeId);
		Collection<StepHint> stepHints = new ArrayList<StepHint>();

		step.setRecipe(recipe);
		step.setStepHints(stepHints);
		result = createEditModelAndView(step);
		
		result.addObject("step", step);
		result.addObject("stepHints",stepHints);
		result.addObject("requestURI","step/user/create.do");
		result.addObject("urlID",step.getRecipe().getId());

		return result;
	}
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView create(@Valid Step step, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(step);
			result.addObject("requestURI","step/user/create.do");
			result.addObject("step",step);
			result.addObject("urlID",step.getRecipe().getId());

		} else {
			try {
				Assert.isTrue(!step.getRecipe().getIsCopy());

				Collection<StepHint> sh =new ArrayList<StepHint>();
				step.setStepHints(sh);
				stepService.save(step);
				result = new ModelAndView("redirect:/recipe/user/edit.do?recipeId=".concat(String.valueOf(step.getRecipe().getId())));
				result.addObject("step",step);
				result.addObject("requestURI","step/user/create.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(step, "step.commit.error");	
				result.addObject("requestURI","step/user/create.do");
				result.addObject("urlID",step.getRecipe().getId());

				result.addObject("step",step);
			}
		}

		return result;
	}
	
	
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value="/user/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int stepId){
		ModelAndView result;
		Step step;
		step = stepService.findOne(stepId);
		if(step.getId()==0){
			Collection<StepHint> sh =new ArrayList<StepHint>();
			step.setStepHints(sh);
		}else{
			step.setStepHints(step.getStepHints());
		}
		result = createEditModelAndView(step);
		result.addObject("requestURI","step/user/edit.do");
		result.addObject("step",step);
		result.addObject("urlID",step.getRecipe().getId());

		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid Step step, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {		
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(step);
			result.addObject("urlID",step.getRecipe().getId());

		} else {
			try {
				Assert.isTrue(!step.getRecipe().getIsCopy());

				stepService.save(step);		
				result = new ModelAndView("redirect:/recipe/user/edit.do?recipeId=".concat(String.valueOf(step.getRecipe().getId())));
			} catch (Throwable oops) {
				result = createEditModelAndView(step, "step.commit.error");	
				result.addObject("urlID",step.getRecipe().getId());

			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Step step, BindingResult binding) {
		ModelAndView result;

		try {			
			Assert.isTrue(!step.getRecipe().getIsCopy());

			stepService.delete(step);
			ArrayList<Step> steps = new ArrayList<Step>();
			steps.addAll(step.getRecipe().getSteps());
			int index =1;
			for(Step s:steps){
				s.setNumber(index);
				index++;
			}
			stepService.save(steps);
			result = new ModelAndView("redirect:/recipe/user/edit.do?recipeId=".concat(String.valueOf(step.getRecipe().getId())));
			result.addObject("requestURI","step/user/delete.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(step, "step.commit.error");
		}

		return result;
	}
	
	
	
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(Step step) {
			ModelAndView result;

			result = createEditModelAndView(step, null);
			result.addObject("step", step);
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(Step step, String message) {
			ModelAndView result;
			
			User user = userService.findByPrincipal();
			
			result = new ModelAndView("step/user/edit");
			result.addObject("user",user);
			result.addObject("step", step);
			result.addObject("message", message);

			return result;
		}
}