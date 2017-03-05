package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BillRepository;
import security.LoginService;
import security.UserAccount;
import domain.Banner;
import domain.Bill;
import domain.Campaign;

@Service
@Transactional
public class BillService {
	//Manage Repository-------------------------------
	
	@Autowired
	private BillRepository billRepository;
	

	//Supporting Services-----------------------------
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private CampaignService campaignService;
	
	//Constructors------------------------------------
		
	//Simple CRUD methods-----------------------------
	public Bill create(){
		Bill result=new Bill();
		Date dateOfCreation=new Date(System.currentTimeMillis() -100);
		result.setDateOfCreation(dateOfCreation);
		return result;
	}
	
	public Bill save(Bill bill){
		//UserAccount principal=loginService.getPrincipal();
		Assert.notNull(bill,"SAVE: La bill no puede ser null");
		Assert.notNull(bill.getCampaign(),"La campaign de bill no puede ser null");
		Assert.notNull(bill.getSponsor(),"El sponsor de bill no puede ser null");
		//Assert.isTrue(principal.equals(bill.getSponsor().getUserAccount()),"SAVE: UserAccount no valido");
		
		Bill result=billRepository.save(bill);
		return result;
	}
	
	public Collection<Bill> findAll(){
		Collection<Bill> result=billRepository.findAll();
		return result;
	}
	
	public Bill findOne(Integer billId){
		Bill result=billRepository.findOne(billId);
		return result;
	}
	
	@SuppressWarnings("static-access")
	public void delete(Bill bill){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(bill,"DELETE: La bill no puede ser null");
		Assert.isTrue(bill.getId()!=0,"La bill ha de estar guardada");
		Assert.isTrue(principal.equals(bill.getSponsor().getUserAccount()),"DELETE: UserAccount no valido");
		billRepository.delete(bill);
	}

	
	//Other business methods--------------------------
	public void createMonthlyBills(){
				Collection<Campaign> campaigns=campaignService.activeCampaigns();
				Double BannerCost=configurationService.findOne().getBannerCost();
				Double cost;
				Collection<Bill> newBills=new ArrayList<Bill>();
				Collection <Bill> bills=new ArrayList<Bill>();
				for (Campaign campaign:campaigns){
					if(hasThisMonthlyBill(campaign,bills)){
						Bill bill=create();
						bill.setCampaign(campaign);
						cost=campaign.getnumOfDisplays()*BannerCost;
						bill.setCost(cost);
						bill.setSponsor(campaign.getSponsor());
						bill.setDescription("se han mostrado "+campaign.getnumOfDisplays()+" veces los siguientes banners: ");
						for(Banner banner:campaign.getBannerList()){
							bill.setDescription(bill.getDescription().concat(banner.getPicture().concat(" ")));
				
						}
						newBills.add(bill);
					}	
				}
				for(Bill newBill:newBills){
					save(newBill);
				}
			}
	@SuppressWarnings(value = { "deprecation" })
	private boolean hasThisMonthlyBill(Campaign campaign,Collection<Bill> bills) {
				boolean result=true;
				bills.addAll(billRepository.billsOfCampaign(campaign));
				Date currentDate=new Date(System.currentTimeMillis());
				if(bills!=null){
					for(Bill bill:bills){
						if(bill.getDateOfCreation().getMonth()==currentDate.getMonth()
								&&bill.getDateOfCreation().getYear()==currentDate.getYear()){
							result=false;
						}
					}
				}
				return result;
			}
			
			
	public Collection<Bill> UnpaidBills(){
		Collection<Bill> result=billRepository.UnpaidBills();
		return result;
	}
}
