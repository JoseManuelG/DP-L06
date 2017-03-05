/* AdministratorController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import services.CookService;
import services.MasterClassService;
import services.RecipeService;
import services.SponsorService;
import services.UserService;
import domain.Contest;
import domain.Cook;
import domain.Sponsor;
import domain.User;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Services ----------------------------------------------------------------
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContestService contestService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}
	
	// dashBoard ---------------------------------------------------------------
	
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;
		
		//The minimum, the average, and the maximum number of recipes per user.
		Integer maxRecipesForUsers= userService.findMaxRecipesForUsers();
		Integer minRecipesForUsers= userService.findMinRecipesForUsers();
		Double avgRecipesForUsers= userService.findAvgRecipesForUsers();
		//The user/s who has/have authored more recipes.
		Collection<User> usersWithMoreRecipes= userService.findUsersWithMoreRecipes();
		//The minimum, the average, and the maximum number of recipes that have qualified for a contest.
		Double avgRecipesForContests= contestService.findAvgRecipesForContests();
		Integer maxRecipesForContests= contestService.findMaxRecipesForContests();
		Integer minRecipesForContests= contestService.findMinRecipesForContests();
		//The contest/s for which more recipes has/have qualified.
		Collection<Contest> contestsWithMoreRecipes= contestService.findContestsWithMoreRecipes();
		//The average and the standard deviation of number of steps per recipe.
		Double avgOfStepsForRecipes= recipeService.findAvgOfStepsForRecipes();
		Double stdDevOfStepsForRecipes= recipeService.findStdDevOfStepsForRecipes();
		//The average and the standard deviation of number of ingredients per recipe.
		Double avgOfIngredientsPerRecipe=recipeService.findAvgOfIngredientsPerRecipe();
		Double stdDevOfIngredientsPerRecipe=recipeService.findStdDevOfIngredientsPerRecipe();
		//A listing of users in descending order of popularity.
		Collection<User> usersForPoplarityDesc=userService.findUsersForPoplarityDesc();
		//A listing of users in descending order regarding the average number of likes and dislikes that their recipes get.
		Collection<User> usersForPopularityOfTheirRecipes= userService.findUsersForPopularityOfTheirRecipes();
		
		
		//The minimum, the average, and the maximum number of campaigns per sponsor.
		Double avgOfCampaignperSponsor=sponsorService.avgOfCampaignperSponsor();
		Integer maxOfCampaignperSponsor=sponsorService.maxOfCampaignperSponsor();
		Integer minOfCampaignperSponsor=sponsorService.minOfCampaignperSponsor();
		//The minimum, the average, and the maximum number of active campaigns per sponsor.
		Double avgOfActiveCampaignperSponsor=sponsorService.avgOfActiveCampaignperSponsor();
		Integer maxOfActiveCampaignperSponsor=sponsorService.maxOfActiveCampaignperSponsor();
		Integer minOfActiveCampaignperSponsor=sponsorService.minOfActiveCampaignperSponsor();
		//The ranking of companies according the number of campaigns that they have organised via their sponsors.
		Collection<String> listOfCompaniesByCampaignsOrganiced= sponsorService.listOfCompaniesByCampaignsOrganiced();
		//The ranking of companies according their monthly bills.
		Collection<String> listOfCompaniesByMonthlyBills=sponsorService.listOfCompaniesByMonthlyBills();
		//The average and the standard deviation of paid and unpaid monthly bills.
		Double sttdevOfPaidBills=sponsorService.sttdevOfPaidBills();
		Double sttdevOfUnpaidBills=sponsorService.sttdevOfUnpaidBills();
		Double avgOfPaidBills=sponsorService.avgOfPaidBills();
		Double avgOfUnpaidBills=sponsorService.avgOfUnpaidBills();
		//The sponsors who have not managed a campaign for the last three months.
		Collection<Sponsor> sponsorsWithNoCampaignOnThreeMonths=sponsorService.sponsorsWithNoCampaignOnThreeMonths();
		//The companies that have spent less than the average in their campaigns.
		Collection<String> listOfCompaniesThatSpentLessThanTheAvg=sponsorService.listOfCompaniesThatSpentLessThanTheAvg();
		//The companies that have spent at least 90% the maximum amount of money that a company has spent on a campaign.
		Collection<String> listOfCompaniesThatSpentMoreThan90=sponsorService.listOfCompaniesThatSpentMoreThan90();
		
		
		//The minimum, the maximum, the average, and the standard deviation of the number of master classes per cook.
		Integer maxOfMasterClassPerCook= cookService.maxOfMasterClassPerCook();
		Integer minOfMasterClassPerCook=cookService.minOfMasterClassPerCook();
		Double avgOfMasterClassPerCook=cookService.avgOfMasterClassPerCook();
		Double stddevOfMasterClassPerCook=cookService.stddevOfMasterClassPerCook();
		//The average number of learning materials per master class, grouped by kind of learning material.
		Double avgOfLearningMaterialPerMasterClassByVideo=masterClassService.avgOfLearningMaterialPerMasterClassByKindLearningMaterial("video");
		Double avgOfLearningMaterialPerMasterClassByText=masterClassService.avgOfLearningMaterialPerMasterClassByKindLearningMaterial("text");
		Double avgOfLearningMaterialPerMasterClassByPresentation=masterClassService.avgOfLearningMaterialPerMasterClassByKindLearningMaterial("presentation");
		//The number of master classes that have been promoted.
		Integer numberOfMasterClassesPromoted=masterClassService.numberOfMasterClassesPromoted();
		//The listing of cooks, sorted according to the number of master classes that have been promoted.
		Collection<Cook> listOfCooksByPromotedMasterClass=cookService.listOfCooksByPromotedMasterClass();
		//The average number of promoted and demoted master classes per cook.
		Collection<Double> avgOfDemotedClass=cookService.avgOfDemotedClass();
		Collection<Cook> cooksOfDemotedClass=cookService.cooksOfDemotedClass();
		Collection<Cook> cooksOfPromotedClass=cookService.listOfCooksByPromotedMasterClass();
		Collection<Double> avgOfPromotedClass=cookService.avgOfPromotedClass();
		
		
		result = new ModelAndView("administrator/dashboard");
		result.addObject("cooksOfDemotedClass",cooksOfDemotedClass);
		result.addObject("cooksOfPromotedClass",cooksOfPromotedClass);
		result.addObject("avgOfPromotedClass",avgOfPromotedClass);
		result.addObject("avgOfDemotedClass",avgOfDemotedClass);
		result.addObject("listOfCooksByPromotedMasterClass",listOfCooksByPromotedMasterClass);
		result.addObject("numberOfMasterClassesPromoted",numberOfMasterClassesPromoted);
		result.addObject("avgOfLearningMaterialPerMasterClassByPresentation",avgOfLearningMaterialPerMasterClassByPresentation);
		result.addObject("avgOfLearningMaterialPerMasterClassByText",avgOfLearningMaterialPerMasterClassByText);
		result.addObject("avgOfLearningMaterialPerMasterClassByVideo",avgOfLearningMaterialPerMasterClassByVideo);
		result.addObject("stddevOfMasterClassPerCook",stddevOfMasterClassPerCook);
		result.addObject("avgOfMasterClassPerCook",avgOfMasterClassPerCook);
		result.addObject("minOfMasterClassPerCook",minOfMasterClassPerCook);
		result.addObject("maxOfMasterClassPerCook",maxOfMasterClassPerCook);
		result.addObject("listOfCompaniesThatSpentMoreThan90",listOfCompaniesThatSpentMoreThan90);
		result.addObject("listOfCompaniesThatSpentLessThanTheAvg",listOfCompaniesThatSpentLessThanTheAvg);
		result.addObject("sponsorsWithNoCampaignOnThreeMonths",sponsorsWithNoCampaignOnThreeMonths);
		result.addObject("avgOfUnpaidBills",avgOfUnpaidBills);
		result.addObject("avgOfPaidBills",avgOfPaidBills);
		result.addObject("sttdevOfUnpaidBills",sttdevOfUnpaidBills);
		result.addObject("sttdevOfPaidBills",sttdevOfPaidBills);
		result.addObject("listOfCompaniesByMonthlyBills",listOfCompaniesByMonthlyBills);
		result.addObject("listOfCompaniesByCampaignsOrganiced",listOfCompaniesByCampaignsOrganiced);
		result.addObject("minOfActiveCampaignperSponsor",minOfActiveCampaignperSponsor);
		result.addObject("maxOfActiveCampaignperSponsor",maxOfActiveCampaignperSponsor);
		result.addObject("avgOfActiveCampaignperSponsor",avgOfActiveCampaignperSponsor);
		result.addObject("minOfCampaignperSponsor",minOfCampaignperSponsor);
		result.addObject("maxOfCampaignperSponsor",maxOfCampaignperSponsor);
		result.addObject("avgOfCampaignperSponsor",avgOfCampaignperSponsor);
		result.addObject("usersForPopularityOfTheirRecipes",usersForPopularityOfTheirRecipes);
		result.addObject("usersForPoplarityDesc",usersForPoplarityDesc);
		result.addObject("stdDevOfIngredientsPerRecipe",stdDevOfIngredientsPerRecipe);
		result.addObject("avgOfIngredientsPerRecipe",avgOfIngredientsPerRecipe);
		result.addObject("stdDevOfStepsForRecipes",stdDevOfStepsForRecipes);
		result.addObject("avgOfStepsForRecipes",avgOfStepsForRecipes);
		result.addObject("contestsWithMoreRecipes",contestsWithMoreRecipes);
		result.addObject("minRecipesForContests",minRecipesForContests);
		result.addObject("maxRecipesForContests",maxRecipesForContests);
		result.addObject("avgRecipesForContests",avgRecipesForContests);
		result.addObject("usersWithMoreRecipes",usersWithMoreRecipes);
		result.addObject("avgRecipesForUsers",avgRecipesForUsers);
		result.addObject("minRecipesForUsers",minRecipesForUsers);
		result.addObject("maxRecipesForUsers",maxRecipesForUsers);
		
		return result;
	}
	
}