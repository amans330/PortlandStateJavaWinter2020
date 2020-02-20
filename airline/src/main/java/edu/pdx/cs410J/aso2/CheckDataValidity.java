package edu.pdx.cs410J.aso2;

import edu.pdx.cs410J.AirportNames;

public class CheckDataValidity {
	
	public static void checkArgumentsDataValidity(String[] args){
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
		
		// check if date is valid
		// regex source:
		// https://forums.asp.net/t/1945240.aspx?regular+expression+to+check+date+mm+dd+yyyy+which+allows+1+1+2013
		if (!departDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}") || !arriveDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
			Util.printErrorAndExit("date is not in the right format.");
		}

		// check if time is valid
		if (!departTime.matches("\\d{1,2}:\\d{1,2}") || !arriveTime.matches("\\d{1,2}:\\d{1,2}")) {
			Util.printErrorAndExit("time is not in the right format.");
		}

		// check src and dest length is 3
		if (src.length() != 3 || dest.length() != 3) {
			Util.printErrorAndExit("source or destination code is not 3 letters");
		}

		// check if src or dest contains digits, only letters allowed
		if (src.matches(".*\\d.*") || dest.matches(".*\\d.*")) {
			Util.printErrorAndExit("source or destination contains digits, only letters are allowed");
		}

		// check if flight number is an integer
		if (!flightNumber.matches("^[0-9]*$")) {
			Util.printErrorAndExit("flight number is not a number");
		}
		
		// check if am or pm is mentioned correctly in depart
		if(!(departampm.equalsIgnoreCase("am") || departampm.equalsIgnoreCase("pm"))) {
			Util.printErrorAndExit("am or pm is not mentioned correctly.");
		}
		
		// check if am or pm is mentioned correctly in depart
		if(!(arriveampm.equalsIgnoreCase("am") || arriveampm.equalsIgnoreCase("pm"))) {
			Util.printErrorAndExit("am or pm is not mentioned correctly.");
		}
		
		// check if airport src is valid
		if(!AirportNames.getNamesMap().containsKey(src.toUpperCase())) {
			Util.printErrorAndExit("Invalid source airport code.");
		}
		
		// check if airport dest is valid
		if(!AirportNames.getNamesMap().containsKey(dest.toUpperCase())) {
			Util.printErrorAndExit("Invalid destination airport code.");
		}
		
		// if all is well, return 
		return;
	}
}
