package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Contest;
import domain.Recipe;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Integer> {
	
	//The minimum number of recipes that have qualified for a contest
	@Query("select min(c.qualifieds.size) from Contest c")
	public int findMinRecipesForContests();
	
	//The average number of recipes that have qualified for a contest
	@Query("select avg(c.qualifieds.size) from Contest c")
	public double findAvgRecipesForContests();
	
	//The maximum number of recipes that have qualified for a contest
	@Query("select max(c.qualifieds.size) from Contest c")
	public int findMaxRecipesForContests();
	
	//The contest/s for which more recipes has/have qualified.
	@Query("select c from Contest c where c.qualifieds.size = (select max(cc.qualifieds.size) from Contest cc)")
	public Collection<Contest> findContestsWithMoreRecipes();

	//The maximum number of recipes of all the contests
	@Query("select max(c.qualifieds.size) from Contest c ")
	public Integer findMaxRecipesForContest();

	//The minimum number of recipes of all the contests
	@Query("select c from Contest c where qualifieds.size=?1")
	public Collection<Contest> findContestBySize(Integer aux);

	//The recipes which participates in a contest
	@Query("select cq.recipe from Contest c join c.qualifieds cq where c.id=?1")
	public Collection<Recipe> findRecipesByContestId(int id);

	
	

}
