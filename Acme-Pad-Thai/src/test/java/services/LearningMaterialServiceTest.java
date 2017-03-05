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
public class LearningMaterialServiceTest extends AbstractTest{
	//Service under test-------------------------
	@Autowired
	private LearningMaterialService learningMaterialService;
	
	//Tests----------------------------------------
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private CookService cookService ;
	@Test
	public void testCreateLearningMaterial(){

		//Creamos el LearningMaterial
		LearningMaterial learningMaterial;
		learningMaterial= learningMaterialService.create();
		Assert.notNull(learningMaterial);
	}
	@Test
	public void testSaveLearningMaterial(){
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
	//Creamos el LearningMaterial
		LearningMaterial learningMaterial,learningMaterialSaved;
		Collection<LearningMaterial> learningMaterials2= new ArrayList<LearningMaterial>();
		learningMaterial= learningMaterialService.create();
		learningMaterial.setMasterClass(masterClassSaved);
		
		learningMaterial.setType("text");
		learningMaterial.setData("dataTest");
		learningMaterial.setSummary("SummaryTest");
		learningMaterial.setTitle("titleTest");
		Collection<String> attachments = new ArrayList<String>();
		learningMaterial.setAttachments(attachments);
		
	//Guardamos el LearningMaterl
		super.authenticate("cookTest");
		learningMaterialSaved= learningMaterialService.save(learningMaterial);
	//Asignamos a la MasterClass el LearninMaterial	
		learningMaterials2=masterClassSaved.getLearningMaterials();
		learningMaterials2.add(learningMaterialSaved);
		masterClassSaved.setLearningMaterials(learningMaterials2);
		masterClassSaved=masterClassService.save(masterClassSaved);
	//Comprobamos	
		learningMaterials2= learningMaterialService.findAll();
		Assert.isTrue(learningMaterials2.contains(learningMaterialSaved),"El learningMaterial no se ha guardado");
		Assert.isTrue(masterClassSaved.getLearningMaterials().contains(learningMaterialSaved),"El learningMaterial no se ha asginado a la masterClass");
		
	}

	@Test
	public void testSaveLearningMaterialNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El learninMaterial no puede ser nulo");
		LearningMaterial learningMaterial;
		learningMaterial=null;
		learningMaterialService.save(learningMaterial);
		exception= ExpectedException.none();
	}
	@Test
	public void testSaveLearningMaterialOfAnotherPerson(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No puedes modigicar un learningMaterial que no es de tu clase");
		
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
	//Creamos el LearningMaterial
		LearningMaterial learningMaterial,learningMaterialSaved;
		Collection<LearningMaterial> learningMaterials2= new ArrayList<LearningMaterial>();
		learningMaterial= learningMaterialService.create();
		learningMaterial.setMasterClass(masterClassSaved);
		
		learningMaterial.setType("text");
		learningMaterial.setData("dataTest");
		learningMaterial.setSummary("SummaryTest");
		learningMaterial.setTitle("titleTest");
		Collection<String> attachments = new ArrayList<String>();
		learningMaterial.setAttachments(attachments);
		
	//Guardamos el LearningMaterl
		super.authenticate("cook2Test");
		learningMaterialSaved= learningMaterialService.save(learningMaterial);
	//Asignamos a la MasterClass el LearninMaterial	
		learningMaterials2=masterClassSaved.getLearningMaterials();
		learningMaterials2.add(learningMaterialSaved);
		masterClassSaved.setLearningMaterials(learningMaterials2);
		masterClassSaved=masterClassService.save(masterClassSaved);
		super.unauthenticate();
	exception=ExpectedException.none();
	}	
	@Test
	public void testSaveLearningMaterialTitleHasText(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El titulo del learningMaterial debe tener texto");
		
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
	//Creamos el LearningMaterial
		LearningMaterial learningMaterial;
		learningMaterial= learningMaterialService.create();
		learningMaterial.setMasterClass(masterClassSaved);
		
		learningMaterial.setType("text");
		learningMaterial.setData("dataTest");
		learningMaterial.setSummary("SummaryTest");
		learningMaterial.setTitle("titleTest");
		Collection<String> attachments = new ArrayList<String>();
		learningMaterial.setAttachments(attachments);
		
	//Guardamos el LearningMaterl
		super.authenticate("cookTest");
		//title nulo
		learningMaterial.setTitle(null);
		learningMaterialService.save(learningMaterial);
		//title cadena vacia
		learningMaterial.setTitle("");
		 learningMaterialService.save(learningMaterial);
		//title con espacios y tabuladores
		learningMaterial.setTitle("  		  ");
		learningMaterialService.save(learningMaterial);
						
		super.unauthenticate();
		exception=ExpectedException.none();	
	}

	@Test
	public void testDeleteLearningMaterial(){
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
	//Creamos el LearningMaterial
		LearningMaterial learningMaterial,learningMaterialSaved;
		Collection<LearningMaterial> learningMaterials2= new ArrayList<LearningMaterial>();
		learningMaterial= learningMaterialService.create();
		learningMaterial.setMasterClass(masterClassSaved);
		
		learningMaterial.setType("text");
		learningMaterial.setData("dataTest");
		learningMaterial.setSummary("SummaryTest");
		learningMaterial.setTitle("titleTest");
		Collection<String> attachments = new ArrayList<String>();
		learningMaterial.setAttachments(attachments);
		
	//Guardamos el LearningMaterl
		super.authenticate("cookTest");
		learningMaterialSaved= learningMaterialService.save(learningMaterial);
		
		//Asignamos a la MasterClass el LearninMaterial	
		learningMaterials2=masterClassSaved.getLearningMaterials();
		learningMaterials2.add(learningMaterialSaved);
		masterClassSaved.setLearningMaterials(learningMaterials2);
		masterClassSaved=masterClassService.save(masterClassSaved);
		super.unauthenticate();
		//Comprobamos	
		learningMaterials2= learningMaterialService.findAll();
		Assert.isTrue(learningMaterials2.contains(learningMaterialSaved),"El learningMaterial no se ha guardado");
		Assert.isTrue(masterClassSaved.getLearningMaterials().contains(learningMaterialSaved),"El learningMaterial no se ha asginado a la masterClass");
	//Borramos
		super.authenticate("cookTest");
		learningMaterialService.delete(learningMaterialSaved);
		learningMaterials2= learningMaterialService.findAll();
		Assert.isTrue(!(learningMaterials2.contains(learningMaterialSaved)),"El learningMaterial no se ha borrado");
		super.unauthenticate();

	}
	@Test
	public void testDeleteLearningMaterialOfOtherPerson(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar un learningMaterial que de una clase que no le pertecene");
		
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
	//Creamos el LearningMaterial
		LearningMaterial learningMaterial,learningMaterialSaved;
		Collection<LearningMaterial> learningMaterials2= new ArrayList<LearningMaterial>();
		learningMaterial= learningMaterialService.create();
		learningMaterial.setMasterClass(masterClassSaved);
		
		learningMaterial.setType("text");
		learningMaterial.setData("dataTest");
		learningMaterial.setSummary("SummaryTest");
		learningMaterial.setTitle("titleTest");
		Collection<String> attachments = new ArrayList<String>();
		learningMaterial.setAttachments(attachments);
		
	//Guardamos el LearningMaterl
		super.authenticate("cookTest");
		learningMaterialSaved= learningMaterialService.save(learningMaterial);
		
		//Asignamos a la MasterClass el LearninMaterial	
		learningMaterials2=masterClassSaved.getLearningMaterials();
		learningMaterials2.add(learningMaterialSaved);
		masterClassSaved.setLearningMaterials(learningMaterials2);
		masterClassSaved=masterClassService.save(masterClassSaved);
		super.unauthenticate();
		//Comprobamos	
		learningMaterials2= learningMaterialService.findAll();
		Assert.isTrue(learningMaterials2.contains(learningMaterialSaved),"El learningMaterial no se ha guardado");
		Assert.isTrue(masterClassSaved.getLearningMaterials().contains(learningMaterialSaved),"El learningMaterial no se ha asginado a la masterClass");
	//Borramos
		super.authenticate("cook2Test");
		learningMaterialService.delete(learningMaterialSaved);
		learningMaterials2= learningMaterialService.findAll();
		Assert.isTrue(!(learningMaterials2.contains(learningMaterialSaved)),"El learningMaterial no se ha borrado");
		super.unauthenticate();
		exception=ExpectedException.none();
	}
	@Test
	public void testDeleteLearningMaterialNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar un learningMaterial nulo");
		LearningMaterial learningMaterial;
		learningMaterial=null;
		learningMaterialService.delete(learningMaterial);
		
		exception=ExpectedException.none();
	}
	@Test
	public void testDeleteLearningMaterialId0(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar un learningMaterial con ID=0");
		
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
	//Creamos el LearningMaterial
		LearningMaterial learningMaterial;
		Collection<LearningMaterial> learningMaterials2= new ArrayList<LearningMaterial>();
		learningMaterial= learningMaterialService.create();
		learningMaterial.setMasterClass(masterClassSaved);
		
		learningMaterial.setType("text");
		learningMaterial.setData("dataTest");
		learningMaterial.setSummary("SummaryTest");
		learningMaterial.setTitle("titleTest");
		Collection<String> attachments = new ArrayList<String>();
		learningMaterial.setAttachments(attachments);
		
	//Borramos
		super.authenticate("cookTest");
		learningMaterialService.delete(learningMaterial);
		learningMaterials2= learningMaterialService.findAll();
		Assert.isTrue(!(learningMaterials2.contains(learningMaterial)),"El learningMaterial no se ha borrado");
		super.unauthenticate();
		exception=ExpectedException.none();
		
	}
	@Test
	public void testDeleteLearningMaterialNotExist(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No se puede borrar un learningMaterial que no existe en la base de datos");
		
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
	//Creamos el LearningMaterial
		LearningMaterial learningMaterial;
		Collection<LearningMaterial> learningMaterials2= new ArrayList<LearningMaterial>();
		learningMaterial= learningMaterialService.create();
		learningMaterial.setMasterClass(masterClassSaved);
		
		learningMaterial.setType("text");
		learningMaterial.setData("dataTest");
		learningMaterial.setSummary("SummaryTest");
		learningMaterial.setTitle("titleTest");
		Collection<String> attachments = new ArrayList<String>();
		learningMaterial.setAttachments(attachments); 
		learningMaterial.setId(25555);
	//Borramos
		super.authenticate("cookTest");
		learningMaterialService.delete(learningMaterial);
		learningMaterials2= learningMaterialService.findAll();
		Assert.isTrue(!(learningMaterials2.contains(learningMaterial)),"El learningMaterial no se ha borrado");
		super.unauthenticate();
		exception=ExpectedException.none();
		
	}
	
}
