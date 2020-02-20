package edu.pdx.cs410J.aso2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.AirportNames;

/**
 * Utility Class for common tasks across the application
 * @author Aman
 *
 */
public class Util {
	/**
	 * 
	 * @param date
	 * @return Return date in short format and type String
	 */
	public static String dateToStringFormat(Date date) {
		DateFormat dateFormat;
		// Date Format SHORT constant
		dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		return(dateFormat.format(date));
	}
	
	/**
	 * 
	 * @param dateString converts the string passed to java date
	 * @return Java.Util.Date
	 */
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
	
	/**
	 * 
	 * @param flight the flight object to pretty print
	 * @return Pretty string format of the flight object
	 */
	public static String prettyPrint(Flight flight) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEEE, dd MMMMM yyyy hh:mm aa");
		long duration = (flight.getArrival().getTime() - flight.getDeparture().getTime())/(1000*60);
		return "Flight "+flight.getNumber()+" departs "+AirportNames.getNamesMap().get(flight.getSource().toUpperCase())+" on "
				+formatter.format(flight.getDeparture())+" and arrives "+AirportNames.getNamesMap().get(flight.getDestination().toUpperCase()) +" on "
						+ formatter.format(flight.getArrival())+" with a duration of "+duration+""
								+ " minutes."; 
	}
	
	/**
	 * Prints the error message and exits with status 1
	 * @param message error message to print
	 */
	public static void printErrorAndExit(String message) {
		System.out.println(message);
		System.exit(1);
	}

}
