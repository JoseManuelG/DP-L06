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

import services.IngredientService;
import services.PropertyService;
import services.PropertyValueService;
import domain.Ingredient;
import domain.Property;
import domain.PropertyValue;

@Controller
@RequestMapping("/propertyValue/nutritionist")
public class PropertyValueController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private PropertyValueService propertyValueService;

	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private PropertyService propertyService;
	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public PropertyValueController() {
		super();
	}
	
	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer propertyValueId) {
		ModelAndView result;
		PropertyValue propertyValue = propertyValueService.findOne(propertyValueId);
	
		result = new ModelAndView("propertyValue/nutritionist/view");
		result.addObject("propertyValue",propertyValue);
		return result;
	}
		
	
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer ingredientId) {
		ModelAndView result;
		PropertyValue propertyValue = propertyValueService.create();
		Ingredient ingredient = ingredientService.findOne(ingredientId);
		Collection<Property> properties =propertyService.findPropertiesNotInIngredient(ingredient);
		
	
		result = new ModelAndView("propertyValue/nutritionist/create");
		propertyValue.setIngredient(ingredient);
		result.addObject("requestURI","propertyValue/nutritionist/edit.do");
		
		result.addObject("properties",properties);
		result.addObject("propertyValue", propertyValue);

		return result;
	}

	
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int propertyValueId){
		ModelAndView result;
		PropertyValue propertyValue;
		propertyValue = propertyValueService.findOne(propertyValueId);
		result = createEditModelAndView(propertyValue);
		result.addObject("requestURI","propertyValue/nutritionist/edit.do");
		Collection<Property> properties =propertyService.findAll();
		result.addObject("properties",properties);
		
	
		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save( PropertyValue propertyValue, BindingResult binding) {
		ModelAndView result;
		Collection<Property> properties =propertyService.findAll();
		if (binding.hasErrors()) {
			
			System.out.println(binding.getAllErrors());
			
			result = createEditModelAndView(propertyValue);
			
		} else {
			try {
				propertyValueService.save(propertyValue);		
				result = new ModelAndView("redirect:../../ingredient/nutritionist/view.do?ingredientId="+propertyValue.getIngredient().getId() );
				//result.addObject("ingredient",propertyValue.getIngredient());
			//	result.addObject("requestURI","propertyValue/nutritionist/edit.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(propertyValue, "propertyValue.commit.error");	
				result.addObject("properties",properties);
			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(Integer propertyValueId) {
		ModelAndView result;
		PropertyValue propertyValue=	propertyValueService.findOne(propertyValueId);
		try {			
			
			propertyValueService.delete(propertyValue);
			result = new ModelAndView("redirect:../../ingredient/nutritionist/view.do?ingredientId="+propertyValue.getIngredient().getId() );
			//result.addObject("requestURI","propertyValue/nutritionist/delete.do");
			//result.addObject("ingredient",propertyValue.getIngredient());
		} catch (Throwable oops) {
			result = createEditModelAndView(propertyValue, "propertyValue.commit.error");
		}

		return result;
	}
	
	
	
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(PropertyValue propertyValue) {
			ModelAndView result;

			result = createEditModelAndView(propertyValue, null);
			
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(PropertyValue propertyValue, String message) {
			ModelAndView result;
			
			
			
			result = new ModelAndView("propertyValue/nutritionist/edit");
			result.addObject("propertyValue", propertyValue);
			result.addObject("message", message);

			return result;
		}
}