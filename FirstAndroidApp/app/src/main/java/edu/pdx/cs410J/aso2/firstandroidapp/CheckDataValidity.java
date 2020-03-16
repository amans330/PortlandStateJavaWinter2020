package edu.pdx.cs410J.aso2.firstandroidapp;

import edu.pdx.cs410J.AirportNames;

public class CheckDataValidity {
	public static final String ALLGOOD = "allgood";

	public static String checkAirline(String value) {
		// null and empty check
		if (value == null || value.isEmpty())
			return "Null or Empty value passed for Airline";
		return ALLGOOD;
	}

	public static String checkFlightNumber(String value) {
		// null and empty check
		if (value == null || value.isEmpty())
			return "Null or Empty value passed for FlightNumber";

		// check if flight number is an integer
		if (!value.matches("^[0-9]*$"))
			return ("flight number is not a number");

		return ALLGOOD;
	}

	public static String checkSource(String value) {
		// null and empty check
		if (value == null || value.isEmpty())
			return "Null or Empty value passed for Source";

		// check src and dest length is 3
		if (value.length() != 3)
			return ("source code is not 3 letters");

		// check if src or dest contains digits, only letters allowed
		if (value.matches(".*\\d.*"))
			return ("source contains digits, only letters are allowed");

		// check if airport src is valid
		if (!AirportNames.getNamesMap().containsKey(value.toUpperCase()))
			return ("Invalid source airport code.");

		return ALLGOOD;
	}

	public static String checkDestination(String value) {
		// null and empty check
		if (value == null || value.isEmpty())
			return "Null or Empty value passed for Destination";

		// check src and dest length is 3
		if (value.length() != 3)
			return ("destination code is not 3 letters");

		// check if src or dest contains digits, only letters allowed
		if (value.matches(".*\\d.*"))
			return ("destination contains digits, only letters are allowed");

		// check if airport src is valid
		if (!AirportNames.getNamesMap().containsKey(value.toUpperCase()))
			return ("Invalid destination airport code.");

		return ALLGOOD;
	}

	public static String checkDeparture(String value) {
		// null and empty check
		if (value == null || value.isEmpty())
			return "Null or Empty value passed for Departure";

		String[] depart = value.split(" ");

		// length check
		if (depart.length != 3)
			return ("Not enough arguments for Departure");

		String departDate = depart[0];
		String departTime = depart[1];
		String departampm = depart[2];

		// check if date is valid
		if (!departDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
			return ("depart date is not in the right format.");
		}

		// check if time is valid
		if (!departTime.matches("\\d{1,2}:\\d{1,2}")) {
			return ("depart time is not in the right format.");
		}

		// check if am or pm is mentioned correctly in depart
		if (!(departampm.equalsIgnoreCase("am") || departampm.equalsIgnoreCase("pm"))) {
			return ("depart am or pm is not mentioned correctly.");
		}
		
		return ALLGOOD;
	}
	
	public static String checkArrival(String value) {
		// null and empty check
		if (value == null || value.isEmpty())
			return "Null or Empty value passed for Arrival";

		String[] arrive = value.split(" ");

		// length check
		if (arrive.length != 3)
			return ("Not enough arguments for Arrival");

		String arriveDate = arrive[0];
		String arriveTime = arrive[1];
		String arriveampm = arrive[2];

		// check if date is valid
		if (!arriveDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
			return ("arrival date is not in the right format.");
		}

		// check if time is valid
		if (!arriveTime.matches("\\d{1,2}:\\d{1,2}")) {
			return ("arrival time is not in the right format.");
		}

		// check if am or pm is mentioned correctly in depart
		if (!(arriveampm.equalsIgnoreCase("am") || arriveampm.equalsIgnoreCase("pm"))) {
			return ("arrival am or pm is not mentioned correctly.");
		}
		
		return ALLGOOD;
	}

	public static String checkArgumentsDataValidity(String[] args){
		// check for argument list
		// check if there is no option in between
		for (String arg : args) {
			if (arg.charAt(0) == '-') {
				Util.printErrorAndExit("Not enough arguments passed.");
			}

			// check empty elements
			if (arg.trim().equalsIgnoreCase("")) {
				Util.printErrorAndExit("Empty strings are not allowed!!");
			}
		}
		String airlineName = args[0];
		String flightNumber = args[1];
		String src = args[2];
		String departDate = args[3];
		String departTime = args[4];
		String departampm = args[5];
		String dest = args[6];
		String arriveDate = args[7];
		String arriveTime = args[8];
		String arriveampm = args[9];

		// null and empty check
		if (airlineName == null || airlineName.isEmpty())
			return "Null or Empty value passed for Airline";

		// check if date is valid
		// regex source:
		// https://forums.asp.net/t/1945240.aspx?regular+expression+to+check+date+mm+dd+yyyy+which+allows+1+1+2013
		if (!departDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}") || !arriveDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
			return ("date is not in the right format.");
		}

		// check if time is valid
		if (!departTime.matches("\\d{1,2}:\\d{1,2}") || !arriveTime.matches("\\d{1,2}:\\d{1,2}")) {
			return ("time is not in the right format.");
		}

		// check src and dest length is 3
		if (src.length() != 3 || dest.length() != 3) {
			return ("source or destination code is not 3 letters");
		}

		// check if src or dest contains digits, only letters allowed
		if (src.matches(".*\\d.*") || dest.matches(".*\\d.*")) {
			return ("source or destination contains digits, only letters are allowed");
		}

		// check if flight number is an integer
		if (!flightNumber.matches("^[0-9]*$")) {
			return ("flight number is not a number");
		}

		// check if am or pm is mentioned correctly in depart
		if(!(departampm.equalsIgnoreCase("am") || departampm.equalsIgnoreCase("pm"))) {
			return ("am or pm is not mentioned correctly.");
		}

		// check if am or pm is mentioned correctly in depart
		if(!(arriveampm.equalsIgnoreCase("am") || arriveampm.equalsIgnoreCase("pm"))) {
			return ("am or pm is not mentioned correctly.");
		}

		// check if airport src is valid
		if(!AirportNames.getNamesMap().containsKey(src.toUpperCase())) {
			return ("Invalid source airport code.");
		}

		// check if airport dest is valid
		if(!AirportNames.getNamesMap().containsKey(dest.toUpperCase())) {
			return ("Invalid destination airport code.");
		}

		// if all is well, return
		return ALLGOOD;
	}

}
