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
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Nutritionist;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class AttendServiceTest extends AbstractTest {
	// Service under test-------------------------
	@Autowired
	private AttendService attendService;

	// Tests----------------------------------------
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private CookService cookService;

	@Test
	public void testCreateAttend() {
		Attend attend;
		attend = attendService.create();
		Assert.notNull(attend);
	}

	@Test
	public void testSaveAttend() {
		// Creamos Nutritionist
		Nutritionist nutritionist, nutritionistSaved;
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
		Collection<Attend> nutritionistAttends = new ArrayList<Attend>();
		nutritionist.setAttends(nutritionistAttends);
		nutritionistSaved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(nutritionistSaved),
				"El nutritionist no se ha guardado");
		// ---------------------------------------------

		// Creamos cook dueño de la clase
		Cook cook, cookSaved;
		// Creamos cook
		cook = cookService.create();
		Collection<Authority> authorities2 = new ArrayList<Authority>();
		Authority authority2 = new Authority();
		authority2.setAuthority("COOK");
		authorities2.add(authority2);
		cook.setName("nameTest");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc2 = new UserAccount();
		userAcc2.setPassword("cookTest");
		userAcc2.setUsername("cookTest");
		userAcc2.setAuthorities(authorities2);
		cook.setUserAccount(userAcc2);

		// guardamaos el cook
		cookSaved = cookService.save(cook);
		// Creamos la MasterClass
		MasterClass masterClass, masterClassSaved;
		Collection<MasterClass> masterClasses = new ArrayList<MasterClass>();
		masterClass = masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		Collection<Attend> masterClassAttends = new ArrayList<Attend>();
		masterClass.setAttends(masterClassAttends);
		super.authenticate("cookTest");
		masterClassSaved = masterClassService.save(masterClass);
		super.unauthenticate();
		masterClasses = masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(masterClassSaved),
				"No se ha guardado la MasterClass");

		// Creamos Attend
		Attend attend, attendSaved;
		Collection<Attend> attends = new ArrayList<Attend>();
		attend = attendService.create();
		// Damos valores
		attend.setActor(nutritionistSaved);
		attend.setMasterClass(masterClassSaved);

		// Guardamos el Attend
		super.authenticate("nutritionistTest");
		attendSaved = attendService.save(attend);
		super.unauthenticate();
		// Comprobamos si se guarda
		attends = attendService.findAll();
		Assert.isTrue(attends.contains(attendSaved),
				"No se ha guardado el Attend");
		// Assignamos al actor
		attends = nutritionistSaved.getAttends();
		attends.add(attendSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		// Assignamos al actor
		attends = masterClassSaved.getAttends();
		attends.add(attendSaved);
		super.authenticate("cookTest");
		masterClassSaved = masterClassService.save(masterClassSaved);
		super.unauthenticate();
		// comprobamos las asignaciones
		Assert.isTrue(nutritionistSaved.getAttends().contains(attendSaved),
				"No se ha asigando el Attend al actor");
		Assert.isTrue(masterClassSaved.getAttends().contains(attendSaved),
				"No se ha asigando el Attend a la masterClass");

	}

	@Test
	public void testSaveAttendNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede guardar un attend nulo");
		Attend attend;
		attend = null;
		attendService.save(attend);
		exception = ExpectedException.none();
	}

	@Test
	public void testSaveAttendActorNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El actor del attend no puede ser nulo");
		// Creamos cook dueño de la clase
		Cook cook, cookSaved;

		// Creamos cook
		cook = cookService.create();
		Collection<Authority> authorities2 = new ArrayList<Authority>();
		Authority authority2 = new Authority();
		authority2.setAuthority("COOK");
		authorities2.add(authority2);
		cook.setName("nameTest");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc2 = new UserAccount();
		userAcc2.setPassword("cookTest");
		userAcc2.setUsername("cookTest");
		userAcc2.setAuthorities(authorities2);
		cook.setUserAccount(userAcc2);

		// guardamaos el cook
		cookSaved = cookService.save(cook);
		// Creamos la MasterClass
		MasterClass masterClass, masterClassSaved;
		Collection<MasterClass> masterClasses = new ArrayList<MasterClass>();
		masterClass = masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		Collection<Attend> masterClassAttends = new ArrayList<Attend>();
		masterClass.setAttends(masterClassAttends);
		super.authenticate("cookTest");
		masterClassSaved = masterClassService.save(masterClass);
		super.unauthenticate();
		masterClasses = masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(masterClassSaved),
				"No se ha guardado la MasterClass");

		// Creamos Attend
		Attend attend;
		attend = attendService.create();
		// Damos valores
		Nutritionist nutritionist;
		nutritionist = null;
		attend.setActor(nutritionist);
		attend.setMasterClass(masterClassSaved);

		// Guardamos el Attend
		 attendService.save(attend);

		exception = ExpectedException.none();
	}

	@Test
	public void testSaveAttendMasterClassNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("La clase del attend no puede ser nulo");
		// Creamos Nutritionist
		Nutritionist nutritionist, nutritionistSaved;
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
		Collection<Attend> nutritionistAttends = new ArrayList<Attend>();
		nutritionist.setAttends(nutritionistAttends);
		nutritionistSaved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(nutritionistSaved),
				"El nutritionist no se ha guardado");

		// Creamos Attend
		Attend attend;
		attend = attendService.create();
		// Damos valores
		attend.setActor(nutritionistSaved);
		MasterClass masterClass;
		masterClass = null;
		attend.setMasterClass(masterClass);

		// Guardamos el Attend
		super.authenticate("nutritionistTest");
		 attendService.save(attend);
		 exception= ExpectedException.none();
	}

	@Test
	public void testSaveAttendOfOtherPerson() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No puedes guardar attends de otra persona");

		// Creamos Nutritionist
		Nutritionist nutritionist2, nutritionistSaved2;
		Collection<Nutritionist> nutritionists2;
		nutritionist2 = nutritionistService.create();
		Collection<Authority> authorities3 = new ArrayList<Authority>();
		Authority authority3 = new Authority();
		authority3.setAuthority("NUTRITIONIST");
		authorities3.add(authority3);
		nutritionist2.setName("nameTest");
		nutritionist2.setSurname("suernameTest");
		nutritionist2.setEmail("email@Test.test");
		UserAccount userAcc3 = new UserAccount();
		userAcc3.setPassword("nutritionist2Test");
		userAcc3.setUsername("nutritionist2Test");
		userAcc3.setAuthorities(authorities3);
		nutritionist2.setUserAccount(userAcc3);
		Collection<Attend> nutritionistAttends2 = new ArrayList<Attend>();
		nutritionist2.setAttends(nutritionistAttends2);
		nutritionistSaved2 = nutritionistService.save(nutritionist2);
		nutritionists2 = nutritionistService.findAll();
		Assert.isTrue(nutritionists2.contains(nutritionistSaved2),
				"El nutritionist2 no se ha guardado");
		// Creamos Nutritionist dueño del attend
		Nutritionist nutritionist, nutritionistSaved;
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
		Collection<Attend> nutritionistAttends = new ArrayList<Attend>();
		nutritionist.setAttends(nutritionistAttends);
		nutritionistSaved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(nutritionistSaved),
				"El nutritionist no se ha guardado");
		// ---------------------------------------------

		// Creamos cook dueño de la clase
		Cook cook, cookSaved;

		// Creamos cook
		cook = cookService.create();
		Collection<Authority> authorities2 = new ArrayList<Authority>();
		Authority authority2 = new Authority();
		authority2.setAuthority("COOK");
		authorities2.add(authority2);
		cook.setName("nameTest");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc2 = new UserAccount();
		userAcc2.setPassword("cookTest");
		userAcc2.setUsername("cookTest");
		userAcc2.setAuthorities(authorities2);
		cook.setUserAccount(userAcc2);

		// guardamaos el cook
		cookSaved = cookService.save(cook);
		// Creamos la MasterClass
		MasterClass masterClass, masterClassSaved;
		Collection<MasterClass> masterClasses = new ArrayList<MasterClass>();
		masterClass = masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		Collection<Attend> masterClassAttends = new ArrayList<Attend>();
		masterClass.setAttends(masterClassAttends);
		super.authenticate("cookTest");
		masterClassSaved = masterClassService.save(masterClass);
		super.unauthenticate();
		masterClasses = masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(masterClassSaved),
				"No se ha guardado la MasterClass");

		// Creamos Attend
		Attend attend, attendSaved;
		Collection<Attend> attends = new ArrayList<Attend>();
		attend = attendService.create();
		// Damos valores
		attend.setActor(nutritionistSaved);
		attend.setMasterClass(masterClassSaved);

		// Guardamos el Attend
		super.authenticate("nutritionistTest");
		attendSaved = attendService.save(attend);
		super.unauthenticate();
		// Comprobamos si se guarda
		attends = attendService.findAll();
		Assert.isTrue(attends.contains(attendSaved),
				"No se ha guardado el Attend");
		// Assignamos al actor
		attends = nutritionistSaved.getAttends();
		attends.add(attendSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		// Assignamos al actor
		attends = masterClassSaved.getAttends();
		attends.add(attendSaved);
		super.authenticate("cookTest");
		masterClassSaved = masterClassService.save(masterClassSaved);
		super.unauthenticate();
		super.authenticate("nutritionist2Test");
		attendSaved = attendService.save(attendSaved);
		super.unauthenticate();
		exception = ExpectedException.none();
	}

	public void testDeleteAttend() {
		// Creamos Nutritionist
		Nutritionist nutritionist, nutritionistSaved;
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
		Collection<Attend> nutritionistAttends = new ArrayList<Attend>();
		nutritionist.setAttends(nutritionistAttends);
		nutritionistSaved = nutritionistService.save(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(nutritionistSaved),
				"El nutritionist no se ha guardado");
		// ---------------------------------------------

		// Creamos cook dueño de la clase
		Cook cook, cookSaved;

		// Creamos cook
		cook = cookService.create();
		Collection<Authority> authorities2 = new ArrayList<Authority>();
		Authority authority2 = new Authority();
		authority2.setAuthority("COOK");
		authorities2.add(authority2);
		cook.setName("nameTest");
		cook.setSurname("suernameTest");
		cook.setEmail("email@Test.test");
		UserAccount userAcc2 = new UserAccount();
		userAcc2.setPassword("cookTest");
		userAcc2.setUsername("cookTest");
		userAcc2.setAuthorities(authorities2);
		cook.setUserAccount(userAcc2);

		// guardamaos el cook
		cookSaved = cookService.save(cook);
		// Creamos la MasterClass
		MasterClass masterClass, masterClassSaved;
		Collection<MasterClass> masterClasses = new ArrayList<MasterClass>();
		masterClass = masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		Collection<Attend> masterClassAttends = new ArrayList<Attend>();
		masterClass.setAttends(masterClassAttends);
		super.authenticate("cookTest");
		masterClassSaved = masterClassService.save(masterClass);
		super.unauthenticate();
		masterClasses = masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(masterClassSaved),
				"No se ha guardado la MasterClass");

		// Creamos Attend
		Attend attend, attendSaved;
		Collection<Attend> attends = new ArrayList<Attend>();
		attend = attendService.create();
		// Damos valores
		attend.setActor(nutritionistSaved);
		attend.setMasterClass(masterClassSaved);

		// Guardamos el Attend
		super.authenticate("nutritionistTest");
		attendSaved = attendService.save(attend);
		super.unauthenticate();
		// Comprobamos si se guarda
		attends = attendService.findAll();
		Assert.isTrue(attends.contains(attendSaved),
				"No se ha guardado el Attend");
		// Assignamos al actor
		attends = nutritionistSaved.getAttends();
		attends.add(attendSaved);
		nutritionistSaved = nutritionistService.save(nutritionistSaved);
		// Assignamos al actor
		attends = masterClassSaved.getAttends();
		attends.add(attendSaved);
		super.authenticate("cookTest");
		masterClassSaved = masterClassService.save(masterClassSaved);
		super.unauthenticate();
		// comprobamos las asignaciones
		Assert.isTrue(nutritionistSaved.getAttends().contains(attendSaved),
				"No se ha asigando el Attend al actor");
		Assert.isTrue(masterClassSaved.getAttends().contains(attendSaved),
				"No se ha asigando el Attend a la masterClass");
		// Borramos el Attend
		super.authenticate("nutritionistTest");
		attendService.delete(attendSaved);
		super.unauthenticate();
		// Comprobamos si se borra
		attends = attendService.findAll();
		Assert.isTrue(!(attends.contains(attendSaved)),
				"No se ha guardado el Attend");
		//Comprobamos Asignaciones
		nutritionistSaved=nutritionistService.findOne(nutritionistSaved.getId());
		Assert.isTrue(nutritionistSaved.getAttends().contains(attendSaved),
				"No se ha borrado el Attend al actor");
		masterClassSaved=masterClassService.findOne(masterClassSaved.getId());
		Assert.isTrue(masterClassSaved.getAttends().contains(attendSaved),
				"No se ha borrado el Attend a la MasterClass");
		
	}
	
	@Test
	public void testDeleteAttendNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar un attend nulo");

		Attend attend;
		attend =null;
		attendService.delete(attend);
		exception=ExpectedException.none();
	}
	@Test
	public void testDeleteAttendId0(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar un attend con ID=0");

		Attend attend;
		attend = attendService.create();
		attendService.delete(attend);
		exception=ExpectedException.none();
	}	@Test
	public void testDeleteAttendNotExist(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("no puedes borrar un attend que no existe en la base de datos");

		Attend attend;
		attend = attendService.create();
		attend.setId(25555);
		attendService.delete(attend);
		exception=ExpectedException.none();
	}
}
