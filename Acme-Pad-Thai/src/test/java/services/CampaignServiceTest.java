package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Campaign;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@SuppressWarnings(value = { "all" })
@Transactional
public class CampaignServiceTest extends AbstractTest {
	
	
	//System under test---------------------------
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private SponsorService sponsorService;
	
	//Tests---------------------------------------
	
	@Test
	public void testSave(){
		Sponsor sponsor=sponsorService.findOne(16);
		super.authenticate("sponsor1");
		Campaign campaign=campaignService.create();
		campaign.setSponsor(sponsor);
		Date dateOfStart=new Date("2020/2/3");
		campaign.setDateOfStart(dateOfStart);
		Campaign savedCampaign=campaignService.save(campaign);
		Assert.notNull(savedCampaign);
		Assert.notNull(campaignService.findOne(savedCampaign.getId()));
		super.authenticate(null);
	}
	@Test
	public void testSaveOnjectNull(){
		super.authenticate("sponsor1");
		Campaign campaign=null;
		try{
			campaignService.save(campaign);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: La campaign no puede ser null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testSaveSponsorNull(){
		super.authenticate("sponsor1");
		Sponsor sponsor=null;
		Campaign campaign=campaignService.create();
		campaign.setSponsor(sponsor);
		try{
			campaignService.save(campaign);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("El sponsor no puede ser null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testSaveBadUserAccount(){
		super.authenticate("user1");
		Sponsor sponsor=sponsorService.findOne(16);
		Campaign campaign=campaignService.create();
		campaign.setSponsor(sponsor);
		try{
			campaignService.save(campaign);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: UserAccount no valido"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDelete(){
		Sponsor sponsor=sponsorService.findOne(16);
		super.authenticate("sponsor1");
		Campaign campaign=campaignService.create();
		campaign.setSponsor(sponsor);
		Date dateOfStart=new Date("2020/2/3");
		campaign.setDateOfStart(dateOfStart);
		Campaign savedCampaign=campaignService.save(campaign);
		campaignService.delete(savedCampaign);
		Assert.isNull(campaignService.findOne(savedCampaign.getId()));
		super.authenticate(null);
	}
	@Test
	public void testDeleteObjectNull(){
		super.authenticate("sponsor1");
		Campaign campaign=null;
		try{
		campaignService.delete(campaign);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: La campaign no puede ser null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteNotSave(){
		super.authenticate("sponsor1");
		Campaign campaign=new Campaign();
		try{
		campaignService.delete(campaign);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("La campaign ha de estar guardada"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteBadUserAccount(){
		super.authenticate("user1");
		Campaign campaign=campaignService.findOne(17);
		try{
		campaignService.delete(campaign);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: UserAccount no valido"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteStartedCampaign(){
		super.authenticate("sponsor1");
		Campaign campaign=campaignService.findOne(17);
		try{
		campaignService.delete(campaign);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("NO puede ser borrada una campaign que ya ha comenzado"));
		}
		super.authenticate(null);
	}
}