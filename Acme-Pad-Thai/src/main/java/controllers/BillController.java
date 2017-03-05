package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BillService;
import services.SponsorService;
import domain.Bill;
import domain.Sponsor;

@Controller
@RequestMapping("bill")
public class BillController extends AbstractController {
	// Services ---------------------------------------------------------------
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private SponsorService sponsorService;
	
	// Constructors -----------------------------------------------------------

	public BillController() {
		super();
	}

	// List -------------------------------------------------------------------	

	@RequestMapping(value="/sponsor/list",method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Bill> bills=billService.findAll();
		Sponsor sponsor=sponsorService.findByPrincipal();
		Collection<Bill> resultBills=new ArrayList<Bill>();
//TODO Revisar porque no meter este for en una query		
		for(Bill bill:bills){
			if(bill.getSponsor().equals(sponsor)){
				resultBills.add(bill);
			}
		}
//Fin del todo 		
		result = new ModelAndView("bill/sponsor/list");
		result.addObject("bills",resultBills);
		result.addObject("requestURI","bill/sponsor/list.do");

		return result;
	}
	
	@RequestMapping(value="/admin/listAll",method=RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		
		Collection<Bill> bills=billService.findAll();
		
		result = new ModelAndView("bill/admin/listAll");
		result.addObject("bills",bills);
		result.addObject("requestURI","bill/admin/listAll.do");

		return result;
	}
	// View -------------------------------------------------------------------	

	@RequestMapping(value="/sponsor/view",method=RequestMethod.GET,params="billId")
	public ModelAndView view(int billId) {
		ModelAndView result;
		
		Bill bill=billService.findOne(billId);
		Assert.notNull(bill);
		
		boolean unPaid= false;
		if(bill.getDateOfPay()==null){
			unPaid=true;
		}
		result = new ModelAndView("bill/sponsor/view");
		result.addObject("bill",bill);
		result.addObject("unPaid",unPaid);
		
		return result;
	}
	
	// Edit -------------------------------------------------------------------	

	@RequestMapping(value="/sponsor/edit",method=RequestMethod.GET,params="billId")
	public ModelAndView edit(int billId) {
		ModelAndView result;
	
		Bill bill=billService.findOne(billId);
//TODO Pasar este bloque a un servicio		
		if(bill.getDateOfPay()==(null)){
			Date currentTime=new Date(System.currentTimeMillis()-10000);
			bill.setDateOfPay(currentTime);
			bill=billService.save(bill);
		}
//Fin del todo 	
		result = new ModelAndView("bill/sponsor/view");
		result.addObject("bill",bill);
		
		return result;
	}
	
	// Create ------------------------------------------------------------------
	
	@RequestMapping(value="/admin/createNews",method=RequestMethod.GET)
	public ModelAndView createNews() {
		ModelAndView result;
	
		billService.createMonthlyBills();
		result = new ModelAndView("redirect:/bill/admin/listAll.do");
	
		
		return result;
	}
	
	@RequestMapping(value="/admin/unPaidMessage",method=RequestMethod.GET)
	public ModelAndView unPaidMessage() {
		ModelAndView result;
	
		sponsorService.sendMessageToSponsorsWithUnpaidBills();
		result = new ModelAndView("redirect:/bill/admin/listAll.do");
	
		
		return result;
	}
	
}