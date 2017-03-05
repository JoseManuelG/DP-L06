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
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AttendService;
import services.CookService;
import services.MasterClassService;
import domain.Actor;
import domain.Attend;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;

@Controller
@RequestMapping("/masterclass")
public class MasterClassController extends AbstractController {

	// Constructors -----------------------------------------------------------
	@Autowired
	MasterClassService masterClassService;
	public MasterClassController() {
		super();
	}
		
	//Services
	@Autowired
	LoginService loginService;
	@Autowired
	ActorService actorService;
	@Autowired
	AttendService attendService;
	@Autowired
	CookService cookService;
	
	// List---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;				
		Collection<MasterClass> masterClasses = masterClassService.findAll();
		result = new ModelAndView("masterclass/list");
		result.addObject("masterClasses", masterClasses);
		
		try {
			Actor actor = actorService.findActorByPrincial();
			if(actor !=null && actor instanceof Cook){
				masterClasses.removeAll(cookService.findByPrincipal().getMasterClasses());
			}
			ArrayList<Integer> attendClasses = new ArrayList<Integer>();
			for(Attend attend:actor.getAttends()){
				attendClasses.add(attend.getMasterClass().getId());
			}
			result.addObject("attendClasses", attendClasses);
			result.addObject("requestURI", false);

		}catch(IllegalArgumentException e){
			
		}			
		return result;
	}

	@RequestMapping(value = "/listByCook", method = RequestMethod.GET)
	public ModelAndView listByCook(@RequestParam(required = false) Integer userId) {
		ModelAndView result;
		Collection<MasterClass> masterClasses;
		if(userId == null){
			Cook cook = cookService.findByPrincipal();
			masterClasses = masterClassService.findRecipesByCook(cook);
		}else{
			Cook cook= cookService.findOne(userId);
			masterClasses = masterClassService.findRecipesByCook(cook);
		}
		result = new ModelAndView("masterclass/listByCook");
		
		result.addObject("masterClasses",masterClasses);

		result.addObject("requestURI", true);
		return result;
	}

	// Attend---------------------------------------------------------------		

	@RequestMapping("/attend")
	public ModelAndView attend(int masterclassId) {
		ModelAndView result;
		
		
		
		try {
			
			Attend attend= attendService.create();
			Actor actor= actorService.findActorByPrincial();
			System.out.println(actor.getId());
			attend.setActor(actor);
			attend.setMasterClass(masterClassService.findOne(masterclassId));
			attendService.save(attend);
			
		}catch(IllegalArgumentException e){
			
		}

		result=this.list();	
		return result;
	}

	// Unattend---------------------------------------------------------------		

		@RequestMapping("/unattend")
		public ModelAndView unattend(int masterclassId) {
			ModelAndView result;
			
			
			
			
			try {
				Actor actor = actorService.findActorByPrincial();
				Attend attend = attendService.findAttendByMasterClassAndActor(actor.getId(), masterclassId);
				attendService.delete(attend);
				
			}catch(IllegalArgumentException e){
				
			}

			result=this.list();
			return result;
		}

		// Promote---------------------------------------------------------------		

		@RequestMapping("/administrator/promote")
		public ModelAndView promote(int masterclassId) {
			ModelAndView result;
			
			
			
			try {
				MasterClass masterClass = masterClassService.findOne(masterclassId);
				masterClass.setPromoted(true);
				masterClassService.save(masterClass);
			}catch(IllegalArgumentException e){
				
			}

			result=this.list();	
			return result;
		}

		// Unpromote---------------------------------------------------------------		

			@RequestMapping("/administrator/unpromote")
			public ModelAndView unpromote(int masterclassId) {
				ModelAndView result;
				
				
				
				
				
				
				try {
					MasterClass masterClass = masterClassService.findOne(masterclassId);
					masterClass.setPromoted(false);
					masterClassService.save(masterClass);
				}catch(IllegalArgumentException e){
					
				}

				result=this.list();
				return result;
			}


	// View---------------------------------------------------------------
	
	@RequestMapping("/view")
	public ModelAndView view(int masterClassId) {
		ModelAndView result;
		Actor actor = actorService.findActorByPrincial();
		MasterClass mc= masterClassService.findOne(masterClassId);
		Boolean aux = false;
		for(Attend attend : actor.getAttends()){
			aux=attend.getMasterClass().equals(mc);
			if(aux){
				break;
			}
		}
		if(aux||mc.getCook().equals(actor)){
		result = new ModelAndView("masterclass/view");
		result.addObject("masterclass", mc);

		result.addObject("learningmaterials", mc.getLearningMaterials());
		}else{
			result = this.list();
		}
		if(actor !=null && actor instanceof Cook ){
			Cook ownerCook= cookService.findByPrincipal();
			result.addObject("ownerCook", ownerCook);
		}
	
		return result;
	}
	// Create ----------------------------------------------------------------	

	@RequestMapping(value="/cook/create",method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MasterClass masterClass=masterClassService.create();
		Assert.notNull(masterClass);
		
		Collection<Attend> attends=new ArrayList<Attend>();
		Collection<LearningMaterial>learningMaterials =new ArrayList<LearningMaterial>();
		masterClass.setAttends(attends);
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setCook(cookService.findByPrincipal());
		result=createEditModelAndView(masterClass);

		return result; 
	}
	// Edition ----------------------------------------------------------------	

			@RequestMapping(value="/cook/edit",method=RequestMethod.GET)
			public ModelAndView edit(int masterClassId) {
				ModelAndView result;
				
				MasterClass masterClass=masterClassService.findOne(masterClassId);
				Assert.notNull(masterClass);
				
				result=new ModelAndView("masterclass/cook/edit");
				result.addObject("masterClass",masterClass);

				return result;
			}
			//Save----------------------------------------
			@RequestMapping(value = "/cook/edit", method = RequestMethod.POST, params = "save")
			public @ResponseBody ModelAndView save(@Valid MasterClass masterClass, BindingResult binding) {
				ModelAndView result=null;
				
				
				if (binding.hasErrors()) {
					result = createEditModelAndView(masterClass);
					System.out.println(binding.getAllErrors().toString());
				} else {
					try {
						masterClassService.save(masterClass);
					
						result = this.listByCook(null);	
						
				} catch (Throwable oops) {
					result = createEditModelAndView(masterClass, "recipe.commit.error");	
					}
					
			}
					return result;
			}
			@RequestMapping(value = "/cook/edit", method = RequestMethod.POST, params = "delete")
			public @ResponseBody ModelAndView save(@Valid MasterClass masterClass) {
				ModelAndView result=null;
					try {
						masterClassService.delete(masterClass);
					
						result = this.listByCook(null);

						
				} catch (Throwable oops) {
					result = createEditModelAndView(masterClass, "recipe.commit.error");	
					}
					
			
					return result;
			}

	
	// Ancillary Methods --------------------------------------------------
	
	protected ModelAndView createEditModelAndView(MasterClass masterClass){
		ModelAndView result;
		result=createEditModelAndView(masterClass,null);
		return result;
	}

	protected ModelAndView createEditModelAndView(MasterClass masterClass,String message) {
		ModelAndView result;
		result=new ModelAndView("masterclass/cook/edit");
		result.addObject("masterClass",masterClass);
		result.addObject("message",message);
		return result;
	}

	
}