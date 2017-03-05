package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Sponsor;

@Service
@Transactional
public class CreditCardService {
	//Manage Repository-------------------------------
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	
	//Supporting Services-----------------------------
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private LoginService loginService;
	
	//Constructors------------------------------------
	
	//Simple CRUD methods-----------------------------
	public CreditCard create(){
		CreditCard result=new CreditCard();
		return result;
	}

	@SuppressWarnings("static-access")
	public CreditCard save(CreditCard creditCard){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(creditCard,"La CreditCard no puede ser null");
		Assert.isTrue(creditCard.getId()==0||principal.equals(
				sponsorService.sponsorWithCreditCard(creditCard).getUserAccount()),
				"SAVE: UserAccount no valido");
		CreditCard result=creditCardRepository.save(creditCard);
		return result;
	}
	
	public Collection<CreditCard> findAll(){
		Collection<CreditCard> result=creditCardRepository.findAll();
		return result;
	}
	
	public CreditCard findOne(Integer creditCardId){
		CreditCard result=creditCardRepository.findOne(creditCardId);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(CreditCard creditCard){
		UserAccount principal=loginService.getPrincipal();
		Sponsor sponsor=sponsorService.sponsorWithCreditCard(creditCard);
		Assert.isTrue(principal.equals(sponsor.getUserAccount()),
		 				"DELETE: UserAccount no valido");
		sponsor.getCreditCards().remove(creditCard);
		sponsorService.save(sponsor);
		creditCardRepository.delete(creditCard);
	}
	
	//Other business methods--------------------------
	
}
