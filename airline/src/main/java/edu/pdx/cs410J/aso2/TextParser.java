package edu.pdx.cs410J.aso2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

public class TextParser implements AirlineParser {

	String filepath;
	Airline airline;
	
	/**
	 * Reads the file at the given path. Calls a check method to determine if data is in the 
	 * correct format or not. Checks if right number of arguments are present. Creates a new file 
	 * if file doesn't exist at the given path. Reads all the flights from the flight and returns 
	 * the airline object containing those to the caller.
	 * @return AirlineObject parsed from the file
	 */
	@Override
	public AbstractAirline<?> parse() throws ParserException {
		File file = new File(filepath);
		String st;
		BufferedReader br;
		try {
			// if file does not exist, create a new empty file at that path
			if(file.exists() == false) {
				file.createNewFile();
				System.out.println("New file created.");
			}
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String[] args = st.split("@");
				// the text file is empty, nothing to read. Return the airline object with no changes
				if(args[0] == null || args[0].trim().equals("")) {
					br.close();
					return airline;
				}
				if(args.length != 10) {
					br.close();
					System.out.println("File is Malformatted. Incorrect number of arguments found!");
					throw new ParserException("");
				}
				if(CheckDataValidity.checkArgumentsDataValidity(args) == false) {
					br.close();
					System.out.println("File is Malformatted or contains Invalid data.");
					throw new ParserException("");
				}
				// airline name is case insensitive
				if(!airline.getName().equalsIgnoreCase(args[0])) {
					br.close();
					System.out.println("Provided Airline name does not match with the file.");
					throw new ParserException("");
				}
				
				// Everything is fine. create the flight object now.
				String flightNumber = args[1];
				String src = args[2];
				Date departDateTime = Util.getDateFromString(args[3]+" "+args[4]+" "+args[5]);
				String dest = args[6];
				Date arriveDateTime = Util.getDateFromString(args[7]+" "+args[8]+" "+args[9]);
				
				Flight flight = new Flight(Integer.parseInt(flightNumber), src, departDateTime, dest, arriveDateTime);
				airline.addFlight(flight);
			}
			br.close();
			return airline;
		} catch (IOException e) {
			System.out.println("File cannot be created.");
			System.exit(1);
		}

		return airline;
	}
	
	/**
	 * Constructor method
	 * @param filepath path of the file to read from
	 * @param airline Airline object to work on.
	 */
	
	public TextParser(String filepath, Airline airline) {
		this.filepath = filepath;
		this.airline = airline;
	}
}
