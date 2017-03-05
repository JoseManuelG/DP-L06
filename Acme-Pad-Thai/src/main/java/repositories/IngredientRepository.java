package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
	//Select the ingredient by a given quantity
	@Query("select q.ingredient from Quantity q where q.id=?1")
	public Ingredient findIngredientByQuantity(int quantityId);

//	@Query("select distinct i from Quantity q,Ingredient i where q.recipe.id!=?1 or i.quantities.size=0")
//	public Collection<Ingredient> findIngredientsAbleToAddForRecipe(int recipeId);
	
	@Query("select distinct q.ingredient from Quantity q where q.recipe.id=?1")
	public Collection<Ingredient> findIngredentsByRecipe(int recipeId);


}
