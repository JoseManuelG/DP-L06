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

import repositories.StepRepository;
import domain.Step;

@Component
@Transactional
public class StringToStepConverter implements Converter<String, Step> {

	@Autowired
	StepRepository stepRepository;

	@Override
	public Step convert(String text) {
		Step result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = stepRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
