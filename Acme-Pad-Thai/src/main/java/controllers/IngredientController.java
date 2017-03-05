package controllers;

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
import services.IngredientService;
import services.NutritionistService;
import domain.Ingredient;

@Controller
@RequestMapping("/ingredient/nutritionist")
public class IngredientController extends AbstractController {
	@Autowired
	IngredientService ingredientService;
	@Autowired
	NutritionistService nutritionistService;
	@Autowired
	LoginService loginService;
	
	
	// Constructors -----------------------------------------------------------
	
	public IngredientController () {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Ingredient> ingredients=ingredientService.findAll();
		result = new ModelAndView("ingredient/nutritionist/list");
		result.addObject("ingredients", ingredients);
		
		return result;
	}
	@RequestMapping("/view")
	public ModelAndView view(Integer ingredientId) {
		ModelAndView result;
		Ingredient ingredient=ingredientService.findOne(ingredientId);
		
		
		result = new ModelAndView("ingredient/nutritionist/view");
		result.addObject("ingredient",ingredient);
		result.addObject("propertyValues",ingredient.getPropertyValues());
		return result;
	}
	// Create ----------------------------------------------------------------	

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Ingredient ingredient= ingredientService.create();
		
		
		result=createEditModelAndView(ingredient);

		return result; 
	}
	// Edition ----------------------------------------------------------------	

			@RequestMapping(value="/edit",method=RequestMethod.GET)
			public ModelAndView edit(Integer ingredientId) {
				ModelAndView result;
				
				Ingredient ingredient = ingredientService.findOne(ingredientId);
				result=new ModelAndView("ingredient/nutritionist/edit");
				
				result.addObject("ingredient", ingredient);
				return result;
			}
			
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public @ResponseBody ModelAndView save(@Valid Ingredient ingredient, BindingResult binding) {
				ModelAndView result=null;
				
				
				if (binding.hasErrors()) {
					result = createEditModelAndView(ingredient);
					System.out.println(binding.getAllErrors().toString());
				} else {
					try {
						
						ingredientService.save(ingredient);
						result = this.list();
						
				} catch (Throwable oops) {
					result = createEditModelAndView(ingredient, "ingredient.commit.error");	
					}
					
			}
					return result;
			}
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
			public @ResponseBody ModelAndView save(@Valid Ingredient ingredient) {
				ModelAndView result=null;
					try {
						ingredientService.delete(ingredient);
					
						result = this.list();
						
				} catch (Throwable oops) {
					result = createEditModelAndView(ingredient, "ingredient.commit.error");	
					}
					
			
					return result;
			}

	
	// Ancillary Methods --------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Ingredient ingredient){
		ModelAndView result;
		result=createEditModelAndView(ingredient,null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Ingredient ingredient,String message) {
		ModelAndView result;
		result=new ModelAndView("ingredient/nutritionist/edit");
		result.addObject("ingredient",ingredient);
		result.addObject("message",message);
		return result;
	}


}
