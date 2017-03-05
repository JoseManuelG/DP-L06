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
import domain.Qualification;
import domain.Recipe;

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
		Assert.isTrue(qualification.getCustomer().getUserAccount()!=loginService.getPrincipal(),"Un usuario no puede dar like a su propia receta");

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
}
