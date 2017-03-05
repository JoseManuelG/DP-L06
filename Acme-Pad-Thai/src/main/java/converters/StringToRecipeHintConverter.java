/* StringToAnnouncementConverter.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RecipeHintRepository;
import domain.RecipeHint;

@Component
@Transactional
public class StringToRecipeHintConverter implements Converter<String, RecipeHint> {

	@Autowired
	RecipeHintRepository recipeHintRepository;

	@Override
	public RecipeHint convert(String text) {
		RecipeHint result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = recipeHintRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
