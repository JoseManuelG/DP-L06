package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {
	//Attributes----------------------------
	private String companyName;
	
	@NotBlank
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	//Relationships-------------------------
	private Collection<CreditCard> creditCards;
	private Collection<Bill> bills;
	private Collection<Campaign> campaigns;
	
	@OneToMany(cascade=CascadeType.ALL)
	public Collection<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
	@OneToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE}, mappedBy="sponsor")
	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="sponsor")
	public Collection<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(Collection<Campaign> campaigns) {
		this.campaigns = campaigns;
	}
}
