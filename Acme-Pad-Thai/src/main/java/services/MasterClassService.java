package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MasterClassRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Attend;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Message;

@Service
@Transactional
public class MasterClassService {

	// Managed Repository --------------------------------------
	@Autowired
	private MasterClassRepository masterClassRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private MessageService messageService;
	@Autowired
	private LoginService loginService;

	// Simple CRUD methods --------------------------------------
	public MasterClass create() {
		MasterClass result;
		result = new MasterClass();
		Collection<Attend> attends=new ArrayList<Attend>();
		
		Collection<LearningMaterial>learningMaterials =new ArrayList<LearningMaterial>();
		
		result.setAttends(attends);
		result.setLearningMaterials(learningMaterials);
		return result;
	}

	public Collection<MasterClass> findAll() {
		Collection<MasterClass> result;
		Assert.notNull(masterClassRepository);
		result = masterClassRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public MasterClass findOne(int masterClassId) {
		Assert.isTrue(masterClassId != 0);
		MasterClass result;
		result = masterClassRepository.findOne(masterClassId);
		return result;
	}

	@SuppressWarnings("static-access")
	public MasterClass save(MasterClass masterClass) {
		Assert.notNull(masterClass, "No se puede guardar una MasterClass nula");
		Assert.notNull(masterClass.getCook(),
				"La propiedad Cook no puede ser nula");
		Collection<Authority> auhtAuthorities = loginService.getPrincipal().getAuthorities();
		boolean esAdmin= false;
		for(Authority a:auhtAuthorities){
			esAdmin=a.getAuthority().equals(Authority.ADMIN);
			if(esAdmin){
				break;
			}
		}
		Assert.isTrue(loginService.getPrincipal().equals(masterClass.getCook().getUserAccount())||esAdmin,
				"No se puede guardar una MasterClass que no le pertenezca");
		MasterClass result;
		Assert.hasText(masterClass.getTitle(),
				"El title de la masterClass debe contener texto");
		Assert.hasText(masterClass.getDescription(),
				"La Description de la masterClass debe contener texto");

		result = masterClassRepository.save(masterClass);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(MasterClass masterClass) {
		Assert.notNull(masterClass, "No se puede borrar una MasterClass nula");
		Assert.isTrue(masterClass.getId() != 0,
				"No se puede borrar una MasterClass con ID=0");
		Assert.isTrue(masterClassRepository.findOne(masterClass.getId())!=null,
				"No se puede borrar una MasterClass que no existe en la base de datos");
		Assert.isTrue(loginService.getPrincipal().equals(masterClass.getCook().getUserAccount()),
				"No se puede borrar una MasterClass que no te pertenece");
		notifyDeleteOfMasterClass(masterClass);
		masterClassRepository.delete(masterClass);

	}

	// other business methods --------------------------------------
	public MasterClass promotedMasterClass(MasterClass masterClass) {
		masterClass.setPromoted(true);
		MasterClass result = save(masterClass);
		return result;
	}

	public MasterClass demoteMasterClass(MasterClass masterClass) {
		masterClass.setPromoted(false);
		MasterClass result = save(masterClass);
		return result;
	}

	public boolean existMasterClassesOfCook(int cookId) {
		// TODO Auto-generated method stub
		Collection<MasterClass> MasterClasses = masterClassRepository
				.existMasterClassesOfCook(cookId);
		Boolean result = MasterClasses.isEmpty();
		return !result;
	}

	public boolean notifyDeleteOfMasterClass(MasterClass masterClass) {

		Collection<Attend> attends = masterClassRepository
				.getAttendsOfMasterClass(masterClass.getId());
		Message message;
		Actor attender;
		for (Attend attend : attends) {
			attender = attend.getActor();
			message = messageService.create();
			message.setSubject("the master class: " + masterClass.getTitle()
					+ " have been deleted");
			message.setBody("the master class: " + masterClass.getTitle()
					+ " have been deleted\n"+"la clase maestra: " + masterClass.getTitle()
					+ " ha sido borrada");
			message.setRecipient(attender.getName());
			message.setSender("System");
			message.setSendingMoment(new Date(System.currentTimeMillis()));
			message.setPriority("NEUTRAL");
			messageService.sendMessage(message,attender);

		}
		return false;
	}
	public Collection<MasterClass> findRecipesByCook(Cook cook){
		Collection<MasterClass> result;
		result= masterClassRepository.findRecipesBycook(cook.getId());
		return result;
	}
	public Double avgOfLearningMaterialPerMasterClass() {
		Double result = masterClassRepository
				.avgOfLearningMaterialPerMasterClass();
		return result;
	}

	public Integer minOfLearningMaterialPerMasterClass() {
		Integer result = masterClassRepository
				.minOfLearningMaterialPerMasterClass();
		return result;
	}

	public Integer maxOfLearningMaterialPerMasterClass() {
		Integer result = masterClassRepository
				.maxOfLearningMaterialPerMasterClass();
		return result;
	}

	public Double stddevOfLearningMaterialPerMasterClass() {
		Double result = masterClassRepository
				.stddevOfLearningMaterialPerMasterClass();
		return result;
	}

	public Integer numberOfMasterClassesPromoted() {
		Integer result = masterClassRepository.numberOfMasterClassesPromoted();
		return result;
	}

	public Double avgOfLearningMaterialPerMasterClassByKindLearningMaterial(
			String KindOfLearnigMaterial) {
		Double result = masterClassRepository
				.avgOfLearningMaterialPerMasterClassByKindLearningMaterial(KindOfLearnigMaterial);
		return result;
	}
	public Collection<MasterClass> findPromotedMasterClasses(){
		Collection<MasterClass> result = masterClassRepository.findPromotedMasterClasses();
		return result;
	}

}