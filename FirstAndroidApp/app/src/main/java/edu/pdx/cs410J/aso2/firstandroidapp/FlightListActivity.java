package edu.pdx.cs410J.aso2.firstandroidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
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
        ArrayAdapter<Flight> adapter = new FlightAdapter(this);
        listview.setAdapter(adapter);

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
                adapter.add(flight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listview.setAdapter(adapter);
    }

    class FlightAdapter extends ArrayAdapter<Flight> {

        public FlightAdapter(@NonNull Context context) {
            super(context, R.layout.flight_view);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View flightView, @NonNull ViewGroup parent) {
            if (flightView == null) {
                flightView = getLayoutInflater().inflate(R.layout.flight_view, parent, false);
            }

            Flight flight = getItem(position);

            TextView textView = flightView.findViewById(R.id.flight_details);
            textView.setText(Util.prettyPrint(flight));

            return flightView;
        }
    }

}
