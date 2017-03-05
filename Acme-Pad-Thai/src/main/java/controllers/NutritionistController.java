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

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.CurriculumService;
import services.CustomerService;
import services.FollowService;
import services.NutritionistService;
import services.RecipeService;
import domain.Cook;
import domain.Curriculum;
import domain.Customer;
import domain.Endorser;
import domain.Follow;
import domain.Nutritionist;
import domain.Recipe;
import forms.ActorForm;
import forms.StringForm;

@Controller
@RequestMapping("/nutritionist")
public class NutritionistController extends AbstractController {
	
	// Services -------------------------------------------------------------
	
	@Autowired
	private NutritionistService nutritionistService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private FollowService followService;

	@Autowired
	private CurriculumService curriculumService;

	@Autowired
	private RecipeService recipeService;

	// Constructors -----------------------------------------------------------
	
	public NutritionistController() {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Nutritionist> nutritionists;
		nutritionists = nutritionistService.findAll();
		result = new ModelAndView("nutritionist/list");
		result.addObject("requestURI","nutritionist/list.do");
		result.addObject("nutritionists",nutritionists);
		
		return result;
	}
	
	// Search ---------------------------------------------------------------
	
	@RequestMapping(value = "/search",method=RequestMethod.GET )
	public ModelAndView search() {
		ModelAndView result;
		StringForm sf = new StringForm();
		result = new ModelAndView("nutritionist/search");
		result.addObject("stringForm",sf);
		return result;
	}
	
	@RequestMapping(value = "/search",method=RequestMethod.POST,params="Accept" )
	public ModelAndView search(StringForm stringForm ) {
		ModelAndView result;
		String text= stringForm.getText();
		Collection<Nutritionist> nutritionistsReturned = nutritionistService.searchForNutritionist(text);
		result = new ModelAndView("nutritionist/list");
		result.addObject("nutritionists",nutritionistsReturned);
		return result;
	}

	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = false) Integer nutritionistId) {
		ModelAndView result;
		Collection<Endorser> endorsers;
		result = new ModelAndView("nutritionist/view");
		Nutritionist nutritionist;
		Curriculum curriculum;
		if(nutritionistId==null){
			nutritionist = nutritionistService.findByPrincipal();
			result.addObject("principal",true);
			
			
		}else{
			nutritionist = nutritionistService.findOne(nutritionistId);
			curriculum = nutritionist.getCurriculum();
			endorsers= curriculum.getEndorsers();
		}
		
		result.addObject("socialIdentities",nutritionist.getSocialIdentities());

		if(nutritionist.getCurriculum()!=null){
			curriculum= nutritionist.getCurriculum();
			
		}else{
				curriculum= curriculumService.create();
			}
		//TODO pal servicio
		Integer follow =1;
		if(customerService.findActorByPrincial()==null){
			follow=3;
		}else{
			Customer customer =customerService.findActorByPrincial();
			for(Follow f : nutritionist.getFollowers()){
				if( f.getFollower().equals(customer)){
					follow=2;
					break;
				}
			}
		}
		//fin del todo
		endorsers= curriculum.getEndorsers();
		result.addObject("nutritionist",nutritionist);
		result.addObject("curriculum",curriculum);
		result.addObject("endorsers",curriculum.getEndorsers());
		result.addObject("requestURI","nutritionist/view.do");
		result.addObject("endosers",endorsers);
		result.addObject("follow",follow);
		result.addObject("followers",nutritionist.getFollowers());
		result.addObject("followeds",nutritionist.getFolloweds());
		boolean esMiPerfil;
		if(customerService.findActorByPrincial()!=null){
			esMiPerfil= customerService.findActorByPrincial().equals(nutritionist);
		}else{
			esMiPerfil = true;
		}
		result.addObject("esMiPerfil",esMiPerfil);
		Collection<Recipe> recipesOfFolloweds =recipeService.findFollowedsLastRecipes(nutritionist);
		result.addObject("recipesOfFolloweds",recipesOfFolloweds);
		return result;
	}

	
	

	// Edit ---------------------------------------------------------------
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Nutritionist nutritionist= nutritionistService.findByPrincipal();
		ActorForm actorForm= nutritionistService.fillActorForm(nutritionist);
		result = new ModelAndView("user/edit");
		
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				Nutritionist nutritionist= nutritionistService.findByPrincipal();
				nutritionist=nutritionistService.reconstruct(nutritionist, actorForm);
				
				nutritionistService.save(nutritionist);	
				result = this.view(null);
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "recipe.commit.error");	
				}
		}
			
		return result;
	}

	// Follow ----------------------------------------------------------------

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam int nutritionistId) {
		ModelAndView result;
		
		Nutritionist followed = nutritionistService.findOne(nutritionistId);
		Customer follower = customerService.findActorByPrincial();
		Follow follow = followService.create(follower,followed);
		
		followService.save(follow);
		result = new ModelAndView("redirect:/nutritionist/view.do?nutritionistId=" + nutritionistId);
		return result;
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int nutritionistId) {
		ModelAndView result;
		Nutritionist followed = nutritionistService.findOne(nutritionistId);
		Customer follower = customerService.findActorByPrincial();
		Follow follow = followService.findFollowByFollowedAndFollower(followed,
				follower);
		followService.delete(follow);
		result = new ModelAndView("redirect:/nutritionist/view.do?nutritionistId=" + nutritionistId);
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(ActorForm actorForm) {
	ModelAndView result;

	result = createEditModelAndView(actorForm, null);
	
	return result;
}	

	protected ModelAndView createEditModelAndView(ActorForm actorForm, String message) {
		ModelAndView result;
		result = new ModelAndView("user/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}

}