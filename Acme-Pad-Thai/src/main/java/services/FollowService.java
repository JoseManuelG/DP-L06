package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FollowRepository;
import security.LoginService;
import domain.Actor;
import domain.Customer;
import domain.Follow;

@Service
@Transactional
public class FollowService {

	//Managed Repository --------------------------------------
		@Autowired
		private FollowRepository followRepository;
		
		//Supporting Services --------------------------------------
		//Simple CRUD methods --------------------------------------

		public Follow create() {
			Follow result;
			result = new Follow();
			return result;
		}
		public Follow create(Customer follower,Customer followed) {
			Follow result;
			result = new Follow();
			result.setFollowed(followed);
			result.setFollower(follower);
			return result;		
			
		}
		public Collection<Follow> findAll() {
			Collection<Follow> result;
			Assert.notNull(followRepository);
			result = followRepository.findAll();		
			Assert.notNull(result);
			
			return result;
		}

		public Follow findOne(int followId) {
			Follow result;
			
			result = followRepository.findOne(followId);		

			return result;
		}
		
		public Follow save(Follow follow) {
			Assert.notNull(follow);
			Follow result;
			Assert.notNull(follow.getFollowed());
			Assert.notNull(follow.getFollower());
			Assert.isTrue(LoginService.getPrincipal().equals(follow.getFollower().getUserAccount()));
			Assert.isTrue(!existAttendOfActorAndMasterClass(follow.getFollowed(), follow.getFollower()));
			Assert.isTrue(!follow.getFollower().equals(follow.getFollowed()));
			result = followRepository.save(follow);
			return result;
		}	
		
		public void delete(Follow follow) {
			Assert.notNull(follow);
			Assert.isTrue(follow.getId() != 0);
			Assert.isTrue(LoginService.getPrincipal().equals(follow.getFollower().getUserAccount()));
			Assert.isTrue(followRepository.exists(follow.getId()));
			followRepository.delete(follow);
		}
		//other business methods --------------------------------------
		boolean existAttendOfActorAndMasterClass(Actor followed,Actor follower){
			Follow follow = followRepository.existsFollowOfFollowerAndFollowed(followed.getId(), follower.getId());
			boolean result= follow!=null;
			return result;
		}
		
		public Follow findFollowByFollowedAndFollower(Customer followed, Customer follower){
			Follow result = followRepository.existsFollowOfFollowerAndFollowed(followed.getId(),follower.getId());
			return result;
		}

}
