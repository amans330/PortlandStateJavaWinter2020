package edu.pdx.cs410J.aso2.firstandroidapp;

import android.content.Intent;
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

public class SearchFlightsActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.pdx.cs410J.aso2.firstandroidapp.MESSAGE";
    public static final String ALLGOOD = "allgood";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flights);
    }

    public void searchFlights(View view) {
        EditText editText = findViewById(R.id.airline_name);
        String airlineName = editText.getText().toString();
        editText = findViewById(R.id.source);
        String source = editText.getText().toString();
        editText = findViewById(R.id.destination);
        String destination = editText.getText().toString();

        /**
         * Start Checking Data Validity
         */
        String message = null;

        message = CheckDataValidity.checkAirline(airlineName);
        if (message != ALLGOOD) {
            showPopUp(view, message);
            return;
        }

        if(!source.equals("")){
            message = CheckDataValidity.checkSource(source);
            if (message != ALLGOOD) {
                showPopUp(view, message);
                return;
            }
        }

        if(!destination.equals("")){
            message = CheckDataValidity.checkDestination(destination);
            if (message != ALLGOOD) {
                showPopUp(view, message);
                return;
            }
        }

        if(source.equals("") || destination.equals("")) {
            if (!(source.equals("") && destination.equals(""))) {
                showPopUp(view, "One of source or destination was null. Both should be provided.");
                return;
            }
        }


        // if all good then check if the file with this airline name exists
        File file = new File(getFilesDir(), airlineName);
        // if file does not exist, show pop up with message
        if (file.exists() == false) {
            showPopUp(view, "No airline found with this name.");
            return;
        }

        // else airline exists, now find matching flights
        Airline temp = new Airline(airlineName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            List<Flight> result = new ArrayList<Flight>();
            while ((st = br.readLine()) != null) {
                String[] args = st.split("@");
                if (args[0] == null || args[0].trim().equals(""))
                    continue;
                if (args.length != 10) {
                    br.close();
                    showPopUp(view, "File is Malformatted. Incorrect number of arguments found!");
                    return;
                }
                if ((source.equals("") && destination.equals("")) || (args[0].equalsIgnoreCase(airlineName) && args[2].equalsIgnoreCase(source) && args[6].equalsIgnoreCase(destination))) {
                    // If Everything is fine. create the flight object now.
                    String flightNumber = args[1];
                    String src = args[2];
                    Date departDateTime = Util.getDateFromString(args[3] + " " + args[4] + " " + args[5]);
                    String dest = args[6];
                    Date arriveDateTime = Util.getDateFromString(args[7] + " " + args[8] + " " + args[9]);

                    Flight flight = new Flight(Integer.parseInt(flightNumber), src, departDateTime, dest, arriveDateTime);
                    temp.addFlight(flight);
                }
            }
            if(temp.getFlights().size() == 0){
                showPopUp(view, "No matching Flights found.");
                return;
            }

            // Save the results to a temp file named tempfile.txt
            File tempfile = new File(getFilesDir(), "tempfile.txt");
            TextDumper dumper = new TextDumper(tempfile);
            dumper.saveDataToFile(temp);

            // go to the next view ie list view
            Intent intent = new Intent(this, FlightListActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void launchFlightListView(View view) {
        Intent intent = new Intent(this, FlightListActivity.class);
        startActivity(intent);
    }

    public void showPopUp(View view, String message) {
        // create a pop up message here
        Snackbar mySnackbar = Snackbar.make(view, message, 3000);
        mySnackbar.show();
    }
}
