package edu.pdx.cs410J.aso2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.AirportNames;

public class Util {
	public static String dateToStringFormat(Date date) {
		DateFormat dateFormat;
		// Date Format SHORT constant
		dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		return(dateFormat.format(date));
	}
	
	public static Date getDateFromString(String dateString) {
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		try {
			date = sd.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String prettyPrint(Flight flight) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEEE, MMMMM yyyy hh:mm aa");
		long duration = (flight.getArrival().getTime() - flight.getDeparture().getTime())/(1000*60);
		return "Flight "+flight.getNumber()+" departs "+AirportNames.getNamesMap().get(flight.getSource().toUpperCase())+" on "
				+formatter.format(flight.getDeparture())+" and arrives "+AirportNames.getNamesMap().get(flight.getDestination().toUpperCase()) +" on "
						+ formatter.format(flight.getArrival())+" with a duration of "+duration+""
								+ " minutes."; 
	}

}
