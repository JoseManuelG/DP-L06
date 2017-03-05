package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {
	
	//Attributes-----------------------------------
	private Collection<String> keyWords;
	private double bannerCost;
	private Collection<String> basicFolders;
	@NotNull
	@ElementCollection
	public Collection<String> getKeyWords() {
		return keyWords;
	}

	public   void setKeyWords(Collection<String> keyWords2) {
		keyWords = keyWords2;
	}
	@NotNull
	public  double getBannerCost(){
		return bannerCost;
	}
	public  void setBannerCost(double bannerCost2){
		bannerCost=bannerCost2;
	}
	@NotNull
	@ElementCollection
	public  Collection<String> getBasicFolders() {
		return basicFolders;
	}

	public void setBasicFolders(Collection<String> basicFolders2) {
		basicFolders = basicFolders2;
	}
	
	
	
	

}
