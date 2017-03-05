package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.Attend;
import domain.Comment;
import domain.Contest;
import domain.Folder;
import domain.Follow;
import domain.Qualification;
import domain.Qualified;
import domain.Recipe;
import domain.SocialIdentity;
import domain.User;

@Service
@Transactional
public class UserService {
	
	
	//Managed Repository-----------------------------
	
	@Autowired
	private UserRepository userRepository;
	
	//Supporting services-----------------------------
	
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private QualifiedService qualifiedService;
	@Autowired
	private LoginService loginService;

	
		
	//Constructors------------------------------------
	
	public UserService(){
		super();
	}
	
	//Simple CRUD methods----------------------------
	
	public User create() {
		User result;
		
		result = new User();

		result.setAttends(new ArrayList<Attend>());
		result.setComments(new ArrayList<Comment>());
		result.setFolders(new ArrayList<Folder>());
		result.setFolloweds(new ArrayList<Follow>());
		result.setFollowers(new ArrayList<Follow>());
		result.setQualifications(new ArrayList<Qualification>());
		result.setRecipes(new ArrayList<Recipe>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
	
		return result;
	}

	public Collection<User> findAll() {
		Collection<User> result;

		result = userRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public User findOne(int userId) {
		User result;

		result = userRepository.findOne(userId);
		Assert.notNull(result);

		return result;
	}

	public User save(User user) {
		Assert.notNull(user,"El usuario no puede ser nulo");
		Assert.hasText(user.getName(),"El nombre del usuario no puede ser nulo ni estar vacío");
		Assert.hasText(user.getSurname(),"Los apellidos del usuario no pueden ser nulos ni estar vacíos");
		Assert.hasText(user.getEmail(),"El email del usuario no puede ser nulo ni estar vacío");
	
		User result;

		result = userRepository.save(user);
		
		return result;
	}

	public void delete(User user) {
		Assert.notNull(user,"El usuario no puede ser nulo");
		Assert.isTrue(user.getId() != 0,"El usuario debe estar en la base de datos");

		userRepository.delete(user);
	}
	
	
	//Other bussiness methods------------------------
	
	
	public User findByPrincipal() {
		User result;
		UserAccount userAccount;
	
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	
	public User findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		User result;

		result = userRepository.findByUserAccountId(userAccount.getId());		

		return result;
	}

	@SuppressWarnings("static-access")
	public Qualified registerRecipeForContest(Recipe recipe, Contest contest) {
		Assert.notNull(recipe);
		Assert.notNull(contest);
		Assert.isTrue(recipe.getId() != 0);
		Assert.isTrue(contest.getId() != 0);
		Assert.isTrue(recipeService.isValidToQualify(recipe));
		Qualified qualified;
		Qualified qualifiedSaved;
		Date currentMoment;
		
		Assert.isTrue(recipe.getUser().getUserAccount().equals(loginService.getPrincipal()));

		
		
		contest = contestService.findOne(contest.getId());
		Assert.notNull(contest);
		
		recipe = recipeService.findOne(contest.getId());
		Assert.notNull(recipe);
		recipeService.isValidToQualify(recipe);
		
		currentMoment = new Date(System.currentTimeMillis());
		Assert.isTrue(contest.getOpeningTime().before(currentMoment));
		Assert.isTrue(contest.getClosingTime().after(currentMoment));
		
		qualified = qualifiedService.findByContestAndRecipe(contest, recipe);
		Assert.isNull(qualified);
		
		Recipe copyOfRecipe =recipeService.createCopyOfRecipe(recipe);	
		Recipe copyOfRecipeSaved = recipeService.save(copyOfRecipe);
		qualified = qualifiedService.create(contest,copyOfRecipeSaved,false);
		//qualified.setContest(contest);
		//qualified.setRecipe(copyOfRecipeSaved);
		qualifiedSaved =qualifiedService.save(qualified);
		
				
		
		return qualifiedSaved;
	}
	
	public int findMinRecipesForUsers(){
		int result = userRepository.findMinRecipesForUsers();
		return result;
	}
	
	public int findAvgRecipesForUsers(){
		int result = userRepository.findAvgRecipesForUsers();
		return result;
	}
	
	public int findMaxRecipesForUsers(){
		int result = userRepository.findMaxRecipesForUsers();
		return result;
	}
	
	public Collection<User> findUsersWithMoreRecipes(){
		Collection<User> result = userRepository.findUsersWithMoreRecipes();
		return result;
	}
	
	public Collection<User> findUsersForPoplarityDesc(){
		Collection<User> result = userRepository.findUsersForPopularityDesc();
		return result;
	}
	
	public Collection<User> findUsersForPopularityOfTheirRecipes(){
		Collection<User> result = userRepository.findUsersForPopularityOfTherRecipes();
		return result;
	}
	
	public Collection<User> searchForUser(String searchTerm){
		Collection<User> result = userRepository.searchForUser(searchTerm);
		return result;
	}
	public void newUser(ActorForm actorForm){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		UserAccount userAccount = new UserAccount();
		userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
		userAccount.setUsername(actorForm.getUsername());
		
		User user = create();
		user.setName(actorForm.getName());
		user.setSurname(actorForm.getSurname());
		user.setAddress(actorForm.getAddress());
		user.setEmail(actorForm.getEmail());
		user.setPhone(actorForm.getPhone());
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(actorForm.getTypeOfActor());
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		user.setUserAccount(userAccount);
		folderService.createBasicsFolders(user);
		save(user);
		
	}
//	public Collection<Recipe> getWinnerRecipes(Collection<Recipe> recipes){
//	int index = 0;
//	int index2 = 0;
//	Collection<Recipe> result = new LinkedList<Recipe>();
//	Recipe arrayRecipes[]=new Recipe[recipes.size()];
//	arrayRecipes=recipes.toArray(arrayRecipes);
//	
//	
//	int score=0;
//	for(;index2<3&&index<3;index2++){
//		score=arrayRecipes[index].getScore();
//		for(;index<=arrayRecipes.length;index++){
//			if(arrayRecipes[index].getScore()==score){
//				result.add(arrayRecipes[index]);
//			}
//		}
//		index++;
//	}
//	return result;
//}
}
