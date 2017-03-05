package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Folder;

@Repository
public interface ActorRepository extends JpaRepository<Administrator,Integer>{
	//Selecciona los folders de un actor
	@Query("select a.folders from Actor a where a.id = ?1")
	public Collection<Folder> findActorFoldersById(int id);

}
