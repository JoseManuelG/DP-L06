package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;

@Service
@Transactional
public class ActorService {
	
	@Autowired
	ActorRepository actorRepository;
	
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
			result=(Actor)sponsorService.findOne(id);
		}catch (Throwable oops) {}
		if(result==null){
			try{
				result=(Actor)userService.findOne(id);
			}catch (Throwable oops) {}
		}
			if(result==null) {
			try{
				result=(Actor)cookService.findOne(id);
			}catch (Throwable oops) {}
		}
			if(result==null){
			try{
				result=(Actor)administratorService.findOne(id);
			}catch (Throwable oops) {}
		}
			if(result==null){
			try{
				result=(Actor)nutritionistService.findOne(id);
			}catch (Throwable oops) {}
		}
		return (Actor) result;
	}
	
	public Collection<Folder> findActorFoldersById(int actorId){
		Collection<Folder> result;
		result=actorRepository.findActorFoldersById(actorId);
		return result;
	}
}
