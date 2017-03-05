package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer>{
	
	//Select an 
	@Query("select f from Follow f where followed_id = ?1 and follower_id = ?2")
	Follow existsFollowOfFollowerAndFollowed(int followedId1,int followerId2);
	
}
