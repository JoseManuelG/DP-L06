package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;
import forms.ConfigurationForm;

@Controller
@RequestMapping("/configuration/admin")
public class ConfigurationController extends AbstractController {
	// Services ---------------------------------------------------------------
	
	@Autowired
	public ConfigurationService configurationService;
	
	// Constructors -----------------------------------------------------------
	
	public ConfigurationController() {
		super();
	}
		
	// View ---------------------------------------------------------------		

	
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		
		Configuration configuration=configurationService.findOne();
		Assert.notNull(configuration);
		
		result = new ModelAndView("configuration/admin/edit");
		result.addObject("configuration",configuration);
		result.addObject("keyWords",configuration.getKeyWords());

		return result;
	}

	@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid Configuration configuration,BindingResult binding) {
		ModelAndView result;
		
		Assert.notNull(configuration);
		
		if(binding.hasErrors()){
			System.out.println(binding);
			System.out.println("algo falla");
			result = new ModelAndView("configuration/admin/edit");
			result.addObject("configuration",configuration);
			result.addObject("keyWords",configuration.getKeyWords());
			
		}else{
			
			Configuration configurationSaved =configurationService.save(configuration);
			result=new ModelAndView("redirect:edit.do");
			result.addObject(configurationSaved);
			
		}
		
		return result;
	}
	
	// Create --------------------------------------------------------------
		@RequestMapping("/createKeyWord")
		public ModelAndView createKeyWord() {
			ModelAndView result;
			ConfigurationForm configurationForm = new ConfigurationForm();
			//TODO FUera de aqui(porque el formulario lleva en su interior el propio objeto?)
			configurationForm.setText("spam word here");
		
			Configuration configuration = configurationService.findOne();
			configurationForm.setConfiguration(configuration);
			//Fin del todo	
			result = new ModelAndView("configuration/admin/createKeyWord");
			result.addObject("configurationForm",configurationForm);
			return result;
		}
		
		@RequestMapping(value = "/createKeyWord",method=RequestMethod.POST,params="Accept" )
		public ModelAndView createKeyWord(ConfigurationForm configurationForm) {
			ModelAndView result;
			Configuration configuration = configurationService.findOne();
			//TODO pa servicios
			List<String> keyWords = new ArrayList<String>();
			keyWords.addAll(configuration.getKeyWords());
			keyWords.add(configurationForm.getText());
			configuration.setKeyWords(keyWords);
			//Fin del todo
			result = new ModelAndView("redirect:edit.do");
			Configuration savedConfiguration=configurationService.save(configuration);
			result.addObject(savedConfiguration);
			return result;
		}
		
		// Delete --------------------------------------------------------------
		@RequestMapping(value = "/deleteKeyWord")
		public 	ModelAndView deleteKeyWord(Integer keyWordIndex) {
			ModelAndView result;
			Configuration configuration = configurationService.findOne();
			try {
				//TODO pa Servicio
				ArrayList<String> keyWords = new ArrayList<String>();
				keyWords.addAll(configuration.getKeyWords());
				String keyWordToDelete= keyWords.get(Integer.valueOf(keyWordIndex));
				keyWords.remove(keyWordToDelete);
				configuration.setKeyWords(keyWords);
				//Fin del todo
				Configuration savedConfiguration=configurationService.save(configuration);
				result = new ModelAndView("redirect:edit.do");
				result.addObject(savedConfiguration);
				
			} catch (Throwable oops) {
				result = new ModelAndView("configuration.commit.error");
			}

			return result;
		}
		
	//Ancillary Methods-----------------------------------------------------
	
}