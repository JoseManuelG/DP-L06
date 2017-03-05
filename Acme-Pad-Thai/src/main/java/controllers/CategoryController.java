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
import java.util.LinkedList;
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

import services.ActorService;
import services.CategoryService;
import services.RecipeService;
import domain.Actor;
import domain.Belongs;
import domain.Category;
import domain.Recipe;
import forms.CategoryForm;
import forms.TagForm;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {
	
	// Services --------------------------------------------------------------
	
	@Autowired
	private ActorService actorService;
		
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RecipeService recipeService ;
	
	//InitBinder----------------------
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public CategoryController() {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories;
		categories = categoryService.findAll();
		result = new ModelAndView("category/list");
		result.addObject("requestURI","category/list.do");
		result.addObject("categories",categories);
		
		return result;
	}
	
	@RequestMapping(value = "/listrecipe", method = RequestMethod.GET)
	public ModelAndView listrecipe() {
		ModelAndView result;
		Collection<Category> categories;
		Collection<Recipe> recipes = recipeService.findOriginalRecipes();
		CategoryForm categoryForm= new CategoryForm();
		categories = categoryService.findAll();
		result = new ModelAndView("category/listrecipe");
		result.addObject("requestURI","category/listrecipefinal.do");
		result.addObject("categories",categories);
		result.addObject("categoryForm",categoryForm);
		result.addObject("recipes", recipes);
		return result;
	}
	
//list recipe-------------------------------------------	
	@RequestMapping(value = "/listrecipefinal", method = RequestMethod.POST, params = "accept")
	public ModelAndView listrecipefinal( CategoryForm categoryForm, BindingResult binding) {
		ModelAndView result;
		Collection<Category> categories = categoryService.findAll();
		Collection<Recipe> recipes;
		if(categoryForm.getCategory()!=null){
			recipes = categoryService.findRecipesForCategory(categoryForm.getCategory());
		}else{
			recipes = recipeService.findOriginalRecipes();

		}

		result = new ModelAndView("category/listrecipe");
		result.addObject("requestURI","category/listrecipefinal.do");
		result.addObject("categories",categories);
		result.addObject("recipes", recipes);
		//result.addObject("category",category);

		return result;
	}


	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Integer categoryId) {
		ModelAndView result;
		Category category = categoryService.findOne(categoryId);
		Collection<String> tags = category.getTags();
		Collection<Category> subCategory = category.getSubCategory();
		Collection<Recipe> recipes=categoryService.findRecipesForCategory(category);
		
		result = new ModelAndView("category/view");
		result.addObject("category", category);
		result.addObject("tags", tags);
		result.addObject("subCategory", subCategory);
		result.addObject("recipes", recipes);
		
		return result;
	}
	
	// Create -----------------------------------------	----------------------
	
	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category category;

		category = categoryService.create();

		Collection<Category> categories =categoryService.findAll();
		result = createEditModelAndView(category);
		result.addObject("requestURI","category/administrator/edit.do");
		result.addObject("category", category);

		result.addObject("categories", categories);

		return result;
	}
		
	// Edit ---------------------------------------------------------------
	@RequestMapping(value="/administrator/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int categoryId){
		ModelAndView result;
		Category category;

		category = categoryService.findOne(categoryId);
		Collection<Category> subCategory= category.getSubCategory();
		Collection<Belongs> belongs= category.getBelongs();
		
		Collection<Category> categories =categoryService.getCategoriesNoCicle(category);
		
	
		result = createEditModelAndView(category);
		
		result.addObject("requestURI","category/administrator/edit.do");
		result.addObject("category",category);
		result.addObject("subCategory",subCategory);
		result.addObject("Belongs",belongs);
		result.addObject("categories",categories);
		result.addObject("tags",category.getTags());
		
		return result;
	}
	
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid Category category, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(category);
			Collection<Category> categories =categoryService.getCategoriesNoCicle(category);
			categories.remove(category);
			result.addObject("categories", categories);
			
		} else {
			try {
				categoryService.save(category);		
				result = new ModelAndView("redirect:/category/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(category, "category.commit.error");	
				result.addObject("tags",category.getTags());
				Collection<Category> categories =categoryService.getCategoriesNoCicle(category);
				categories.remove(category);
				result.addObject("categories", categories);
			}
		}
		result.addObject("tags",category.getTags());
		return result;
	}
		
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Category category, BindingResult binding) {
		ModelAndView result;

		try {			
			categoryService.delete(category);
			result = new ModelAndView("redirect:../list.do");
			//result.addObject("requestURI","recipe/user/delete.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(category, "category.commit.error");
			result.addObject("tags",category.getTags());
			Collection<Category> categories =categoryService.getCategoriesNoCicle(category);
			categories.remove(category);
			result.addObject("categories", categories);
		}
		result.addObject("tags",category.getTags());
		return result;
	}
	
	// ----------------------------------------Tag -----------------------
	
	// Create --------------------------------------------------------------
	@RequestMapping("/administrator/createtag")
	public ModelAndView createtag(String categoryId) {
		ModelAndView result;
	
		
		Category category = categoryService.findOne(Integer.valueOf(categoryId));
		TagForm tf = new TagForm(category);

		

		result = new ModelAndView("category/administrator/createtag");
		result.addObject("tagForm",tf);
		return result;
	}
	
	@RequestMapping(value = "/administrator/createtag",method=RequestMethod.POST,params="Accept" )
	public ModelAndView createtag(TagForm tagForm) {
		ModelAndView result;
		Category category = tagForm.getCategory();
		
		category=(categoryService.addTag(category, tagForm.getText()));
			
		categoryService.save(category);
		result = new ModelAndView("redirect:../administrator/edit.do?categoryId="+category.getId());
		return result;
	}
	
	// Delete --------------------------------------------------------------
	@RequestMapping(value = "/administrator/deleteTag")
	public 	ModelAndView deletePicture(String categoryId, Integer tagIndex) {
		ModelAndView result;
		Category category = categoryService.findOne(Integer.valueOf(categoryId));
		try {
			category=categoryService.removeTag(category, tagIndex);
			
			categoryService.save(category);
			result = new ModelAndView("redirect:../administrator/edit.do?categoryId="+category.getId());
		} catch (Throwable oops) {
			result = new ModelAndView("category.commit.error");
		}

		return result;
	}
	
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Category category) {
		ModelAndView result;
		Actor actor = actorService.findActorByPrincial();
		result = createEditModelAndView(category, null);
		result.addObject("actor",actor);
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Category category, String message) {
		ModelAndView result;
		Actor actor = actorService.findActorByPrincial();
		
		result = new ModelAndView("category/administrator/edit");
		result.addObject("actor",actor);
		result.addObject("category",category);
		result.addObject("message", message);


		return result;
	}
}