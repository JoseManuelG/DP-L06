package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
public class Contest extends DomainEntity{
	//Attributes-----------------------
	private String tittle;
	private Date openingTime;
	private Date closingTime;
	
	
	@NotBlank
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	@NotNull
	public Date getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}
	@NotNull
	public Date getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}
	//Relationships---------------------
	private Collection<Qualified> qualifieds;

	
	@NotNull
	@OneToMany(mappedBy = "contest")
	public Collection<Qualified> getQualifieds() {
		return qualifieds;
	}
	public void setQualifieds(Collection<Qualified> qualifieds) {
		this.qualifieds = qualifieds;
	}
	

}
