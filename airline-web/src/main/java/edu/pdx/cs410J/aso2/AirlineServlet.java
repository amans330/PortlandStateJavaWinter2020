package edu.pdx.cs410J.aso2;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>. However, in its current state, it is an example of how
 * to use HTTP and Java servlets to store simple Airline of words and their
 * definitions.
 */
public class AirlineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ALLGOOD = "allgood";
	static final String AIRLINE = "airline";
	static final String FLIGHT_NUMBER = "flightNumber";
	static final String SRC = "src";
	static final String DEPART = "depart";
	static final String DEST = "dest";
	static final String ARRIVE = "arrive";

	private final Map<String, Airline> airlineMap = new HashMap<String, Airline>();

	/**
	 * Handles an HTTP GET request from a client by writing the definition of the
	 * word specified in the "word" HTTP parameter to the HTTP response. If the
	 * "word" parameter is not specified, all of the entries in the Airline are
	 * written to the HTTP response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter pw = response.getWriter();
		
		String airlinename = getParameter(AIRLINE, request);
		String src = getParameter(SRC, request);
		String dest = getParameter(DEST, request);
		String judgement;
		
		judgement = CheckDataValidity.checkAirline(airlinename);
		if (judgement != ALLGOOD) response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
		
		if ((src == null && dest != null) || (src != null && dest == null))
			return;
		if (src != null && dest != null && !src.isEmpty() && !dest.isEmpty()) {
			judgement = CheckDataValidity.checkSource(src);
			if (judgement != ALLGOOD) response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
			judgement = CheckDataValidity.checkDestination(dest);
			if (judgement != ALLGOOD) response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
			
			Airline airline = airlineMap.get(airlinename);
			if (airline == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No Airline Found with this name");
				return;
			}
			List<Flight> flights = airline.getFlights();

			// create a copy object so airline is unchanged
			Airline copy = new Airline(airline.getName());
			List<Flight> temp = new ArrayList<Flight>();
			for (int i = 0; i < flights.size(); i++) {
				if (flights.get(i).getSource().equalsIgnoreCase(src)
						&& flights.get(i).getDestination().equalsIgnoreCase(dest))
					temp.add(flights.get(i));
			}
			if(temp.size() == 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No matching flights found");
				return;
			}
			copy.setFlights(temp);
			XmlDumper dumper = new XmlDumper(pw);
			dumper.dump(copy);
			response.setStatus(HttpServletResponse.SC_OK);
			
		}
		else
			getAirline(airlinename, response);
	}

	/**
	 * Handles an HTTP POST request by storing the Airline entry for the "word"
	 * and "definition" request parameters. It writes the Airline entry to the
	 * HTTP response.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.setContentType("text/xml");
		String airlinename = getParameter(AIRLINE, request);
		String flightNumber = getParameter(FLIGHT_NUMBER, request);
		String src = getParameter(SRC, request);
		String depart = getParameter(DEPART, request);
		String dest = getParameter(DEST, request);
		String arrival = getParameter(ARRIVE, request);

		// if data is invalid return error message
		String judgement;
		judgement = CheckDataValidity.checkAirline(airlinename);
		if (judgement != ALLGOOD) response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
		judgement = CheckDataValidity.checkFlightNumber(flightNumber);
		if (judgement != ALLGOOD)
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
		judgement = CheckDataValidity.checkSource(src);
		if (judgement != ALLGOOD) response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
		judgement = CheckDataValidity.checkDeparture(depart);
		if (judgement != ALLGOOD) response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
		judgement = CheckDataValidity.checkDestination(dest);
		if (judgement != ALLGOOD) response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
		judgement = CheckDataValidity.checkArrival(arrival);
		if (judgement != ALLGOOD) response.sendError(HttpServletResponse.SC_BAD_REQUEST, judgement);
		
		List<Flight> flightList;
		Airline airline;
		if (airlineMap.containsKey(airlinename)) {
			airline = airlineMap.get(airlinename);
			flightList = airline.getFlights();
		} else {
			airline = new Airline(airlinename);
			flightList = new ArrayList<Flight>();
		}

		Flight flight = new Flight(Integer.parseInt(flightNumber), src, Util.getDateFromString(depart), dest,
				Util.getDateFromString(arrival));
		flightList.add(flight);
		airline.setFlights(flightList);
		airlineMap.put(airlinename, airline);

		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter pw = response.getWriter();
		XmlDumper dumper = new XmlDumper(pw);
		dumper.dump(airline);
		pw.close();
	}

	/**
	 * Returns the value of the HTTP request parameter with the given name.
	 *
	 * @return <code>null</code> if the value of the parameter is <code>null</code>
	 *         or is the empty string
	 */
	private String getParameter(String name, HttpServletRequest request) {
		String value = request.getParameter(name);
		if (value == null || "".equals(value))
			return null;
		else
			return value;
	}

	/**
	 * Prints all the Flights of the HTTP request parameter with the given airline
	 * name.
	 * 
	 * @param airline_name The airline name for which all flights are required
	 * @param response     The HTTP response object
	 */
	private void getAirline(String airline_name, HttpServletResponse response) throws IOException {
		Airline airline = airlineMap.get(airline_name);
		if (airline == null)
			return;
		PrintWriter pw = response.getWriter();
		XmlDumper dumper = new XmlDumper(pw);
		dumper.dump(airline);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	@VisibleForTesting
	Airline getAirline(String airline) {
		return this.airlineMap.get(airline);
	}
}
