package br.com.abevieiramota.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DataUtil {

	private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public static int compare(String dataX, String dataY) throws ParseException {
		checkNotNull(dataX);
		checkNotNull(dataY);
		
		Date dateX = formatter.parse(dataX);
		Date dateY = formatter.parse(dataY);
		
		return dateX.compareTo(dateY);
	}

	public static List<String> range(String dataX, String dataY) throws ParseException {
		checkArgument(compare(dataX, dataY) <= 0);
		
		List<String> range = new ArrayList<>();
		Date dateX = formatter.parse(dataX);
		Date dateY = formatter.parse(dataY);

		Calendar calRange = new GregorianCalendar();
		calRange.setTime(dateX);
		Calendar calFim = new GregorianCalendar();
		calFim.setTime(dateY);

		do {
			Date data = calRange.getTime();
			String dataAsString = formatter.format(data);
			range.add(dataAsString);
			calRange.add(Calendar.DAY_OF_MONTH, 1);
		} while (calRange.compareTo(calFim) <= 0);

		return range;
	}

	public static String hoje() {
		
		return formatter.format(new GregorianCalendar().getTime());
	}

}
