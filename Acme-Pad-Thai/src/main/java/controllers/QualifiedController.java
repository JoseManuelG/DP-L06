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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import services.QualifiedService;
import services.RecipeService;
import services.UserService;

@Controller
@RequestMapping("/qualified/user")
public class QualifiedController extends AbstractController {
	@Autowired
	QualifiedService qualifiedService;
	@Autowired
	RecipeService recipeService;
	@Autowired
	UserService userService;
	@Autowired
	ContestService contestService;

	// Constructors -----------------------------------------------------------
	
	public QualifiedController() {
		super();
	}
		
	// Create ---------------------------------------------------------------		

	@RequestMapping("/create")
	public ModelAndView create() {
//		ModelAndView result;
//		Contest contest = contestService.findOne(87);
//		Recipe recipe = recipeService.findOne(76);
//		Qualified qualified = qualifiedService.create();
//		qualified.setContest(contest);
//		qualified.setRecipe(recipe);
//		Collection<Recipe> recipes = recipeService.findAll();
//		result = new ModelAndView("qualified/user/create");
//		result.addObject("recipes", recipes);
//		result.addObject("contest",contest);
//		result.addObject("qualified", qualified);
		ModelAndView result= new ModelAndView("/");

		return result;
	}
	
}