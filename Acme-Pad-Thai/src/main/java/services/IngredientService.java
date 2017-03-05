package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.IngredientRepository;
import domain.Ingredient;
import domain.PropertyValue;
import domain.Quantity;
import domain.Recipe;

@Service
@Transactional
public class IngredientService {
	
	
	//Managed Repository-----------------------------
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	//Supporting services-----------------------------
	@Autowired
	private QuantityService quantityService;
	
	//Constructors------------------------------------
	
	public IngredientService(){
		super();
	}
	
	//Simple CRUD methods----------------------------
	
	public Ingredient create() {
		Ingredient result;

		result = new Ingredient();
		Collection<PropertyValue> propertyValues= new ArrayList<PropertyValue>();
		result.setPropertyValues(propertyValues);
		Collection<Quantity> quantities = new ArrayList<Quantity>();
		result.setQuantities(quantities);
		return result;
	}

	public Collection<Ingredient> findAll() {
		Collection<Ingredient> result;

		result = ingredientRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Ingredient findOne(int ingredientId) {
		Ingredient result;

		result = ingredientRepository.findOne(ingredientId);
		Assert.notNull(result);

		return result;
	}

	public Ingredient save(Ingredient ingredient) {
		Assert.notNull(ingredient,"El ingrediente no puede ser nulo");
		Assert.hasText(ingredient.getDescription(),"La descripción del ingrediente no puede ser nula");
		Assert.hasText(ingredient.getName(),"El nombre del ingrediente no puede ser nulo");
		Ingredient result;
		result = ingredientRepository.save(ingredient);
		
		return result;
	}
	
	public Collection<Ingredient> save(Collection<Ingredient> ingredients){
		Assert.notNull(ingredients,"La colección de ingredientes no puede ser nula");
		Collection<Ingredient> result=new ArrayList<Ingredient>();
		for(Ingredient i : ingredients){
			result.add(save(i));
		}
		return result;
	}

	public void delete(Ingredient ingredient) {
		Assert.notNull(ingredient,"El ingrediente no puede ser nulo");
		Assert.isTrue(ingredient.getId() != 0,"El id del ingrediente no puede ser 0");
		
		Assert.isTrue(ingredientRepository.exists(ingredient.getId()),"El ingrediente debe existir para poder borrarse de la bd");
		Assert.isTrue(!quantityService.existsQuantityForIngredient(ingredient),"El ingrediente no puede estar relacionado con una receta");

		ingredientRepository.delete(ingredient);
	}
	
	
	//Other bussiness methods------------------------
//	
//	public Collection<Ingredient> findIngredientsAbleToAddForRecipe(Recipe recipe){
//		Collection<Ingredient> result = ingredientRepository.findIngredientsAbleToAddForRecipe(recipe.getId());
//		return result;
//	}
	
	public Collection<Ingredient> findIngredentsByRecipe(Recipe recipe){
		Collection<Ingredient> result = ingredientRepository.findIngredentsByRecipe(recipe.getId());
		return result;
	}
	
//	public void addPropertyForIngredient(Ingredient ingredient, Property property) {
//		Assert.isTrue(property.getId() != 0);
//		Assert.isTrue(ingredient.getId() !=0);
//		PropertyValue propertyValue = propertyValueService.create();;
//		
//		ingredient = ingredientRepository.findOne(ingredient.getId());
//		Assert.notNull(ingredient);
//		property = propertyService.findOne(property.getId());
//		Assert.notNull(property);
//		propertyValue = propertyValueService.findByIngredientAndProperty(ingredient, property);
//		Assert.isNull(propertyValue);
//		
//		propertyValue.setIngredient(ingredient);
//		propertyValue.setProperty(property);
//		propertyValue = propertyValueService.save(propertyValue);
//		ingredient.addPropertyValue(propertyValue);
//		property.addPropertyValue(propertyValue);
//		
//		ingredientRepository.save(ingredient);		
//		propertyService.save(property);
//		propertyValueService.save(propertyValue);
//	}
	
//	public Collection<Ingredient> copyIngredientsForCopyOfRecipe(Collection<Quantity> quantities){
//		List<Ingredient> result = new LinkedList<Ingredient>(); 
//		for(Quantity q : quantities){
//			Ingredient aux= ingredientRepository.findIngredientByQuantity(q.getId());
//			aux.addQuantity(q);
//			result.add(aux);
//		}
//		return result;
//	}
//

}
