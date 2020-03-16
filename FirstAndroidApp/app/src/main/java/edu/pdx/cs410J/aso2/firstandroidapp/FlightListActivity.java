package edu.pdx.cs410J.aso2.firstandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        ListView listview = findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

        // read the flights from the temp file
        try {
            File file = new File(getFilesDir(), "tempfile.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            List<Flight> result = new ArrayList<Flight>();
            while ((st = br.readLine()) != null) {
                String[] args = st.split("@");
                String flightNumber = args[1];
                String src = args[2];
                Date departDateTime = Util.getDateFromString(args[3]+" "+args[4]+" "+args[5]);
                String dest = args[6];
                Date arriveDateTime = Util.getDateFromString(args[7]+" "+args[8]+" "+args[9]);

                Flight flight = new Flight(Integer.parseInt(flightNumber), src, departDateTime, dest, arriveDateTime);
                adapter.add(Util.prettyPrint(flight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listview.setAdapter(adapter);
    }
}
