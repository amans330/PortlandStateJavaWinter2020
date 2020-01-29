package edu.pdx.cs410J.aso2;

import edu.pdx.cs410J.AbstractFlight;

/**
 * 
 * @author Aman Singh Solanki POJO class for Flight
 *
 */
public class Flight extends AbstractFlight {

	int flightnumber;
	String src;
	String dest;
	String arrive;
	String depart;

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
	 * @return single departure date and time String
	 */
	@Override
	public String getDepartureString() {
		return depart;
	}
	
	/**
	 * getter method for dest
	 */
	@Override
	public String getDestination() {
		return dest;
	}
	
	/**
	 * getter method for arive
	 */
	@Override
	public String getArrivalString() {
		return arrive;
	}
	
	/**
	 * constrcutor method for Flight
	 * @param flightnumber flightnumber should be integer
	 */
	public Flight(int flightnumber, String src, String depart, String dest, String arrive) {
		this.flightnumber = flightnumber;
		this.src = src;
		this.dest = dest;
		this.arrive = arrive;
		this.depart = depart;
	}
	
	/**
	 * method to get proper string representation to be written to the file for easy delimition
	 * @return String '@' separated representation of flight object
	 */
	public String getFlightForFile() {
		String[] departure = this.getDepartureString().split(" ");
		String[] arrival = this.getArrivalString().split(" ");
		return this.flightnumber+"@"+this.getSource()+"@"+departure[0]+"@"+departure[1]+"@"+this.getDestination()+"@"+arrival[0]+"@"+arrival[1];
	}
}
