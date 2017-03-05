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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class AdministratorServiceTest {
	//Service under test-------------------------
		@Autowired
		private AdministratorService administratorService;
		
		//Tests----------------------------------------
		@Rule
		  public ExpectedException exception = ExpectedException.none();
		
		@Test
		public void testCreateAdministrator(){
			Administrator administrator;
			administrator = administratorService.create();
			Assert.notNull(administrator);
			Assert.isNull(administrator.getName());
			Assert.isNull(administrator.getSurname());
			Assert.isNull(administrator.getEmail());
			Assert.isNull(administrator.getAddress());
			Assert.isNull(administrator.getPhone());
		}
		
		@Test
		public void testSaveAdministrator(){
		Administrator administrator,saved;
		Collection<Administrator> administrators;
		administrator = administratorService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("ADMIN");
		authorities.add(authority);
		administrator.setName("nameTest");
		administrator.setSurname("suernameTest");
		administrator.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("administratorTest");
		userAcc.setUsername("administratorTest");
		userAcc.setAuthorities(authorities);
		administrator.setUserAccount(userAcc);
		
		saved=administratorService.save(administrator);
		administrators = administratorService.findAll();
		Assert.isTrue(administrators.contains(saved));
		}
		
		@Test
		public void testSaveAdministratorObjectNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El administrador no puede ser null");
		Administrator administrator,saved;
		Collection<Administrator> administrators;
		administrator=null;
		saved=administratorService.save(administrator);
		administrators = administratorService.findAll();
		Assert.isTrue(administrators.contains(saved));

		exception=ExpectedException.none();
		}
	
		@Test
		public void testDeleteAdministrator(){
		//create y save administrador	
		Administrator administrator,saved;
		Collection<Administrator> administrators;
		administrator = administratorService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("ADMIN");
		authorities.add(authority);
		administrator.setName("nameTest");
		administrator.setSurname("suernameTest");
		administrator.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("administratorTest");
		userAcc.setUsername("administratorTest");
		userAcc.setAuthorities(authorities);
		administrator.setUserAccount(userAcc);
		
		saved=administratorService.save(administrator);
		administrators = administratorService.findAll();
		Assert.isTrue(administrators.contains(saved),"El administrator no se ha guardado");
		administratorService.delete(saved);
		administrators = administratorService.findAll();
		Assert.isTrue(!(administrators.contains(saved)),"El administrator no se ha Borrado");
		}
		@Test
		public void testDeleteAdministratornull(){
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("El administrador no puede ser null");
			Administrator administrator;
			administrator=null;
			administratorService.delete(administrator);
			exception=ExpectedException.none();
		}
		@Test
		public void testDeleteAdministratorId0(){
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("No puede borrar un objeto de ID=0");
			Administrator administrator;
			administrator = administratorService.create();
			administratorService.delete(administrator);
			exception=ExpectedException.none();
		}
		@Test
		public void testDeleteAdministratorNotExist(){
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("El administrador debe existir");
			Administrator administrator;
			administrator = administratorService.create();
			administrator.setId(1);
			administratorService.delete(administrator);
			exception=ExpectedException.none();
		}
		
}
