package edu.pdx.cs410J.aso2.firstandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReadMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);

        TextView textview = (TextView) findViewById(R.id.textView);
        textview.setText("This is a README for a project created by Aman Solanki. This is a small device Airline application. You can do things like creating an airline, entering flights, " +
                "pretty printing an airline and its flights, searching for flights,\n" +
                "etc. \n\nThe flights can be searched for a particular airline running between the provided source and destination airports. The matching flights will be pretty" +
                "printed for you in a nice and easy way." +
                "\n\nTo Add a flight click on add flight and enter flight details. The time is in the 12 hour format with am and pm provided by the user. Please " +
                "follow the format of MM/DD/YYYY HH:mm AM/PM." +
                "\n\n Click read me again anytime to read this again.");
    }
}
