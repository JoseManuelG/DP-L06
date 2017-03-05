package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Qualification;
import domain.Recipe;
import domain.Step;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	
	//The average of number of steps per recipe.
	@Query("select avg(r.steps.size) from Recipe r where r.isCopy=false")
	public Double findAvgOfStepsForRecipes();
	
	//The standard deviation  of number of steps per recipe.
	@Query("select stddev(r.steps.size) from Recipe r where r.isCopy=false")
	public Double findStdDevOfStepsForRecipes();
	
	//The average of number of ingredients per recipe.
	@Query("select avg(r.quantities.size) from Recipe r where r.isCopy=false")
	public Double findAvgOfIngredientsPerRecipe();
		
	//The standard deviation of number of ingredients per recipe.
	@Query("select stddev(r.quantities.size) from Recipe r where r.isCopy=false")
	public Double findStdDevOfIngredientsPerRecipe();
	
	//Recipes that belongs to a desired category
	@Query("select b.recipe from Category c join c.belongs b where c.id=?1 and b.recipe.isCopy=false")
	public Collection<Recipe> findRecipesByCategory(int categoryId);
	
	//Recipes authored by an user
	@Query("select distinct r from User u join u.recipes r where u.id=?1 and r.isCopy=false")
	public Collection<Recipe> findRecipesByUser(int userId);
	

	//Select the qualifications for a given recipe identifier
	@Query("select r.qualifications from Recipe r where r.id=?1")
	public Collection<Qualification> findQualificationsByRecipeId(int recipeId);
	
	//Select the steps of a given recipe
	@Query("select r.steps from Recipe r where r.id=?1")
	public Collection<Step> findStepsByRecipeId(int recipeId);
	
	//Count the steps of a given recipe
	@Query("select r.steps.size from Recipe r where r.id=?1")
	public int countStepsForRecipeId(int recipeId);
	//Return a collection of recipes using a single keyword that must appear verbatim in its ticker, title, or summary
	@Query("select r from Recipe r where r.title=?1 and r.isCopy=false or r.ticker=?1 and r.isCopy=false or r.summary=?1 and r.isCopy=false")
	public Collection<Recipe> searchForRecipe(String searchTerm);
	
	//Return a collection of recipes que tienen en el parntTicker el que tu le pasas
	@Query("select r from Recipe r where r.parentTicker=?1 ")
	public Collection<Recipe> findRecipesByParentTicker(String parentTicker);
	
	//Return a collection of recipes que tienen en el parntTicker el que tu le pasas
	@Query("select r from Recipe r where r.ticker=?1 ")
	public Recipe findRecipeByParentTicker(String parentTicker);

	//Returns every non-copy recipe
	@Query("select r from Recipe r where r.isCopy=false")
	public Collection<Recipe> findOriginalRecipes();
	
	//Returns a collection with the latest recipes that the users they follow have authored
	@Query("select rp from Follow f join f.followed.recipes rp where f.follower.id=?1 order by rp.authorMoment desc")
	public Collection<Recipe> findFollowedsLastRecipes(int customerId);
	
	//Returns every non-copy recipe
		@Query("select distinct r from User u join u.recipes r where u.id=?1 and r.isCopy=true")
		public Collection<Recipe> findRecipesInContestByUser(int userId);
}
