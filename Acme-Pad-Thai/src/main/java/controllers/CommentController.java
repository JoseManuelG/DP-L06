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

import services.CommentService;
import services.CustomerService;
import services.RecipeService;
import domain.Comment;
import domain.Customer;
import domain.Recipe;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private CustomerService customerService;
	

	@Autowired
	private CommentService commentService;

	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public CommentController() {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Comment> comments;
		comments = commentService.findAll();
		result = new ModelAndView("comment/list");
		result.addObject("requestURI","comment/list.do");
		result.addObject("comments",comments);
		
		return result;
	}
	

	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer commentId) {
		ModelAndView result;
		Comment comment = commentService.findOne(commentId);
		result = new ModelAndView("comment/view");
		result.addObject("comment",comment);
		return result;
	}
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer recipeId) {
		ModelAndView result;
		Comment comment;
		comment = commentService.create();
		//TODO pa servicio
		Recipe recipe = recipeService.findOne(recipeId);
		comment.setRecipe(recipe);
		Customer customer = customerService.findActorByPrincial();
		comment.setCustomer(customer);
		comment.setDateCreation(new Date(System.currentTimeMillis()-1000));
		//Fin del todo
		result = createEditModelAndView(comment);
		

		return result;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid Comment comment, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(comment);
		} else {
			try {
				commentService.save(comment);		
				result = new ModelAndView("redirect:../recipe/view.do?recipeId="+comment.getRecipe().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(comment, "comment.commit.error");				
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(Comment comment) {
			ModelAndView result;

			result = createEditModelAndView(comment, null);
			
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(Comment comment, String message) {
			ModelAndView result;
			
			Customer  customer= customerService.findActorByPrincial();
			result = new ModelAndView("comment/edit");
			result.addObject(customer);
			result.addObject("comment", comment);
			result.addObject("message", message);

			return result;
		}
}