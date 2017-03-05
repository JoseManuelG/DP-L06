package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;
@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity{
	
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String address;

	
//attributes------------
	@NotBlank
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Pattern(regexp = "(^$)|(^\\+[1-9][0-9][0-9]|^\\+[1-9][0-9]|^\\+[1-9])?(\\([0][0][1-9]\\)|\\([0][1-9][0-9]\\)|\\([1-9][0-9][0-9]\\))?([A-Z0-9a-z][- ]?){2}([A-Z0-9a-z][- ]?)+([A-Z0-9a-z])$")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


	//relationships-------------------
	private Collection<Folder> folders;
	private Collection<SocialIdentity> socialIdentities;
	private Collection<Attend> attends;
	private UserAccount userAccount;
	
	@OneToMany(mappedBy="actor",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	public Collection<SocialIdentity> getSocialIdentities() {
		return socialIdentities;
	}

	public void setSocialIdentities(Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}
	@OneToMany(mappedBy="actor",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	public Collection<Attend> getAttends() {
		return attends;
	}
	
	public void setAttends(Collection<Attend> attends) {
		this.attends = attends;
	}
	@OneToMany(mappedBy="actor",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	public Collection<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<Folder> folders) {
		this.folders = folders;
	}
	

	@NotNull
	@Valid	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
}
