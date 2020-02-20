package edu.pdx.cs410J.aso2;

import java.io.IOException;

import edu.pdx.cs410J.ParserException;

public class Converter {

	public static void main(String[] args) {
		String[] arguments = new String[2];
		if(args.length != 2)
			Util.printErrorAndExit("Incorrect number of arguments passed.");
		 TextParser parser = new TextParser(args[0], null);
		 try {
			Airline airline = (Airline)parser.parse();
			if(airline == null)
				Util.printErrorAndExit("The text file is empty. Nothing to write to XML");
			XmlDumper xmldumper = new XmlDumper(args[1]);
			xmldumper.dump(airline);
			System.out.println("Successfully converted from text to XML");
		} catch (ParserException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
