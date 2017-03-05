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

import security.LoginService;
import services.CurriculumService;
import services.NutritionistService;
import domain.Attend;
import domain.Curriculum;
import domain.Endorser;
import domain.LearningMaterial;
import domain.Nutritionist;

@Controller
@RequestMapping("/curriculum/nutritionist")
public class CurriculumController extends AbstractController {
	@Autowired
	CurriculumService curriculumService;
	@Autowired
	NutritionistService nutritionistService;
	@Autowired
	LoginService loginService;
	
	
	// Constructors -----------------------------------------------------------
	
	public CurriculumController () {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Nutritionist> nutritionists=nutritionistService.findAll();
		result = new ModelAndView("curriculum/list");
		result.addObject("nutritionists", nutritionists);
		
		return result;
	}
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView result;
		Nutritionist nutritionist=nutritionistService.findByPrincipal();
		Curriculum curriculum;
		if(nutritionist.getCurriculum()!=null){
		curriculum= nutritionist.getCurriculum();
		}else{
			curriculum= curriculumService.create();
		}
		Collection<Endorser> endorsers= curriculum.getEndorsers();
		
		result = new ModelAndView("curriculum/view");
		result.addObject("curriculum",curriculum);
		result.addObject("endorsers", endorsers);
		
		return result;
	}
	// Create ----------------------------------------------------------------	

	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Nutritionist nutritionist = nutritionistService.findByPrincipal();
		
		Curriculum curriculum;
		if(nutritionist.getCurriculum()!=null){
			curriculum= nutritionist.getCurriculum();
			}else{
				curriculum= curriculumService.create();
			}
		
	
		result=createEditModelAndView(curriculum);

		return result; 
	}
	// Edition ----------------------------------------------------------------	

			@RequestMapping(value="/edit",method=RequestMethod.GET)
			public ModelAndView edit() {
				ModelAndView result;
				
				Nutritionist nutritionist = nutritionistService.findByPrincipal();
				result=new ModelAndView("curriculum/nutritionist/edit");
				Curriculum curriculum;
				if(nutritionist.getCurriculum()!=null){
					curriculum= nutritionist.getCurriculum();
					}else{
						curriculum= curriculumService.create();
					}
				result.addObject("curriculum", curriculum);
				return result;
			}
			
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public @ResponseBody ModelAndView save(@Valid Curriculum curriculum, BindingResult binding) {
				ModelAndView result=null;
				Nutritionist nutritionist = nutritionistService.findByPrincipal();
				
				if (binding.hasErrors()) {
					result = createEditModelAndView(curriculum);
					System.out.println(binding.getAllErrors().toString());
				} else {
					try {
						//TODO revisar esto
						nutritionist.setCurriculum(curriculumService.save(curriculum));
						nutritionistService.save(nutritionist);
						//fin del todo
						result = new ModelAndView("curriculum/view");
						
				} catch (Throwable oops) {
					result = createEditModelAndView(curriculum, "recipe.commit.error");	
					}
					
			}
					return result;
			}
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
			public @ResponseBody ModelAndView save(@Valid Curriculum curriculum) {
				ModelAndView result=null;
					try {
						curriculumService.delete(curriculum);
					
						result = this.view();
						
				} catch (Throwable oops) {
					result = createEditModelAndView(curriculum, "recipe.commit.error");	
					}
					
			
					return result;
			}

	
	// Ancillary Methods --------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Curriculum curriculum){
		ModelAndView result;
		result=createEditModelAndView(curriculum,null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Curriculum curriculum,String message) {
		ModelAndView result;
		result=new ModelAndView("curriculum/nutritionist/edit");
		result.addObject("curriculum",curriculum);
		result.addObject("message",message);
		return result;
	}


}
