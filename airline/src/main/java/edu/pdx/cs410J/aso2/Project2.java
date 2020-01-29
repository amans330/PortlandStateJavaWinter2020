package edu.pdx.cs410J.aso2;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import edu.pdx.cs410J.ParserException;

public class Project2 {

	public static void main(String[] args) {

		String[] arguments = new String[8];
		String path = null;
		boolean printDetails = false;
		boolean readFromFile = false;
		Set<String> optionsValues = new HashSet<String>();
		// populate possible option values in the set
		optionsValues.add("-README");
		optionsValues.add("-print");
		optionsValues.add("-textFile");

		// 0 arguments check
		if (args.length == 0) {
			System.out.print("There were no arguments passed!! Please read the ReadMe and then proceed.");
			System.exit(1);
		}

		// total elements should be at least 8 and not more than 10
		if (args.length < 8 || args.length > 12) {
			// this can only be readme
			if (args[0].equals("-README")) {
				printReadMe();
				System.exit(0);
			}
			System.out.print("Not enough or too many arguments passed!! Please read the ReadMe and then proceed.");
			System.exit(1);
		}
		// count index of arguments array
		int index = 0;

		for (int i = 0; i < args.length; i++) {
			String str = args[i];
			if(str.charAt(0) == '-') {
				if(!optionsValues.contains(str)) {
					System.out.print("Unknown command line option.");
					System.exit(1);
				}
			}
			if (str.equals("-README")) {
				printReadMe();
				System.exit(0);
			} else if (str.equals("-print")) {
				printDetails = true;
			} else if (str.equals("-textFile")) {
				if (i + 1 >= args.length) { // there is no argument after -textFile
					System.out.print("Please pass the file name to read/write from.");
					System.exit(1);
				}
				readFromFile = true;
				path = args[i + 1];
				i++;
			} else {
				if(index == 8) {
					System.out.print("Too many arguments passed.");
					System.exit(1);
				}
				arguments[index] = str;
				index++;
			}
		}
		
		if(index < 8) {
			System.out.println("Not enough arguments passed");
			System.exit(1);
		}

		if (CheckDataValidity.checkArgumentsDataValidity(arguments) == false)
			System.exit(1);

		String airlineName = arguments[0];
		String flightNumber = arguments[1];
		String src = arguments[2];
		String departDate = arguments[3];
		String departTime = arguments[4];
		String dest = arguments[5];
		String arriveDate = arguments[6];
		String arriveTime = arguments[7];

		Airline airline = new Airline(airlineName);
		Airline updatedAirline = null;
		if (readFromFile == true) {
			// append the flights mentioned in the file here, throw an error if airline
			// names mismatch
			TextParser parser = new TextParser(path, airline);
			try {
				updatedAirline = (Airline) parser.parse();
			} catch (ParserException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			updatedAirline = airline;
		}
		
		// create the flight object from the details provided in the command line
		Flight flight = new Flight(Integer.parseInt(flightNumber), src, departDate + " " + departTime, dest,
				arriveDate + " " + arriveTime);
		// add to the the flights in the file
		updatedAirline.addFlight(flight);
		System.out.println("Flight added to the airline "+updatedAirline.getName());
		// print details here if mentioned in options
		if (printDetails) {
			for (Flight f : updatedAirline.getFlights()) {
				System.out.println(f.toString());
			}
		}
		// if read from file is true, dump the whole airline object there
		if(readFromFile == true) {
			TextDumper dumper = new TextDumper(path);
			try {
				dumper.dump(updatedAirline);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to write to the file.");
				System.exit(1);
			}
		}

	}
	
	/**
	 * prints the read me on console.
	 */
	public static void printReadMe() {
		System.out.print("Welcome to the class project of CS510!!\n\nMy name is Aman Singh Solanki. "
				+ "This is the README for project 2. This program is used to input new airline details in the system. An airline "
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
