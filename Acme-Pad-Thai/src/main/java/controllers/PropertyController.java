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

import services.PropertyService;
import domain.Property;

@Controller
@RequestMapping("/property/nutritionist")
public class PropertyController extends AbstractController {
	@Autowired
	PropertyService propertyService;
	
	// Constructors -----------------------------------------------------------
	
	public PropertyController () {
		super();
	}
		
	// List ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Property> properties=propertyService.findAll();
		result = new ModelAndView("property/nutritionist/list");
		result.addObject("properties", properties);
		
		return result;
	}
	// Create ----------------------------------------------------------------	

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Property property= propertyService.create();
		
		
		result=new ModelAndView("property/nutritionist/create");
		result.addObject("property", property);

		return result; 
	}
	// Edition ----------------------------------------------------------------	

			@RequestMapping(value="/edit",method=RequestMethod.GET)
			public ModelAndView edit(Integer propertyId) {
				ModelAndView result;
				
				Property property = propertyService.findOne(propertyId);
				result=new ModelAndView("property/nutritionist/edit");
				
				result.addObject("property", property);
				return result;
			}
			
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public @ResponseBody ModelAndView save(@Valid Property property, BindingResult binding) {
				ModelAndView result=null;
				
				
				if (binding.hasErrors()) {
					result=new ModelAndView("property/nutritionist/edit");
					result.addObject("property", property);
					System.out.println(binding.getAllErrors().toString());
				} else {
					try {
						
						propertyService.save(property);
						result = this.list();
						
				} catch (Throwable oops) {
					result = createEditModelAndView(property, "recipe.commit.error");	
					}
					
			}
					return result;
			}
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
			public @ResponseBody ModelAndView save(@Valid Property property) {
				ModelAndView result=null;
					try {
						propertyService.delete(property);
					
						result = this.list();
						
				} catch (Throwable oops) {
					result = createEditModelAndView(property, "recipe.commit.error");	
					}
					
			
					return result;
			}

	
	// Ancillary Methods --------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Property property){
		ModelAndView result;
		result=createEditModelAndView(property,null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Property property,String message) {
		ModelAndView result;
		result=new ModelAndView("property/nutritionist/edit");
		result.addObject("property",property);
		result.addObject("message",message);
		return result;
	}


}
