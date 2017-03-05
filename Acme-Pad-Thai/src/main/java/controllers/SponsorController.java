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
import services.SponsorService;
import domain.Campaign;
import domain.CreditCard;
import domain.Sponsor;
import forms.ActorForm;
import forms.StringForm;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {
	
	// Services -------------------------------------------------------------
	
	@Autowired
	private SponsorService sponsorService;

	// Constructors -----------------------------------------------------------
	
	public SponsorController() {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsor> sponsors;
		sponsors = sponsorService.findAll();
		result = new ModelAndView("sponsor/list");
		result.addObject("requestURI","sponsor/list.do");
		result.addObject("sponsors",sponsors);
		
		return result;
	}
	
	// Search ---------------------------------------------------------------
	
	@RequestMapping(value = "/search",method=RequestMethod.GET )
	public ModelAndView search() {
		ModelAndView result;
		StringForm sf = new StringForm();
		result = new ModelAndView("sponsor/search");
		result.addObject("stringForm",sf);
		return result;
	}
	
	@RequestMapping(value = "/search",method=RequestMethod.POST,params="Accept" )
	public ModelAndView search(StringForm stringForm ) {
		ModelAndView result;
		String text= stringForm.getText();
		Collection<Sponsor> sponsorsReturned = sponsorService.searchForSponsor(text);
		result = new ModelAndView("sponsor/list");
		result.addObject("sponsors",sponsorsReturned);
		return result;
	}

	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = false) Integer sponsorId) {
		ModelAndView result;
		Collection<Campaign> campaigns;
		Collection<CreditCard> creditCards;
		result = new ModelAndView("sponsor/view");
		Sponsor sponsor;
		if(sponsorId==null){
			sponsor =sponsorService.findByPrincipal();
			campaigns= sponsor.getCampaigns();
			creditCards= sponsor.getCreditCards();
			result.addObject("principal",true);
		}else{
			sponsor= sponsorService.findOne(sponsorId);
			campaigns =sponsor.getCampaigns();
			creditCards= sponsor.getCreditCards();
			result.addObject("principal",false);
		}
		result.addObject("campaigns",campaigns);
		result.addObject("creditCards",creditCards);
		result.addObject(sponsor);
		result.addObject("socialIdentities",sponsor.getSocialIdentities());
		
		return result;
	}

	

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Sponsor sponsor= sponsorService.findByPrincipal();
		ActorForm actorForm= new ActorForm();
		result = new ModelAndView("user/edit");
		actorForm.setAddress(sponsor.getAddress());
		actorForm.setEmail(sponsor.getEmail());
		actorForm.setName(sponsor.getName());
		actorForm.setPassword(sponsor.getUserAccount().getPassword());
		actorForm.setPhone(sponsor.getPhone());
		actorForm.setSurname(sponsor.getSurname());
		actorForm.setTypeOfActor("SPONSOR");
		actorForm.setUsername(sponsor.getUserAccount().getUsername());
		result.addObject("actorForm", actorForm);
		result.addObject("typeActor", "user");
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				Sponsor sponsor= sponsorService.findByPrincipal();

				UserAccount userAccount = sponsor.getUserAccount();
				
				
				userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
				userAccount.setUsername(actorForm.getUsername());
		
				sponsor.setName(actorForm.getName());
				sponsor.setSurname(actorForm.getSurname());
				sponsor.setAddress(actorForm.getAddress());
				sponsor.setEmail(actorForm.getEmail());
				sponsor.setPhone(actorForm.getPhone());
				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				userAccount.setAuthorities(authorities);
				sponsor.setUserAccount(userAccount);
				
				sponsorService.save(sponsor);	
				
				result = this.view(null);
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
		result = new ModelAndView("user/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}

}