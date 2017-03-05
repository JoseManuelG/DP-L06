package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	//The user for an userAccountId
	@Query("select u from User u where u.userAccount.id = ?1")
	public User findByUserAccountId(int userAccountId);
	
	//The minimum of recipes per user
	@Query("select min(u.recipes.size) from User u")
	public int findMinRecipesForUsers();
	
	//The average of recipes per user
	@Query("select avg(u.recipes.size) from User u")
	public int findAvgRecipesForUsers();
	
	//The maximum of recipes per user
	@Query("select max(u.recipes.size) from User u")
	public int findMaxRecipesForUsers();
	
	//The user/s who has/have authored more recipes.
	@Query("select u from User u where u.recipes.size = (select max(uu.recipes.size) from User uu)")
	public Collection<User> findUsersWithMoreRecipes();
	
	//A listing of users in descending order of popularity.
	@Query("select s from User s order by s.followers.size DESC")
	public Collection<User> findUsersForPopularityDesc();
	
	//A listing of users in descending order regarding the average number of likes and dislikes that their recipes get.
	@Query("select r.user from Recipe r LEFT JOIN r.qualifications q group by r.user order by avg(q.opinion) DESC")
	public Collection<User> findUsersForPopularityOfTherRecipes();
	
	//Return a collection of users using a single keyword that must appear verbatim in his or her name
	@Query("select u from User u where u.name=?1")
	public Collection<User> searchForUser(String searchTerm);

	

}
