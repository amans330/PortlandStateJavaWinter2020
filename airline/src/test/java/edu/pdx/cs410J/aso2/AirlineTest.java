package edu.pdx.cs410J.aso2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AirlineTest {

    @Test
    public void airlineNumberShouldBeNotEmpty(){
        Airline airline = new Airline("abc");
        assertThat(airline.getName(), is(not("")));
    }

    @Test
    public void airlineShouldAddFlights(){
        Airline airline = new Airline("abc");
        Flight flight = new Flight(0123, "abc", "3/15/2017 10:39", "xyz", "03/2/2017 01:03");
        airline.addFlight(flight);
        assertThat(airline.getFlights().size(), equalTo(1));
    }

}
