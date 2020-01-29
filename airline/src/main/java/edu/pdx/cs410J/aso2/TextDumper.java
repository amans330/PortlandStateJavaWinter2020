package edu.pdx.cs410J.aso2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

public class TextDumper implements AirlineDumper {
	
	String filepath;
	/**
	 * writes the airline object to a file. Each flight is on a new line
	 * @param airline The airline object to be written to the file
	 */
	@Override
	public void dump(AbstractAirline airline) throws IOException {
		try {
			FileWriter writer = new FileWriter(filepath);
			BufferedWriter bw = new BufferedWriter(writer);
			// first empty the file
			bw.write("");
			for(int i = 0; i < airline.getFlights().size(); i++) {
				bw.append(airline.getName()+"@"+((Airline)airline).getFlights().get(i).getFlightForFile());
				if(i < airline.getFlights().size()-1)
					bw.newLine();
			}
			System.out.println("File written successfully!");
			bw.close();
		} catch (IOException e) {
			System.out.print("Cannot write to file.");
			System.exit(1);
		}

	}
	
	/**
	 * constructor method. takes the filepath as input. filepath is the path of the file to write to.
	 * @param filepath
	 */
	public TextDumper (String filepath) {
		this.filepath = filepath;
	}

}
