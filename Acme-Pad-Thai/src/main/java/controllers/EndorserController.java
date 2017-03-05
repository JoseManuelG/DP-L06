package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculumService;
import services.EndorserService;
import services.NutritionistService;
import domain.Curriculum;
import domain.Endorser;
import domain.Nutritionist;

@Controller
@RequestMapping("/endorser/nutritionist")
public class EndorserController {
	@Autowired
	EndorserService endorserService;
	
	@Autowired
	CurriculumService curriculumService;
	@Autowired
	NutritionistService nutritionistService;
	@Autowired
	LoginService loginService;
	
	
	// Constructors -----------------------------------------------------------
	
	public EndorserController () {
		super();
	}
		
	// Create ----------------------------------------------------------------	

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Nutritionist nutritionist = nutritionistService.findByPrincipal();
		Curriculum curriculum=nutritionist.getCurriculum();
		Endorser endorser= endorserService.create();
		
		endorser.setCurriculum(curriculum);
		result=createEditModelAndView(endorser);

		return result; 
	}
	// Edition ----------------------------------------------------------------	

			@RequestMapping(value="/edit",method=RequestMethod.GET)
			public ModelAndView edit(Integer endorserId) {
				ModelAndView result;
				
				
				result=new ModelAndView("endorser/nutritionist/edit");
				nutritionistService.findByPrincipal();
				Endorser endorser= endorserService.findOne(endorserId);
				result.addObject("endorser", endorser);
				
				return result;
			}
			
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public @ResponseBody ModelAndView save(@Valid Endorser endorser, BindingResult binding) {
				ModelAndView result=null;
				nutritionistService.findByPrincipal();
				
				if (binding.hasErrors()) {
					result = createEditModelAndView(endorser);
					System.out.println(binding.getAllErrors().toString());
				} else {
					try {
						endorserService.save(endorser);
						
						result = new ModelAndView("redirect:../../curriculum/nutritionist/view.do");
						
				} catch (Throwable oops) {
					result = createEditModelAndView(endorser, "endorser.commit.error");	
					}
					
			}
					return result;
			}
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
			public @ResponseBody ModelAndView save(@Valid Endorser endorser) {
				ModelAndView result=null;
					try {
						endorserService.delete(endorser);
					
						result = new ModelAndView("redirect:../../curriculum/nutritionist/view.do");
						
				} catch (Throwable oops) {
					result = createEditModelAndView(endorser, "endorser.commit.error");	
					}
					
			
					return result;
			}

			// Ancillary Methods --------------------------------------------------
			
			protected ModelAndView createEditModelAndView(Endorser endorser){
				ModelAndView result;
				result=createEditModelAndView(endorser,null);
				return result;
			}

			protected ModelAndView createEditModelAndView(Endorser endorser,String message) {
				ModelAndView result;
				result=new ModelAndView("endorser/nutritionist/edit");
				result.addObject("endorser",endorser);
				result.addObject("message",message);
				return result;
			}


}
