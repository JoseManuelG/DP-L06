package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Banner;

@Service
@Transactional
public class BannerService {
	
	//Manage Repository-------------------------------
	
	@Autowired
	private BannerRepository bannerRepository;
	

	//Supporting Services-----------------------------
	
	@Autowired
	private LoginService loginService;
	
	//Constructors------------------------------------
	
	//Simple CRUD methods-----------------------------
	public Banner create(){
		Banner result=new Banner();
		return result;
	}
	
	@SuppressWarnings("static-access")
	public Banner save(Banner banner){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(banner,"SAVE: El banner no puede ser null");
		Assert.notNull(banner.getCampaign(),"La campaign del banner no puede ser null");
		Assert.isTrue(principal.equals(banner.getCampaign().getSponsor().getUserAccount()),"SAVE: UserAccount no valido");
		Banner result=bannerRepository.save(banner);
		return result;
	}
	
	public Collection<Banner> findAll(Banner banner){
		Collection<Banner> result=bannerRepository.findAll();
		return result;
	}
	
	public Banner findOne(Integer bannerId){
		Banner result=bannerRepository.findOne(bannerId);
		return result;
	}
	
	public void delete(Banner banner){
		@SuppressWarnings("static-access")
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(banner,"DELETE: El banner no puede ser null");
		Assert.isTrue(banner.getId()!=0,"El banner ha de estar guardado");
		Assert.isTrue(principal.equals(banner.getCampaign().getSponsor().getUserAccount()),"DELETE: UserAccount no valido");
		bannerRepository.delete(banner);
	}

	
	//Other business methods--------------------------
	
	public Boolean isActive(Banner banner){
	    Boolean result=null;
	    Date start=banner.getCampaign().getDateOfStart();
	    Date end=banner.getCampaign().getDateOfStart();
	    Date current=new Date(System.currentTimeMillis()-1000);
	    result=start.before(current)&&end.after(current);
	    return result;
	  }
	public Banner randomBanner(){
		Banner result=null;
		List<Banner> banners=(List<Banner>) bannerRepository.activeBanners();
		if(!banners.isEmpty()){
			Random randomIndex=new Random();
			Integer index=randomIndex.nextInt(banners.size());
			result=banners.get(index);
		}
		return result;
	}
		
	public Banner randomStarBanner(){
		Banner result=null;
		List<Banner> banners=(List<Banner>) bannerRepository.activeStarBanners();
		if(!banners.isEmpty()){
			Random randomIndex=new Random();
			Integer index=randomIndex.nextInt(banners.size());
			result=banners.get(index);
		}
		return result;
	}

}
