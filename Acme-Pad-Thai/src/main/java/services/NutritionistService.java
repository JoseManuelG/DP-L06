package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NutritionistRepository;
import security.LoginService;
import security.UserAccount;
import domain.Attend;
import domain.Comment;
import domain.Curriculum;
import domain.Folder;
import domain.Follow;
import domain.Nutritionist;
import domain.Qualification;
import domain.SocialIdentity;

@Service
@Transactional
public class NutritionistService {
//Managed Repository --------------------------------------
	@Autowired
	private NutritionistRepository nutritionistRepository;
	

	//Simple CRUD methods --------------------------------------

	public Nutritionist create() {
		Nutritionist result;
		result = new Nutritionist();
		result.setAttends(new ArrayList<Attend>());
		result.setComments(new ArrayList<Comment>());
		result.setFolders(new ArrayList<Folder>());
		result.setFolloweds(new ArrayList<Follow>());
		result.setFollowers(new ArrayList<Follow>());
		result.setQualifications(new ArrayList<Qualification>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
	
		return result;
	}
	public Collection<Nutritionist> findAll() {
		Collection<Nutritionist> result;
		Assert.notNull(nutritionistRepository);
		result = nutritionistRepository.findAll();		
		Assert.notNull(result);
		
		return result;
	}

	public Nutritionist findOne(int nutritionistId) {
		Nutritionist result;
		
		result = nutritionistRepository.findOne(nutritionistId);		

		return result;
	}
	
	public Nutritionist save(Nutritionist Nutritionist) {
		
		Assert.notNull(Nutritionist,"El nutritionist no puede ser nulo");
		Assert.hasText(Nutritionist.getName(),"El nombre del nutritionist debe contener texto");
		Assert.hasText(Nutritionist.getEmail(),"El email del nutritionist debe contener texto");
		Assert.hasText(Nutritionist.getSurname(),"El apellido del nutritionist debe contener texto");
		Nutritionist result;
		result = nutritionistRepository.save(Nutritionist);
		
		return result;
	}	
	
	public void delete(Nutritionist nutritionist) {
		Assert.notNull(nutritionist,"No puede borrar un objeto nulo");
		Assert.isTrue( nutritionist.getId() != 0,"No puede borrar un objeto con ID=0");
		
		Assert.isTrue(nutritionistRepository.exists(nutritionist.getId()),"No puede borrar un objeto que no existe en la base de datos");
		nutritionistRepository.delete(nutritionist);
	}
	//other business methods --------------------------------------

	public Nutritionist nutritionistOfCurriculum(Curriculum curriculum) {
		// TODO Auto-generated method stub
		Nutritionist result=nutritionistRepository.nutritionistOfCurriculum(curriculum.getId());
		return result;
	}
	public Nutritionist findByPrincipal() {
		Nutritionist  result;
		UserAccount userAccount;
	
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	private Nutritionist  findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Nutritionist result;

		result = nutritionistRepository.findByUserAccountId(userAccount.getId());		

		return result;
	}
	public Collection<Nutritionist> searchForNutritionist(String searchTerm){
		Collection<Nutritionist> result = nutritionistRepository.searchForNutritionist(searchTerm);
		return result;
	}

}
