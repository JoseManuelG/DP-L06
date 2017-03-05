package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import security.LoginService;
import domain.Curriculum;
import domain.Endorser;
import domain.Nutritionist;

@Service
@Transactional
public class CurriculumService {
	//Managed Repository --------------------------------------
	@Autowired
	private CurriculumRepository curriculumRepository;
	
	//Supporting Services --------------------------------------
	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private LoginService loginService;
	//Simple CRUD methods --------------------------------------
	

	public Curriculum create() {
		Curriculum result;
		result = new Curriculum ();
		Collection<Endorser> endorsers =new ArrayList<Endorser>();
		result.setEndorsers(endorsers);
		return result;
	}
	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;
		Assert.notNull(curriculumRepository);
		result = curriculumRepository.findAll();		
		Assert.notNull(result);
		
		return result;
	}

	public Curriculum findOne(int curriculumId) {
		Curriculum result;
		Assert.isTrue(curriculumId!=0);
		result = curriculumRepository.findOne(curriculumId);
		return result;
	}

	@SuppressWarnings("static-access")
	public Curriculum save(Curriculum curriculum) {
		Assert.notNull(curriculum,"No puede guardar un curriculum nulo");
		Curriculum result;
		Assert.isTrue(curriculum.getId()==0||loginService.getPrincipal().equals(nutritionistService.nutritionistOfCurriculum(curriculum).getUserAccount()),"El curriculum debe ser nuevo o debe ser suyo para poder se modificado");
		Assert.hasText(curriculum.getEducationSection(),"La EducationSection del curriculum debe contener texto");
		Assert.hasText(curriculum.getExperienceSection(),"La ExperienceSection del curriculum debe contener texto");
		Assert.hasText(curriculum.getHobbiesSection(),"La HobbiesSection del curriculum debe contener texto");
		Assert.notNull(curriculum.getPicture(),"El curriculum debe contener una foto");
		result = curriculumRepository.save(curriculum);
		
		
		return result;
	}	

	@SuppressWarnings("static-access")
	public void delete(Curriculum curriculum) {
		Assert.notNull(curriculum); 
		Assert.isTrue(curriculum.getId() != 0);
		Assert.isTrue(curriculumRepository.exists(curriculum.getId()));
		Assert.isTrue(loginService.getPrincipal().equals(nutritionistService.nutritionistOfCurriculum(curriculum).getUserAccount()));
		Nutritionist nutritionist= nutritionistService.nutritionistOfCurriculum(curriculum);
		nutritionist.setCurriculum(null);
		nutritionistService.save(nutritionist);
		curriculumRepository.delete(curriculum);
		
	}
	//other business methods --------------------------------------
	public boolean existCurriculumOfNutritionist(int nutritionistId) {
		// TODO Auto-generated method stub
		Collection<Curriculum> curriculums=curriculumRepository.existCurriculumOfNutritionist(nutritionistId);
		Boolean result=curriculums.isEmpty();
		return !result;
	}
	
	
}
