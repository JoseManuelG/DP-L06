
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.FolderService;
import services.MessageService;
import utilities.AbstractTest;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class MessageServiceTest extends AbstractTest {
	
	
	//System under test---------------------------
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private FolderService folderService;
	//Tests---------------------------------------


	@Test
	public void testSave() {
		Folder folder=folderService.findOne(124);
		super.authenticate("admin");
		Message message= messageService.create();
		message.setFolder(folder);
		message.setSender("System");
		message.setRecipient(message.getFolder().getActor().getEmail());
		message.setSubject("TestMessage");
		message.setBody("AutoGenMessage");
		message.setPriority("HIGH");
		Message savedMessage=messageService.save(message);
		Assert.notNull(savedMessage);
		super.authenticate(null);
	}
	@Test
	public void testSaveObjectNull() {
		super.authenticate("admin");
		Message message= null;
		try{
			messageService.save(message);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: El message ha de ser NO null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testSaveBadUserAccount() {
		super.authenticate("user1");
		Folder folder=folderService.findOne(124);
		Message message= messageService.create();
		message.setFolder(folder);
		try{
			messageService.save(message);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("SAVE: El message pertenece ha otra userAccount"));
		}
		super.authenticate(null);
	}
	@Test
	public void testSaveNullFolder() {
		super.authenticate("user1");
		Folder folder=null;
		Message message= messageService.create();
		message.setFolder(folder);
		try{
			messageService.save(message);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("El message ha de tener un folder NO null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDelete() {
		Folder folder=folderService.findOne(124);
		super.authenticate("admin");
		Message message= messageService.create();
		message.setFolder(folder);
		message.setSender("System");
		message.setRecipient(message.getFolder().getActor().getEmail());
		message.setSubject("TestMessage");
		message.setBody("AutoGenMessage");
		message.setPriority("HIGH");
		Message savedMessage=messageService.save(message);
		Assert.notNull(savedMessage);
		messageService.delete(savedMessage);
		if(!messageService.findOne(savedMessage.getId()).equals(0)){
			Assert.isTrue(messageService.findOne(savedMessage.getId()).getFolder().getName().equals("trashbox"));
			messageService.delete(savedMessage);
		}
		Assert.isNull(messageService.findOne(savedMessage.getId()));
		super.authenticate(null);
	}
	@Test
	public void testDeleteObjectNull() {
		super.authenticate("admin");
		Message message= null;
		try{
			messageService.delete(message);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: El message ha de ser NO null"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteBadId() {
		Folder folder=folderService.findOne(128);
		super.authenticate("admin");
		Message message= messageService.create();
		message.setFolder(folder);
		message.setSender("System");
		message.setRecipient(message.getFolder().getActor().getEmail());
		message.setSubject("TestMessage");
		message.setBody("AutoGenMessage");
		message.setPriority("HIGH");
		try{
			messageService.delete(message);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("El message ha de haber sido guardado"));
		}
		super.authenticate(null);
	}
	@Test
	public void testDeleteBadUserAcount() {
		Folder folder=folderService.findOne(128);
		super.authenticate("admin");
		Message message= messageService.create();
		message.setFolder(folder);
		message.setSender("System");
		message.setRecipient(message.getFolder().getActor().getEmail());
		message.setSubject("TestMessage");
		message.setBody("AutoGenMessage");
		message.setPriority("HIGH");
		Message savedMessage=messageService.save(message);
		Assert.notNull(savedMessage);
		super.authenticate(null);
		super.authenticate("user1");
		try{
			messageService.delete(savedMessage);
		}catch (IllegalArgumentException e) {
			Assert.isTrue(e.getMessage().equals("DELETE: El message pertenece ha otra userAccount"));
		}
		super.authenticate(null);
	}
}

