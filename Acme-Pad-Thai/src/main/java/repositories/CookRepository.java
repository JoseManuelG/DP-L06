package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cook;

@Repository
public interface CookRepository extends JpaRepository<Cook,Integer>{

	//Select the minimum number of masterclasses per cook
	@Query("select min(c.masterClasses.size) from Cook c")
	public Integer minOfMasterClassPerCook();
	
	//Select the average number of masterclasses per cook
	@Query("select avg(c.masterClasses.size) from Cook c")
	public Double avgOfMasterClassPerCook();
	
	//Select the maximum number of masterclasses per cook
	@Query("select max(c.masterClasses.size) from Cook c")
	public Integer maxOfMasterClassPerCook();
	
	//Select the stddev number of masterclasses per cook
	@Query("select stddev(c.masterClasses.size) from Cook c")
	public Double stddevOfMasterClassPerCook();
	
	//Select the list of cooks ordered by their amount of cook
	@Query("select r.cook from MasterClass r group by r.cook order by count(r.promoted) ASC")
	public Collection<Cook> listOfCooksByPromotedMasterClass();
	
	//The average number of promoted masterclasses by cook
	@Query("select avg(m.promoted) from Cook c JOIN c.masterClasses m group by c order by count(m.promoted) ASC")
	public Collection<Double> avgOfPromotedClass();
	
	//The average number of demoted masterclasses by cook
	@Query("select 1-avg(m.promoted) from Cook c JOIN c.masterClasses m group by c")
	public Collection<Double> avgOfDemotedClass();
	
	//Cook from The average number of demoted/promoted masterclasses
	@Query("select r.cook from MasterClass r group by r.cook order by count(r.promoted) DESC")
	public Collection<Cook> listOfCooksByDemotedMasterClass();
			
	@Query("select u from Cook u where u.userAccount.id = ?1")
	public Cook findByUserAccountId(int id);	
		
	
	//Return a collection of nutritionists using a single keyword that must appear verbatim in his or her name
	@Query("select c from Cook c where c.name=?1")
	public Collection<Cook> searchForCook(String searchTerm);
}
