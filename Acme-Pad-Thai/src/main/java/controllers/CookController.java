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
import services.CookService;
import services.MasterClassService;
import domain.Cook;
import domain.MasterClass;
import forms.ActorForm;
import forms.StringForm;

@Controller
@RequestMapping("/cook")
public class CookController extends AbstractController {
	
	// Services -------------------------------------------------------------
	
	@Autowired
	private CookService cookService;

	@Autowired
	private MasterClassService masterClassService;

	// Constructors -----------------------------------------------------------
	
	public CookController() {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Cook> cooks;
		cooks = cookService.findAll();
		result = new ModelAndView("cook/list");
		result.addObject("requestURI","cook/list.do");
		result.addObject("cooks",cooks);
		
		return result;
	}
	
	// Search ---------------------------------------------------------------
	
	@RequestMapping(value = "/search",method=RequestMethod.GET )
	public ModelAndView search() {
		ModelAndView result;
		StringForm sf = new StringForm();
		result = new ModelAndView("cook/search");
		result.addObject("stringForm",sf);
		return result;
	}
	
	@RequestMapping(value = "/search",method=RequestMethod.POST,params="Accept" )
	public ModelAndView search(StringForm stringForm ) {
		ModelAndView result;
		String text= stringForm.getText();
		Collection<Cook> cooksReturned = cookService.searchForCook(text);
		result = new ModelAndView("cook/list");
		result.addObject("cooks",cooksReturned);
		return result;
	}

	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = false) Integer cookId) {
		ModelAndView result;
		Collection<MasterClass> masterClasses;
		result = new ModelAndView("cook/view");
		Cook cook;
		if(cookId==null){
			cook =cookService.findByPrincipal();
			masterClasses= masterClassService.findRecipesByCook(cook);
		}else{
			cook = cookService.findOne(cookId);
			masterClasses = masterClassService.findRecipesByCook(cook);
		}
		result.addObject("masterclasses",masterClasses);
		result.addObject(cook);
		return result;
	}

	//create--------------------------------------------------------------
	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		ActorForm actorForm= new ActorForm();
		result = new ModelAndView("cook/edit");
		actorForm.setTypeOfActor("COOK");
		result.addObject("actorForm", actorForm);

		result.addObject("requestURI", "cook/administrator/create.do");
		result.addObject("typeActor", "cook");
		return result;
	}
	
	@RequestMapping(value = "/administrator/create", method = RequestMethod.POST,params = "save")
	public ModelAndView create(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		actorForm.setTypeOfActor("COOK");
		
		if (binding.hasErrors()) {
			System.out.print(binding.getAllErrors().toString());
			result = createEditModelAndView(actorForm);
		} else {
			try {
				Cook cook= cookService.create();

				UserAccount userAccount = new UserAccount();
				
				
				userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
				userAccount.setUsername(actorForm.getUsername());
		
				cook.setName(actorForm.getName());
				cook.setSurname(actorForm.getSurname());
				cook.setAddress(actorForm.getAddress());
				cook.setEmail(actorForm.getEmail());
				cook.setPhone(actorForm.getPhone());
				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				userAccount.setAuthorities(authorities);
				cook.setUserAccount(userAccount);
				
				cookService.save(cook);	
				
				result = this.list();
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "recipe.commit.error");	
				result.addObject("requestURI", "cook/administrator/create.do");
				
			}
		}
			
		return result;
	}	
	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Cook cook= cookService.findByPrincipal();
		ActorForm actorForm= new ActorForm();
		result = new ModelAndView("cook/edit");
		actorForm.setAddress(cook.getAddress());
		actorForm.setEmail(cook.getEmail());
		actorForm.setName(cook.getName());
		actorForm.setPassword(cook.getUserAccount().getPassword());
		actorForm.setPhone(cook.getPhone());
		actorForm.setSurname(cook.getSurname());
		actorForm.setTypeOfActor("COOK");
		actorForm.setUsername(cook.getUserAccount().getUsername());
		result.addObject("actorForm", actorForm);
		result.addObject("requestURI", "cook/edit.do");
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		actorForm.setTypeOfActor("COOK");
		if (binding.hasErrors()) {
			System.out.print(binding.getAllErrors().toString());
			result = createEditModelAndView(actorForm);
		} else {
			try {
				Cook cook= cookService.findByPrincipal();

				UserAccount userAccount = cook.getUserAccount();
				
				
				userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
				userAccount.setUsername(actorForm.getUsername());
		
				cook.setName(actorForm.getName());
				cook.setSurname(actorForm.getSurname());
				cook.setAddress(actorForm.getAddress());
				cook.setEmail(actorForm.getEmail());
				cook.setPhone(actorForm.getPhone());
				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				userAccount.setAuthorities(authorities);
				cook.setUserAccount(userAccount);
				
				cookService.save(cook);	
				
				result = this.view(null);
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "recipe.commit.error");	
				result.addObject("requestURI", "cook/edit.do");
				
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