package edu.pdx.cs410J.aso2;

import java.util.Arrays;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
	  String[] arguments = null;
	  String[] options = null;
	  boolean printDetails = false;
	  
	  // 0 arguments check
      if(args.length == 0){
          System.out.print("There were no arguments passed!! Please read the ReadMe and then proceed.");
          System.exit(1);
      }
      
      // total elements should be at least 8 and not more than 10
      if(args.length < 8 || args.length > 10){
    	// this can only be readme 
    	  if(args[0].equals("-README")) {
    		  System.out.print("Welcome to the class project of CS510!!\n\nMy name is Aman Singh Solanki. "
  			  		+ "This is the README for project 1. This program is used to input new airline details in the system. An airline "
  			  		+ "has a name and has multiple flights operating from it. \n\nA Flight in turn has all the details a flight "
  			  		+ "can have like flight number, depart and arrive timings, date source and destinations. \n\n"
  			  		+ "Usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n"
  			  		+ "args are (in this order):\n"
  			  		+ "airline: The name of the airline\n"
  			  		+ "flightNumber: The flight number\n" + 
  			  		"src: Three-letter code of departure airport\n" + 
  			  		"depart: Departure date and time (24-hour time)\n" + 
  			  		"dest: Three-letter code of arrival airport\n" + 
  			  		"arrive: Arrival date and time (24-hour time)\n\n"
  			  		+ "options are (options may appear in any order):\n"
  			  		+ "-print: Prints a description of the new flight\n" + 
  			  		"-README: Prints a README for this project and exits\n\n"
  			  		+ "There are various rules to input data:\n1. A flight number should be an integer. "
  			  		+ "\n2. Date should be in the format mm/dd/yyyy\n3. src and dest should be 3 letters");
  			  System.exit(0);
    	  }
          System.out.print("Not enough or too many arguments passed!! Please read the ReadMe and then proceed.");
          System.exit(1);
      }
      
      // copy into respective lists
      if(args.length == 8) {
    	  arguments = Arrays.copyOfRange(args, 0, 8);
      }else if(args.length == 9) {
    	  options = Arrays.copyOfRange(args, 0, 1);
    	  arguments = Arrays.copyOfRange(args, 1, 9);
      }else {
    	  options = Arrays.copyOfRange(args, 0, 2);
    	  arguments = Arrays.copyOfRange(args, 2, 10);
      }
      
      if(options != null) {
    	  //check for options list
    	  // check if an argument is put here
    	  for(String option : options) {
    		  if(option.charAt(0) != '-'){
    			  System.out.print("Too many arguments passed.");
    			  System.exit(1);
    		  }
    		  // else process them
    		  if(option.equals("-README")){
    			  System.out.print("Welcome to the class project of CS510!!\n\nMy name is Aman Singh Solanki. "
    			  		+ "This is the README for project 1. This program is used to input new airline details in the system. An airline "
    			  		+ "has a name and has multiple flights operating from it. \n\nA Flight in turn has all the details a flight "
    			  		+ "can have like flight number, depart and arrive timings, date source and destinations. \n\n"
    			  		+ "Usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n"
    			  		+ "args are (in this order):\n"
    			  		+ "airline: The name of the airline\n"
    			  		+ "flightNumber: The flight number\n" + 
    			  		"src: Three-letter code of departure airport\n" + 
    			  		"depart: Departure date and time (24-hour time)\n" + 
    			  		"dest: Three-letter code of arrival airport\n" + 
    			  		"arrive: Arrival date and time (24-hour time)\n\n"
    			  		+ "options are (options may appear in any order):\n"
    			  		+ "-print: Prints a description of the new flight\n" + 
    			  		"-README: Prints a README for this project and exits\n\n"
    			  		+ "There are various rules to input data:\n1. A flight number should be an integer. "
    			  		+ "\n2. Date should be in the format mm/dd/yyyy\n3. src and dest should be 3 letters");
    			  System.exit(0);
    		  }
    		  if(option.equals("-print")){
    			  printDetails = true;
    		  }
    	  }
      }
      
      // check for argument list
      //check if there is no option in between
      for(String arg : arguments){
          if(arg.charAt(0) == '-'){
              System.out.print("Not enough arguments passed");
              System.exit(1);
          }

          //check empty elements
          if(arg.trim().equalsIgnoreCase("")){
              System.out.print("Empty strings are not allowed!!");
              System.exit(1);
          }
      }
      
      
      String airlineName = arguments[0];
      String flightNumber = arguments[1];
      String src = arguments[2];
      String departDate = arguments[3];
      String departTime = arguments[4];
      String dest = arguments[5];
      String arriveDate = arguments[6];
      String arriveTime = arguments[7];
      
      // check if date is valid 
      // regex source: https://forums.asp.net/t/1945240.aspx?regular+expression+to+check+date+mm+dd+yyyy+which+allows+1+1+2013
      if(!departDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}") || !arriveDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
    	  System.out.print("date is not in the right format.");
		  System.exit(1);
      }
      
      //check if time is valid
      if(!departTime.matches("\\d{1,2}:\\d{2}") || !arriveTime.matches("\\d{1,2}:\\d{2}")) {
    	  System.out.print("time is not in the right format.");
		  System.exit(1);
      }
      
      //check src and dest length is 3
      if(src.length() != 3 || dest.length() != 3) {
    	  System.out.print("source or destination code is not 3 letters");
		  System.exit(1);
      }
      
      // check if src or dest contains digits, only letters allowed
      if(src.matches(".*\\d.*") || dest.matches(".*\\d.*")) {
    	  System.out.print("source or destination contains digits, only letters are allowed");
		  System.exit(1);
      }
      
      // check if flight number is an integer
      if(!flightNumber.matches("^[0-9]*$")) {
    	  System.out.print("flight number is not a number");
		  System.exit(1);
      }
      
      Airline airline = new Airline(airlineName);
      Flight flight = new Flight(Integer.parseInt(flightNumber), src, departDate+" "+departTime, dest, arriveDate+" "+arriveTime);
      airline.addFlight(flight);
      
      // print details here
      if(printDetails) {
    	  System.out.println(flight.toString());
      }
      
  }

}