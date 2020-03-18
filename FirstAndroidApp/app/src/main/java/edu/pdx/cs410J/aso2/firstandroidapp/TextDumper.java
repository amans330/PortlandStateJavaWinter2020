package edu.pdx.cs410J.aso2.firstandroidapp;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Dumoer class to put airline object into a text file
 */
public class TextDumper {
    private File file;

    /**
     *
     * @param file The file object to dump the file
     */
    public TextDumper(File file) {
        this.file = file;
    }

    // saves airline data to file with name as airline name

    /**
     *
     * @param airline Airline object to be dumped
     * @throws IOException
     */
    public void saveDataToFile(Airline airline) throws IOException {
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            // first empty the file
            bw.write("");
            List<Flight> flights = airline.getFlights();
            Collections.sort(flights);
            for(int i = 0; i < flights.size(); i++) {
                bw.append(airline.getName()+"@"+((Airline)airline).getFlights().get(i).getFlightForFile());
                if(i < airline.getFlights().size()-1)
                    bw.newLine();
            }
            System.out.println("File written successfully!");
            bw.close();
        } catch (IOException e) {
            Log.e("AddFlight", e.getMessage());
        }
    }
}
