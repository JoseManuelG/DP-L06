
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
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
public class SponsorServiceTest extends AbstractTest {
	
	
	//System under test---------------------------
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private CreditCardService creditCardService;
	//Tests---------------------------------------
	

	

	@Test
	public void testSave() {
		super.authenticate("user1");
		Sponsor sponsor,savedSponsor;
		
		CreditCard creditCard=creditCardService.create();
		creditCard.setHolderName("TestHM");
		creditCard.setBrandName("TEST");
		creditCard.setExpirationMonth(12);
		creditCard.setNumber("378734493671000");
		creditCard.setExpirationYear(2040);
		creditCard.setCvvCode(200);
		
		sponsor = sponsorService.create();
		Collection<CreditCard> creditCards=new ArrayList<CreditCard>();
		creditCards.add(creditCard);
		sponsor.setCreditCards(creditCards);
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("SPONSOR");
		authorities.add(authority);
		sponsor.setName("nameTest");
		sponsor.setSurname("suernameTest");
		sponsor.setEmail("email@Test.test");
		sponsor.setCompanyName("companyNameTest");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("SponsorTest");
		userAcc.setUsername("SponsorTest");
		userAcc.setAuthorities(authorities);
		sponsor.setUserAccount(userAcc);
		
		savedSponsor= sponsorService.save(sponsor);
		Collection<Sponsor> sponsors = sponsorService.findAll();
		Assert.isTrue(sponsors.contains(savedSponsor));
		super.authenticate(null);
	}
	@Test
	public void testSaveObjectNull(){
		Sponsor sponsor=null;
		try{
			sponsorService.save(sponsor);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: El sponsor no puede ser null"));
		}
	}
	@Test
	public void testDelete() {
		super.authenticate("user1");
		Sponsor sponsor,savedSponsor;
		
		CreditCard creditCard=creditCardService.create();
		creditCard.setHolderName("TestHM");
		creditCard.setBrandName("TEST");
		creditCard.setExpirationMonth(12);
		creditCard.setNumber("378734493671000");
		creditCard.setExpirationYear(2040);
		creditCard.setCvvCode(200);
		
		sponsor = sponsorService.create();
		Collection<CreditCard> creditCards=new ArrayList<CreditCard>();
		creditCards.add(creditCard);
		sponsor.setCreditCards(creditCards);
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("SPONSOR");
		authorities.add(authority);
		sponsor.setName("nameTest");
		sponsor.setSurname("suernameTest");
		sponsor.setEmail("email@Test.test");
		sponsor.setCompanyName("companyNameTest");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("SponsorTest");
		userAcc.setUsername("SponsorTest");
		userAcc.setAuthorities(authorities);
		sponsor.setUserAccount(userAcc);
		
		savedSponsor= sponsorService.save(sponsor);
		Collection<Sponsor> sponsors = sponsorService.findAll();
		Assert.isTrue(sponsors.contains(savedSponsor));
		super.authenticate(null);
		super.authenticate("SponsorTest");
		sponsorService.delete(savedSponsor);
		super.authenticate(null);
	}
	@Test
	public void testDeleteObjectNull(){
		super.authenticate("sponsor1");
		Sponsor sponsor=null;
		try{
			sponsorService.delete(sponsor);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: El sponsor no puede ser null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteNotSaved(){
		super.authenticate("sponsor1");
		Sponsor sponsor=sponsorService.create();
		Collection<CreditCard> creditCards=new ArrayList<CreditCard>();
		sponsor.setCreditCards(creditCards);
		try{
			sponsorService.delete(sponsor);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("El sponsor ha de haber sido guardado"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteBadUserAccount(){
		super.authenticate("user1");
		Sponsor sponsor=sponsorService.findOne(16);
		try{
			sponsorService.delete(sponsor);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("UserAccount no valido"));
		}
		super.authenticate(null);
	}
}

