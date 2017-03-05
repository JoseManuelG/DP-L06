package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;
import domain.Campaign;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>{
	//Select the unpaid bills
	@Query("select b from Bill b where b.dateOfPay=null")
	public Collection<Bill> UnpaidBills();
	
	//Select bills from a campaign
	@Query("select b from Bill b where b.campaign=?1")
	public Collection<Bill> billsOfCampaign(Campaign campaign);
}
