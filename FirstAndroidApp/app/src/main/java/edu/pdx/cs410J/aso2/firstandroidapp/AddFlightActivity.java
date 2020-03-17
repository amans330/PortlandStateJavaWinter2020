package edu.pdx.cs410J.aso2.firstandroidapp;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;

import edu.pdx.cs410J.ParserException;

public class AddFlightActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.pdx.cs410J.aso2.firstandroidapp.MESSAGE";
    public static final String ALLGOOD = "allgood";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
    }

    /** Called when the user taps the Submit button */
    public void sendFormData(View view) {
        EditText editText = findViewById(R.id.airline_name);
        String airlineName = editText.getText().toString();
        editText = findViewById(R.id.flight_number);
        String flightnumber = editText.getText().toString();
        editText = findViewById(R.id.source);
        String source = editText.getText().toString();
        editText = findViewById(R.id.departure);
        String departure = editText.getText().toString();
        editText = findViewById(R.id.destination);
        String destination = editText.getText().toString();
        editText = findViewById(R.id.arrival);
        String arrival = editText.getText().toString();

        /**
         * Start Checking Data Validity
         */
        String message = null;

        message = CheckDataValidity.checkAirline(airlineName);
        if (message != ALLGOOD) {
            showPopUp(view, message);
            return;
        }

        message = CheckDataValidity.checkFlightNumber(flightnumber);
        if (message != ALLGOOD) {
            showPopUp(view, message);
            return;
        }

        message = CheckDataValidity.checkSource(source);
        if (message != ALLGOOD) {
            showPopUp(view, message);
            return;
        }

        message = CheckDataValidity.checkDeparture(departure);
        if (message != ALLGOOD) {
            showPopUp(view, message);
            return;
        }

        message = CheckDataValidity.checkDestination(destination);
        if (message != ALLGOOD) {
            showPopUp(view, message);
            return;
        }

        message = CheckDataValidity.checkArrival(arrival);
        if (message != ALLGOOD) {
            showPopUp(view, message);
            return;
        }

        //create airline object with this data, we'll append data found in file to this object
        Airline airline = new Airline(airlineName);
        List<Flight> flightList = new ArrayList<Flight>();

        //create flight with data passed from user here
        Flight flight = new Flight(Integer.parseInt(flightnumber), source, Util.getDateFromString(departure), destination,
                Util.getDateFromString(arrival));
        flightList.add(flight);
        airline.setFlights(flightList);

        //check if the file with this airline name exists, if yes then add file data to above airline object
        File file = new File(getFilesDir(), airlineName);
        String st;
        BufferedReader br;

        try {
            // if file does not exist, create a new empty file at that path, and post this airline object
            if(file.exists() == false) {
                file.createNewFile();
                Log.e("AddFlight", "New file created.");
                TextDumper dumper = new TextDumper(file);
                dumper.saveDataToFile(airline);
            }else{
                br = new BufferedReader(new FileReader(file));
                while ((st = br.readLine()) != null) {
                    String[] args = st.split("@");
                    // the text file is empty, nothing to read. Return the airline object with no changes
                    if(args[0] == null || args[0].trim().equals("")) {
                        Log.e("AddFlight", "New file created.");
                        TextDumper dumper = new TextDumper(file);
                        dumper.saveDataToFile(airline);
                        return;
                    }
                    if(args.length != 10) {
                        br.close();
                        System.out.println("File is Malformatted. Incorrect number of arguments found!");
                        throw new ParserException("File is Malformatted");
                    }

                    // check if airline names match. airline name is case insensitive
                    if(!airline.getName().equalsIgnoreCase(args[0])) {
                        br.close();
                        Log.e("AddFlight", "Provided Airline name does not match with the file.");
                        throw new ParserException("Provided Airline name does not match with the file.");
                    }

                    // If Everything is fine. create the flight object now.
                    String flightNumber = args[1];
                    String src = args[2];
                    Date departDateTime = Util.getDateFromString(args[3]+" "+args[4]+" "+args[5]);
                    String dest = args[6];
                    Date arriveDateTime = Util.getDateFromString(args[7]+" "+args[8]+" "+args[9]);

                    flight = new Flight(Integer.parseInt(flightNumber), src, departDateTime, dest, arriveDateTime);
                    airline.addFlight(flight);
                }
                br.close();
                TextDumper dumper = new TextDumper(file);
                dumper.saveDataToFile(airline);
            }

            // create a pop up message here
            Snackbar mySnackbar = Snackbar.make(view, "Flight "+flight.toString()+" Added Successfully!!", 3000);
            mySnackbar.show();

        } catch (IOException | ParserException e) {
//            Log.e("AddFlight", e.getMessage());
            e.printStackTrace();
        }
    }

    public void showPopUp(View view, String message) {
        // create a pop up message here
        Snackbar mySnackbar = Snackbar.make(view, message, 3000);
        mySnackbar.show();
    }
}
