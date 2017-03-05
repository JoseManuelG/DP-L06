package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AttendRepository;
import security.LoginService;
import domain.Actor;
import domain.Attend;
import domain.MasterClass;

@Service
@Transactional
public class AttendService {
	// Managed Repository --------------------------------------
	@Autowired
	private AttendRepository attendRepository;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ActorService actorService;
	
	// Supporting Services --------------------------------------
	// Simple CRUD methods --------------------------------------

	public Attend create() {
		Attend result;
		result = new Attend();
		return result;
	}
	public Attend create(MasterClass masterClass) {
		Attend result;
		result = new Attend();
		Actor actor= actorService.findActorByPrincial();
		result.setActor(actor);
		return result;
	}

	public Collection<Attend> findAll() {
		Collection<Attend> result;
		Assert.notNull(attendRepository);
		result = attendRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Attend findOne(int attendId) {
		Attend result;
		Assert.isTrue(attendId != 0);
		result = attendRepository.findOne(attendId);
		return result;
	}

	@SuppressWarnings({ "static-access" })
	public Attend save(Attend attend) {
		Assert.notNull(attend, "No se puede guardar un attend nulo");
		Attend result;
		Assert.notNull(attend.getActor(),
				"El actor del attend no puede ser nulo");
		Assert.notNull(attend.getMasterClass(),
				"La clase del attend no puede ser nulo");
		Assert.isTrue(attend.getActor().getUserAccount().equals( loginService
				.getPrincipal()), "No puedes guardar attends de otra persona");
		Assert.isTrue(!existAttendOfActorAndMasterClass(attend.getActor(), attend.getMasterClass()),"No puede participar en una misma clase dos vecess");
		
		result = attendRepository.save(attend);

		return result;
	}

	public void delete(Attend attend) {
		Assert.notNull(attend, "No se puede borrar un attend nulo");
		Assert.isTrue(attend.getId() != 0,
				"No se puede borrar un attend con ID=0");
		Assert.isTrue(attendRepository.exists(attend.getId()),
				"no puedes borrar un attend que no existe en la base de datos");
		Assert.isTrue(attend.getActor().getUserAccount().equals(LoginService
				.getPrincipal()), "No puedes borrar attends de otra persona");
		attendRepository.delete(attend);
	}
	// other business methods --------------------------------------
	boolean existAttendOfActorAndMasterClass(Actor actor,MasterClass masterClass){
		Assert.notNull(actor);
		Assert.notNull(masterClass);
		Attend attend = attendRepository.existAttendOfActorAndMasterClass(actor.getId(), masterClass.getId());
		boolean result= attend!=null;
		return result;
	}
	public Attend findAttendByMasterClassAndActor(int actorId,int masterClassId){
		Attend result= attendRepository.existAttendOfActorAndMasterClass(actorId,masterClassId);
		return result;
	}
}
