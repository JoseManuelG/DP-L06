package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {
	//Manage Repository-------------------------------
	
	@Autowired
	private MessageRepository messageRepository;
		
	//Supporting Services-----------------------------
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private LoginService loginService;
	//Constructors------------------------------------
	
	//Simple CRUD methods-----------------------------
	public Message create(){
		Message result=new Message();
		Date sendingMoment=new Date(System.currentTimeMillis()-100);
		result.setSendingMoment(sendingMoment);
		return result;
	}

	@SuppressWarnings("static-access")
	public Message save(Message message){
		loginService.getPrincipal();
		Assert.notNull(message,"SAVE: El message ha de ser NO null");
		Assert.notNull(message.getFolder(),"El message ha de tener un folder NO null");
		Message result=messageRepository.save(message);
		return result;
	}
	
	public Collection<Message> findAll(){
		Collection<Message> result=messageRepository.findAll();
		return result;
	}
	
	public Message findOne(Integer messageId){
		Message result=messageRepository.findOne(messageId);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Message message){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(message,"DELETE: El message ha de ser NO null");
		Assert.isTrue(principal.equals(message.getFolder().getActor().getUserAccount())
				,"DELETE: El message pertenece ha otra userAccount");
		Assert.isTrue(message.getId()!=0,"El message ha de haber sido guardado");
		if(message.getFolder().getName().equals("trashbox")){
			messageRepository.delete(message);
		}else {
//			Folder folder=folderService.folderFromActor(message.getSender(),"trashbox");
			
			Actor actor=message.getFolder().getActor();
			Folder folder=folderService.findFolderOfActor(actor,"trashbox");
			Folder inbox=message.getFolder();
			inbox.getMessages().remove(message);
			folderService.save(folder);
			message.setFolder(folder);
			save(message);
			
		}
	}
	
	//Other business methods--------------------------
	
	public void sendMessage(Message message, Actor sender, Actor recipient){
		Message message1=message;
		Folder folder=folderService.findFolderOfActor(sender,"outbox");
		message1.setFolder(folder);
		save(message1);
		Message message2=message;
		Boolean spam=false;
		Folder folder2;
		
		
		String body = message.getBody();
		String[] palabrasSeparadas = body.split(" ");
		
		String subject = message.getSubject();
		String[] palabrasSeparadas2 = subject.split(" ");
		
		for(String keyWord:configurationService.findOne().getKeyWords()){
			for(String string:palabrasSeparadas){
				if(string.equalsIgnoreCase(keyWord)||message.getBody().contains(keyWord)){
					spam=true;
					break;
				}
			}
			for(String string2:palabrasSeparadas2){
				if(string2.equalsIgnoreCase(keyWord)||message.getSubject().contains(keyWord)){
					spam=true;
					break;
				}
			}
		}
		if(spam){
			folder2=folderService.findFolderOfActor(recipient,"spambox");
			message2.setFolder(folder2);
		}else{
			folder2=folderService.findFolderOfActor(recipient,"inbox");
			message2.setFolder(folder2);
		}
		
		save(message2);
	}
	public void sendMessage(Message message, Actor recipient){
		Folder folder=folderService.findFolderOfActor(recipient,"inbox");
		message.setFolder(folder);
		save(message);
	}
}
