package edu.pdx.cs410J.aso2;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.pdx.cs410J.ParserException;

/**
 * The main class that parses the command line and communicates with the Airline
 * server using REST.
 */
public class Project5 {
	public static final String ALLGOOD = "allgood";
	public static final String MISSING_ARGS = "Missing command line arguments";
	public static final String GETFLIGHTS = "GETFLIGHTS";
	public static final String SEARCHFLIGHTS = "SEARCHFLIGHTS";
	public static final String POSTFLIGHT = "POSTFLIGHT";
	public static final String STAYHERE = "STAYHERE";

	public static void main(String[] args) {

		String[] arguments = new String[10];
		String port = null;
		String host = null;
		String todo = null;
		boolean goOnline = false;
		boolean printDetails = false;
		boolean search = false;
		Set<String> optionsValues = new HashSet<String>();

		// populate possible option values in the set
		optionsValues.add("-README");
		optionsValues.add("-print");
		optionsValues.add("-search");
		optionsValues.add("-port");
		optionsValues.add("-host");
		// options max length is 7 & args max length is 10

		// 0 arguments check
		if (args.length == 0)
			Util.printErrorAndExit("There were no arguments passed!! Please read the ReadMe and then proceed.");

		// 0 arguments check
		if (args.length > 17)
			Util.printErrorAndExit("Too many arguments passed!! Please read the ReadMe and then proceed.");
		
		// count index of arguments array
		int index = 0;

		for (int i = 0; i < args.length; i++) {
			String str = args[i];

			if (str.equals("-README")) {
				printReadMe();
				System.exit(0);
			} else if (str.equals("-print")) {
				printDetails = true;
			} else if (str.equals("-search")) {
				search = true;
				goOnline = true;
			} else if (str.equals("-port")) {
				if (i + 1 >= args.length) { // there is no argument after -xmlFile
					Util.printErrorAndExit("Please mention the port number.");
				}
				port = args[i + 1];
				i++;
				goOnline = true;
			} else if (str.equals("-host")) {
				if (i + 1 >= args.length) { // there is no argument after -xmlFile
					Util.printErrorAndExit("Please mention the host name.");
				}
				host = args[i + 1];
				i++;
				goOnline = true;
			} else {
				if (index >= 10)
					Util.printErrorAndExit("Too many arguments passed.");
				arguments[index] = str;
				index++;
			}
		}

		// less than 10 args is wrong except for online
		if (!goOnline && index < 10)
			Util.printErrorAndExit("Not enough arguments passed");

		// check if 3 args are there for -search
		if (search && index != 3)
			Util.printErrorAndExit("if -search is mentioned then airline, src and dest are required");

		// check if both host and port are present and not either
		if (goOnline && (port == null || host == null))
			Util.printErrorAndExit("Please mention both, the host name and the port number.");

		if(printDetails && index < 10)
			Util.printErrorAndExit("Not enough arguments are provided for flight, to be printed.");

		/***** Start data validation *****/
		String message = null;
		// 1 argument means get flights
		if (index == 1 && goOnline) {
			message = CheckDataValidity.checkAirline(arguments[0]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);
			todo = GETFLIGHTS;
		} else if (index == 3 && search) {
			// data validation
			message = CheckDataValidity.checkAirline(arguments[0]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);
			message = CheckDataValidity.checkSource(arguments[1]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);
			message = CheckDataValidity.checkDestination(arguments[2]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);

			todo = SEARCHFLIGHTS;
		} else if (index == 10) {
			// data validation
			message = CheckDataValidity.checkAirline(arguments[0]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);

			message = CheckDataValidity.checkFlightNumber(arguments[1]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);

			message = CheckDataValidity.checkSource(arguments[2]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);

			message = CheckDataValidity.checkDeparture(arguments[3] + " " + arguments[4] + " " + arguments[5]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);

			message = CheckDataValidity.checkDestination(arguments[6]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);

			message = CheckDataValidity.checkArrival(arguments[7] + " " + arguments[8] + " " + arguments[9]);
			if (message != ALLGOOD)
				Util.printErrorAndExit(message);

			if (goOnline)
				todo = POSTFLIGHT;
			else
				todo = STAYHERE;
		} else {
			Util.printErrorAndExit("Wrong number of arguments passed for this operation. Please read the README.");
		}

		// check if port number is an integer
		if (goOnline && !port.matches("^[0-9]*$"))
			Util.printErrorAndExit("port number is not a number");
		/***
		 * validation done
		 ****/

		// do we need to make http call, then dont create the airline object yet
		if (goOnline) {
			try {
				AirlineRestClient client = new AirlineRestClient(host, Integer.parseInt(port));
				String airlineXMLString;
				Airline airline;
				XmlParser parser;

				switch (todo) {
				case GETFLIGHTS:
					airlineXMLString = client.getFlights(arguments[0]);
					// nothing returned, return message
					if (airlineXMLString == null || airlineXMLString.isEmpty()) {
						System.out.println("No matching flights found!");
						System.exit(0);
					}
					// data found, create Airline Object from XML string returned
					parser = new XmlParser(airlineXMLString);
					airline = (Airline) parser.parse();
					// pretty print
					System.out.println("Matching Flights found:");
					for (Flight flight : ((Airline) airline).getFlights()) {
						System.out.println(Util.prettyPrint(flight));
					}
					System.exit(0);

				case SEARCHFLIGHTS:
					airlineXMLString = client.searchFlights(arguments[0], arguments[1], arguments[2]);

					// nothing returned, return message
					if (airlineXMLString == null || airlineXMLString.isEmpty()) {
						System.out.println("No matching flights found!");
						System.exit(0);
					}

					// data found, create Airline Object from XML string returned
					parser = new edu.pdx.cs410J.aso2.XmlParser(airlineXMLString);
					airline = (Airline) parser.parse();
					// pretty print
					System.out.println("Matching Flights found for " + arguments[0]);
					for (Flight flight : ((Airline) airline).getFlights()) {
						System.out.println(Util.prettyPrint(flight));
					}
					System.exit(0);

				case POSTFLIGHT:
					client.postFlight(arguments);
					System.out.println("The flight is posted successfully.");
				}
			} catch (IOException | ParserException e) {
				// TODO Auto-generated catch block
				error("Error while connecting to server");
			}
		}
		
		if(printDetails) {
			// comes here to just print the flight
			String airlineName = arguments[0];
			String flightNumber = arguments[1];
			String src = arguments[2];
			Date departDateTime = Util.getDateFromString(arguments[3] + " " + arguments[4] + " " + arguments[5]);
			String dest = arguments[6];
			Date arriveDateTime = Util.getDateFromString(arguments[7] + " " + arguments[8] + " " + arguments[9]);
			
			// arrive date should be after depart date
			if (arriveDateTime.getTime() - departDateTime.getTime() < 0) {
				System.out.println("Arrival date should be after Departure date. " + "Unless you are in a time machine!");
				System.exit(1);
			}
			
			// create flight object
			Flight flight = new Flight(Integer.parseInt(flightNumber), src, departDateTime, dest, arriveDateTime);
			System.out.println(flight.toString());
		}
	}

	private static void error(String message) {
		PrintStream err = System.err;
		err.println("** " + message);
		System.exit(1);
	}

	/**
	 * Prints usage information for this program and exits
	 * 
	 * @param message An error message to print
	 */
	private static void usage(String message) {
		PrintStream err = System.err;
		err.println("** " + message);
		err.println();
		err.println("usage: java Project5 host port [word] [definition]");
		err.println("  host         Host of web server");
		err.println("  port         Port of web server");
		err.println("  word         Word in dictionary");
		err.println("  definition   Definition of word");
		err.println();
		err.println("This simple program posts words and their definitions");
		err.println("to the server.");
		err.println("If no definition is specified, then the word's definition");
		err.println("is printed.");
		err.println("If no word is specified, all dictionary entries are printed");
		err.println();

		System.exit(1);
	}

	/**
	 * prints the read me on console.
	 */
	public static void printReadMe() {
		System.out.print("Welcome to the class project of CS510!!\n\nMy name is Aman Singh Solanki. "
				+ "This is the README for project 5. This program is used to input new airline details in the system. An airline "
				+ "has a name and has multiple flights operating from it. \n\nA Flight in turn has all the details a flight "
				+ "can have like flight number, depart and arrive timings, date source and destinations. \n\n"
				+ "Usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" + "args are (in this order):\n"
				+ "airline: The name of the airline\n" + "flightNumber: The flight number\n"
				+ "src: Three-letter code of departure airport\n" + "depart: Departure date and time (24-hour time)\n"
				+ "dest: Three-letter code of arrival airport\n" + "arrive: Arrival date and time (24-hour time)\n\n"
				+ "options are (options may appear in any order):\n"
				+ "-print: Prints a description of the new flight\n"
				+ "-README: Prints a README for this project and exits\n\n"
				+ "There are various rules to input data:\n1. A flight number should be an integer. "
				+ "\n2. Date should be in the format mm/dd/yyyy\n3. src and dest should be 3 letters");
	}

}