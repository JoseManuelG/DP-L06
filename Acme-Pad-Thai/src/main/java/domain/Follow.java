package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Follow extends DomainEntity{
//Attributes--------------------------
//Relationships-----------------------
private Customer follower;//el que sigue
private Customer followed;//el seguido
@Valid
@NotNull
@ManyToOne(optional=false)
public Customer getFollower() {
	return follower;
}
public void setFollower(Customer follower) {
	this.follower = follower;
}
@Valid
@NotNull
@ManyToOne(optional=false)
public Customer getFollowed() {
	return followed;
}
public void setFollowed(Customer followed) {
	this.followed = followed;
}

}
