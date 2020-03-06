package edu.pdx.cs410J.aso2;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AirlineTest {

    @Test
    public void setAirlineName() {
        Airline airline = new Airline("Aman");
        assertThat(airline.getName(), equalTo("Aman"));
    }

    @Test
    public void addFlightTest() {
        Airline airline = new Airline("Aman");
        int flightNumber = 123;
        String src = "PDX";
        Date depart = Util.getDateFromString("03/21/2020 07:00 AM");
        Date arrive = Util.getDateFromString("03/21/2020 09:00 AM");
        String dest = "JFK";
        Flight flight = new Flight(flightNumber, src, depart, dest, arrive);
        airline.addFlight(flight);
        assertThat(airline.getFlights().size(), equalTo(1));
    }


}
