package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Cook extends Actor{
//attributes------------------
//relationships---------------
	private Collection<MasterClass> masterClasses;
	@OneToMany(mappedBy="cook",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	public Collection<MasterClass> getMasterClasses() {
		return masterClasses;
	}

	public void setMasterClasses(Collection<MasterClass> masterClasses) {
		this.masterClasses = masterClasses;
	}
	
}
