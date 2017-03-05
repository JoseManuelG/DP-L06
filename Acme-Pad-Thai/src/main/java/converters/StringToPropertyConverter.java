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

import services.PropertyService;
import domain.Property;

@Component
@Transactional
public class StringToPropertyConverter implements Converter<String, Property> {

	@Autowired
	PropertyService propertyService;

	@Override
	public Property convert(String text) {
		Property result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = propertyService.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
