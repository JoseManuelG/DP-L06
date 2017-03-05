package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {
	//Manage Repository-------------------------------
	
	@Autowired
	private FolderRepository folderRepository;
	
	
	//Supporting Services-----------------------------
	
	@Autowired
	private LoginService loginService;
	//Constructors------------------------------------
	
	//Simple CRUD methods-----------------------------
	public Folder create(){
		Folder result=new Folder();
		Collection<Message> messages=new ArrayList<Message>();
		result.setMessages(messages);
		return result;
	}

	@SuppressWarnings("static-access")
	public Folder save(Folder folder){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(folder,"SAVE: El folder ha de ser NO nulo");
		Assert.notNull(folder.getActor(),"El actor no puede ser nulo");
		Assert.isTrue(principal.equals(folder.getActor().getUserAccount()),"SAVE: UserAccount no valido");
		Folder result=folderRepository.save(folder);
		return result;
	}
	
	public Collection<Folder> findAll(){
		Collection<Folder> result=folderRepository.findAll();
		return result;
	}
	
	public Folder findOne(Integer folderId){
		Folder result=folderRepository.findOne(folderId);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Folder folder){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(folder,"DELETE: El folder ha de ser NO nulo");
		Assert.isTrue(folder.getId()!=0,"El folder ha de haber sido guardado");
		Assert.isTrue(principal.equals(folder.getActor().getUserAccount()),"DELETE: UserAccount no valido");
		Assert.isTrue(!folder.getisBasic(),"Los folder basicos NO pueden ser borrados");
		folderRepository.delete(folder);
	}
	
	//Other business methods--------------------------
	public void createBasicsFolders(Actor actor){
		Collection<Folder> folders =new ArrayList<Folder>();
		Folder folder;
		for(int i=0;i<4;i++){
			folder= create();
			folder.setActor(actor);
			folder.setisBasic(true);
			if(i==0){
				folder.setName("inbox");
			}else if(i==1){
				folder.setName("outbox");
			}else if(i==2){
				folder.setName("trashbox");
			}else if(i==3){
				folder.setName("spambox");
			}
			folders.add(folder);
		}
		actor.setFolders(folders);
		
	}
	//Busca un Folder con el nombre "name" del actor "actor"
//	public Folder findFolderFromActor(Actor actor, String name) {
//		// TODO Auto-generated method stub
//		Assert.notNull(actor);
//		Assert.hasText(name);
//		Collection<Folder> folders =findFoldersOfActor(actor);
//		Folder result=null;
//		for(Folder folder:folders){
//			if(folder.getName()==name){
//				result=folder;
//				break;
//			}
//		}
//		Assert.notNull(result);
//		return result;
//	}
	//busca todos los Folder del actor "actor"
	public Collection<Folder> findFoldersOfActor(Actor actor) {
		// TODO Auto-generated method stub
		Collection<Folder> result = folderRepository.findFoldersOfActor(actor.getId());
		return result;
	}
	
	public Folder findFolderOfActor(Actor actor, String folderName) {
		Collection<Folder> folders = folderRepository.findFoldersOfActor(actor.getId());
		Folder result=null;
		for(Folder folder:folders){
			if(folder.getName().equals(folderName)){
				result=folder;
				break;
			}
		}
		return result;
	}
}
