package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Nutritionist;
@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist,Integer> {
	
	//Select a nutritionist given a curriculum
	@Query("select n from Nutritionist n where n.curriculum.id=?1")
	Nutritionist nutritionistOfCurriculum(int CurriculumId);

	//The user for an userAccountId
	@Query("select u from Nutritionist u where u.userAccount.id = ?1")
	Nutritionist findByUserAccountId(int id);
	

	//Return a collection of nutritionists using a single keyword that must appear verbatim in his or her name
	@Query("select n from Nutritionist n where n.name=?1")
	public Collection<Nutritionist> searchForNutritionist(String searchTerm);


}

