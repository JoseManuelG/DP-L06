package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CookRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Attend;
import domain.Cook;
import domain.MasterClass;
import domain.SocialIdentity;

@Service
@Transactional
public class CookService {
	// Managed Repository --------------------------------------
	@Autowired
	private CookRepository cookRepository;
	// Supporting Services --------------------------------------
	@Autowired
	private FolderService folderService;
	@Autowired
	private LoginService loginService;

	// Simple CRUD methods --------------------------------------

	public Cook create() {
		Cook result;
		result = new Cook();
		folderService.createBasicsFolders(result);
		result.setAttends(new ArrayList<Attend>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setMasterClasses(new ArrayList<MasterClass>());
		
		
		return result;
	}

	public Collection<Cook> findAll() {
		Collection<Cook> result;
		Assert.notNull(cookRepository);
		result = cookRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Cook findOne(int CookId) {
		Assert.isTrue(CookId != 0);
		Cook result;
		result = cookRepository.findOne(CookId);
		return result;
	}

	@SuppressWarnings("static-access")
	public Cook save(Cook cook) {
		Assert.notNull(cook, "No se puede guardar un cook nulo");
		Cook result;
		Assert.hasText(cook.getName(), "El nombre del cook debe tener texto");
		Assert.hasText(cook.getEmail(), "El email del cook debe tener texto");
		Assert.hasText(cook.getSurname(),
				"El surname del cook debe tener texto");
		Collection<Authority> auhtAuthorities = loginService.getPrincipal().getAuthorities();
		boolean esAdmin= false;
		for(Authority a:auhtAuthorities){
			esAdmin=a.getAuthority().equals(Authority.ADMIN);
			if(esAdmin){
				break;
			}
		}
	Assert.isTrue(loginService.getPrincipal().equals(cook.getUserAccount())||esAdmin);
		result = cookRepository.save(cook);
		return result;
	}

	public void delete(Cook cook) {
		Assert.isTrue(cook != null, "No se puede borrar un cook nulo");
		Assert.isTrue(cook.getId() != 0, "No se puede borrar un cook con ID=0");
		Assert.isTrue(cookRepository.exists(cook.getId()),
				"No se puede borrar un cook que no existe en la base de datos");
		cookRepository.delete(cook);
	}

	// other business methods --------------------------------------
	public Integer minOfMasterClassPerCook() {
		Integer result = cookRepository.minOfMasterClassPerCook();
		return result;
	}

	public Integer maxOfMasterClassPerCook() {
		Integer result = cookRepository.maxOfMasterClassPerCook();
		return result;
	}

	public Double avgOfMasterClassPerCook() {
		Double result = cookRepository.avgOfMasterClassPerCook();
		return result;
	}

	public Collection<Cook> listOfCooksByPromotedMasterClass() {
		Collection<Cook> result = cookRepository
				.listOfCooksByPromotedMasterClass();
		return result;
	}

	public Collection<Double> avgOfPromotedClass() {
		Collection<Double> result = cookRepository.avgOfPromotedClass();
		return result;
	}

	public Collection<Double> avgOfDemotedClass() {
		Collection<Double> result = cookRepository.avgOfDemotedClass();
		return result;
	}
	public Collection<Cook> cooksOfDemotedClass(){
		Collection<Cook> result = cookRepository.cooksOfDemotedClass();
		return result;
	}
	
	public Double stddevOfMasterClassPerCook(){
			Double result;
			result=cookRepository.stddevOfMasterClassPerCook();
			return result;
		}
	public Cook findByPrincipal() {
		Cook result;
		UserAccount userAccount;
	
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	public Cook findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Cook result;

		result = cookRepository.findByUserAccountId(userAccount.getId());		

		return result;
	}
	public Collection<Cook> searchForCook(String searchTerm){
		Collection<Cook> result = cookRepository.searchForCook(searchTerm);
		return result;
	}

}
