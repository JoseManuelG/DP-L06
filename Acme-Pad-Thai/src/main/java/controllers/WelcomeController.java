/* WelcomeController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BannerService;
import services.CampaignService;
import services.CookService;
import services.MasterClassService;
import domain.Actor;
import domain.Banner;
import domain.Cook;
import domain.MasterClass;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	//Services-----------------------------------------------------------
	@Autowired
	private BannerService bannerService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private CookService cookService;
	
	// Constructors -----------------------------------------------------------
	
	public WelcomeController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required=false, defaultValue="John Doe") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		Banner banner = bannerService.randomStarBanner();
		campaignService.bannerDisplayed(banner);
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		Collection<MasterClass> promotedMasterClasses = masterClassService.findPromotedMasterClasses();
		int cookId=0;
		try {
			Actor actor = actorService.findActorByPrincial();
			if(actor !=null && actor instanceof Cook){
				cookId=cookService.findByPrincipal().getId();
			}
		}catch(Throwable t){
			
		}
			
		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("banner2", banner);
		result.addObject("moment", moment);
		result.addObject("promotedMasterClasses",promotedMasterClasses);
		if(cookId !=0)
			result.addObject("cookId",cookId);

		return result;
	}
}