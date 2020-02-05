package edu.pdx.cs410J.aso2;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.pdx.cs410J.AbstractFlight;

/**
 * 
 * @author Aman Singh Solanki POJO class for Flight
 *
 */
public class Flight extends AbstractFlight implements Comparable<Flight>{

	int flightnumber;
	String src;
	String dest;
	Date arrivalDateTime;
	Date departureDateTime;

	/**
	 * getter method for flightnumber
	 */
	@Override
	public int getNumber() {
		return flightnumber;
	}

	/**
	 * getter method for src
	 */
	@Override
	public String getSource() {
		return src;
	}

	/**
	 * getter method for depart
	 * 
	 * @return single departure date and time String
	 */
	@Override
	public String getDepartureString() {
		return Util.dateToStringFormat(departureDateTime);
	}
	
	/**
	 * @return departure date as java date
	 */
	@Override
	public Date getDeparture() {
		return departureDateTime;
	}

	/**
	 * getter method for dest
	 */
	@Override
	public String getDestination() {
		return dest;
	}

	/**
	 * getter method for arrival
	 */
	@Override
	public String getArrivalString() {
		return Util.dateToStringFormat(arrivalDateTime);
	}
	
	/**
	 * @return arrival date as java date
	 */
	@Override
	public Date getArrival() {
	    return arrivalDateTime;
	  }

	/**
	 * constrcutor method for Flight
	 * 
	 * @param flightnumber flightnumber should be integer
	 */
	public Flight(int flightnumber, String src, Date departureDateTime, String dest, Date arrivalDateTime) {
		this.flightnumber = flightnumber;
		this.src = src;
		this.dest = dest;
		this.arrivalDateTime = arrivalDateTime;
		this.departureDateTime = departureDateTime;
	}

	/**
	 * method to get proper string representation to be written to the file for easy
	 * delimition
	 * 
	 * @return String '@' separated representation of flight object
	 */
	public String getFlightForFile() {
		Date departure = this.getDeparture();
		Date arrival = this.getArrival();
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");  
	    String[] departureString = formatter.format(departure).split(" ");
	    String[] arrivalString = formatter.format(arrival).split(" ");
		/**
		 * to be done later
		 */
		return this.flightnumber + "@" + this.getSource() + "@" + departureString[0] + "@" + departureString[1] + "@"
				+ departureString[2] + "@" + this.getDestination() + "@" + arrivalString[0] + "@" + arrivalString[1] + "@" + arrivalString[2];
	}
	
	/**
	 * Comparator method for Flight Class.
	 * compareTo should return <0 if this is supposed to be
     * less than other, > 0 if this is supposed to be greater than 
     * other and 0 if they are supposed to be equal
	 */
	@Override
	public int compareTo(Flight other) {
		if(this.src.compareTo(other.src) != 0)
			// src is case insensitive
			return this.src.toLowerCase().compareTo(other.src.toLowerCase());
		else {
			return this.getDeparture().compareTo(other.getDeparture());
		}
	}
}
