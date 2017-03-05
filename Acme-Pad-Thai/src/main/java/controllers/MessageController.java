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
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("message")
public class MessageController extends AbstractController {
	
	//InitBinder---------------------------------------------------------------
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Services ---------------------------------------------------------------
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	

	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	// Creation ----------------------------------------------------------------	

		@RequestMapping(value="/create",method=RequestMethod.GET)
		public ModelAndView create(int recipientId) {
			ModelAndView result;
		
			Message message=messageService.create();
			//TODO en el servicio
			Assert.notNull(message);
			Actor sender=actorService.findActorByPrincial();
			Assert.notNull(sender);
			Actor recipient=actorService.findOne(recipientId);
			Assert.notNull(recipient);
			Folder folder=folderService.findFolderOfActor(recipient,"inbox");
			message.setFolder(folder);
			message.setSender(sender.getName());
			message.setRecipient(recipient.getName());
			//fin del todo
			result=createCreateModelAndView(message);
			return result;
		}
		
		@RequestMapping(value="/create",method=RequestMethod.POST,params="save")
		public ModelAndView save(@Valid Message message,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(message);
			
			
			if(binding.hasErrors()){
				System.out.println(binding);
				result=createCreateModelAndView(message);
				
			}else{
				//Pal servicio
				Actor sender =actorService.findActorByPrincial();
				//fin del todo
				messageService.sendMessage(message, sender , message.getFolder().getActor());
				result=new ModelAndView("redirect:/folder/list.do");
				
			}

			return result;
		}
		
		// View -------------------------------------------------------------------	

		@RequestMapping(value="/view",method=RequestMethod.GET,params="messageId")
		public ModelAndView view(int messageId) {
			ModelAndView result;
			
			Message message=messageService.findOne(messageId);
			
			result=createEditModelAndView(message);
			
			return result;
		}
		
		@RequestMapping(value="/view",method=RequestMethod.POST,params="save2")
		public ModelAndView save2(@Valid Message message,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(message);
			
			
			if(binding.hasErrors()){
				System.out.println(binding);
				result=createEditModelAndView(message);
				
			}else{
				
				messageService.save(message);
				result=new ModelAndView("redirect:/folder/list.do");
				
			}

			return result;
		}
		
		@RequestMapping(value="/view",method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@Valid Message message,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(message);
			
			try{
				messageService.delete(message);
				result=new ModelAndView("redirect:/folder/list.do");
			}catch (Throwable oops) {
				result=createEditModelAndView(message,"message.commit.error");
			}
			return result;
		}
		
		
		// Ancillary Methods --------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Message message){
			ModelAndView result;
			result=createEditModelAndView(message,null);
			return result;
		}

		protected ModelAndView createEditModelAndView(Message messag,String message) {
			ModelAndView result;
			Collection<Folder> folders=messag.getFolder().getActor().getFolders();
			result=new ModelAndView("message/view");
			result.addObject("messag",messag);
			result.addObject("folders",folders);
			result.addObject("message",message);
			
			return result;
		}
		protected ModelAndView createCreateModelAndView(Message message){
			ModelAndView result;
			result=createCreateModelAndView(message,null);
			return result;
		}

		protected ModelAndView createCreateModelAndView(Message messag,String message) {
			ModelAndView result;
			result=new ModelAndView("message/create");
			Collection<Folder> folders=folderService.findFoldersOfActor(messag.getFolder().getActor());
			result.addObject("messag",messag);
			result.addObject("folders",folders);
			result.addObject("message",message);
			return result;
		}

}
