package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum,Integer> {
	
	//Return the curricula of a nutritionist
	@Query("select c from Curriculum c where Nutritionist_id = ?1")
	Collection<Curriculum> existCurriculumOfNutritionist(int nutritionistId);
	

}
