package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RecipeHint;

@Repository
public interface RecipeHintRepository extends JpaRepository<RecipeHint,Integer> {
	
	//Selects all the recipeHints for a recipe
	@Query("select r.recipeHints from Recipe r where r.id=?1")
	public Collection<RecipeHint> findRecipeHintsByRecipe(int recipeId);

}
