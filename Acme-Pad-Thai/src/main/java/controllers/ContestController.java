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
import java.util.LinkedList;

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
import services.ContestService;
import services.QualifiedService;
import services.RecipeService;
import services.UserService;
import domain.Actor;
import domain.Contest;
import domain.Qualified;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/contest")
public class ContestController extends AbstractController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QualifiedService qualifiedService;

	@Autowired
	private ContestService contestService;
	
	@Autowired
	private ActorService actorService;
	
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Constructors -----------------------------------------------------------
	
	public ContestController() {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Contest> contests;
		contests = contestService.findAll();
		result = new ModelAndView("contest/list");
		result.addObject("requestURI","contest/list.do");
		result.addObject("contests",contests);

		return result;
	}
	
	// View ---------------------------------------------------------------		

	@RequestMapping("/view")
	public ModelAndView view(int contestId) {
		ModelAndView result;
		Contest contest = contestService.findOne(contestId);
		result = new ModelAndView("contest/view");
		result.addObject("contest" , contest);
		
		result.addObject("qualifieds" , contest.getQualifieds());
		
		return result;
	}
	
	// Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Contest contest;
		contest = contestService.create();
		result = createEditModelAndView(contest);
		result.addObject("requestURI","contest/administrator/edit.do");
		result.addObject("contest", contest);

		return result;
	}
	// SUBMIT ---------------------------------------------------------------		


	@RequestMapping("/submit")
	public ModelAndView submit(int contestId) {
		ModelAndView result;
		Contest contest = contestService.findOne(contestId);
		result = new ModelAndView("contest/submit");
		result.addObject("contest" , contest);
		User user= userService.findByPrincipal();
		
		
		Collection<Recipe> recipes = contestService.recipesNotQualifiedsForContest(user,contest);
		
		Collection<Recipe> recipesQualificable =recipeService.recipesValidToQualify(recipes);
		result.addObject("recipes",recipesQualificable);
		result.addObject("contestId",contestId);
		result.addObject("requestURI","/contest/submit.do");
		
		return result;
	}
	
	@RequestMapping("/submitfinal")
	public ModelAndView submitfinal(int contestId, int recipeId) {
		ModelAndView result;
		Contest contest = contestService.findOne(contestId);
		Recipe recipe = recipeService.findOne(recipeId);
		result = new ModelAndView("contest/submitfinal");
		Qualified qualified= qualifiedService.create(contest,recipe,false);
		
		qualifiedService.save(qualified);
		
		
		result=this.view(contestId);
		
		return result;
	}

	
	@RequestMapping("/administrator/compute")
	public ModelAndView compute(int contestId) {
		ModelAndView result;
		Contest contest = contestService.findOne(contestId);
		
		contestService.setWinners(contest);
		result = this.list();
		//result.addObject("contest" , contest);
		//User user= userService.findByPrincipal();
		//result.addObject("recipes" , recipeService.findRecipesByUser(user));
		//result.addObject("contestId",contestId);
		
		return result;
	}
	
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int contestId) {
		ModelAndView result;
		Contest contest;
		contest = contestService.findOne(contestId);
		//TODO no entiendo el porque setear de vuelta el objeto revisar mas palante
		Collection<Qualified> qualifieds = contest.getQualifieds();
		//contest.setQualifieds(qualifieds);
		//Fin del todo
		result = createEditModelAndView(contest);
		result.addObject("requestURI","contest/list.do");
		result.addObject("contest",contest);
		result.addObject("qualifieds",qualifieds);

		

		return result;
	}
	
	
	
	// Save ---------------------------------------------------------------
	
		@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
		public @ResponseBody ModelAndView save(@Valid Contest contest, BindingResult binding) {
			ModelAndView result;
			if (binding.hasErrors()) {
				System.out.println(binding.getAllErrors());
				result = createEditModelAndView(contest);
			} else {
				try {
					contestService.save(contest);		
					result = new ModelAndView("redirect:/contest/list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(contest, "contest.commit.error");				
				}
			}

			return result;
		}
		
	// Delete -------------------------------------------------------------
		
		@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Contest contest, BindingResult binding) {
			ModelAndView result;

			try {			
				contestService.delete(contest);
				result = new ModelAndView("redirect:../list.do");
				//result.addObject("requestURI","recipe/user/delete.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(contest, "contest.commit.error");
			}

			return result;
		}	
	
	// Ancillary methods ------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(Contest contest) {
			ModelAndView result;
			Actor actor = actorService.findActorByPrincial();
			Collection<Qualified> qualifieds = contest.getQualifieds();
			result = createEditModelAndView(contest, null);
			result.addObject("actor",actor);
			result.addObject("contest",contest);
			result.addObject("qualifieds",qualifieds);
			return result;
		}	
		
		protected ModelAndView createEditModelAndView(Contest contest, String message) {
			ModelAndView result;
			Actor actor = actorService.findActorByPrincial();
			Collection<Qualified> qualifieds = contest.getQualifieds();
			result = new ModelAndView("contest/administrator/edit");
			result.addObject("actor",actor);
			result.addObject("contest",contest);
			result.addObject("message", message);
			result.addObject("qualifieds",qualifieds);


			return result;
		}
	
}