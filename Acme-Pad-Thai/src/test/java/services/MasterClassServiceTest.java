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

import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class MasterClassServiceTest extends AbstractTest{
	//Service under test-------------------------
	@Autowired
	private MasterClassService masterClassService;
	
	//Tests----------------------------------------
	@Autowired
	private CookService cookService;
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	
	
	@Test
	public void testCreateMasterClass(){
		MasterClass class1;
		super.authenticate("cook1");
		class1=masterClassService.create();
		Assert.notNull(class1);
		super.authenticate(null);
	}
	@Test
	public void testSaveMasterClass(){
		//Creamos cook dueño de la clase
		super.authenticate("admin");
		Cook cook,cookSaved;

		//Creamos cook
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
		//guardamaos el cook
		cookSaved = cookService.save(cook);
		super.authenticate(null);
		super.authenticate("cookTest");
		//Creamos la MasterClass
		MasterClass masterClass,masterClassSaved;
		Collection<MasterClass> masterClasses = new ArrayList<MasterClass>();
		masterClass=masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		super.authenticate("cookTest");
		masterClassSaved= masterClassService.save(masterClass);
		super.unauthenticate();
		masterClasses= masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(masterClassSaved),"No se ha guardado la MasterClass");
		super.authenticate(null);
	}
	@Test
	public void testSaveMasterClassNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede guardar una MasterClass nula");
		MasterClass masterClass;
		masterClass=null;
		masterClassService.save(masterClass);
		exception=ExpectedException.none();
	}
	@Test
	public void testDeleteMasterClassOfOtherPerson(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar una MasterClass que no te pertenece");
		
		
		super.authenticate("admin");
		//Creamos cook NO dueño de la clase
		Cook cook2;

		//Creamos cook
		cook2 = cookService.create();
		Collection<Authority> authorities2 = new ArrayList<Authority>();
		Authority authority2 = new Authority();
		authority2.setAuthority("COOK");
		authorities2.add(authority2);
		cook2.setName("nameTest");
		cook2.setSurname("suernameTest");
		cook2.setEmail("email@Test.test");
		UserAccount userAcc2 = new UserAccount();
		userAcc2.setPassword("cook2Test");
		userAcc2.setUsername("cook2Test");
		userAcc2.setAuthorities(authorities2);
		cook2.setUserAccount(userAcc2);
		//guardamaos el cook
		 cookService.save(cook2);
		
		//Creamos cook dueño de la clase
		Cook cook,cookSaved;
		//Creamos cook
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
		//guardamaos el cook
		cookSaved = cookService.save(cook);
		super.authenticate(null);
		//Creamos la MasterClass
		super.authenticate("cookTest");
		MasterClass masterClass,masterClassSaved;
		masterClass=masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		
		
		masterClassSaved= masterClassService.save(masterClass);
		super.unauthenticate();
		super.authenticate("cook2Test");
		masterClassService.delete(masterClassSaved);
		super.unauthenticate();
		exception=ExpectedException.none();
		
	}
	@Test
	public void testSaveMasterClassTitleHasText(){
		exception.expect(IllegalArgumentException.class);	
		
		super.authenticate("admin");
		//Creamos cook dueño de la clase
		Cook cook,cookSaved;
		//Creamos cook
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
		//guardamaos el cook
		cookSaved = cookService.save(cook);
		super.authenticate(null);
		super.authenticate("cookTest");
		//Creamos la MasterClass
		MasterClass masterClass;
		masterClass=masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle(null);
		masterClassService.save(masterClass);
		masterClass.setTitle("");
		 masterClassService.save(masterClass);
		masterClass.setTitle("  		  		");
		masterClassService.save(masterClass);
		
		
		super.unauthenticate();
		exception=ExpectedException.none();
	}
	@Test
	public void testSaveMasterCookNull(){
		exception.expect(IllegalArgumentException.class);
		
		
		//Creamos la MasterClass
		MasterClass masterClass;
		Cook cook=null;
		masterClass=masterClassService.create();
		masterClass.setPromoted(true);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setTitle("titleTest");
		masterClass.setCook(cook);
		
		//super.authenticate("cookTest");
		masterClassService.save(masterClass);
		super.unauthenticate();
		exception=ExpectedException.none();
		
	}
	@Test
	public void testDeleteMasterClass(){
		//Creamos cook dueño de la clase
		super.authenticate("admin");
		Cook cook,cookSaved;
		//Creamos cook
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
		//guardamaos el cook
		cookSaved = cookService.save(cook);
		super.authenticate(null);
		super.authenticate("cookTest");
		//Creamos la MasterClass
		MasterClass masterClass,masterClassSaved;
		Collection<MasterClass> masterClasses = new ArrayList<MasterClass>();
		masterClass=masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		//Ahora la guardamos
		super.authenticate("cookTest");
		masterClassSaved= masterClassService.save(masterClass);
		super.unauthenticate();
		masterClasses= masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(masterClassSaved),"No se ha guardado la MasterClass");
		//Ahora la borramos
		super.authenticate("cookTest");
		masterClassService.delete(masterClassSaved);
		super.unauthenticate();
		masterClasses= masterClassService.findAll();
		Assert.isTrue(!(masterClasses.contains(masterClassSaved)),"No se ha borradola MasterClass");
		super.authenticate(null);
	}
	@Test
	public void testDeleteMasterClassNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar una MasterClass nula");
		MasterClass masterClass;
		masterClass=null;
		masterClassService.delete(masterClass);
		exception=ExpectedException.none();
	}


	@Test
	public void testDeleteMasterClassID0(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar una MasterClass con ID=0");
		//Creamos cook dueño de la clase
		super.authenticate("admin");
		Cook cook,cookSaved;
		//Creamos cook
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
		//guardamaos el cook
		cookSaved = cookService.save(cook);
		super.authenticate(null);
		super.authenticate("cookTest");
		//Creamos la MasterClass
		MasterClass masterClass;
		masterClass=masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		super.authenticate("cookTest");
		masterClassService.delete(masterClass);
		exception=ExpectedException.none();
	}
	@Test
	public void testDeleteMasterClassNotExist(){
		exception.expect(IllegalArgumentException.class);
		super.authenticate("admin");
		//Creamos cook dueño de la clase
		Cook cook,cookSaved;
		//Creamos cook
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
		//guardamaos el cook
		cookSaved = cookService.save(cook);
		super.authenticate(null);
		super.authenticate("cookTest");
		//Creamos la MasterClass
		MasterClass masterClass;
		masterClass=masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		masterClass.setId(2555555);

		masterClassService.delete(masterClass);
		exception=ExpectedException.none();
		super.authenticate(null);
	}

	@Test
	public void testSaveMasterClassOfAnotherPerson(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede guardar una MasterClass que no le pertenezca");
		
		super.authenticate("admin");
		//Creamos cook dueño de la clase
		Cook cook2;
		//Creamos cook
		cook2 = cookService.create();
		Collection<Authority> authorities2 = new ArrayList<Authority>();
		Authority authority2 = new Authority();
		authority2.setAuthority("COOK");
		authorities2.add(authority2);
		cook2.setName("nameTest");
		cook2.setSurname("suernameTest");
		cook2.setEmail("email@Test.test");
		UserAccount userAcc2 = new UserAccount();
		userAcc2.setPassword("cook2Test");
		userAcc2.setUsername("cook2Test");
		userAcc2.setAuthorities(authorities2);
		cook2.setUserAccount(userAcc2);
		//guardamaos el cook
		cookService.save(cook2);
		
		//Creamos cook dueño de la clase
		Cook cook,cookSaved;
		//Creamos cook
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
		//guardamaos el cook
		cookSaved = cookService.save(cook);
		super.authenticate(null);
		super.authenticate("cookTest");
		//Creamos la MasterClass
		MasterClass masterClass,masterClassSaved;
		Collection<MasterClass> masterClasses = new ArrayList<MasterClass>();
		masterClass=masterClassService.create();
		masterClass.setCook(cookSaved);
		masterClass.setDescription("DescriptionTest");
		Collection<LearningMaterial> learningMaterials = new ArrayList<LearningMaterial>();
		masterClass.setLearningMaterials(learningMaterials);
		masterClass.setPromoted(false);
		masterClass.setTitle("titleTest");
		//Ahora la guardamos
		masterClassSaved= masterClassService.save(masterClass);
		super.unauthenticate();
		masterClasses= masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(masterClassSaved),"No se ha guardado la MasterClass");
		//Ahora la borramos
		super.authenticate("cook2Test");
		masterClassService.save(masterClassSaved);
		super.unauthenticate();
		
		exception=ExpectedException.none();
		 
	}
	
}
