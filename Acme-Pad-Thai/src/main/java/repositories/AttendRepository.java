package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Attend;

@Repository
public interface AttendRepository extends JpaRepository<Attend,Integer>{
	//Select the attends for a masterclass
	@Query("select a from  Attend a where MasterClass_id = ?1")
	Collection<Attend> existAttendOfMasterClass(int masterClassId);
	
	//Select the attend for a masterclass and an actor
	@Query("select a from Attend a where actor_id=?1 and masterClass_id=?2")
	Attend existAttendOfActorAndMasterClass(int actorId,int masterClassId);

	
}
