package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quantity;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Integer> {
	
	//For an ingredient returns all the quantities of that ingredient
	@Query("select q from Quantity q where q.ingredient.id = ?1")
	Collection<Quantity> findByIngredientId(int ingredientId);
	
	//For an ingredient and a recipe returns an unique quantity
	@Query("select q from Quantity q where q.recipe.id = ?1 and q.ingredient.id = ?2")
	Quantity findByRecipeIdAndIngredientId(int recipeId, int ingredientId);
	
	//Find all the quantities for a given recipe
	@Query("select r.quantities from Recipe r where r.id=?1")
	public Collection<Quantity> findQuantitiesByRecipe(int recipeId);

}
