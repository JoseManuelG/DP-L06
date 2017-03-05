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

import services.BelongsService;
import services.CategoryService;
import services.RecipeService;
import services.UserService;
import domain.Category;
import domain.Belongs;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/belongs")
public class BelongsController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private BelongsService belongsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private CategoryService categoryService;
	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public BelongsController() {
		super();
	}
	
	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer belongsId) {
		ModelAndView result;
		Belongs belongs = belongsService.findOne(belongsId);
	
		result = new ModelAndView("belongs/view");
		result.addObject("belongs",belongs);
		return result;
	}
		
	
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer recipeId) {
		ModelAndView result;
		Belongs belongs = belongsService.create();
		Recipe recipe = recipeService.findOne(recipeId);
		Collection<Category> categorys = categoryService.findAll();
		Collection<Category> categorysAdded =categoryService.findCategoriesByRecipe(recipe);
		categorys.removeAll(categorysAdded);
		
		belongs.setRecipe(recipe);
		result = createEditModelAndView(belongs);
		result.addObject("categorys",categorys);
		result.addObject("category",belongs.getCategory());
		result.addObject("requestURI","belongs/user/create.do");

		result.addObject("urlID",belongs.getRecipe().getId());
		return result;
	}
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView create(@Valid Belongs belongs, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(belongs);
			result.addObject("requestURI","belongs/user/create.do");
			result.addObject("category",belongs.getCategory());
		} else {
			try {
				Assert.isTrue(!belongs.getRecipe().getIsCopy());
				belongsService.save(belongs);
				result = new ModelAndView("redirect:/recipe/user/edit.do?recipeId=".concat(String.valueOf(belongs.getRecipe().getId())));
				result.addObject("category",belongs.getCategory());
			} catch (Throwable oops) {
				result = createEditModelAndView(belongs, "belongs.commit.error");	
				result.addObject("requestURI","belongs/user/create.do");
				result.addObject("category",belongs.getCategory());
			}
		}

		return result;
	}
	
	
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value="/user/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int belongsId){
		ModelAndView result;
		Belongs belongs;
		belongs = belongsService.findOne(belongsId);
		Category category=belongs.getCategory();
		result = createEditModelAndView(belongs);
		result.addObject("requestURI","belongs/user/edit.do");
		result.addObject("category",category);
		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid Belongs belongs, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			Collection<String> units =new ArrayList<String>();
			System.out.println(binding.getAllErrors());
			String[] unitsArray = { "grams","kilograms","ounces", "pounds", "millilitres", "litres", "spoons", "cups", "pieces" };
			for(int i =  0; i < unitsArray.length; i++){
				units.add(unitsArray[i]);  
			}
			result = createEditModelAndView(belongs);
			result.addObject("units",units);
		} else {
			try {
				Assert.isTrue(!belongs.getRecipe().getIsCopy());
				belongsService.save(belongs);		
				result = new ModelAndView("redirect:/recipe/user/edit.do?recipeId=".concat(String.valueOf(belongs.getRecipe().getId())));
			} catch (Throwable oops) {
				result = createEditModelAndView(belongs, "belongs.commit.error");				
			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Belongs belongs, BindingResult binding) {
		ModelAndView result;

		try {			
			Assert.isTrue(!belongs.getRecipe().getIsCopy());
			belongsService.delete(belongs);
			result = new ModelAndView("redirect:../../recipe/user/edit.do?recipeId="+belongs.getRecipe().getId());
			result.addObject("requestURI","belongs/user/delete.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(belongs, "belongs.commit.error");
		}

		return result;
	}
	
	
	
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(Belongs belongs) {
			ModelAndView result;

			result = createEditModelAndView(belongs, null);
			
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(Belongs belongs, String message) {
			ModelAndView result;
			
			User user = userService.findByPrincipal();
			Collection<Category> categorys = categoryService.findAll();
			Collection<Category> categorysAdded =categoryService.findCategoriesByRecipe(belongs.getRecipe());
			categorys.removeAll(categorysAdded);
			Collection<String> units =new ArrayList<String>();
			String[] unitsArray = { "grams","kilograms","ounces", "pounds", "millilitres", "litres", "spoons", "cups", "pieces" };
			for(int i =  0; i < unitsArray.length; i++){
				units.add(unitsArray[i]);  
			}
			result = new ModelAndView("belongs/user/edit");
			result.addObject("units",units);
			result.addObject("user",user);
			result.addObject("categorys",categorys);
			result.addObject("belongs", belongs);
			result.addObject("message", message);

			return result;
		}
}