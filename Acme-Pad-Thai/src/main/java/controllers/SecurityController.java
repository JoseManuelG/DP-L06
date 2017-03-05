package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.NutritionistService;
import services.SponsorService;
import services.UserService;
import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SecurityController extends AbstractController{

	//Services------------------------------------------------------------
	@Autowired
	UserService userService;
	@Autowired
	SponsorService sponsorService;
	@Autowired
	NutritionistService nutritionistService;
	
	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		ActorForm actorForm= new ActorForm();
		result = createEditModelAndView(actorForm);

		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				
				if(actorForm.getTypeOfActor().equals("USER")){
				
					userService.newUser(actorForm);
				
				}else if(actorForm.getTypeOfActor().equals("SPONSOR")){
				
					sponsorService.newSponsor(actorForm);
				
				}else if(actorForm.getTypeOfActor().equals("NUTRITIONIST")){
					
					nutritionistService.newNutritionist(actorForm);
					
				}

				
				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "recipe.commit.error");				
			}
		}

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
		result = new ModelAndView("security/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}
}

