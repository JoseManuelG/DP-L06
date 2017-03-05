package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Banner;
@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
	//Returns a list of active banners
	@Query("select b from Banner b Where b.campaign.dateOfStart < CURRENT_DATE and  b.campaign.dateOfEnd > CURRENT_DATE")
	public Collection<Banner> activeBanners();
	
	//Returns a list of active banners
	@Query("select b from Banner b Where b.campaign.dateOfStart < CURRENT_DATE and  b.campaign.dateOfEnd > CURRENT_DATE and b.campaign.starCampaign = true")
	public Collection<Banner> activeStarBanners();
}
