package converters;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DateToStringConverter implements Converter<Date, String> {

	@SuppressWarnings("deprecation")
	@Override
	public String convert(Date arg0) {
		String result=String.valueOf(arg0.getYear());
		result=result.concat("-");
		 result=result.concat(String.valueOf(arg0.getMonth()));

		result=result.concat("-");
		 result=result.concat(String.valueOf(arg0.getDay()));

		return result;
	}

}
