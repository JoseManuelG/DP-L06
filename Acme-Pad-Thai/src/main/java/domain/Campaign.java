package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Entity
@Access(AccessType.PROPERTY)
public class Campaign extends DomainEntity {
	//Attributes----------------------------
	private Date dateOfStart;
	private Date dateOfEnd;
	private boolean starCampaign;
	private int numOfDisplays;
	private int maxNumOfDisplays;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getDateOfStart() {
		return dateOfStart;
	}
	public void setDateOfStart(Date dateOfStart) {
		this.dateOfStart = dateOfStart;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getDateOfEnd() {
		return dateOfEnd;
	}
	public void setDateOfEnd(Date dateOfEnd) {
		this.dateOfEnd = dateOfEnd;
	}
	public boolean getStarCampaign() {
		return starCampaign;
	}
	public void setStarCampaign(boolean starCampaign) {
		this.starCampaign = starCampaign;
	}
	public int getnumOfDisplays() {
		return numOfDisplays;
	}
	public void setnumOfDisplays(int numOfDisplays) {
		this.numOfDisplays = numOfDisplays;
	}
	@Min(1)
	public int getMaxNumOfDisplays() {
		return maxNumOfDisplays;
	}
	public void setMaxNumOfDisplays(int maxNumOfDisplays) {
		this.maxNumOfDisplays = maxNumOfDisplays;
	}

	//Relationships----------------------------------
	private Sponsor sponsor;
	private Collection<Banner> bannerList;

	
	@NotNull
	@ManyToOne(optional=false)
	public Sponsor getSponsor() {
		return sponsor;
	}
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="campaign")
	public Collection<Banner> getBannerList() {
		return bannerList;
	}
	public void setBannerList(Collection<Banner> bannerList) {
		this.bannerList = bannerList;
	}


	
}
