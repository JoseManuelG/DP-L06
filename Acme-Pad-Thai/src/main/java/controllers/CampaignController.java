package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import services.CampaignService;
import services.SponsorService;
import domain.Banner;
import domain.Campaign;
import domain.Sponsor;

@Controller
@RequestMapping("campaign/sponsor")
public class CampaignController extends AbstractController {
	
	//InitBinder---------------------------------------------------------------
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	// Services ---------------------------------------------------------------
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private SponsorService sponsorService;
	
	// Constructors -----------------------------------------------------------

	public CampaignController() {
		super();
	}

	// List -------------------------------------------------------------------	

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Sponsor sponsor=sponsorService.findByPrincipal();
		Collection<Campaign> campaigns=campaignService.sponsorCampaigns(sponsor.getId());
		result = new ModelAndView("campaign/sponsor/list");
		result.addObject("campaigns",campaigns);
		result.addObject("requestURI","campaign/sponsor/list.do");

		return result;
	}
	
	// Edition ----------------------------------------------------------------	

		@RequestMapping(value="/edit",method=RequestMethod.GET,params="campaignId")
		public ModelAndView edit(int campaignId) {
			ModelAndView result;
			
			Campaign campaign=campaignService.findOne(campaignId);
			Assert.notNull(campaign);
			
			result=createEditModelAndView(campaign);
			result = new ModelAndView("campaign/sponsor/edit");
			result.addObject("campaign",campaign);

			return result;
		}
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")
		public ModelAndView save(@Valid Campaign campaign,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(campaign);
			Date currentDate=new Date(System.currentTimeMillis()-1000);
			if(binding.hasErrors()){
				result=createEditModelAndView(campaign,"campaign.commit.error");
			}else if(campaign.getDateOfEnd().before(campaign.getDateOfStart())
					||campaign.getDateOfStart().before(currentDate)){
				result=createEditModelAndView(campaign,"campaign.commit.error");
			}else{
				
				campaignService.save(campaign);
				result=new ModelAndView("redirect:list.do");
				
			}

			return result;
		}
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@Valid Campaign campaign,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(campaign);

			try{
				campaignService.delete(campaign);
				result=new ModelAndView("redirect:list.do");
			}catch (Throwable oops) {
				result=createEditModelAndView(campaign,"campaign.commit.error");
			}

			return result;
		}
		
		// Create ----------------------------------------------------------------	

		@RequestMapping(value="/create",method=RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			
			Campaign campaign=campaignService.create();
			Assert.notNull(campaign);				
			result=createEditModelAndView(campaign);
			result = new ModelAndView("campaign/sponsor/edit");
			result.addObject("campaign",campaign);

			return result;
		}
		
		// View -------------------------------------------------------------------	

		@RequestMapping(value="/view",method=RequestMethod.GET,params="campaignId")
		public ModelAndView view(int campaignId) {
			ModelAndView result;
			
			Campaign campaign=campaignService.findOne(campaignId);
			
			result = new ModelAndView("campaign/sponsor/view");
			result.addObject("campaign",campaign);
			result.addObject("banners",campaign.getBannerList());
			result.addObject("requestURI","campaign/sponsor/view.do");
			return result;
		}
		
		// Ancillary Methods --------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Campaign campaign){
			ModelAndView result;
			result=createEditModelAndView(campaign,null);
			return result;
		}

		protected ModelAndView createEditModelAndView(Campaign campaign,String message) {
			ModelAndView result;
			Sponsor sponsor=campaign.getSponsor();
			Assert.notNull(sponsor);
			Collection<Banner> banners=campaign.getBannerList();
			result=new ModelAndView("campaign/sponsor/edit");
			result.addObject("sponsor",sponsor);
			result.addObject("banners",banners);
			result.addObject("message",message);
			return result;
		}

}
