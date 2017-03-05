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

import services.MasterClassService;

import domain.MasterClass;

@Component
@Transactional
public class StringToMasterClassConverter implements Converter<String, MasterClass> {

	@Autowired
	MasterClassService masterClassService;

	@Override
	public MasterClass convert(String text) {
		MasterClass result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = masterClassService.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
