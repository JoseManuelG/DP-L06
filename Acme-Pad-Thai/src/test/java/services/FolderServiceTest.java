
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AdministratorService;
import services.FolderService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class FolderServiceTest extends AbstractTest {
	
	
	//System under test---------------------------
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private AdministratorService administratorService;
	//Tests---------------------------------------
	

	

	@Test
	public void testSave() {
		Administrator administrator=administratorService.findOne(55);
		super.authenticate("admin");
		Folder folder= folderService.create();
		folder.setActor(administrator);
		folder.setName("autogenFolder");
		folder.setisBasic(false);
		Folder savedFolder=folderService.save(folder);
		Assert.notNull(savedFolder);
		super.authenticate(null);
	}
	@Test
	public void testSaveObjectNull() {
		super.authenticate("admin");
		Folder folder= null;
		try{
			folderService.save(folder);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: El folder ha de ser NO nulo"));
		}
		super.authenticate(null);
	}
	@Test
	public void testSaveActorNull() {
		super.authenticate("admin");
		Administrator administrator=null;
		Folder folder= folderService.create();
		folder.setActor(administrator);
		try{
			folderService.save(folder);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("El actor no puede ser nulo"));
		}
		super.authenticate(null);
	}
	@Test
	public void testSaveBadUserAccount() {
		super.authenticate("user1");
		Administrator administrator=administratorService.findOne(55);
		Folder folder= folderService.create();
		folder.setActor(administrator);
		folder.setName("autogenFolder");
		folder.setisBasic(false);
		try{
			folderService.save(folder);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: UserAccount no valido"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDelete() {
		Administrator administrator=administratorService.findOne(55);
		super.authenticate("admin");
		Folder folder= folderService.create();
		folder.setActor(administrator);
		folder.setName("autogenFolder");
		folder.setisBasic(false);
		Folder savedFolder=folderService.save(folder);
		Assert.notNull(savedFolder);
		folderService.delete(savedFolder);
		Assert.isNull(folderService.findOne(savedFolder.getId()));
		super.authenticate(null);
	}
	@Test
	public void testDeleteObjectNull() {
		super.authenticate("admin");
		Folder folder= null;
		try{
			folderService.delete(folder);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: El folder ha de ser NO nulo"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteBasicFolder() {
		super.authenticate("admin");
		Administrator administrator=administratorService.findOne(55);
		Folder folder= folderService.create();
		folder.setActor(administrator);
		folder.setName("autogenFolder");
		folder.setisBasic(true);
		Folder savedFolder=folderService.save(folder);
		Assert.notNull(savedFolder);
		try{
			folderService.delete(savedFolder);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("Los folder basicos NO pueden ser borrados"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteBadId() {
		super.authenticate("admin");
		Administrator administrator=administratorService.findOne(55);
		Folder folder= folderService.create();
		folder.setActor(administrator);
		folder.setName("autogenFolder");
		folder.setisBasic(false);
		try{
			folderService.delete(folder);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("El folder ha de haber sido guardado"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteBadUserAccount() {
		super.authenticate("admin");
		Administrator administrator=administratorService.findOne(55);
		Folder folder= folderService.create();
		folder.setActor(administrator);
		folder.setName("autogenFolder");
		folder.setisBasic(false);
		Folder savedFolder=folderService.save(folder);
		Assert.notNull(savedFolder);
		super.authenticate(null);
		super.authenticate("user1");
		try{
			folderService.delete(savedFolder);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: UserAccount no valido"));
		}
		super.authenticate(null);
	}
}

