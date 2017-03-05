package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {
	@Autowired
	LoginService loginService;
	@Autowired
	UserService userService;
	@Autowired
	NutritionistService nutritionistService;
	@Autowired
	AdministratorService administratorService;
	@Autowired
	SponsorService sponsorService;
	@Autowired
	CookService cookService;

	
	
	@SuppressWarnings("static-access")
	public Actor findActorByPrincial(){
		UserAccount userAcc= loginService.getPrincipal();
		Actor result=null;
		
		Authority[] authorities =  new Authority[userAcc.getAuthorities().size()];
				authorities=userAcc.getAuthorities().toArray(authorities);
				
		if(authorities[0].getAuthority().equals(Authority.USER)){
			result = userService.findByPrincipal();
		}else if(authorities[0].getAuthority().equals(Authority.NUTRITIONIST)){
			result = nutritionistService.findByPrincipal();
		}else if(authorities[0].getAuthority().equals(Authority.ADMIN)){
			
			result= administratorService.findByPrincipal();
		}else if(authorities[0].getAuthority().equals(Authority.SPONSOR)){
			
			result= sponsorService.findByPrincipal();
		}else if(authorities[0].getAuthority().equals(Authority.COOK)){
			
			result = cookService.findByPrincipal();
		}
		return result;
	}
	public Actor findOne(int id){
		Actor result=null;
		
		try{
			result=sponsorService.findOne(id);
		}catch (Exception e) {}
		if(result==null){
			try{
				result=userService.findOne(id);
			}catch (Exception e) {}
		}
			if(result==null) {
			try{
				result=cookService.findOne(id);
			}catch (Exception e) {}
		}
			if(result==null){
			try{
				result=administratorService.findOne(id);
			}catch (Exception e) {}
		}
			if(result==null){
			try{
				result=nutritionistService.findOne(id);
			}catch (Exception e) {}
		}
		return result;
	}
}
