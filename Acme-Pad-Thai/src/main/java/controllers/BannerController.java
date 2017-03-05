package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.CampaignService;
import domain.Banner;
import domain.Campaign;

@Controller
@RequestMapping("banner/sponsor")
public class BannerController extends AbstractController {
	

	// Services ---------------------------------------------------------------
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private CampaignService campaignService;

	
	// Constructors -----------------------------------------------------------

	public BannerController() {
		super();
	}

	
	// Edition ----------------------------------------------------------------	

		@RequestMapping(value="/edit",method=RequestMethod.GET)
		public ModelAndView edit(int bannerId) {
			ModelAndView result;
			
			Banner banner=bannerService.findOne(bannerId);
			Assert.notNull(banner);
			
			result=createEditModelAndView(banner);

			return result;
		}
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")
		public ModelAndView save(@Valid Banner banner,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(banner);
			
			
			if(binding.hasErrors()){
				System.out.println(binding);
				result=createEditModelAndView(banner);
				
			}else{
				
				bannerService.save(banner);
				result=new ModelAndView("redirect:/campaign/sponsor/view.do?campaignId="+banner.getCampaign().getId());
				
			}

			return result;
		}
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@Valid Banner banner,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(banner);

			try{
				bannerService.delete(banner);
				result=new ModelAndView("redirect:/campaign/sponsor/view.do?campaignId="+banner.getCampaign().getId());
			}catch (Throwable oops) {
				result=createEditModelAndView(banner,"banner.commit.error");
			}

			return result;
		}
		
		// Create ----------------------------------------------------------------	

		@RequestMapping(value="/create",method=RequestMethod.GET,params="campaignId")
		public ModelAndView create(int campaignId) {
			ModelAndView result;
			
			Banner banner=bannerService.create();
			Assert.notNull(banner);
			Campaign campaign=campaignService.findOne(campaignId);
			banner.setCampaign(campaign);
			
			result=createEditModelAndView(banner);

			return result;
		}
		
		// Ancillary Methods --------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Banner banner){
			ModelAndView result;
			result=createEditModelAndView(banner,null);
			return result;
		}

		protected ModelAndView createEditModelAndView(Banner banner,String message) {
			ModelAndView result;
			Campaign campaign=banner.getCampaign();
			Assert.notNull(campaign);
			result=new ModelAndView("banner/sponsor/edit");
			result.addObject("banner",banner);
			result.addObject("message",message);
			return result;
		}

}
