package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Contest;
import domain.Qualified;
import domain.Recipe;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class ContestServiceTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//Service under test-------------------------
	@Autowired
	private ContestService contestService;
	
	//Tests----------------------------------------
	@Test
	public void testGetWinners(){
		Contest contest = contestService.findOne(128);
		System.out.println(contest.getQualifieds());
		Collection<Recipe> winners = contestService.getWinners(contest);
		for(Recipe r : winners){
			System.out.println(r.getTicker());
		}
	}
		//Tests de creates----------------------------------------
		@Test
		public void testCreateContest(){
			Contest contest;
			contest = contestService.create();
			Assert.notNull(contest);
			Assert.isNull(contest.getClosingTime());
			Assert.isNull(contest.getOpeningTime());
			Assert.notNull(contest.getQualifieds());
			Assert.isNull(contest.getTittle());
			
		}
		
		
		//Tests de saves----------------------------------------
		@Test
		public void testSaveContest(){
			Contest contest, saved;
			Collection<Contest> contests;
			Collection<Qualified> qualifieds = new ArrayList<Qualified>();
			
			contest =contestService.create();
	        Date closeFutureTime= new Date(System.currentTimeMillis()+1000000000);
	        Date futureTime= new Date(System.currentTimeMillis()+1800000000);
	        contest.setOpeningTime(closeFutureTime);
	        contest.setClosingTime(futureTime);
	        contest.setQualifieds(qualifieds);
	        contest.setTittle("RecipeTittle");
	        
	        
	        saved=contestService.save(contest);
	        contests = contestService.findAll();
	        Assert.isTrue(contests.contains(saved));
			
		}
		
		@Test
		public void testSaveContestObjectNull(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("El concurso a guardar no puede ser nulo");
		Contest contest,saved;
		Collection<Contest> contests;
		contest=null;
		saved=contestService.save(contest);
		contests = contestService.findAll();
		Assert.isTrue(contests.contains(saved));

		exception=ExpectedException.none();
		}
		
//		@Test
//		public void testSaveContestInvalidTittle(){
//			exception.expect(IllegalArgumentException.class);
//			exception.expectMessage("El titulo no debe variar en un concurso en marcha");
//			Contest contest, saved;
//			Collection<Contest> contests;
//			Collection<Qualified> qualifieds = new ArrayList<Qualified>();
//			
//			contest =contestService.create();
//	        Date closeFutureTime= new Date(System.currentTimeMillis()-1000000);
//	        Date futureTime= new Date(System.currentTimeMillis()+18000000);
//	        contest.setOpeningTime(closeFutureTime);
//	        contest.setClosingTime(futureTime);
//	        contest.setQualifieds(qualifieds);
//	        contest.setTittle("RecipeTittle");
//	        
//	        
//	        saved=contestService.save(contest);
//	        contests = contestService.findAll();
//	        Assert.isTrue(contests.contains(saved),"asdas");
//	        
//	        
//	        saved.setTittle("InvalidTittle");
//	        Contest invalid=contestService.save(saved);
//	        
//	        
//	        exception=ExpectedException.none();
//		}
//		
		
		
		//Tests de deletes----------------------------------------
		@Test
		public void testDeleteContest(){
			Contest contest, contestSaved;
			Collection<Contest> contests, contestsPostDelete;
			Collection<Qualified> qualifieds = new ArrayList<Qualified>();
			
			contest =contestService.create();
	        Date closeFutureTime= new Date(System.currentTimeMillis()+1000000000);
	        Date futureTime= new Date(System.currentTimeMillis()+1800000000);
	        contest.setOpeningTime(closeFutureTime);
	        contest.setClosingTime(futureTime);
	        contest.setQualifieds(qualifieds);
	        contest.setTittle("RecipeTittle");
	        
	        
	        contestSaved=contestService.save(contest);
	        contests = contestService.findAll();
	        Assert.isTrue(contests.contains(contestSaved));
	        
	        contestService.delete(contestSaved);
			contestsPostDelete =contestService.findAll();
			Assert.isTrue(!contestsPostDelete.contains(contestSaved));
			
		}
		
		@Test
		public void testDeleteContestnull(){
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("El objeto contest no debe ser nulo");
			Contest contest;
			contest=null;
			contestService.delete(contest);
			exception=ExpectedException.none();
		}
		@Test
		public void testDeleteContestId0(){
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("El objeto constest a borrar debe tener una id valida ");
			Contest contest;
			contest = contestService.create();
			contestService.delete(contest);
			exception=ExpectedException.none();
		}
}