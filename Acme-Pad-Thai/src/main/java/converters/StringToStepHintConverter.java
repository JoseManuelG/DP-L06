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

import repositories.StepHintRepository;
import domain.StepHint;

@Component
@Transactional
public class StringToStepHintConverter implements Converter<String, StepHint> {

	@Autowired
	StepHintRepository stepHintRepository;

	@Override
	public StepHint convert(String text) {
		StepHint result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = stepHintRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
