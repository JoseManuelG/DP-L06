package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.StepHint;

@Repository
public interface StepHintRepository extends JpaRepository<StepHint,Integer> {
	
	//Returns all the stephints for a given step
	@Query("select sh from StepHint sh where sh.step.id=?1")
	public Collection<StepHint> findStepHintByStep(int stepId);

}
