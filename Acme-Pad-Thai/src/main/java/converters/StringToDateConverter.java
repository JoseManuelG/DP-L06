package converters;

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StringToDateConverter implements Converter<String, Date>  {

	
	 
    
 
	@Override
    public Date convert(String source) {
    	String[]aux=source.split("/");
    	int year=Integer.parseInt(aux[0]);
    	int month=Integer.parseInt(aux[1]);
    	int day=Integer.parseInt(aux[2]);
    	GregorianCalendar aux2= new GregorianCalendar( year,  month-1,  day);
        Date result = aux2.getTime();
 
        return result;
    }

}
