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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.FolderService;
import services.NutritionistService;
import services.SponsorService;
import services.UserService;
import domain.Nutritionist;
import domain.Sponsor;
import domain.User;
import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SecurityController extends AbstractController{

	//Services------------------------------------------------------------
	@Autowired
	UserService userService;
	@Autowired
	FolderService folderService;
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
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				UserAccount userAccount = new UserAccount();
				
				
				userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
				userAccount.setUsername(actorForm.getUsername());
				if(actorForm.getTypeOfActor().equals("USER")){
				User user = userService.create();
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
				folderService.createBasicsFolders(user);
				userService.save(user);
				
				
				}else if(actorForm.getTypeOfActor().equals("SPONSOR")){
				Sponsor sponsor =sponsorService.create();
				
				sponsor.setName(actorForm.getName());
				sponsor.setSurname(actorForm.getSurname());
				sponsor.setAddress(actorForm.getAddress());
				sponsor.setEmail(actorForm.getEmail());
				sponsor.setPhone(actorForm.getPhone());
				sponsor.setCompanyName(actorForm.getCompanyName());
				
				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				userAccount.setAuthorities(authorities);
				sponsor.setUserAccount(userAccount);
				folderService.createBasicsFolders(sponsor);
				sponsorService.save(sponsor);
				
				}else if(actorForm.getTypeOfActor().equals("NUTRITIONIST")){
				Nutritionist nutritionist= nutritionistService.create();
				
				nutritionist.setName(actorForm.getName());
				nutritionist.setSurname(actorForm.getSurname());
				nutritionist.setAddress(actorForm.getAddress());
				nutritionist.setEmail(actorForm.getEmail());
				nutritionist.setPhone(actorForm.getPhone());
				
				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				
				userAccount.setAuthorities(authorities);
				nutritionist.setUserAccount(userAccount);
				folderService.createBasicsFolders(nutritionist);
				nutritionistService.save(nutritionist);
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

