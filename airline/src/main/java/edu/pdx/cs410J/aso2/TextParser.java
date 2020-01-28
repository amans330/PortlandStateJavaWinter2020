package edu.pdx.cs410J.aso2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

public class TextParser implements AirlineParser {

	String filepath;
	Airline airline;

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
				// the text file is empty
				if(args[0] == null || args[0].trim().equals("")) {
					br.close();
					return airline;
				}
				if(args.length != 8) {
					br.close();
					System.out.println("File is Malformatted. Incorrect number of arguments found!");
					throw new ParserException("");
				}
				if(CheckDataValidity.checkArgumentsDataValidity(args) == false) {
					br.close();
					System.out.println("File is Malformatted. Invalid data.");
					throw new ParserException("");
				}
				if(!airline.getName().equalsIgnoreCase(args[0])) {
					br.close();
					System.out.println("Provided Airline name does not match with the file.");
					throw new ParserException("");
				}
				
				String flightNumber = args[1];
				String src = args[2];
				String departDate = args[3];
				String departTime = args[4];
				String dest = args[5];
				String arriveDate = args[6];
				String arriveTime = args[7];
				Flight flight = new Flight(Integer.parseInt(flightNumber), src, departDate + " " + departTime, dest,
						arriveDate + " " + arriveTime);
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
	
	public TextParser(String filepath, Airline airline) {
		this.filepath = filepath;
		this.airline = airline;
	}
}
