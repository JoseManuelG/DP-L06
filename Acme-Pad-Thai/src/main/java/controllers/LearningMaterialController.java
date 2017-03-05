package controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import services.LearningMaterialService;
import services.MasterClassService;
import domain.Actor;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;
import forms.AttachmentForm;

@Controller
@RequestMapping("/learningmaterial")
public class LearningMaterialController extends AbstractController {

	@Autowired
	LearningMaterialService learningMaterialService;
	@Autowired
	MasterClassService masterClassService;
	@Autowired
	ActorService actorService;

	@Autowired
	LoginService loginService;

	// Constructors -----------------------------------------------------------

	public LearningMaterialController() {
		super();
	}

	// View-----------------------------------------------------------

	@RequestMapping("/view")
	public ModelAndView view(@RequestParam int learningMaterialId) {
		ModelAndView result;
		LearningMaterial learningmaterial = learningMaterialService
				.findOne(learningMaterialId);
		boolean myAttachment=false;
		try {
			Actor actor = actorService.findActorByPrincial();
			if(actor !=null && actor instanceof Cook && actor.equals(learningmaterial.getMasterClass().getCook()))
				myAttachment=true;
		}catch(IllegalArgumentException e){}	
		result = new ModelAndView("learningmaterial/view");
		result.addObject("learningMaterial", learningmaterial);
		result.addObject("attachments",learningmaterial.getAttachments());
		result.addObject("myAttachment",myAttachment);
		
		return result;
	}

	// Create ----------------------------------------------------------------

	@RequestMapping(value = "/cook/create", method = RequestMethod.GET)
	public ModelAndView create(Integer masterClassId) {
		ModelAndView result;
		LearningMaterial learningMaterial = learningMaterialService.create();
		Assert.notNull(learningMaterial);
		MasterClass masterClass= masterClassService.findOne(masterClassId);
		learningMaterial.setMasterClass(masterClass);
		result = new ModelAndView("learningmaterial/cook/edit");
		result.addObject("learningMaterial",learningMaterial);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/cook/edit", method = RequestMethod.GET)
	public ModelAndView edit(Integer learningMaterialId) {
		ModelAndView result;

		LearningMaterial learningMaterial = learningMaterialService.findOne(learningMaterialId);
		Assert.notNull(learningMaterial);

		result = new ModelAndView("learningmaterial/cook/edit");
		result.addObject("learningMaterial", learningMaterial);

		return result;
	}

	@RequestMapping(value = "/cook/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid LearningMaterial learningMaterial,BindingResult binding) {
		ModelAndView result = null;
		boolean aux = false;
		if(learningMaterial.getType().equals("presentation")){
			aux=learningMaterial.getData().contains("slideshare.net");
		}else if(learningMaterial.getType().contains("video")){
			aux=learningMaterial.getData().contains("youtube.com");	
		}else if(learningMaterial.getType().contains("text")){
			aux=true;
		}
		if (binding.hasErrors()) {
			result = createEditModelAndView(learningMaterial);
			System.out.println(binding.getAllErrors().toString());
		} else {
			try {
				Assert.isTrue(aux);
				learningMaterialService.save(learningMaterial);
				result = new ModelAndView("redirect:../../masterclass/view.do?masterClassId="+learningMaterial.getMasterClass().getId());

			} catch (Throwable oops) {
				result = createEditModelAndView(learningMaterial,
						"recipe.commit.error");
			}

		}
		return result;
	}

	@RequestMapping(value = "/cook/edit", method = RequestMethod.POST, params = "delete")
	public @ResponseBody
	ModelAndView save(@Valid LearningMaterial learningMaterial) {
		ModelAndView result = null;
		try {
			learningMaterialService.delete(learningMaterial);

			result = new ModelAndView("redirect:../../masterclass/view.do?masterClassId="+learningMaterial.getMasterClass().getId());

		} catch (Throwable oops) {
			result = createEditModelAndView(learningMaterial,
					"recipe.commit.error");
		}

		return result;
	}
	
	// ----------------------------------------Attachment -----------------------
	
			// Create --------------------------------------------------------------
			@RequestMapping("/cook/addAttachment")
			public ModelAndView addAttachment(String learningMaterialId) {
				ModelAndView result;
				AttachmentForm af = new AttachmentForm();
				af.setText("http://www.yourattachment.com");
				LearningMaterial learningMaterial = learningMaterialService.findOne(Integer.valueOf(learningMaterialId));
				af.setLearningMaterial(learningMaterial);
				result = new ModelAndView("learningmaterial/cook/addAttachment");
				result.addObject("attachmentForm",af);
				return result;
			}

			@RequestMapping(value = "/cook/addAttachment",method=RequestMethod.POST,params="Accept" )
			public ModelAndView addAttachment(AttachmentForm attachmentForm, BindingResult binding) {
				ModelAndView result;
				LearningMaterial learningMaterial = attachmentForm.getLearningMaterial();
				List<String> attachments = new ArrayList<String>();
				attachments.addAll(learningMaterial.getAttachments());
				String newAttachment =attachmentForm.getText();
				attachments.add(newAttachment);
				learningMaterial.setAttachments(attachments);
				learningMaterialService.save(learningMaterial);
				result = new ModelAndView("redirect:../view.do?learningMaterialId="+learningMaterial.getId());
				return result;
			}
			
			// Delete --------------------------------------------------------------
			@RequestMapping(value = "/cook/deleteAttachment")
			public 	ModelAndView deleteAttachment(String learningMaterialId, Integer attachmentIndex) {
				ModelAndView result;
				LearningMaterial learningMaterial = learningMaterialService.findOne(Integer.valueOf(learningMaterialId));
				try {
					
					List<String> attachments = new LinkedList<String>();
					attachments.addAll(learningMaterial.getAttachments());
					String attachmentToDelete= attachments.get(attachmentIndex);
					attachments.remove(attachmentToDelete);
					learningMaterial.setAttachments(attachments);
					learningMaterialService.save(learningMaterial);
					result = new ModelAndView("redirect:../view.do?learningMaterialId="+learningMaterial.getId());
				} catch (Throwable oops) {
					result = new ModelAndView("recipe.commit.error");
				}

				return result;
			}
			
		

	// Ancillary Methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(
			LearningMaterial learningMaterial) {
		ModelAndView result;
		result = createEditModelAndView(learningMaterial, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(
			LearningMaterial learningMaterial, String message) {
		ModelAndView result;
		result = new ModelAndView("learningmaterial/cook/edit");
		result.addObject("masterClass", learningMaterial);
		result.addObject("message", message);
		return result;
	}

}
