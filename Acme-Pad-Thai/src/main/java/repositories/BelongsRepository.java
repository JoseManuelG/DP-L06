package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Belongs;

@Repository
public interface BelongsRepository extends JpaRepository<Belongs, Integer> {
	//Select all the belongs for a recipe
	@Query("select r.belongs from Recipe r where r.id=?1")
	public Collection<Belongs> findBelongsByRecipe(int recipeId);

}
