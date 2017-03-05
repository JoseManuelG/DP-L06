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
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	//Service under test-------------------------
	@Autowired
	private CurriculumService curriculumService;
	
	//Tests----------------------------------------
	@Rule
	  public ExpectedException exception = ExpectedException.none();

	@Autowired
	private NutritionistService nutritionistService;
	@Test
	public void testCreateCurriculum(){
		Curriculum curriculum;
		curriculum= curriculumService.create();
		Assert.notNull(curriculum);
		}

	@Test
	public void testSaveCurriculum(){
		//Le creamos un nutricionista
		Nutritionist nutritionist,nutritionistSaved;
		nutritionist= nutritionistService.create();
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
		
		nutritionistSaved=nutritionistService.save(nutritionist);
		//Creamos el curriculum
		Curriculum curriculum,saved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		saved = curriculumService.save(curriculum);
		curriculums=curriculumService.findAll();
		//comprobamos que se ha guardado
		Assert.isTrue(curriculums.contains(saved));
		//Comprobamos que se le puede asignar un nutricionista
		nutritionistSaved.setCurriculum(saved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		Assert.isTrue(nutritionistSaved.getCurriculum().equals(saved));
	}
	@Test
	public void testSaveCurriculumNulo(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No puede guardar un curriculum nulo");
		
		Curriculum curriculum;
		curriculum=null;
		curriculumService.save(curriculum);
		exception=ExpectedException.none();

	}

	@Test
	public void testModifyCurriculumOfAnotherPerson(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El curriculum debe ser nuevo o debe ser suyo para poder se modificado");
		//creamos nutritionist NO dueño del curriculum
				Nutritionist nutritionist2;
				nutritionist2= nutritionistService.create();
				Collection<Authority> authorities2 = new ArrayList<Authority>();
				Authority authority2 = new Authority();
				authority2.setAuthority("NUTRITIONIST");
				authorities2.add(authority2);
				nutritionist2.setName("nameTest");
				nutritionist2.setSurname("suernameTest");
				nutritionist2.setEmail("email@Test.test");
				UserAccount userAcc2 = new UserAccount();
				userAcc2.setPassword("nutritionist2Test");
				userAcc2.setUsername("nutritionist2Test");
				userAcc2.setAuthorities(authorities2);
				nutritionist2.setUserAccount(userAcc2);
				
				nutritionistService.save(nutritionist2);
				
		//creamos nutritionist dueño del curriculum
		Nutritionist nutritionist,nutritionistSaved;
		nutritionist= nutritionistService.create();
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
		
		nutritionistSaved=nutritionistService.save(nutritionist);
		
		//Creamos un curriculum
		Curriculum curriculum,curriculumSaved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		curriculumSaved = curriculumService.save(curriculum);
		
		//se lo damos asignamos al nutritionist
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved=nutritionistService.save(nutritionistSaved);
				
		super.authenticate("nutritionist2Test");
		curriculumService.save(curriculumSaved);
		super.unauthenticate();
		
		
		exception=ExpectedException.none();
	}
	@Test
	public void testDeleteCurriculum(){
		//Creamos un curriculum		
		Nutritionist nutritionist,nutritionistSaved;
		nutritionist= nutritionistService.create();
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
		
		nutritionistSaved=nutritionistService.save(nutritionist);
		
		//Creamos un curriculum
		Curriculum curriculum,curriculumSaved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		curriculumSaved = curriculumService.save(curriculum);
		
		//se lo damos asignamos al nutritionist
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved=nutritionistService.save(nutritionistSaved);
				
		super.authenticate("nutritionistTest");
		curriculumService.delete(curriculumSaved);
		super.unauthenticate();
		
		//Comprobamos que se ha borrado
		Collection<Curriculum>curriculums;
		curriculums=curriculumService.findAll();
		Assert.isTrue(!(curriculums.contains(curriculum)),"El curriculum no se ha borrado");
	}

}
