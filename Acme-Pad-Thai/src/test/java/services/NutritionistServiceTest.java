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
import domain.Curriculum;
import domain.Endorser;
import domain.Nutritionist;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class NutritionistServiceTest extends AbstractTest {
	// Service under test-------------------------
	@Autowired
	private NutritionistService nutritionistService;

	// Tests----------------------------------------
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Autowired
	private CurriculumService curriculumService;

	@Test
	public void testCreateNutritionist() {
		Nutritionist nutritionist;
		nutritionist = nutritionistService.create();
		Assert.notNull(nutritionist);
	}

	@Test
	public void testSaveNutritionist() {
		Nutritionist nutritionist, saved;
		Collection<Nutritionist> nutritionists;
		nutritionist = nutritionistService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("NUTRITIONIST");
		authorities.add(authority);
		nutritionist.setName("nameTest");
		nutritionist.setSurname("suernameTest");
		nutritionist.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("nutritionistTest");
		userAcc.setUsername("nutritionistTest");
		userAcc.setAuthorities(authorities);
		nutritionist.setUserAccount(userAcc);

		saved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(saved));
	}

	@Test
	public void testSaveNutritionistObjectNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nutritionist no puede ser nulo");
		Nutritionist nutritionist, saved;
		Collection<Nutritionist> nutritionists;
		nutritionist = null;
		saved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(saved));

		exception = ExpectedException.none();
	}

	@Test
	public void testSaveNutritionistNombreNull() {
		exception.expect(IllegalArgumentException.class);
		exception
				.expectMessage("El nombre del nutritionist debe contener texto");
		Nutritionist nutritionist, saved;
		Collection<Nutritionist> nutritionists;

		nutritionist = nutritionistService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("NUTRITIONIST");
		authorities.add(authority);
		nutritionist.setName(null);
		nutritionist.setSurname("suernameTest");
		nutritionist.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("nutritionistTest");
		userAcc.setUsername("nutritionistTest");
		userAcc.setAuthorities(authorities);
		nutritionist.setUserAccount(userAcc);

		saved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(saved));

		exception = ExpectedException.none();
	}

	@Test
	public void testSaveNutritionistNombreVacio() {
		exception.expect(IllegalArgumentException.class);
		exception
				.expectMessage("El nombre del nutritionist debe contener texto");
		Nutritionist nutritionist, saved;
		Collection<Nutritionist> nutritionists;

		nutritionist = nutritionistService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("NUTRITIONIST");
		authorities.add(authority);
		nutritionist.setName("");
		nutritionist.setSurname("suernameTest");
		nutritionist.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("nutritionistTest");
		userAcc.setUsername("nutritionistTest");
		userAcc.setAuthorities(authorities);
		nutritionist.setUserAccount(userAcc);

		saved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(saved));

		exception = ExpectedException.none();
	}

	@Test
	public void testSaveNutritionistNombreEspaciosytabs() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El nombre del nutritionist debe contener texto");
		Nutritionist nutritionist, saved;
		Collection<Nutritionist> nutritionists;

		nutritionist = nutritionistService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("NUTRITIONIST");
		authorities.add(authority);
		nutritionist.setName("   			   ");
		nutritionist.setSurname("suernameTest");
		nutritionist.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("nutritionistTest");
		userAcc.setUsername("nutritionistTest");
		userAcc.setAuthorities(authorities);
		nutritionist.setUserAccount(userAcc);

		saved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(saved));

		exception = ExpectedException.none();
	}
	
	@Test
	public void testDeleteNutritionist() {
		Nutritionist nutritionist, saved;
		Collection<Nutritionist> nutritionists;
		nutritionist = nutritionistService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("NUTRITIONIST");
		authorities.add(authority);
		nutritionist.setName("nameTest");
		nutritionist.setSurname("suernameTest");
		nutritionist.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("nutritionistTest");
		userAcc.setUsername("nutritionistTest");
		userAcc.setAuthorities(authorities);
		nutritionist.setUserAccount(userAcc);

		saved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(saved),"El nutricionista no se ha guardado");
		nutritionistService.delete(saved);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(!(nutritionists.contains(saved)),"El nutricionista no se ha borrado");
	}

	@Test
	public void testDeleteNutritionistNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No puede borrar un objeto nulo");
		Nutritionist nutritionist;
		nutritionist = null;
		nutritionistService.delete(nutritionist);
		exception = ExpectedException.none();
	}

	@Test
	public void testDeleteNutritionistId0() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No puede borrar un objeto con ID=0");
		Nutritionist nutritionist;
		nutritionist = nutritionistService.create();
		nutritionistService.delete(nutritionist);
		exception = ExpectedException.none();
	}

	@Test
	public void testNutritionistOfCurriculum() {
		Nutritionist nutritionist, nutritionistSaved, nutritionistSaved2;

		nutritionist = nutritionistService.create();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("NUTRITIONIST");
		authorities.add(authority);
		nutritionist.setName("nameTest");
		nutritionist.setSurname("suernameTest");
		nutritionist.setEmail("email@Test.test");
		UserAccount userAcc = new UserAccount();
		userAcc.setPassword("nutritionistTest");
		userAcc.setUsername("nutritionistTest");
		userAcc.setAuthorities(authorities);
		nutritionist.setUserAccount(userAcc);

		nutritionistSaved = nutritionistService.save(nutritionist);

		Curriculum curriculum, curriculumSaved;
		curriculum = curriculumService.create();
		Collection<Endorser> endorsers = new ArrayList<Endorser>();
		Collection<Curriculum> curriculums = new ArrayList<Curriculum>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		curriculumSaved = curriculumService.save(curriculum);
		curriculums = curriculumService.findAll();

		Assert.isTrue(curriculums.contains(curriculumSaved));
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved2 = nutritionistService.save(nutritionistSaved);
		Nutritionist result = nutritionistService
				.nutritionistOfCurriculum(curriculumSaved);
		Assert.isTrue(result.equals(nutritionistSaved2));

	}

}
