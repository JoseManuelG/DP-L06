package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ActorForm {
	
	private String typeOfActor;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String address;
	private String companyName;
	

//Constructor
	public ActorForm(){
		super();
	}
//attributes------------
	@Pattern(regexp ="^SPONSOR$|^USER$|^NUTRITIONIST$|^COOK$")
	public String getTypeOfActor() {
		return typeOfActor;
	}

	public void setTypeOfActor(String typeOfActor) {
		this.typeOfActor = typeOfActor;
	}
	@NotBlank
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@NotBlank
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	




	
}
