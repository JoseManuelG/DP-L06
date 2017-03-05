package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.SponsorService;
import domain.CreditCard;
import domain.Sponsor;

@Controller
@RequestMapping("creditCard/sponsor")
public class CreditCardController extends AbstractController {
	

	// Services ---------------------------------------------------------------
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private SponsorService sponsorService;

	
	// Constructors -----------------------------------------------------------

	public CreditCardController() {
		super();
	}

	
	// Edition ----------------------------------------------------------------	

		@RequestMapping(value="/edit",method=RequestMethod.GET)
		public ModelAndView edit(int creditCardId) {
			ModelAndView result;
			
			CreditCard creditCard=creditCardService.findOne(creditCardId);
			Assert.notNull(creditCard);
			
			result=createEditModelAndView(creditCard);

			return result;
		}
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")
		public ModelAndView save(@Valid CreditCard creditCard,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(creditCard);
			
			
			if(binding.hasErrors()){
				System.out.println(binding);
				result=createEditModelAndView(creditCard);
				
			}else{
				
				if(creditCard.getId()==0){
					creditCard=creditCardService.save(creditCard);
					//No se si debe ir a servicio
					Sponsor sponsor=sponsorService.findByPrincipal();
					sponsor.getCreditCards().add(creditCard);
					//fin del todo
					sponsorService.save(sponsor);
				}else{
					creditCardService.save(creditCard);
				}
				result=new ModelAndView("redirect:../../sponsor/view.do");
				
			}

			return result;
		}
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@Valid CreditCard creditCard,BindingResult binding) {
			ModelAndView result;
			
			Assert.notNull(creditCard);
			System.out.println(binding);
			try{
				creditCardService.delete(creditCard);
				result=new ModelAndView("redirect:../../sponsor/view.do");
			}catch (Throwable oops) {
				result=createEditModelAndView(creditCard,"creditCard.commit.error");
			}

			return result;
		}
		
		// Create ----------------------------------------------------------------	

		@RequestMapping(value="/create",method=RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			
			CreditCard creditCard=creditCardService.create();
			Assert.notNull(creditCard);
			
			result=createEditModelAndView(creditCard);

			return result;
		}
		
		// Ancillary Methods --------------------------------------------------
		
		protected ModelAndView createEditModelAndView(CreditCard creditCard){
			ModelAndView result;
			result=createEditModelAndView(creditCard,null);
			return result;
		}

		protected ModelAndView createEditModelAndView(CreditCard creditCard,String message) {
			ModelAndView result;
			result=new ModelAndView("creditCard/sponsor/edit");
			result.addObject("creditCard",creditCard);
			result.addObject("message",message);
			return result;
		}

}
