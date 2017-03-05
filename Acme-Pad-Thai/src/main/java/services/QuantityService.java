package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuantityRepository;
import domain.Ingredient;
import domain.Quantity;
import domain.Recipe;

@Service
@Transactional
public class QuantityService {
	
	
	//Managed Repository-----------------------------
	
	@Autowired
	private QuantityRepository quantityRepository;
	
	//Supporting services-----------------------------
	
	//Constructors------------------------------------
	
	public QuantityService(){
		super();
	}
	
	//Simple CRUD methods----------------------------
	
	public Quantity create() {
		Quantity result;

		result = new Quantity();

		return result;
	}
	
	public Quantity create(Recipe recipe) {
		Quantity result;

		result = new Quantity();
		result.setRecipe(recipe);
		return result;
	}

	public Collection<Quantity> findAll() {
		Collection<Quantity> result;

		result = quantityRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Quantity findOne(int quantityId) {
		Quantity result;

		result = quantityRepository.findOne(quantityId);
		Assert.notNull(result);

		return result;
	}

	public Quantity save(Quantity quantity) {
		Assert.notNull(quantity,"La cantidad no puede ser nula");
		Assert.notNull(quantity.getValue(),"El valor no puede ser nulo");
		Assert.hasText(quantity.getUnit(),"La unidad no puede ser nula");
		Assert.isTrue(!(findByRecipeAndIngredient(quantity.getRecipe(), quantity.getIngredient()) && quantity.getId()==0),"Una receta no puede tener dos veces el mismo ingrediente");
		Quantity result;
		result = quantityRepository.save(quantity);
		
		return result;
	}
	
	public Collection<Quantity> save(Collection<Quantity> quantities){
		Assert.notNull(quantities,"La colección de cantidades no puede ser nula");
		Collection<Quantity> result=new ArrayList<Quantity>();
		Quantity aux =create();
		for(Quantity q : quantities){
			aux.setIngredient(q.getIngredient());
			aux.setRecipe(q.getRecipe());
			aux.setUnit(q.getUnit());
			aux.setValue(q.getValue());
			result.add(aux);
		}
		return result;
	}

	public void delete(Quantity quantity) {
		Assert.notNull(quantity,"La cantidad no puede ser nula");
		Assert.isTrue(quantity.getId() != 0,"El id de la cantidad no puede ser 0");

		quantityRepository.delete(quantity);
	}
	
	
	//Other bussiness methods------------------------
	
//	public Quantity createIngredientForRecipe(Recipe recipe, Ingredient ingredient){
//		Assert.notNull(recipe);
//		Assert.notNull(ingredient);
//		Quantity result;
//		result=new Quantity();
//		result.setRecipe(recipe);
//		result.setIngredient(ingredient);
//		return result;
//		
//	}
	
	public boolean existsQuantityForIngredient(Ingredient ingredient) {
		boolean result;
		Collection<Quantity> quantities;
		
		quantities = quantityRepository.findByIngredientId(ingredient.getId()); 
		result = !quantities.isEmpty();
		
		return result;
	}
	
	public Collection<Quantity> findQuantitiesForRecipe(Recipe recipe){
		Collection<Quantity> result = quantityRepository.findQuantitiesByRecipe(recipe.getId());
		return result;
	}
	
	public boolean findByRecipeAndIngredient(Recipe recipe, Ingredient ingredient){
		boolean result=false;
		Assert.notNull(recipe,"La receta no puede ser nula");
		Assert.notNull(ingredient,"El ingrediente no puede ser nulo");
		result=(quantityRepository.findByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId()))!=null;
		return result;
	}
	
	
//	public Collection<Quantity> copyQuantitiesForCopyOfRecipe(Recipe recipe){
//		Collection<Quantity> quantities = quantityRepository.findQuantitiesByRecipe(recipe.getId());
//		Collection<Quantity> result = new LinkedList<Quantity>(quantities);
//		for(Quantity s : result){
//			s.setRecipe(recipe);
//		}
//		return result;
//	}
	
	public Collection<Quantity> copyQuantitiesForCopyOfRecipe(Recipe recipe, Recipe oldRecipe){
	    Collection<Quantity> quantities = quantityRepository.findQuantitiesByRecipe(oldRecipe.getId());
	    Collection<Quantity> result = new LinkedList<Quantity>(quantities);
	    for(Quantity q : result){
	    	Quantity quantityCloned = null;
			try {
				quantityCloned = (Quantity) BeanUtils.cloneBean(q);
			}catch(Exception e){
				e.printStackTrace();
			}
			quantityCloned.setRecipe(recipe);
	    }
	    recipe.setQuantities(result);
	    return result;
	  }
	
	public Collection<String> unidades(){
		Collection<String> units =new ArrayList<String>();
		String[] unitsArray = { "grams","kilograms","ounces", "pounds", "millilitres", "litres", "spoons", "cups", "pieces" };
		for(int i =  0; i < unitsArray.length; i++){
			units.add(unitsArray[i]);  
		}
		return units;
	}

}
