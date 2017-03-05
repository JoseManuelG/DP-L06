package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Qualification;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Integer> {
	
	//Select the qualifications for a given recipe identifier
	@Query("select q from Qualification q where q.recipe.id=?1")
	public Collection<Qualification> findQualificationsByRecipeId(int recipeId);
}
