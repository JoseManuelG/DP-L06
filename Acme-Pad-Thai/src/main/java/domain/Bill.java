package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
public class Bill extends DomainEntity {
	//Attributes--------------------------------------
	private Date dateOfCreation;
	private Date dateOfPay;
	private String description;
	private double cost;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Past
	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	
	
	@Temporal(TemporalType.DATE)
	@Past
	public Date getDateOfPay() {
		return dateOfPay;
	}
	
	public void setDateOfPay(Date dateOfPay) {
		this.dateOfPay = dateOfPay;
	}

	public double getCost() {
		return cost;
	}
	
	public void setCost( double cost) {
		this.cost= cost;
	}
	@Column(length=1000)
	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//Relationships--------------------------------------
	private Sponsor sponsor;
	private Campaign campaign;
	@NotNull
	@ManyToOne(optional=false)
	public Sponsor getSponsor() {
		return sponsor;
	}
	
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	
	@Valid
	@NotNull
	@OneToOne
	public Campaign getCampaign() {
		return campaign;
	}
	
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
}
