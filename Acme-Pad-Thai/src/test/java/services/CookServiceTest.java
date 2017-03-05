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
import domain.Cook;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class CookServiceTest extends AbstractTest {
	// Service under test-------------------------
	@Autowired
	private CookService cookService;

	// Tests----------------------------------------
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCreateCook() {
		Cook cook;
		cook = cookService.create();
		Assert.notNull(cook);
	}

	@Test
	public void testSaveCook() {
		Cook cook, cookSaved;
		Collection<Cook> cooks;
		// Creamos cook
		cook = cookService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("COOK");
		authorities.add(authority);
		cook.setName("nameTest");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("cookTest");
		userAcc.setUsername("cookTest");
		userAcc.setAuthorities(authorities);
		cook.setUserAccount(userAcc);
		// guardamaos el cook
		cookSaved = cookService.save(cook);
		// comprobamos si efectivamente se ha guardado
		cooks = cookService.findAll();
		Assert.isTrue(cooks.contains(cookSaved), "El cook no se ha guardado");
	}

	@Test
	public void testSaveCookNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede guardar un cook nulo");
		Cook cook;
		cook = null;
		cookService.save(cook);
		exception = ExpectedException.none();
	}

	@Test
	public void testSaveCookNameNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre del cook debe tener texto");
		// creamos cook
		Cook cook;
		cook = cookService.create();
		// Damos valores con el name a null
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("COOK");
		authorities.add(authority);
		cook.setName(null);
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("cookTest");
		userAcc.setUsername("cookTest");
		userAcc.setAuthorities(authorities);
		cook.setUserAccount(userAcc);

		// Intentamos guardarlo esperando una excepcion
		cookService.save(cook);
		exception = ExpectedException.none();

	}

	@Test
	public void testSaveCookNameEmpty() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre del cook debe tener texto");
		// creamos cook
		Cook cook;
		cook = cookService.create();
		// Damos valores con el name a null
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("COOK");
		authorities.add(authority);
		cook.setName("");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("cookTest");
		userAcc.setUsername("cookTest");
		userAcc.setAuthorities(authorities);
		cook.setUserAccount(userAcc);

		// Intentamos guardarlo esperando una excepcion
		cookService.save(cook);
		exception = ExpectedException.none();

	}

	@Test
	public void testSaveCookNameSpacesAndTabs() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre del cook debe tener texto");
		// creamos cook
		Cook cook;
		cook = cookService.create();
		// Damos valores con el name a null
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("COOK");
		authorities.add(authority);
		cook.setName("  		  ");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("cookTest");
		userAcc.setUsername("cookTest");
		userAcc.setAuthorities(authorities);
		cook.setUserAccount(userAcc);

		// Intentamos guardarlo esperando una excepcion
		cookService.save(cook);
		exception = ExpectedException.none();
	}

	@Test
	public void testDeleteCook() {
		Cook cook, cookSaved;
		Collection<Cook> cooks;
		// Creamos cook
		cook = cookService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("COOK");
		authorities.add(authority);
		cook.setName("nameTest");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("cookTest");
		userAcc.setUsername("cookTest");
		userAcc.setAuthorities(authorities);
		cook.setUserAccount(userAcc);
		// guardamaos el cook
		cookSaved = cookService.save(cook);
		// comprobamos si efectivamente se ha guardado
		cooks = cookService.findAll();
		Assert.isTrue(cooks.contains(cookSaved), "El cook no se ha guardado");

		cookService.delete(cookSaved);
		cooks = cookService.findAll();
		Assert.isTrue(!(cooks.contains(cookSaved)), "El cook no se ha borrado");
	}

	@Test
	public void testDeleteCookNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar un cook nulo");
		Cook cook;
		cook = null;
		cookService.delete(cook);
		exception = ExpectedException.none();
	}

	@Test
	public void testDeleteCookId0() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar un cook con ID=0");
		Cook cook;

		// Creamos cook
		cook = cookService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("COOK");
		authorities.add(authority);
		cook.setName("nameTest");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("cookTest");
		userAcc.setUsername("cookTest");
		userAcc.setAuthorities(authorities);
		cook.setUserAccount(userAcc);

		cookService.delete(cook);
		exception = ExpectedException.none();

	}

	@Test
	public void testDeleteCookNotExist() {
		exception.expect(IllegalArgumentException.class);
		exception
				.expectMessage("No se puede borrar un cook que no existe en la base de datos");
		Cook cook;

		// Creamos cook
		cook = cookService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("COOK");
		authorities.add(authority);
		cook.setName("nameTest");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("cookTest");
		userAcc.setUsername("cookTest");
		userAcc.setAuthorities(authorities);
		cook.setUserAccount(userAcc);
		cook.setId(255555);

		cookService.delete(cook);
		exception = ExpectedException.none();

	}

}
