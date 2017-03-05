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
public class EndorserServiceTest extends AbstractTest{

	//Service under test-------------------------
	@Autowired
	private EndorserService endorserService;
	
	//Tests----------------------------------------
	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private CurriculumService curriculumService;
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Test
	public void testCreateEndorser(){
		//Creamos el curriculum
				
		Endorser endorser;
		endorser = endorserService.create();
		Assert.notNull(endorser);
	}
	@Test
	public void testSaveEndorser(){

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
		Curriculum curriculum,curriculumSaved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculum);
		curriculums=curriculumService.findAll();
		
		
		//comprobamos que se ha guardado
		Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
		
		//asignamos el curriculum al nutricionista
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		
		
		//creamos el endorser
		Endorser endorser, endorserSaved;
		endorser = endorserService.create();
		endorser.setCurriculum(curriculumSaved);
		endorser.setName("nameTest");
		endorser.setHomepage("https://www.homepageTest.org");
		endorser.setCurriculum(curriculumSaved);

		super.authenticate("nutritionistTest");
		endorserSaved=endorserService.save(endorser);
		
		
		endorsers= endorserService.findAll();
		Assert.isTrue(endorsers.contains(endorserSaved),"No se ha guardado el endorsers");
		
		
		endorsers=curriculumSaved.getEndorsers();
		endorsers.add(endorserSaved);
		curriculumSaved.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculumSaved);
		endorsers= curriculumSaved.getEndorsers();
		Assert.isTrue(endorsers.contains(endorserSaved),"El endorser esta asignado al curriculum");
		super.unauthenticate();

		}
	@Test
	public void testSaveEndorserNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede guardar un endorser nulo");
		
		Endorser endorser;
		endorser=null;
		endorserService.save(endorser);
		exception=ExpectedException.none();
	}
	@Test
	public void testModifyEndorserOfOtherPerson(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No puede modificar un endorser que no es de tu curriculum");
		//Nutricionista NO dueño
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
		
		//Le creamos un nutricionista dueño
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
				Curriculum curriculum,curriculumSaved;
				curriculum= curriculumService.create();
				Collection<Endorser> endorsers= new ArrayList<Endorser>();
				Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
				curriculum.setEducationSection("educationSectionTest");
				curriculum.setExperienceSection("experienceSectionTest");
				curriculum.setHobbiesSection("hobbiesSectionTest");
				curriculum.setPicture("https//www.pictureTest.org");
				curriculum.setEndorsers(endorsers);
				
				curriculumSaved= curriculumService.save(curriculum);
				curriculums=curriculumService.findAll();
				
				
				//comprobamos que se ha guardado
				Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
				
				//asignamos el curriculum al nutricionista
				nutritionistSaved.setCurriculum(curriculumSaved);
				nutritionistSaved = nutritionistService.save(nutritionistSaved);
				
				
				//creamos el endorser
				Endorser endorser, endorserSaved;
				endorser = endorserService.create();
				endorser.setCurriculum(curriculumSaved);
				endorser.setName("nameTest");
				endorser.setHomepage("https://www.homepageTest.org");
				endorser.setCurriculum(curriculumSaved);

				super.authenticate("nutritionistTest");
				endorserSaved=endorserService.save(endorser);
				
				
				endorsers= endorserService.findAll();
				Assert.isTrue(endorsers.contains(endorserSaved),"No se ha guardado el endorsers");
				
				
				endorsers=curriculumSaved.getEndorsers();
				endorsers.add(endorserSaved);
				curriculumSaved.setEndorsers(endorsers);
				
				curriculumSaved= curriculumService.save(curriculumSaved);
				super.unauthenticate();
				super.authenticate("nutritionist2Test");
				endorserSaved.setName("newNameTest");
				endorserSaved= endorserService.save(endorserSaved);
				super.unauthenticate();
				exception=ExpectedException.none();
	}

	
	
	
	@Test
	public void testSaveEndorserNameNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("el nombre del endorser debe tener texto");
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
		Curriculum curriculum,curriculumSaved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculum);
		curriculums=curriculumService.findAll();
		
		
		//comprobamos que se ha guardado
		Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
		
		//asignamos el curriculum al nutricionista
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		
		
		//creamos el endorser
		Endorser endorser, endorserSaved;
		endorser = endorserService.create();
		endorser.setCurriculum(curriculumSaved);
		endorser.setName(null);
		endorser.setHomepage("https://www.homepageTest.org");
		endorser.setCurriculum(curriculumSaved);

		super.authenticate("nutritionistTest");
		endorserSaved=endorserService.save(endorser);
		
		
		endorsers= endorserService.findAll();
		Assert.isTrue(endorsers.contains(endorserSaved),"No se ha guardado el endorsers");
		
		
		endorsers=curriculumSaved.getEndorsers();
		endorsers.add(endorserSaved);
		curriculumSaved.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculumSaved);
		endorsers= curriculumSaved.getEndorsers();
		Assert.isTrue(endorsers.contains(endorserSaved),"El endorser esta asignado al curriculum");
		super.unauthenticate();
		exception=ExpectedException.none();
		}

	@Test
	public void testSaveEndorserNameEmpty(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("el nombre del endorser debe tener texto");
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
		Curriculum curriculum,curriculumSaved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculum);
		curriculums=curriculumService.findAll();
		
		
		//comprobamos que se ha guardado
		Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
		
		//asignamos el curriculum al nutricionista
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		
		
		//creamos el endorser
		Endorser endorser, endorserSaved;
		endorser = endorserService.create();
		endorser.setCurriculum(curriculumSaved);
		endorser.setName("");
		endorser.setHomepage("https://www.homepageTest.org");
		endorser.setCurriculum(curriculumSaved);

		super.authenticate("nutritionistTest");
		endorserSaved=endorserService.save(endorser);
		
		
		endorsers= endorserService.findAll();
		Assert.isTrue(endorsers.contains(endorserSaved),"No se ha guardado el endorsers");
		
		
		endorsers=curriculumSaved.getEndorsers();
		endorsers.add(endorserSaved);
		curriculumSaved.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculumSaved);
		endorsers= curriculumSaved.getEndorsers();
		Assert.isTrue(endorsers.contains(endorserSaved),"El endorser esta asignado al curriculum");
		super.unauthenticate();
		exception=ExpectedException.none();
		}

	@Test
	public void testSaveEndorserNameConEspaciosYTab(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("el nombre del endorser debe tener texto");
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
		Curriculum curriculum,curriculumSaved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculum);
		curriculums=curriculumService.findAll();
		
		
		//comprobamos que se ha guardado
		Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
		
		//asignamos el curriculum al nutricionista
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		
		
		//creamos el endorser
		Endorser endorser, endorserSaved;
		endorser = endorserService.create();
		endorser.setCurriculum(curriculumSaved);
		endorser.setName("  		  ");
		endorser.setHomepage("https://www.homepageTest.org");
		endorser.setCurriculum(curriculumSaved);

		super.authenticate("nutritionistTest");
		endorserSaved=endorserService.save(endorser);
		
		
		endorsers= endorserService.findAll();
		Assert.isTrue(endorsers.contains(endorserSaved),"No se ha guardado el endorsers");
		
		
		endorsers=curriculumSaved.getEndorsers();
		endorsers.add(endorserSaved);
		curriculumSaved.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculumSaved);
		endorsers= curriculumSaved.getEndorsers();
		Assert.isTrue(endorsers.contains(endorserSaved),"El endorser esta asignado al curriculum");
		super.unauthenticate();
		exception=ExpectedException.none();
		}
	


	
	
	
	
	
	@Test
	public void testDeleteEndorser(){

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
		Curriculum curriculum,curriculumSaved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculum);
		curriculums=curriculumService.findAll();
		
		
		//comprobamos que se ha guardado
		Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
		
		//asignamos el curriculum al nutricionista
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		
		
		//creamos el endorser
		Endorser endorser, endorserSaved;
		endorser = endorserService.create();
		endorser.setCurriculum(curriculumSaved);
		endorser.setName("nameTest");
		endorser.setHomepage("https://www.homepageTest.org");
		endorser.setCurriculum(curriculumSaved);

		super.authenticate("nutritionistTest");
		endorserSaved=endorserService.save(endorser);
		
		
		endorsers= endorserService.findAll();
		Assert.isTrue(endorsers.contains(endorserSaved),"No se ha guardado el endorser");
		
		
		endorsers=curriculumSaved.getEndorsers();
		endorsers.add(endorserSaved);
		curriculumSaved.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculumSaved);
		
	
		endorserService.delete(endorserSaved);
		endorsers= endorserService.findAll();
		Assert.isTrue(!(endorsers.contains(endorserSaved)),"No se ha borrado el endorser");
		
		super.unauthenticate();

		}
	
	@Test
	public void testDeleteEndorserOfOtherPerson(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No puede modificar un endorser que no es de tu curriculum");
				
		//Le creamos un nutricionista NO dueño
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
		//Le creamos un nutricionista dueño 
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
		Curriculum curriculum,curriculumSaved;
		curriculum= curriculumService.create();
		Collection<Endorser> endorsers= new ArrayList<Endorser>();
		Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setPicture("https//www.pictureTest.org");
		curriculum.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculum);
		
		
		
		//comprobamos que se ha guardado
		curriculums=curriculumService.findAll();
		Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
		
		//asignamos el curriculum al nutricionista
		nutritionistSaved.setCurriculum(curriculumSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		
		
		//creamos el endorser
		Endorser endorser, endorserSaved;
		endorser = endorserService.create();
		endorser.setCurriculum(curriculumSaved);
		endorser.setName("nameTest");
		endorser.setHomepage("https://www.homepageTest.org");
		endorser.setCurriculum(curriculumSaved);

		super.authenticate("nutritionistTest");
		endorserSaved=endorserService.save(endorser);
		
		//Comprobamos que el endorser se ha guardado
		endorsers= endorserService.findAll();
		Assert.isTrue(endorsers.contains(endorserSaved),"No se ha guardado el endorser");
		
		//asignamos el endorser al curriculum
		endorsers=curriculumSaved.getEndorsers();
		endorsers.add(endorserSaved);
		curriculumSaved.setEndorsers(endorsers);
		
		curriculumSaved= curriculumService.save(curriculumSaved);
		super.unauthenticate();
		super.authenticate("nutritionist2Test");
		endorserService.delete(endorserSaved);
		endorsers= endorserService.findAll();
		Assert.isTrue(!(endorsers.contains(endorserSaved)),"No se ha borrado el endorser");
		
		super.unauthenticate();
		exception=ExpectedException.none();
		}
		@Test
		public void testDeleteEndorsersID0(){
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("Nos se puede borrar un endorser con ID=0");
			
			
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
			Curriculum curriculum,curriculumSaved;
			curriculum= curriculumService.create();
			Collection<Endorser> endorsers= new ArrayList<Endorser>();
			Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
			curriculum.setEducationSection("educationSectionTest");
			curriculum.setExperienceSection("experienceSectionTest");
			curriculum.setHobbiesSection("hobbiesSectionTest");
			curriculum.setPicture("https//www.pictureTest.org");
			curriculum.setEndorsers(endorsers);
			
			curriculumSaved= curriculumService.save(curriculum);
			
			
			
			//comprobamos que se ha guardado
			curriculums=curriculumService.findAll();
			Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
			
			//asignamos el curriculum al nutricionista
			nutritionistSaved.setCurriculum(curriculumSaved);
			nutritionistSaved = nutritionistService.save(nutritionistSaved);
			
			
			//creamos el endorser
			Endorser endorser;
			endorser = endorserService.create();
			endorser.setCurriculum(curriculumSaved);
			endorser.setName("nameTest");
			endorser.setHomepage("https://www.homepageTest.org");
			endorser.setCurriculum(curriculumSaved);

			super.authenticate("nutritionistTest");
			endorserService.delete(endorser);
			super.unauthenticate();
			exception=ExpectedException.none();
	
		}

		@Test
		public void testDeleteEndorsersNoEnDB(){
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("No puede borrar un endorser que no existe en la base de datos");
			
			
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
			Curriculum curriculum,curriculumSaved;
			curriculum= curriculumService.create();
			Collection<Endorser> endorsers= new ArrayList<Endorser>();
			Collection<Curriculum> curriculums= new ArrayList<Curriculum>();
			curriculum.setEducationSection("educationSectionTest");
			curriculum.setExperienceSection("experienceSectionTest");
			curriculum.setHobbiesSection("hobbiesSectionTest");
			curriculum.setPicture("https//www.pictureTest.org");
			curriculum.setEndorsers(endorsers);
			
			curriculumSaved= curriculumService.save(curriculum);
			
			
			
			//comprobamos que se ha guardado
			curriculums=curriculumService.findAll();
			Assert.isTrue(curriculums.contains(curriculumSaved),"El curriculum no se ha guardado");
			
			//asignamos el curriculum al nutricionista
			nutritionistSaved.setCurriculum(curriculumSaved);
			nutritionistSaved = nutritionistService.save(nutritionistSaved);
			
			
			//creamos el endorser
			Endorser endorser;
			endorser = endorserService.create();
			endorser.setCurriculum(curriculumSaved);
			endorser.setName("nameTest");
			endorser.setHomepage("https://www.homepageTest.org");
			endorser.setCurriculum(curriculumSaved);
			endorser.setId(2999999);
			super.authenticate("nutritionistTest");
			endorserService.delete(endorser);
			super.unauthenticate();
			exception=ExpectedException.none();
		}
}
