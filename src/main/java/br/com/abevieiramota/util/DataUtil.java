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

import br.com.abevieiramota.messages.Messages;

public class DataUtil {

	private static final String DATE_FORMAT = Messages.getString("date.format");
	private static final DateFormat DATE_FORMATER = new SimpleDateFormat(DATE_FORMAT);

	public static int compare(String dataX, String dataY) throws ParseException {
		checkNotNull(dataX);
		checkNotNull(dataY);

		Date dateX = DATE_FORMATER.parse(dataX);
		Date dateY = DATE_FORMATER.parse(dataY);

		return dateX.compareTo(dateY);
	}

	public static List<String> range(String dataX, String dataY) throws ParseException {
		checkArgument(compare(dataX, dataY) <= 0);

		List<String> range = new ArrayList<>();
		Date dateX = DATE_FORMATER.parse(dataX);
		Date dateY = DATE_FORMATER.parse(dataY);

		Calendar calRange = new GregorianCalendar();
		calRange.setTime(dateX);
		Calendar calFim = new GregorianCalendar();
		calFim.setTime(dateY);

		do {
			Date data = calRange.getTime();
			String dataAsString = DATE_FORMATER.format(data);
			range.add(dataAsString);
			calRange.add(Calendar.DAY_OF_MONTH, 1);
		} while (calRange.compareTo(calFim) <= 0);

		return range;
	}

	public static String hoje() {

		return DATE_FORMATER.format(new GregorianCalendar().getTime());
	}
}
