package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LearningMaterialRepository;
import security.LoginService;
import domain.LearningMaterial;
import domain.MasterClass;

@Service
@Transactional
public class LearningMaterialService {

	//Managed Repository --------------------------------------
		@Autowired
		private LearningMaterialRepository learningMaterialRepository;
		
		//Supporting Services --------------------------------------
		private LoginService loginService;
		//Simple CRUD methods --------------------------------------

		public LearningMaterial create() {
			LearningMaterial result;
			result = new LearningMaterial();
			
			result.setAttachments(new ArrayList<String>());
			return result;
		}
		public Collection<LearningMaterial> findAll() {
			Collection<LearningMaterial> result;
			Assert.notNull(learningMaterialRepository);
			result = learningMaterialRepository.findAll();		
			Assert.notNull(result);
			return result;
		}

		public LearningMaterial findOne(int learningMaterialId) {
			LearningMaterial result;
			Assert.isTrue(learningMaterialId!=0);
			result = learningMaterialRepository.findOne(learningMaterialId);
			return result;
		}

		@SuppressWarnings("static-access")
		public LearningMaterial save(LearningMaterial learningMaterial) {
			Assert.notNull(learningMaterial,"El learninMaterial no puede ser nulo");
			LearningMaterial result;
			Assert.isTrue(learningMaterial.getMasterClass().getCook().getUserAccount().equals(loginService.getPrincipal()),"No puedes modigicar un learningMaterial que no es de tu clase");
			Assert.hasText(learningMaterial.getTitle(),"El titulo del learningMaterial debe tener texto");
			Assert.hasText(learningMaterial.getSummary(),"El summary del learningMaterial debe tener texto");
			Assert.hasText(learningMaterial.getType(),"El tyep del learningMaterial debe tener texto");
			Assert.hasText(learningMaterial.getData());
			
			result = learningMaterialRepository.save(learningMaterial);
			
			return result;
		}	

		@SuppressWarnings("static-access")
		public void delete(LearningMaterial learningMaterial) {
			Assert.notNull(learningMaterial,"No se puede borrar un learningMaterial nulo");
			Assert.isTrue(learningMaterial.getId()!= 0,"No se puede borrar un learningMaterial con ID=0");
			Assert.isTrue(loginService.getPrincipal().equals(learningMaterial.getMasterClass().getCook().getUserAccount()),"No se puede borrar un learningMaterial que de una clase que no le pertecene");
			Assert.isTrue(learningMaterialRepository.exists(learningMaterial.getId()),"No se puede borrar un learningMaterial que no existe en la base de datos");
			learningMaterialRepository.delete(learningMaterial);
		}
		//other business methods --------------------------------------
		public boolean existLearningMaterialOfMasterClass(MasterClass masterClass){
				boolean result;
				Collection<LearningMaterial> attends = learningMaterialRepository.existLearningMaterialOfMasterClass(masterClass.getId());
				result =attends.isEmpty();
				return !result;
		}

}
