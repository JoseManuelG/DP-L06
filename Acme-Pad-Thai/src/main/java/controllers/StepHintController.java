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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.StepHintService;
import services.StepService;
import services.UserService;
import domain.Step;
import domain.StepHint;
import domain.User;

@Controller
@RequestMapping("/stephint")
public class StepHintController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StepHintService stepHintService;
	
	@Autowired
	private StepService stepService;
	
	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public StepHintController() {
		super();
	}
	
	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer stepHintId) {
		ModelAndView result;
		StepHint stepHint = stepHintService.findOne(stepHintId);
	
		result = new ModelAndView("stepHint/view");
		result.addObject("stepHint",stepHint);
		return result;
	}
	
	
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer stepId) {
		ModelAndView result;
		Step step = stepService.findOne(stepId);
		Collection<StepHint> stepHints = stepHintService.findAll();
		
		StepHint stepHint = stepHintService.create(step);

		result = createEditModelAndView(stepHint);
		result.addObject("stepHints",stepHints);
		result.addObject("requestURI","stephint/user/create.do");
		return result;
	}
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView create(@Valid StepHint stepHint, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(stepHint);
			result.addObject("requestURI","stephint/user/create.do");
			result.addObject("stepHint",stepHint);
		} else {
			try {
				stepHintService.save(stepHint);
				result = new ModelAndView("redirect:/recipe/user/edit.do?recipeId=".concat(String.valueOf(stepHint.getStep().getRecipe().getId())));
				result.addObject("stepHint",stepHint);
			} catch (Throwable oops) {
				result = createEditModelAndView(stepHint, "stepHint.commit.error");	
				result.addObject("requestURI","stephint/user/create.do");
				result.addObject("stepHint",stepHint);
			}
		}
		
		return result;
	}
	
	
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value="/user/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int stephintId){
		ModelAndView result;
		StepHint stepHint;
		stepHint = stepHintService.findOne(stephintId);
		result = createEditModelAndView(stepHint);
		result.addObject("requestURI","stephint/user/edit.do");
		result.addObject("stepHint",stepHint);
		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid StepHint stepHint, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {		
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(stepHint);
		} else {
			try {
				stepHintService.save(stepHint);		
				result = new ModelAndView("redirect:/recipe/user/myRecipes.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(stepHint, "stepHint.commit.error");				
			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(StepHint stepHint, BindingResult binding) {
		ModelAndView result;

		try {			
			stepHintService.delete(stepHint);
			result = new ModelAndView("redirect:../../recipe/list.do");
			result.addObject("requestURI","stephint/user/delete.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(stepHint, "stepHint.commit.error");
		}

		return result;
	}
	
	
	
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(StepHint stepHint) {
			ModelAndView result;

			result = createEditModelAndView(stepHint, null);
			
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(StepHint stepHint, String message) {
			ModelAndView result;
			
			User user = userService.findByPrincipal();
			
			result = new ModelAndView("stephint/user/edit");
			result.addObject("user",user);
			result.addObject("stepHint", stepHint);
			result.addObject("message", message);

			return result;
		}
}