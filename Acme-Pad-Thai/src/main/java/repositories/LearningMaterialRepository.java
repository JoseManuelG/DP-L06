package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LearningMaterial;

@Repository
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial,Integer>{
	
	//Select the learning materials for a master class
	@Query("select l from  LearningMaterial l where MasterClass_id = ?1")
	Collection<LearningMaterial> existLearningMaterialOfMasterClass(int MasterClassid);

}
