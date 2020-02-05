package edu.pdx.cs410J.aso2;

import java.util.Date;

import edu.pdx.cs410J.AirportNames;

public class CheckDataValidity {
	
	public static boolean checkArgumentsDataValidity(String[] args){
		// check for argument list
		// check if there is no option in between
		for (String arg : args) {
			if (arg.charAt(0) == '-') {
				System.out.print("Not enough arguments passed.");
				return false;
			}

			// check empty elements
			if (arg.trim().equalsIgnoreCase("")) {
				System.out.print("Empty strings are not allowed!!");
				return false;
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
			System.out.print("date is not in the right format.");
			return false;
		}

		// check if time is valid
		if (!departTime.matches("\\d{1,2}:\\d{2}") || !arriveTime.matches("\\d{1,2}:\\d{2}")) {
			System.out.print("time is not in the right format.");
			return false;
		}

		// check src and dest length is 3
		if (src.length() != 3 || dest.length() != 3) {
			System.out.print("source or destination code is not 3 letters");
			return false;
		}

		// check if src or dest contains digits, only letters allowed
		if (src.matches(".*\\d.*") || dest.matches(".*\\d.*")) {
			System.out.print("source or destination contains digits, only letters are allowed");
			return false;
		}

		// check if flight number is an integer
		if (!flightNumber.matches("^[0-9]*$")) {
			System.out.print("flight number is not a number");
			return false;
		}
		
		// check if am or pm is mentioned correctly in depart
		if(!(departampm.equalsIgnoreCase("am") || departampm.equalsIgnoreCase("pm"))) {
			System.out.print("am or pm is not mentioned correctly.");
			return false;
		}
		
		// check if am or pm is mentioned correctly in depart
		if(!(arriveampm.equalsIgnoreCase("am") || arriveampm.equalsIgnoreCase("pm"))) {
			System.out.print("am or pm is not mentioned correctly.");
			return false;
		}
		
		// check if airport src is valid
		if(!AirportNames.getNamesMap().containsKey(src.toUpperCase())) {
			System.out.print("Invalid source airport code.");
			return false;
		}
		
		// check if airport dest is valid
		if(!AirportNames.getNamesMap().containsKey(dest.toUpperCase())) {
			System.out.print("Invalid destination airport code.");
			return false;
		}
		
		// if all is well, return true
		return true;
	}
}
