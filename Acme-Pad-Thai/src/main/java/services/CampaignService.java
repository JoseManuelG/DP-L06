package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CampaignRepository;
import security.LoginService;
import security.UserAccount;
import domain.Banner;
import domain.Campaign;
import domain.Sponsor;

@Service
@Transactional
public class CampaignService {
	//Manage Repository-------------------------------
	
	@Autowired
	private CampaignRepository campaignRepository;
		
	//Supporting Services-----------------------------
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SponsorService sponsorService;
	
	//Constructors------------------------------------
	
	//Simple CRUD methods-----------------------------
	public Campaign create(){
		Campaign result=new Campaign();
		Sponsor sponsor=sponsorService.findByPrincipal();
		result.setSponsor(sponsor);
		Collection<Banner> BannerList=new ArrayList<Banner>();
		result.setBannerList(BannerList);
		return result;
	}

	@SuppressWarnings("static-access")
	public Campaign save(Campaign campaign){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(campaign,"SAVE: La campaign no puede ser null");
		Assert.notNull(campaign.getSponsor(),"El sponsor no puede ser null");
		Assert.isTrue(principal.equals(campaign.getSponsor().getUserAccount()),"SAVE: UserAccount no valido");
		Campaign result=campaignRepository.save(campaign);
		return result;
	}
	
	public Collection<Campaign> findAll(){
		Collection<Campaign> result=campaignRepository.findAll();
		return result;
	}
	
	public Campaign findOne(Integer campaignId){
		Campaign result=campaignRepository.findOne(campaignId);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Campaign campaign){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(campaign,"DELETE: La campaign no puede ser null");
		Assert.isTrue(campaign.getId()!=0,"La campaign ha de estar guardada");
		Assert.isTrue(principal.equals(campaign.getSponsor().getUserAccount()),"DELETE: UserAccount no valido");
		Date DateOfStart=campaign.getDateOfStart();
		Date currentTime=new Date(System.currentTimeMillis());
		Assert.isTrue(DateOfStart.after(currentTime),"NO puede ser borrada una campaign que ya ha comenzado");
		campaignRepository.delete(campaign);
	}
	
	//Other business methods--------------------------
	public void bannerDisplayed(Banner banner){
		Campaign campaign=findOne(banner.getCampaign().getId());
		Integer numOfDisplays=campaign.getnumOfDisplays();
		campaign.setnumOfDisplays(numOfDisplays+1);
		campaignRepository.save(campaign);
	}
	
	public Collection<Campaign> activeCampaigns(){
		Collection<Campaign> result;
		result=campaignRepository.activeCampaigns();
		return result;
	}
	public Collection<Campaign> sponsorCampaigns(int sponsorId){
		Collection<Campaign> result=new ArrayList<Campaign>();
		result=campaignRepository.sponsorCampaigns(sponsorId);
		return result;
	}
}
