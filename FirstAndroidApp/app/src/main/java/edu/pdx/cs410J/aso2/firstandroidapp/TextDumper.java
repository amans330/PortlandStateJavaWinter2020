package edu.pdx.cs410J.aso2.firstandroidapp;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextDumper {
    private File file;

    public TextDumper(File file) {
        this.file = file;
    }

    // saves airline data to file with name as airline name
    public void saveDataToFile(Airline airline) throws IOException {
        try {
            FileWriter writer = new FileWriter(file);
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
            Log.e("AddFlight", e.getMessage());
        }
    }
}
