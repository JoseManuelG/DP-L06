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
public abstract class SearchAux extends DomainEntity{
	
	private String toSearch
	
//attributes------------
	
	public String getToSearch() {
		return toSearch;
	}
	
	public void setToSearch(String toSearch) {
		this.toSearch = toSearch;
	}
	
	
}
