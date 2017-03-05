package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Attend;
import domain.MasterClass;

@Repository
public interface MasterClassRepository extends JpaRepository<MasterClass,Integer>{
	
	//All the masterclasses of a given cook
	@Query("select m from MasterClass m where Cook_id = ?1")
	Collection<MasterClass> existMasterClassesOfCook(int cookId);
	
	//All the Attends from a given masterclass
	@Query("select a from Attend a where masterClass_id = ?1")
	Collection<Attend> getAttendsOfMasterClass(int masterClassId);
	
	//Query A/1 The minimum, the maximum, the average, and the standard deviation of the number of master classes per cook.
	@Query("select avg(c.masterClasses.size) from Cook c")
	Double avgOfLearningMaterialPerMasterClass();
	
	//The maximum of learning materials per master class
	@Query("select max(c.masterClasses.size) from Cook c")
	Integer maxOfLearningMaterialPerMasterClass();
	
	//The minimum of learning materials per master class
	@Query("select min(c.masterClasses.size) from Cook c")
	Integer minOfLearningMaterialPerMasterClass();
	
	//The standard deviation of learning materials per master class
	@Query("select stddev(c.masterClasses.size) from Cook c")
	Double stddevOfLearningMaterialPerMasterClass();

	//Quwey A/2: The average number of learning materials per master class, grouped by kind of learning material.
	@Query("select count(l)*1.0/(select count(m) from MasterClass m) from LearningMaterial l where l.type=?1")
	Double  avgOfLearningMaterialPerMasterClassByKindLearningMaterial(String KindOfLearnigMaterial);
	
	//Query A/3: The number of master classes that have been promoted.
	@Query("select count(c) from MasterClass c where c.promoted = true")
	Integer numberOfMasterClassesPromoted();
	@Query("select c from MasterClass c where cook_id = ?1")
	Collection<MasterClass> findRecipesBycook(int cookId);
	
	@Query("select mc from MasterClass mc where mc.promoted = true")
	Collection<MasterClass> findPromotedMasterClasses();
	

}
