package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRepository;
import security.LoginService;
import domain.Curriculum;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	//Managed Repository --------------------------------------
		@Autowired
		private EndorserRepository endorserRepository;	
		//Supporting Services --------------------------------------
		@Autowired
		private NutritionistService nutritionistService;
		@Autowired
		private LoginService loginService;
		//Simple CRUD methods --------------------------------------

		public Endorser create() {
			Endorser result;
			result = new Endorser();
			return result;
		}
		public Endorser create(Curriculum  curriculum) {
			Endorser result;
			result = new Endorser();
			result.setCurriculum(curriculum);
			return result;
		}
		public Collection<Endorser> findAll() {
			Collection<Endorser> result;
			Assert.notNull(endorserRepository);
			result = endorserRepository.findAll();		
			Assert.notNull(result);
			
			return result;
		}

		public Endorser findOne(int endorserId) {
			Endorser result;
			
			result = endorserRepository.findOne(endorserId);		

			return result;
		}

		@SuppressWarnings("static-access")
		public Endorser save(Endorser endorser) {
			Assert.notNull(endorser,"No se puede guardar un endorser nulo");
			Endorser result;
			Assert.isTrue(loginService.getPrincipal().equals(nutritionistService.nutritionistOfCurriculum(endorser.getCurriculum()).getUserAccount()),"No puede modificar un endorser que no es de tu curriculum");
			Assert.hasText(endorser.getName(),"el nombre del endorser debe tener texto");
			Assert.hasText(endorser.getHomepage(),("la homepage del endorser debe tener texto"));
			result = endorserRepository.save(endorser);
			
			return result;
		}	

		@SuppressWarnings("static-access")
		public void delete(Endorser endorser) {
			Assert.notNull(endorser);
			Assert.isTrue(loginService.getPrincipal().equals(nutritionistService.nutritionistOfCurriculum(endorser.getCurriculum()).getUserAccount()),"No puede modificar un endorser que no es de tu curriculum");
			Assert.isTrue(endorser.getId() != 0,"Nos se puede borrar un endorser con ID=0");
			Assert.isTrue(endorserRepository.exists(endorser.getId()),"No puede borrar un endorser que no existe en la base de datos");
			endorserRepository.delete(endorser);
			
		}

		//other business methods --------------------------------------

}
