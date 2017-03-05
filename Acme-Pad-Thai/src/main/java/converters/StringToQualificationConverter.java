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

import repositories.QualificationRepository;
import domain.Qualification;

@Component
@Transactional
public class StringToQualificationConverter implements Converter<String, Qualification> {

	@Autowired
	QualificationRepository qualificationRepository;

	@Override
	public Qualification convert(String text) {
		Qualification result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = qualificationRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
