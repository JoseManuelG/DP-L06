package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QualificationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.Qualification;
import domain.Recipe;
import domain.User;

@Service
@Transactional
public class QualificationService {
	
	
	//Managed Repository-----------------------------
	
	@Autowired
	private QualificationRepository qualificationRepository;
	
	//Supporting services-----------------------------
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private CustomerService customerService;
	//Constructors------------------------------------
	
	public QualificationService(){
		super();
	}
	
	//Simple CRUD methods----------------------------
	
	public Qualification create() {
		Qualification result;

		result = new Qualification();

		return result;
	}
	public Qualification create(Recipe recipe,User user) {
		Qualification result;

		result = new Qualification();
		result.setRecipe(recipe);
		result.setCustomer(user);
		return result;
	}

	public Collection<Qualification> findAll() {
		Collection<Qualification> result;

		result = qualificationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Qualification findOne(int qualificationId) {
		Qualification result;

		result = qualificationRepository.findOne(qualificationId);
		Assert.notNull(result);

		return result;
	}

	@SuppressWarnings("static-access")
	public Qualification save(Qualification qualification) {
		Assert.notNull(qualification,"El objeto no puede ser nulo");
		UserAccount account =qualification.getRecipe().getUser().getUserAccount();
		Assert.isTrue(!account.equals(loginService.getPrincipal()),"Un usuario no puede dar like a su propia receta");

		Qualification result;

		result = qualificationRepository.save(qualification);
		
		return result;
	}
	
	public Collection<Qualification> save(Collection<Qualification> qualifications){
		Assert.notNull(qualifications);
		System.out.println(qualifications);
		Qualification aux =create();
		Collection<Qualification> result=new ArrayList<Qualification>();
		for(Qualification q : qualifications){
			aux.setCustomer(q.getCustomer());
			aux.setOpinion(q.getOpinion());
			aux.setRecipe(q.getRecipe());
			result.add(aux);
			
		}
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Qualification qualification) {
		Assert.notNull(qualification);
		Assert.isTrue(qualification.getId() != 0);
		Assert.isTrue(qualification.getCustomer().getUserAccount().equals(loginService.getPrincipal()));

		qualificationRepository.delete(qualification);
	}
	
	
	//Other bussiness methods------------------------
	public Collection<Qualification> copyQualificationsForCopyOfRecipe(Recipe recipeNew,Recipe recipeOld){		
		Collection<Qualification> qualifications = recipeService.findQualificationsByRecipe(recipeOld);
		List<Qualification> result = new LinkedList<Qualification>(qualifications);
		for(Qualification q : result){
			Qualification qualificationCloned = null;
			try {
				qualificationCloned = (Qualification) BeanUtils.cloneBean(q);
			}catch(Exception e){
				e.printStackTrace();
			}
			qualificationCloned.setRecipe(recipeNew);
//			q.setId(0);
//			q.setVersion(0);
		}
		recipeNew.setQualifications(result);
		return result;
	}
	
	
	public Collection<Qualification> findQualificationsByRecipe(Recipe recipe){
		Collection<Qualification> result = qualificationRepository.findQualificationsByRecipeId(recipe.getId());
		return result;
	}
	public int calculateLikesForRecipe(Collection<Qualification> qualifications){
		int likes =0;
		for(Qualification q: qualifications){
			if(q.getOpinion()==Boolean.TRUE){
				likes++;
			}
		}
		return likes;
	}
	
	public int calculateDislikesForRecipe(Collection<Qualification> qualifications){
		int dislikes =0;
		for(Qualification q: qualifications){
			if(q.getOpinion()==Boolean.FALSE){
				dislikes++;
			}
		}
		return dislikes;
	}

	public Integer likeButtonsToShow(Customer customer, Recipe recipe) {
		Integer like = 1;
		
		if(customer!=null){
			for (Qualification q: recipe.getQualifications()){
				if (q.getCustomer().equals(customer) && q.getOpinion()==true) {
					like = 2;
					break;
				}else if(q.getCustomer().equals(customer) && q.getOpinion()==false){
					like=3;
					break;
				}
			}
		}
		return like;
	}
	
	public void like(int recipeId){
		Recipe recipe;
		recipe = recipeService.findOne(recipeId);
		Qualification qualification = null;
		List<Qualification> everyQualificationOfRecipe = new ArrayList<Qualification>(findQualificationsByRecipe(recipe));
		List<Qualification> qualifications = new ArrayList<Qualification>();
		for(Qualification q: everyQualificationOfRecipe){
			if(q.getCustomer().equals(customerService.findActorByPrincial()))
				qualifications.add(q);
		}
		if(qualifications.size()==1){
			qualification=qualifications.get(0);
		}else if(qualifications.size()==0){
			qualification=create();
			qualification.setCustomer(customerService.findActorByPrincial());
			qualification.setRecipe(recipe);
		}
		qualification.setOpinion(true);

		save(qualification);
		
	}
	
	public void dislike(int recipeId){
		Recipe recipe;
		recipe = recipeService.findOne(recipeId);
		Qualification qualification = null;
		List<Qualification> everyQualificationOfRecipe = new ArrayList<Qualification>(findQualificationsByRecipe(recipe));
		List<Qualification> qualifications = new ArrayList<Qualification>();
		for(Qualification q: everyQualificationOfRecipe){
			if(q.getCustomer().equals(customerService.findActorByPrincial()))
				qualifications.add(q);
		}
		if(qualifications.size()==1){
			qualification=qualifications.get(0);
		}else if(qualifications.size()==0){
			qualification=create();
			qualification.setCustomer(customerService.findActorByPrincial());
			qualification.setRecipe(recipe);
		}
		qualification.setOpinion(false);

		save(qualification);
		
	}
}
