package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;
@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {
	
	
	//The user for an userAccountId
	@Query("select s from Sponsor s where s.userAccount.id = ?1")
	public Sponsor findBySponsorAccountId(int userAccountId);
		
	//Return the minimum number of campaigns managed by a sponsor
	@Query("select min(s.campaigns.size) from Sponsor s")
	public Integer minOfCampaignperSponsor();
	
	//Return the average number of campaigns managed by a sponsor
	@Query("select avg(s.campaigns.size) from Sponsor s")
	public Double avgOfCampaignperSponsor();
	
	//Return the maximum number of campaigns managed by a sponsor
	@Query("select max(s.campaigns.size) from Sponsor s")
	public Integer maxOfCampaignperSponsor();
	
	//Return a list with the company names organized by the number of campaigns managed for the sponsors of that companies
	@Query("select s.companyName from Sponsor s order by s.campaigns.size DESC")
	public Collection<String> listOfCompaniesByCampaignsOrganiced();
	
	//Return a list with the company names organized by the number of bills managed for the sponsors of that companies
	@Query("select s.companyName from Sponsor s order by s.bills.size DESC")
	public Collection<String> listOfCompaniesByMonthlyBills();
	
	//Returns the average cost of all the payed bills
	@Query("select avg(b.cost) from Bill b where b.dateOfPay is not null")
	public Double avgOfPaidBills();
	
	//Returns the standard deviation of the cost of all the payed bills
	@Query("select stddev(b.cost) from Bill b where b.dateOfPay is not null")
	public Double sttdevOfPaidBills();
	
	//Returns the average cost of all the unpayed bills
	@Query("select avg(c.cost) from Bill c where c.dateOfPay is null")
	public Double avgOfUnpaidBills();
	
	//Returns the average cost of all the unpayed bills
	@Query("select stddev(c.cost) from Bill c where c.dateOfPay is null")
	public Double sttdevOfUnpaidBills();
	
	//Returns a list of sponsor who have not managed a campaign in 90 days
	@Query("select ss from Sponsor ss where ss not in (select s from Sponsor s, IN(s.campaigns) b where not b.dateOfEnd < CURRENT_DATE-90)")
	public Collection<Sponsor> sponsorsWithNoCampaignOnThreeMonths();
	
	//Returns a list of company names who pay less than the average spent by all the companies
	@Query("select s.companyName from Sponsor s where (select avg(b.cost) from Bill b) > (select avg(t.cost) from Bill t where t.sponsor=s)")
	public Collection<String> listOfCompaniesThatSpentLessThanTheAvg();
	
	//The companies that have spent at least 90% the maximum amount of money that a company has spent on a campaign
	@Query("select s.companyName from Sponsor s where (select max(b.cost) from Bill b where b.sponsor = s) > 0.9*(select max(bb.cost) from Bill bb)")
	public Collection<String> listOfCompaniesThatSpentMoreThan90();

	//Return a collection of sponsors using a single keyword that must appear verbatim in his or her name
	@Query("select s from Sponsor s where s.name=?1")
	public Collection<Sponsor> searchForSponsor(String searchTerm);

}
