package edu.pdx.cs410J.aso2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

public class PrettyPrinter implements AirlineDumper {

	String filepath;

	@Override
	public void dump(AbstractAirline airline) throws IOException {
		if (filepath != null) { // print to file
			try {
				FileWriter writer = new FileWriter(filepath);
				BufferedWriter bw = new BufferedWriter(writer);
				// first empty the file
				bw.write("");
				bw.append("The flights for " + airline.getName() + " are as follows:");
				bw.newLine();
				for (Flight flight : ((Airline) airline).getFlights()) {
					bw.append(Util.prettyPrint(flight));
					bw.newLine();
				}
				System.out.println("Pretty printed to File successfully!");
				bw.close();
			} catch (IOException e) {
				System.out.print("Cannot write to file.");
				System.exit(1);
			}

		}else { // print to console
			System.out.println("The flights for " + airline.getName() + " are as follows:");
			for (Flight flight : ((Airline) airline).getFlights()) {
				System.out.println(Util.prettyPrint(flight));
			}
		}

	}

	/**
	 * constructor method. takes the filepath as input. filepath is the path of the
	 * file to write to.
	 * 
	 * @param filepath
	 */
	public PrettyPrinter(String filepath) {
		this.filepath = filepath;
	}

}
