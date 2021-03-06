package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.SocialIdentityService;
import domain.Curriculum;
import domain.Endorser;
import domain.Folder;
import domain.Nutritionist;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController extends AbstractController {

	@Autowired
	SocialIdentityService socialIdentityService;

	@Autowired
	LoginService loginService;

	@Autowired
	ActorService actorService;
	// Constructors -----------------------------------------------------------
	
	public SocialIdentityController () {
		super();
	}
	//View ------------------------------------------------------------------
	@RequestMapping(value="/view",method=RequestMethod.GET,params="socialIdentityId")
	public ModelAndView view(int socialIdentityId) {
		ModelAndView result;
		SocialIdentity socialId =socialIdentityService.findOne(socialIdentityId);
		
		result = new ModelAndView("socialIdentity/view");
		result.addObject("socialIdentity",socialId);
		
		return result;
	}
	
	// create-----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody ModelAndView save() {
		ModelAndView result= new ModelAndView("socialIdentity/create");
		SocialIdentity socialIdentity= socialIdentityService.create();
		result.addObject("socialIdentity",socialIdentity);
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.addAll(actorService.findActorByPrincial().getUserAccount().getAuthorities());
		String aux=authorities.get(0).getAuthority().toLowerCase();
		result.addObject("typeActor",aux );
		
		return result;
	
	
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView save(int socialIdentityId) {
		ModelAndView result= new ModelAndView("socialIdentity/edit");
		SocialIdentity socialIdentity= socialIdentityService.findOne(socialIdentityId);
		socialIdentity.setActor(actorService.findActorByPrincial());
		result.addObject("socialIdentity",socialIdentity);
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.addAll(socialIdentity.getActor().getUserAccount().getAuthorities());
		String aux=authorities.get(0).getAuthority().toLowerCase();
		result.addObject("typeActor",aux );
		
		return result;
	
	
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid SocialIdentity socialIdentity, BindingResult binding) {
		ModelAndView result=null;
		if (binding.hasErrors()) {
			result = createEditModelAndView(socialIdentity);
			System.out.println(binding.getAllErrors().toString());
		} else {
			try {
				socialIdentityService.save(socialIdentity);
				ArrayList<Authority> authorities = new ArrayList<Authority>();
				authorities.addAll(socialIdentity.getActor().getUserAccount().getAuthorities());
				String aux=authorities.get(0).getAuthority().toLowerCase();
				result = new ModelAndView("redirect:../"+aux+"/view.do");
				
		} catch (Throwable oops) {
			result = createEditModelAndView(socialIdentity, "recipe.commit.error");	
			}
			
	}
			return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public @ResponseBody ModelAndView save( SocialIdentity socialIdentity) {
		ModelAndView result=null;
			try {
				socialIdentityService.delete(socialIdentity);
			
				ArrayList<Authority> authorities = new ArrayList<Authority>();
				authorities.addAll(socialIdentity.getActor().getUserAccount().getAuthorities());
				String aux=authorities.get(0).getAuthority().toLowerCase();
				result = new ModelAndView("redirect:../"+aux+"/view.do");
				
		} catch (Throwable oops) {
			result = createEditModelAndView(socialIdentity, "recipe.commit.error");	
			}
			
	
			return result;
	}

	protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity) {
	ModelAndView result;

	result = createEditModelAndView(socialIdentity, null);
	
	return result;
}	

	protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity, String message) {
		ModelAndView result;
		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", message);

		return result;
	}
	

}

