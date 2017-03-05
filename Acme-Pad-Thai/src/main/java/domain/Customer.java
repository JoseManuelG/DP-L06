package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
@Entity
@Access(AccessType.PROPERTY)
public abstract class Customer extends Actor {
	//Attributes---------
	//Relationships-----
	private Collection<Qualification> qualifications;
	private Collection<Comment> comments;
	private Collection<Follow> followers;//Actores que siguen a este actor
	private Collection<Follow> followeds;//Actores a los que este actor sigue;
	
	@OneToMany(mappedBy="customer")
	public Collection<Qualification>getQualifications() {
		return qualifications;
	}

	public void setQualifications(Collection<Qualification> qualifications) {
		this.qualifications = qualifications;
	}
	@OneToMany(mappedBy="customer")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	@OneToMany(mappedBy="followed",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	public Collection<Follow> getFollowers() {
		return followers;
	}

	public void setFollowers(Collection<Follow> followers) {
		this.followers = followers;
	}
	@OneToMany(mappedBy="follower",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	public Collection<Follow> getFolloweds() {
		return followeds;
	}

	public void setFolloweds(Collection<Follow> followed) {
		this.followeds = followed;
	}
	
	
}
