package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity{
	//Attributes---------
	private String educationSection;
	private String experienceSection;
	private String hobbiesSection;
	private String picture;
	@NotBlank
	public String getEducationSection() {
		return educationSection;
	}
	public void setEducationSection(String educationSection) {
		this.educationSection = educationSection;
	}
	@NotBlank
	public String getExperienceSection() {
		return experienceSection;
	}
	public void setExperienceSection(String experienceSection) {
		this.experienceSection = experienceSection;
	}
	@NotBlank
	public String getHobbiesSection() {
		return hobbiesSection;
	}
	public void setHobbiesSection(String hobbiesSection) {
		this.hobbiesSection = hobbiesSection;
	}
	@NotBlank
	@URL
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
	//Relationships------
	
	private Collection<Endorser> endorsers;
	
	@OneToMany(mappedBy="curriculum",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	public Collection<Endorser> getEndorsers() {
		return endorsers;
	}
	public void setEndorsers(Collection<Endorser> endorsers) {
		this.endorsers = endorsers;
	}
	

}
