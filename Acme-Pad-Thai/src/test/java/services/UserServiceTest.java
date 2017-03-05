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
public class UserServiceTest extends AbstractTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//Service under test-------------------------------------
	
	@Autowired
	private UserService userService;
	
	//Tests--------------------------------------------------
	
	@Test
	public void testCreateUser(){
		User user;
		user = userService.create();
		Assert.notNull(user);
	}
	
	@Test
	public void testSaveUser(){
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
		user.setPhone("+212(001)asdasdas-asdas");
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
		Collection<User>users = userService.findAll();
		Assert.isTrue(users.contains(savedUser));
	}
	
	@Test
	public void testSaveUserNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El usuario no puede ser nulo");
		
		User user = null;
		Collection<User> usersSaved;
		User userSaved = userService.save(user);
		usersSaved = userService.findAll();
		Assert.isTrue(usersSaved.contains(userSaved));
		
		exception = ExpectedException.none();
	}
	
	@Test
	public void testSaveUserNameNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre del usuario no puede ser nulo ni estar vacío");
		
		User user, saved;
		Collection<User> users;

		user = userService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("USER");
		authorities.add(authority);
		user.setName(null);
		user.setSurname("suernameTest");
		user.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("userTest");
		userAcc.setUsername("userTest");
		userAcc.setAuthorities(authorities);
		user.setUserAccount(userAcc);

		saved = userService.save(user);
		users = userService.findAll();
		Assert.isTrue(users.contains(saved));
		
		exception = ExpectedException.none();
	}
	
	@Test
	public void testSaveUserNameEmpty(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre del usuario no puede ser nulo ni estar vacío");
		
		User user, saved;
		Collection<User> users;

		user = userService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("USER");
		authorities.add(authority);
		user.setName("");
		user.setSurname("suernameTest");
		user.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("userTest");
		userAcc.setUsername("userTest");
		userAcc.setAuthorities(authorities);
		user.setUserAccount(userAcc);

		saved = userService.save(user);
		users = userService.findAll();
		Assert.isTrue(users.contains(saved));
		
		exception = ExpectedException.none();
	}

	@Test
	public void testSaveUserNameSpaces(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre del usuario no puede ser nulo ni estar vacío");
		
		User user, saved;
		Collection<User> users;

		user = userService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("USER");
		authorities.add(authority);
		user.setName("   			   ");
		user.setSurname("surnameTest");
		user.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("userTest");
		userAcc.setUsername("userTest");
		userAcc.setAuthorities(authorities);
		user.setUserAccount(userAcc);

		saved = userService.save(user);
		users = userService.findAll();
		Assert.isTrue(users.contains(saved));
		
		exception = ExpectedException.none();
	}
	
	@Test
	public void testDeleteUser() {
		User user, saved;
		Collection<User> users;
		user = userService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("USER");
		authorities.add(authority);
		user.setName("nameTest");
		user.setSurname("suernameTest");
		user.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("userTest");
		userAcc.setUsername("userTest");
		userAcc.setAuthorities(authorities);
		user.setUserAccount(userAcc);

		saved = userService.save(user);
		users = userService.findAll();
		Assert.isTrue(users.contains(saved),"El usuario no se ha guardado");
		userService.delete(saved);
		users = userService.findAll();
		Assert.isTrue(!(users.contains(saved)),"El usuario no se ha borrado");
	}
	
	@Test
	public void testDeleteUserNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El usuario no puede ser nulo");
		User user;
		user = null;
		userService.delete(user);
		exception = ExpectedException.none();
	}
	
	@Test
	public void testDeleteUserId0() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El usuario debe estar en la base de datos");
		User user;
		user = userService.create();
		userService.delete(user);
		exception = ExpectedException.none();
	}

}
