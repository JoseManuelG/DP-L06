package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

@Service
@Transactional
public class CustomerService {
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
	public Customer findActorByPrincial(){
		UserAccount userAcc=null;
		try{
		userAcc= loginService.getPrincipal();
		}catch (Throwable t){
			
		}
		Customer result=null;
		if(userAcc!=null){
		Authority[] authorities =  new Authority[userAcc.getAuthorities().size()];
				authorities=userAcc.getAuthorities().toArray(authorities);
				
		if(authorities[0].getAuthority().equals(Authority.USER)){
			result = userService.findByPrincipal();
		}else if(authorities[0].getAuthority().equals(Authority.NUTRITIONIST)){
			result = nutritionistService.findByPrincipal();
		}
		}
		return result;
	}
}
