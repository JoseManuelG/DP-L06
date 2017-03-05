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

import domain.Qualified;

@Component
@Transactional
public class QualifiedToStringConverter implements Converter<Qualified, String> {

	@Override
	public String convert(Qualified qualified) {
		String result;

		if (qualified == null)
			result = null;
		else
			result = String.valueOf(qualified.getId());
		
		return result;
	}

}
