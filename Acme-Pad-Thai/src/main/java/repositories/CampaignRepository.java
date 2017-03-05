package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Campaign;
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
	//Returns a list of active campaigns
	@Query("select c from Campaign c where c.dateOfStart < CURRENT_DATE AND CURRENT_DATE < c.dateOfEnd")
	public Collection<Campaign> activeCampaigns();
	
	//Returns sponsor campaings
	@Query("select c from Campaign c where c.sponsor.id=?1")
	public Collection<Campaign> sponsorCampaigns(int sponsorId);
}
