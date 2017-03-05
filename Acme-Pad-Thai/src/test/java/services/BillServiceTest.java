package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.BillService;
import services.CampaignService;
import services.SponsorService;
import utilities.AbstractTest;
import domain.Bill;
import domain.Campaign;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class BillServiceTest extends AbstractTest {
	
	
	//System under test---------------------------
	@Autowired
	private BillService billService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private CampaignService campaignService;
	//Tests---------------------------------------
	@Test
	public void testSave(){
		super.authenticate("sponsor1");
		Campaign campaign=campaignService.findOne(17);
		Sponsor sponsor=sponsorService.findOne(16);
		Bill bill=billService.create();
		bill.setCampaign(campaign);
		bill.setSponsor(sponsor);
		Date dateOfCreation=new Date(System.currentTimeMillis()-100);
		bill.setDateOfCreation(dateOfCreation);
		bill.setDescription("billTest");
		Bill savedBill=billService.save(bill);
		Assert.notNull(savedBill);
		Assert.notNull(billService.findOne(savedBill.getId()));
		super.authenticate(null);
	}
	@Test
	public void testSaveObjectNull(){
		super.authenticate("sponsor1");
		Bill bill=null;
		try{
			billService.save(bill);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: La bill no puede ser null"));
		}
	}
	@Test
	public void testSaveCampaignNull(){
		super.authenticate("sponsor1");
		Sponsor sponsor=sponsorService.findOne(16);
		Campaign campaign=null;
		Bill bill=billService.create();
		bill.setCampaign(campaign);
		bill.setSponsor(sponsor);
		try{
			billService.save(bill);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("La campaign de bill no puede ser null"));
		}
	}
	@Test
	public void testSaveSponsorNull(){
		super.authenticate("sponsor1");
		Sponsor sponsor=null;
		Campaign campaign=campaignService.findOne(17);
		Bill bill=billService.create();
		bill.setCampaign(campaign);
		bill.setSponsor(sponsor);
		try{
			billService.save(bill);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("El sponsor de bill no puede ser null"));
		}
	}
	@Test
	public void testSaveBadUserAccount(){
		super.authenticate("user1");
		Sponsor sponsor=sponsorService.findOne(16);
		Campaign campaign=campaignService.findOne(17);
		Bill bill=billService.create();
		bill.setCampaign(campaign);
		bill.setSponsor(sponsor);
		try{
			billService.save(bill);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: UserAccount no valido"));
		}
	}
	@Test
	public void testDelete(){
		super.authenticate("sponsor1");
		Bill bill=billService.findOne(36);
		billService.delete(bill);
		Assert.isNull(billService.findOne(bill.getId()));
	}
	@Test
	public void testDeleteObjectNull(){
		super.authenticate("sponsor1");
		Bill bill=null;
		try{
			billService.delete(bill);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: La bill no puede ser null"));
		}
	}
	@Test
	public void testDeleteNotSave(){
		super.authenticate("sponsor1");
		Bill bill=new Bill();
		try{
			billService.delete(bill);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("La bill ha de estar guardada"));
		}
	}
	@Test
	public void testDeleteBadUserAccount(){
		super.authenticate("user1");
		Bill bill=billService.findOne(36);
		try{
			billService.delete(bill);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: UserAccount no valido"));
		}
	}
}