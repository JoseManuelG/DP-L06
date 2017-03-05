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

import repositories.QuantityRepository;
import domain.Quantity;

@Component
@Transactional
public class StringToQuantityConverter implements Converter<String, Quantity> {

	@Autowired
	QuantityRepository quantityRepository;

	@Override
	public Quantity convert(String text) {
		Quantity result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = quantityRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
