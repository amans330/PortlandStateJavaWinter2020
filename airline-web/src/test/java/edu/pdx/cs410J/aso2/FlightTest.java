package edu.pdx.cs410J.aso2;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FlightTest {

    @Test
    public void flightNumberContainsDigits() {
        int flightNumber = 123;
        String src = "PDX";
        Date depart = Util.getDateFromString("03/21/2020 07:00 AM");
        Date arrive = Util.getDateFromString("03/21/2020 09:00 AM");
        String dest = "JFK";
        Flight flight = new Flight(flightNumber, src, depart, dest, arrive);
        assertThat(flight.getNumber(), equalTo(123));
    }
}
