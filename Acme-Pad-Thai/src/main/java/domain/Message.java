package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {
	//Attributes------------------------------
	private String sender;
	private String recipient;
	private Date sendingMoment;
	private String subject;
	private String body;
	private String priority;
	
	@NotBlank
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	@NotBlank
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendingMoment() {
		return sendingMoment;
	}

	public void setSendingMoment(Date sendingMoment) {
		this.sendingMoment = sendingMoment;
	}
	@NotBlank
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	@NotBlank
	@Pattern(regexp ="^HIGH$|^NEUTRAL$|^LOW$")
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	//Relationships-------------------------
	private Folder folder;
	@NotNull
	@ManyToOne(optional=false)
	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

}
