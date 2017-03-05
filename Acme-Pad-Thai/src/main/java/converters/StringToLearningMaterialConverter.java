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

import services.LearningMaterialService;
import domain.LearningMaterial;

@Component
@Transactional
public class StringToLearningMaterialConverter implements Converter<String, LearningMaterial> {

	@Autowired
	LearningMaterialService learningMaterialService ;

	@Override
	public LearningMaterial convert(String text) {
		LearningMaterial result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = learningMaterialService.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
