package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CreditCardService;
import services.SponsorService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})

@Transactional
public class CreditCardServiceTest extends AbstractTest{
	

	//System under test---------------------------
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private SponsorService sponsorService;
	//Tests---------------------------------------
	
	
	@Test
	public void TestSave(){
		super.authenticate("sponsor1");
		CreditCard creditCard=creditCardService.create();
		creditCard.setHolderName("TestHM");
		creditCard.setBrandName("TEST");
		creditCard.setExpirationMonth(12);
		creditCard.setNumber("378734493671000");
		creditCard.setExpirationYear(2040);
		creditCard.setCvvCode(200);
		CreditCard savedCreditCard=creditCardService.save(creditCard);
		Assert.notNull(savedCreditCard);
		super.authenticate(null);
	}
	@Test
	public void TestSaveObjectNull(){
		super.authenticate("sponsor1");
		CreditCard creditCard=null;
		try{
			creditCardService.save(creditCard);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("La CreditCard no puede ser null"));
		}
		super.authenticate(null);
	}
	@Test
	public void TestSaveBadUserAccount(){
		super.authenticate("sponsor1");
		CreditCard creditCard=creditCardService.create();
		creditCard.setHolderName("TestHM");
		creditCard.setBrandName("TEST");
		creditCard.setExpirationMonth(12);
		creditCard.setNumber("378734493671000");
		creditCard.setExpirationYear(2040);
		creditCard.setCvvCode(200);
		CreditCard savedCreditCard=creditCardService.save(creditCard);
		Assert.notNull(savedCreditCard);
		Sponsor sponsor=sponsorService.findOne(26);
		sponsor.getCreditCards().add(savedCreditCard);
		sponsorService.save(sponsor);
		super.authenticate(null);
		super.authenticate("user1");
		try{
			creditCardService.save(savedCreditCard);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: UserAccount no valido"));
		}
		super.authenticate(null);
	}
	@Test
	public void TestDelete(){
		super.authenticate("sponsor1");
		CreditCard creditCard=creditCardService.create();
		creditCard.setHolderName("TestHM");
		creditCard.setBrandName("TEST");
		creditCard.setExpirationMonth(12);
		creditCard.setNumber("378734493671000");
		creditCard.setExpirationYear(2040);
		creditCard.setCvvCode(200);
		CreditCard savedCreditCard=creditCardService.save(creditCard);
		Assert.notNull(savedCreditCard);
		Sponsor sponsor=sponsorService.findOne(26);
		sponsor.getCreditCards().add(savedCreditCard);
		sponsorService.save(sponsor);
		creditCardService.delete(savedCreditCard);
		super.authenticate(null);
	}
	@Test
	public void TestDeleteBadUserAccount(){
		super.authenticate("sponsor1");
		CreditCard creditCard=creditCardService.create();
		creditCard.setHolderName("TestHM");
		creditCard.setBrandName("TEST");
		creditCard.setExpirationMonth(12);
		creditCard.setNumber("378734493671000");
		creditCard.setExpirationYear(2040);
		creditCard.setCvvCode(200);
		CreditCard savedCreditCard=creditCardService.save(creditCard);
		Assert.notNull(savedCreditCard);
		Sponsor sponsor=sponsorService.findOne(26);
		sponsor.getCreditCards().add(savedCreditCard);
		sponsorService.save(sponsor);
		super.authenticate(null);
		super.authenticate("user1");
		try{
			creditCardService.delete(savedCreditCard);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: UserAccount no valido"));
		}
		super.authenticate(null);
	}
}
