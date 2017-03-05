package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class LearningMaterial extends DomainEntity {
	//attributes--------------
	private String summary;
	private String title;
	private String type;
	private String data;
	private Collection<String> attachments;
	@NotNull
	@NotBlank
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@NotNull
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@NotNull
	@NotBlank
	@Pattern(regexp ="^text$|^presentation$|^video$")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@NotBlank
	@NotNull
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@NotNull
	@ElementCollection
	public Collection<String> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
	}

	
	//Relationships---------------
	
	private MasterClass masterClass;
	@ManyToOne(optional=false)
	@NotNull
	public MasterClass getMasterClass() {
		return masterClass;
	}
	public void setMasterClass(MasterClass masterClass) {
		this.masterClass = masterClass;
	}
	
	
	
}
