/* CertificationToStringConverter.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.StepHint;

@Component
@Transactional
public class StepHintToStringConverter implements Converter<StepHint, String> {

	@Override
	public String convert(StepHint stepHint) {
		String result;

		if (stepHint == null)
			result = null;
		else
			result = String.valueOf(stepHint.getId());
		
		return result;
	}

}
