package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class Attend extends DomainEntity{
	//attributes------
	//relationships-------------
	private Actor actor;
	private MasterClass masterClass;

	@ManyToOne(optional=false)
	@NotNull
	public Actor getActor() {
		return actor;
	}
	
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	@ManyToOne(optional=false)
	@NotNull
	public MasterClass getMasterClass() {
		return masterClass;
	}
	public void setMasterClass(MasterClass masterClass) {
		this.masterClass = masterClass;
	}
	
}
