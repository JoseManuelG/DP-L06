package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Attend;
import domain.Folder;
import domain.SocialIdentity;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class SocialIdentityServiceTest extends AbstractTest {
	//Service under test-------------------------
			@Autowired
			private SocialIdentityService socialIdentityService;
			
			@Autowired
			private UserService userService;
			
			//Tests----------------------------------------
			@Rule
			  public ExpectedException exception = ExpectedException.none();
			
			//Tests de creates----------------------------------------
			@Test
			public void testCreateSocialIdentity(){
				SocialIdentity socialIdentity;
				socialIdentity = socialIdentityService.create();
				Assert.notNull(socialIdentity);
				Assert.isNull(socialIdentity.getActor());
				Assert.isNull(socialIdentity.getLink());
				Assert.isNull(socialIdentity.getNick());
				Assert.isNull(socialIdentity.getPicture());
				Assert.isNull(socialIdentity.getSocialNetwork());
				
			}
			
			//Tests de saves----------------------------------------
			@Test
			public void testSaveSocialIdentity(){
				
				User user = userService.create();
		        user.setName("Nombre del usuario");
		        user.setSurname("Apellidos del usuario");
		        user.setEmail("email@delusuario.com");
		        Collection<Folder> folders = new ArrayList<Folder>();
		        Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		        Collection<Attend> attends = new ArrayList<Attend>();
		        
		        user.setFolders(folders);
		        user.setSocialIdentities(socialIdentities);
		        user.setAttends(attends);
		        
		        Collection<Authority> authorities = new ArrayList<Authority>();
		        Authority authority = new Authority();
		        authority.setAuthority("USER");
		        authorities.add(authority);
		         
		        UserAccount userAcc = new UserAccount();
		        userAcc.setPassword("userTest");
		        userAcc.setUsername("userTest");
		        userAcc.setAuthorities(authorities);
		        user.setUserAccount(userAcc);
		        
		        
		        User savedUser = userService.save(user);
		        
		        super.authenticate("userTest");
		        
		        SocialIdentity socialIdentity= socialIdentityService.create();
				socialIdentity.setActor(savedUser);
				socialIdentity.setLink("http://www.linksocial.com");
				socialIdentity.setNick("socialNick");
				socialIdentity.setPicture("http://www.linkpicture.com");
				socialIdentity.setSocialNetwork("socialNetwork");
				
				SocialIdentity savedSocial =socialIdentityService.save(socialIdentity);
				Collection<SocialIdentity> socialIdentitiesRet=socialIdentityService.findAll();
				Assert.isTrue(socialIdentitiesRet.contains(savedSocial));
				
				
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveNullSocialIdentity(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("La socialIdentity a guardar no puede ser nula");

				
				User user = userService.create();
		        user.setName("Nombre del usuario");
		        user.setSurname("Apellidos del usuario");
		        user.setEmail("email@delusuario.com");
		        Collection<Folder> folders = new ArrayList<Folder>();
		        Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		        Collection<Attend> attends = new ArrayList<Attend>();
		        
		        user.setFolders(folders);
		        user.setSocialIdentities(socialIdentities);
		        user.setAttends(attends);
		        
		        Collection<Authority> authorities = new ArrayList<Authority>();
		        Authority authority = new Authority();
		        authority.setAuthority("USER");
		        authorities.add(authority);
		         
		        UserAccount userAcc = new UserAccount();
		        userAcc.setPassword("userTest");
		        userAcc.setUsername("userTest");
		        userAcc.setAuthorities(authorities);
		        user.setUserAccount(userAcc);
		        
		        
		        User savedUser = userService.save(user);
		        
		        super.authenticate("userTest");
		        
		        SocialIdentity socialIdentity= null;
				
				SocialIdentity savedSocial =socialIdentityService.save(socialIdentity);
				Collection<SocialIdentity> socialIdentitiesRet=socialIdentityService.findAll();
				Assert.isTrue(socialIdentitiesRet.contains(savedSocial));
				
				
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testSaveSocialIdentityAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("Un usuario solo debe registrar sus propios socialIdentities");
				User user = userService.create();
		        user.setName("Nombre del usuario");
		        user.setSurname("Apellidos del usuario");
		        user.setEmail("email@delusuario.com");
		        Collection<Folder> folders = new ArrayList<Folder>();
		        Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		        Collection<Attend> attends = new ArrayList<Attend>();
		        
		        user.setFolders(folders);
		        user.setSocialIdentities(socialIdentities);
		        user.setAttends(attends);
		        
		        Collection<Authority> authorities = new ArrayList<Authority>();
		        Authority authority = new Authority();
		        authority.setAuthority("USER");
		        authorities.add(authority);
		         
		        UserAccount userAcc = new UserAccount();
		        userAcc.setPassword("userTest");
		        userAcc.setUsername("userTest");
		        userAcc.setAuthorities(authorities);
		        user.setUserAccount(userAcc);
		        
		        
		        User savedUser = userService.save(user);
		        
		        User user2 = userService.create();
		        user2.setName("Nombre del usuario2");
		        user2.setSurname("Apellidos del usuario2");
		        user2.setEmail("email@delusuario.com2");
		        Collection<Folder> folders2 = new ArrayList<Folder>();
		        Collection<SocialIdentity> socialIdentities2 = new ArrayList<SocialIdentity>();
		        Collection<Attend> attends2 = new ArrayList<Attend>();
		        
		        user2.setFolders(folders2);
		        user2.setSocialIdentities(socialIdentities2);
		        user2.setAttends(attends2);
		        
		        Collection<Authority> authorities2 = new ArrayList<Authority>();
		        Authority authority2 = new Authority();
		        authority2.setAuthority("USER");
		        authorities2.add(authority2);
		         
		        UserAccount userAcc2 = new UserAccount();
		        userAcc2.setPassword("userTest2");
		        userAcc2.setUsername("userTest2");
		        userAcc2.setAuthorities(authorities2);
		        user2.setUserAccount(userAcc2);
		        
		        
		        User savedUser2 = userService.save(user2);
		        
		        super.authenticate("userTest");
		        
		        SocialIdentity socialIdentity= socialIdentityService.create();
				socialIdentity.setActor(savedUser);
				socialIdentity.setLink("http://www.linksocial.com");
				socialIdentity.setNick("socialNick");
				socialIdentity.setPicture("http://www.linkpicture.com");
				socialIdentity.setSocialNetwork("socialNetwork");
				
				super.authenticate("userTest2");
				
				SocialIdentity savedSocial =socialIdentityService.save(socialIdentity);
				Collection<SocialIdentity> socialIdentitiesRet=socialIdentityService.findAll();
				Assert.isTrue(socialIdentitiesRet.contains(savedSocial));
				
				
			}
			
			//Tests de deletes----------------------------------------
			@Test
			public void testDeleteSocialIdentity(){
				
				User user = userService.create();
		        user.setName("Nombre del usuario");
		        user.setSurname("Apellidos del usuario");
		        user.setEmail("email@delusuario.com");
		        Collection<Folder> folders = new ArrayList<Folder>();
		        Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		        Collection<Attend> attends = new ArrayList<Attend>();
		        
		        user.setFolders(folders);
		        user.setSocialIdentities(socialIdentities);
		        user.setAttends(attends);
		        
		        Collection<Authority> authorities = new ArrayList<Authority>();
		        Authority authority = new Authority();
		        authority.setAuthority("USER");
		        authorities.add(authority);
		         
		        UserAccount userAcc = new UserAccount();
		        userAcc.setPassword("userTest");
		        userAcc.setUsername("userTest");
		        userAcc.setAuthorities(authorities);
		        user.setUserAccount(userAcc);
		        
		        
		        User savedUser = userService.save(user);
		        
		        super.authenticate("userTest");
		        
		        SocialIdentity socialIdentity= socialIdentityService.create();
				socialIdentity.setActor(savedUser);
				socialIdentity.setLink("http://www.linksocial.com");
				socialIdentity.setNick("socialNick");
				socialIdentity.setPicture("http://www.linkpicture.com");
				socialIdentity.setSocialNetwork("socialNetwork");
				
				SocialIdentity savedSocial =socialIdentityService.save(socialIdentity);
				Collection<SocialIdentity> socialIdentitiesRet=socialIdentityService.findAll();
				Assert.isTrue(socialIdentitiesRet.contains(savedSocial));
				
				socialIdentityService.delete(savedSocial);
				Collection<SocialIdentity> socialIdentitiesPostDelete =socialIdentityService.findAll();
				Assert.isTrue(!socialIdentitiesPostDelete.contains(savedSocial));
			}
			
			@Test
			public void testDeleteSocialIdentitynull(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El socialIdentity a borrar no puede ser nulo");
				SocialIdentity socialIdentity;
				socialIdentity=null;
				socialIdentityService.delete(socialIdentity);
				exception=ExpectedException.none();
			}
			@Test
			public void testDeleteSocialIdentityId0(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("El objeto socialIdentity a borrar debe tener una id valida ");
				SocialIdentity socialIdentity;
				socialIdentity = socialIdentityService.create();
				socialIdentityService.delete(socialIdentity);
				exception=ExpectedException.none();
			}
			
			@SuppressWarnings("unused")
			@Test
			public void testDeleteSocialIdentityAnotherUser(){
				exception.expect(IllegalArgumentException.class);
				exception.expectMessage("Un usuario solo debe borrar sus propios socialIdentities");

				User user = userService.create();
		        user.setName("Nombre del usuario");
		        user.setSurname("Apellidos del usuario");
		        user.setEmail("email@delusuario.com");
		        Collection<Folder> folders = new ArrayList<Folder>();
		        Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		        Collection<Attend> attends = new ArrayList<Attend>();
		        
		        user.setFolders(folders);
		        user.setSocialIdentities(socialIdentities);
		        user.setAttends(attends);
		        
		        Collection<Authority> authorities = new ArrayList<Authority>();
		        Authority authority = new Authority();
		        authority.setAuthority("USER");
		        authorities.add(authority);
		         
		        UserAccount userAcc = new UserAccount();
		        userAcc.setPassword("userTest");
		        userAcc.setUsername("userTest");
		        userAcc.setAuthorities(authorities);
		        user.setUserAccount(userAcc);
		        
		        
		        User savedUser = userService.save(user);
		        
		        User user2 = userService.create();
		        user2.setName("Nombre del usuario2");
		        user2.setSurname("Apellidos del usuario2");
		        user2.setEmail("email@delusuario.com2");
		        Collection<Folder> folders2 = new ArrayList<Folder>();
		        Collection<SocialIdentity> socialIdentities2 = new ArrayList<SocialIdentity>();
		        Collection<Attend> attends2 = new ArrayList<Attend>();
		        
		        user2.setFolders(folders2);
		        user2.setSocialIdentities(socialIdentities2);
		        user2.setAttends(attends2);
		        
		        Collection<Authority> authorities2 = new ArrayList<Authority>();
		        Authority authority2 = new Authority();
		        authority2.setAuthority("USER");
		        authorities2.add(authority2);
		         
		        UserAccount userAcc2 = new UserAccount();
		        userAcc2.setPassword("userTest2");
		        userAcc2.setUsername("userTest2");
		        userAcc2.setAuthorities(authorities2);
		        user2.setUserAccount(userAcc2);
		        
		        
		        User savedUser2 = userService.save(user2);
		        
		        super.authenticate("userTest");
		        
		        SocialIdentity socialIdentity= socialIdentityService.create();
				socialIdentity.setActor(savedUser);
				socialIdentity.setLink("http://www.linksocial.com");
				socialIdentity.setNick("socialNick");
				socialIdentity.setPicture("http://www.linkpicture.com");
				socialIdentity.setSocialNetwork("socialNetwork");
				
				SocialIdentity savedSocial =socialIdentityService.save(socialIdentity);
				Collection<SocialIdentity> socialIdentitiesRet=socialIdentityService.findAll();
				Assert.isTrue(socialIdentitiesRet.contains(savedSocial));
				
				
				super.authenticate("userTest2");
				socialIdentityService.delete(savedSocial);
				Collection<SocialIdentity> socialIdentitiesPostDelete =socialIdentityService.findAll();
				Assert.isTrue(!socialIdentitiesPostDelete.contains(savedSocial));
			}
			
}
