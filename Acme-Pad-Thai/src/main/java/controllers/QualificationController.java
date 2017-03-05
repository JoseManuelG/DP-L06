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
import java.util.List;

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

import services.CustomerService;
import services.QualificationService;
import services.RecipeService;
import services.UserService;
import domain.Customer;
import domain.Qualification;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/qualification")
public class QualificationController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private QualificationService qualificationService;
	

	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public QualificationController() {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Qualification> qualifications;
		qualifications = qualificationService.findAll();
		result = new ModelAndView("qualification/list");
		result.addObject("requestURI","qualification/list.do");
		result.addObject("qualifications",qualifications);
		
		return result;
	}
	

	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer qualificationId) {
		ModelAndView result;
		Qualification qualification = qualificationService.findOne(qualificationId);
		result = new ModelAndView("qualification/view");
		result.addObject("qualification",qualification);
		return result;
	}
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer recipeId) {
		ModelAndView result;
		Qualification qualification;
		User user = userService.findByPrincipal();
		Recipe recipe = recipeService.findOne(recipeId);
		qualification = qualificationService.create(recipe,user);
		List<String> options = new ArrayList<String>();
		options.add(String.valueOf(Boolean.TRUE));
		options.add(String.valueOf(Boolean.FALSE));
		result = createEditModelAndView(qualification);
		result.addObject("qualification",qualification);
		result.addObject("options",options);
		

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid Qualification qualification, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(qualification);
		} else {
			try {
				Customer customer = customerService.findActorByPrincial();
				qualification.setCustomer(customer);
				qualificationService.save(qualification);		
				result = new ModelAndView("redirect:../recipe/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(qualification, "qualification.commit.error");				
			}
		}

		return result;
	}
	
	
	
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(Qualification qualification) {
			ModelAndView result;

			result = createEditModelAndView(qualification, null);
			
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(Qualification qualification, String message) {
			ModelAndView result;
			
			Customer customer = userService.findByPrincipal();
			result = new ModelAndView("qualification/edit");
			result.addObject("customer",customer);
			result.addObject("qualification", qualification);
			result.addObject("message", message);

			return result;
		}
}