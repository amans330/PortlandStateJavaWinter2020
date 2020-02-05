package edu.pdx.cs410J.aso2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

/**
 * 
 * @author Aman Singh Solanki
 * POJO class for Airline
 *
 */
public class Airline extends AbstractAirline{
	
	private String name;
	List<Flight> flights;
	
	/**
	 * getter method for name
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	/**
	 * setter method for name
	 * @param name airline-name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * setter method for flights
	 * @param flight Object of type flight
	 */
	@Override
	public void addFlight(AbstractFlight flight) {
		// TODO Auto-generated method stub
		flights.add((Flight) flight);
	}
	
	/**
	 * getter method for flights
	 */
	@Override
	public List<Flight> getFlights() {
		// TODO Auto-generated method stub
		Collections.sort(flights);
		return flights;
	}
	
	/**
	 * constructor
	 */
	public Airline(String name) {
		this.name = name;
		flights = new ArrayList<Flight>();
	}
	
	

}