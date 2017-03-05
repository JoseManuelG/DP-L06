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

import domain.Bill;

@Component
@Transactional
public class BillToStringConverter implements Converter<Bill, String> {

	@Override
	public String convert(Bill bill) {
		String result;

		if (bill == null)
			result = null;
		else
			result = String.valueOf(bill.getId());
		
		return result;
	}


}
