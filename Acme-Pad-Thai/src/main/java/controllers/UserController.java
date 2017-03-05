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
import services.CustomerService;
import services.FollowService;
import services.RecipeService;
import services.UserService;
import domain.Customer;
import domain.Follow;
import domain.Recipe;
import domain.SocialIdentity;
import domain.User;
import forms.ActorForm;
import forms.StringForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService userService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private FollowService followService;
	@Autowired
	private RecipeService recipeService;


	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;
		users = userService.findAll();
		result = new ModelAndView("user/list");
		result.addObject("requestURI", "user/list.do");
		result.addObject("users", users);

		return result;
	}

	// Search ---------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		StringForm sf = new StringForm();
		result = new ModelAndView("user/search");
		result.addObject("stringForm", sf);
	
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "Accept")
	public ModelAndView search(StringForm stringForm) {
		ModelAndView result;
		String text = stringForm.getText();
		Collection<User> usersReturned = userService.searchForUser(text);
		result = new ModelAndView("user/list");
		result.addObject("users", usersReturned);
		return result;
	}

	// View ---------------------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = false) Integer userId) {
		ModelAndView result;
		Collection<Recipe> recipes;
		result = new ModelAndView("user/view");
		User user = userService.create();
		if (userId == null) {
			user = userService.findByPrincipal();
			recipes = recipeService.findRecipesByUser(user);
			boolean principal = true;
			result.addObject("principal", principal);
		} else {
			user = userService.findOne(userId);
			recipes = recipeService.findRecipesByUser(user);
			boolean principal = false;
			result.addObject(principal);
		}
		Collection<SocialIdentity> socialIdentities = user
				.getSocialIdentities();
		if (socialIdentities != null) {
			result.addObject("socialIdentities", socialIdentities);
		} else {
			socialIdentities = new ArrayList<SocialIdentity>();
			result.addObject("socialIdentities", socialIdentities);

		}
		Boolean follow = false;
			Customer customer = customerService.findActorByPrincial();
			for (Follow f : user.getFollowers()) {
				if (f.getFollower().equals(customer)) {
					follow = true;
					break;
				}
			}
		result.addObject("recipes", recipes);
		result.addObject("follow", follow);
		result.addObject("user", user);
		result.addObject("followers", user.getFollowers());
		result.addObject("followeds", user.getFolloweds());
		boolean esMiPerfil;
		if(customerService.findActorByPrincial()!=null){
			esMiPerfil= customerService.findActorByPrincial().equals(user);
		}else{
			esMiPerfil = true;
		}
		result.addObject("esMiPerfil",esMiPerfil);
		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		User user = userService.findByPrincipal();
		ActorForm actorForm = new ActorForm();
		result = new ModelAndView("user/edit");
		actorForm.setAddress(user.getAddress());
		actorForm.setEmail(user.getEmail());
		actorForm.setName(user.getName());
		actorForm.setPassword(user.getUserAccount().getPassword());
		actorForm.setPhone(user.getPhone());
		actorForm.setSurname(user.getSurname());
		actorForm.setTypeOfActor("USER");
		actorForm.setUsername(user.getUserAccount().getUsername());
		result.addObject("actorForm", actorForm);
		result.addObject("typeActor", "user");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				User user = userService.findByPrincipal();

				UserAccount userAccount = user.getUserAccount();

				userAccount.setPassword(encoder.encodePassword(
						actorForm.getPassword(), null));
				userAccount.setUsername(actorForm.getUsername());

				user.setName(actorForm.getName());
				user.setSurname(actorForm.getSurname());
				user.setAddress(actorForm.getAddress());
				user.setEmail(actorForm.getEmail());
				user.setPhone(actorForm.getPhone());
				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				userAccount.setAuthorities(authorities);
				user.setUserAccount(userAccount);

				userService.save(user);

				result = this.view(null);
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm,
						"recipe.commit.error");
			}
		}

		return result;
	}

	// Follow ----------------------------------------------------------------

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam int userId) {
		ModelAndView result;
		User followed = userService.findOne(userId);
		Customer follower = customerService.findActorByPrincial();
		Follow follow = followService.create();
		follow.setFollowed(followed);
		follow.setFollower(follower);
		followService.save(follow);
		result = new ModelAndView("redirect:/user/view.do?userId=" + userId);
		return result;
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int userId) {
		ModelAndView result;
		User followed = userService.findOne(userId);
		Customer follower = customerService.findActorByPrincial();
		Follow follow = followService.findFollowByFollowedAndFollower(followed,
				follower);
		followService.delete(follow);
		result = new ModelAndView("redirect:/user/view.do?userId=" + userId);
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(ActorForm actorForm) {
		ModelAndView result;

		result = createEditModelAndView(actorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ActorForm actorForm,
			String message) {
		ModelAndView result;
		result = new ModelAndView("user/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}

}