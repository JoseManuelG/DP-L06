package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.BannerService;
import services.CampaignService;
import utilities.AbstractTest;
import domain.Banner;
import domain.Campaign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class BannerServiceTest extends AbstractTest {
	
	
	//System under test---------------------------
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private CampaignService campaignService;
	//Tests---------------------------------------
	
	@Test
	public void testSave(){
		super.authenticate("sponsor1");
		Campaign campaign=campaignService.findOne(17);
		Banner banner=bannerService.create();
		banner.setCampaign(campaign);
		banner.setPicture("https://www.google.es/url?sa=i&rct=j&q=&esrc=s&source=imgres&cd=&ved=0ahUKEwjFmZOTtarQAhVElxoKHVo6D7kQjBwIBA&url=http%3A%2F%2Fg02.a.alicdn.com%2Fkf%2FHTB1.0loKFXXXXciXpXXq6xXFXXXE%2Fbb977-Thai-font-b-Food-b-font-font-b-Banner-b-font-Shop-Sign-Wholesale-Dropshipping.jpg&psig=AFQjCNHzBB8Lq3GbKoue_JfR0O5UNntQqw&ust=1479287403300282");
		Banner savedBanner=bannerService.save(banner);
		Assert.notNull(savedBanner);
		Assert.notNull(bannerService.findOne(savedBanner.getId()));
		super.authenticate(null);
	}
	@Test
	public void testSaveObjectNull(){
		super.authenticate("sponsor1");
		Banner banner=null;
		try{
			bannerService.save(banner);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: El banner no puede ser null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testSaveCampaignNull(){
		super.authenticate("sponsor1");
		Campaign campaign=null;
		Banner banner=bannerService.create();
		banner.setCampaign(campaign);
		try{
			bannerService.save(banner);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("La campaign del banner no puede ser null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testSaveBadUserAccount(){
		super.authenticate("user1");
		Campaign campaign=campaignService.findOne(17);
		Banner banner=bannerService.create();
		banner.setCampaign(campaign);
		try{
			bannerService.save(banner);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: UserAccount no valido"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDelete(){
		super.authenticate("sponsor1");
		Campaign campaign=campaignService.findOne(17);
		Banner banner=bannerService.create();
		banner.setCampaign(campaign);
		banner.setPicture("https://www.google.es/url?sa=i&rct=j&q=&esrc=s&source=imgres&cd=&ved=0ahUKEwjFmZOTtarQAhVElxoKHVo6D7kQjBwIBA&url=http%3A%2F%2Fg02.a.alicdn.com%2Fkf%2FHTB1.0loKFXXXXciXpXXq6xXFXXXE%2Fbb977-Thai-font-b-Food-b-font-font-b-Banner-b-font-Shop-Sign-Wholesale-Dropshipping.jpg&psig=AFQjCNHzBB8Lq3GbKoue_JfR0O5UNntQqw&ust=1479287403300282");
		Banner savedBanner=bannerService.save(banner);
		bannerService.delete(savedBanner);
		Assert.isNull(bannerService.findOne(savedBanner.getId()));
		super.authenticate(null);
	}
	@Test
	public void testDeleteObjectNull(){
		super.authenticate("sponsor1");
		Banner banner=null;
		try{
			bannerService.delete(banner);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: El banner no puede ser null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteNotSaved(){
		super.authenticate("sponsor1");
		Banner banner=new Banner();
		try{
			bannerService.delete(banner);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("El banner ha de estar guardado"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteBadUserAccount(){
		super.authenticate("user1");
		Banner banner=bannerService.findOne(18);
		try{
			bannerService.delete(banner);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: UserAccount no valido"));
		}
		super.authenticate(null);
	}
}