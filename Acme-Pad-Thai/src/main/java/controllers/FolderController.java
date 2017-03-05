package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import domain.Actor;
import domain.Folder;

@Controller
@RequestMapping("folder")
public class FolderController extends AbstractController {
	
	//InitBinder---------------------------------------------------------------
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Services ---------------------------------------------------------------
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public FolderController() {
		super();
	}

	// List -------------------------------------------------------------------	

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Actor actor=actorService.findActorByPrincial();
		Collection<Folder> folders=actorService.findActorFoldersById(actor.getId());

		//fin del todo
		result = new ModelAndView("folder/list");
		result.addObject("folders",folders);
		result.addObject("requestURI","folder/list.do");

		return result;
	}
	
	// Edition ----------------------------------------------------------------	

		@RequestMapping(value="/edit",method=RequestMethod.GET,params="folderId")
		public ModelAndView edit(int folderId) {
			ModelAndView result;
			
			Folder folder=folderService.findOne(folderId);
			Assert.notNull(folder);
			
			result=createEditModelAndView(folder);

			return result;
		}
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")
		public ModelAndView save(@Valid Folder folder,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(folder);
			
			
			if(binding.hasErrors()){
				System.out.println(binding);
				result=createEditModelAndView(folder);
				
			}else if (folder.getisBasic()){
				result=new ModelAndView("redirect:list.do");
			}else{
				
				folderService.save(folder);
				result=new ModelAndView("redirect:list.do");
				
			}

			return result;
		}
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@Valid Folder folder,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(folder);
			
			if(folder.getisBasic()){
				result=new ModelAndView("redirect:list.do");
			}else{
			try{
				folderService.delete(folder);
				result=new ModelAndView("redirect:list.do");
			}catch (Throwable oops) {
				result=createEditModelAndView(folder,"folder.commit.error");
			}
			}
			return result;
		}
		
		// Create ----------------------------------------------------------------	

		@RequestMapping(value="/create",method=RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			
			Folder folder=folderService.create();
			Assert.notNull(folder);
			result=createEditModelAndView(folder);

			return result;
		}
		
		// View -------------------------------------------------------------------	

		@RequestMapping(value="/view",method=RequestMethod.GET,params="folderId")
		public ModelAndView view(int folderId) {
			ModelAndView result;
			
			Folder folder=folderService.findOne(folderId);
			
			result = new ModelAndView("folder/view");
			result.addObject("folder",folder);
			result.addObject("messages",folder.getMessages());
			
			return result;
		}
		
		// Ancillary Methods --------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Folder folder){
			ModelAndView result;
			result=createEditModelAndView(folder,null);
			return result;
		}

		protected ModelAndView createEditModelAndView(Folder folder,String message) {
			ModelAndView result;
			result=new ModelAndView("folder/edit");
			result.addObject("folder",folder);
			result.addObject("message",message);
			return result;
		}

}
