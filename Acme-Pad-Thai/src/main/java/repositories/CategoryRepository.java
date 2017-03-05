package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Recipe;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

	//Select the subcategories from a category
	@Query("select s.subCategory from Category s where s.id = ?1")
	Collection<Category> findSubCategoryId(int id);

	//Select categories for a given recipe
	@Query("select b.category from Recipe r join r.belongs b where r.id = ?1")
	Collection<Category> findCategoriesForRecipeId(int recipeId);
	
	@Query("select distinct b.category from Belongs b where b.recipe.id=?1")
	public Collection<Category> findCategoriesByRecipe(int recipeId);
	
	//Select recipes for a given category
		@Query("select b.recipe from Category c join c.belongs b where c.id = ?1 and b.recipe.isCopy=false")
		Collection<Recipe> findRecipesForCategoryId(int recipeId);
	
}
