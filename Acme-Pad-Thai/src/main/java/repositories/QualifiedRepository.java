package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Qualified;
import domain.Recipe;

@Repository
public interface QualifiedRepository extends JpaRepository<Qualified,Integer>{
	
	//Select the qualified for a contest, with a contest id
	@Query("select s from Qualified s where s.contest.id = ?1")
	Collection<Qualified> findByContestId(int id);
	
	//Select the qualified for a contest, with a recipe id
	@Query("select s from Qualified s where s.recipe.id = ?1")
	Collection<Qualified> findByRecipeId(int id);

	//Select the qualified for a contest, with a contest id and a recipe id. it must be unique
	@Query("select q from Qualified q where q.recipe.id=?1 and q.contest.id=?2 ")
	public Qualified findQualifiedByRecipeAndContest(int recipeId, int contestId);

	
	@Query("select q.recipe from Contest c join c.qualifieds q where q.recipe.parentTicker='?1' and c.id=?2")
	public Recipe findQualifiedByRecipeParentTickerAndContest(String parentTicker, int contestId);

	
	
	
}
