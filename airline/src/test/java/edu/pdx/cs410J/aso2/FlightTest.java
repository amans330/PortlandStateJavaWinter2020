package edu.pdx.cs410J.aso2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {
  
  @Test
  public void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(0123, "abc", "3/15/2017 10:39", "xyz", "03/2/2017 01:03");
    assertThat(flight.getArrivalString(), not(nullValue()));
  }

  @Test
  public void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight(42, "abc", "3/15/2017 10:39", "xyz", "03/2/2017 01:03");
    assertThat(flight.getNumber(), equalTo(42));
  }

  @Test
  public void flightNumberShouldBeInteger(){
    Flight flight = new Flight(012, "abc", "3/15/2017 10:39", "xyz", "03/2/2017 01:03");
    assertThat(flight.getNumber(), is(instanceOf(Integer.class)));
  }

  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight(0123, "abc", "3/15/2017 10:39", "xyz", "03/2/2017 01:03");
    assertThat(flight.getDeparture(), is(nullValue()));
  }

  @Test
  public void srcShouldBeThreeLetters(){
    Flight flight = new Flight(0123, "abc", "3/15/2017 10:39", "xyz", "03/2/2017 01:03");
    assertThat(flight.getSource().length(), equalTo(3));
  }

  @Test
  public void destShouldBeThreeLetters(){
    Flight flight = new Flight(0123, "abc", "3/15/2017 10:39", "xyz", "03/2/2017 01:03");
    assertThat(flight.getDestination().length(), equalTo(3));
  }

  @Test(expected = NumberFormatException.class)
  public void arrivalDateIsNotNumbers(){
    Flight flight = new Flight(0123, "abc", "3/15/201a 10:39", "xyz", "03/2/201a 01:03");

    for(String str : flight.getArrivalString().split(" ")[0].split("/")){
      Integer.parseInt(str);
    }
  }

  @Test(expected = NumberFormatException.class)
  public void arrivalTimeIsNotNumbers(){
    Flight flight = new Flight(0123, "abc", "3/15/201a 10:39", "xyz", "03/2/201a 01:0a");
    for(String str : flight.getArrivalString().split(" ")[1].split(":")){
      Integer.parseInt(str);
    }
  }

  @Test(expected = NumberFormatException.class)
  public void departureDateIsNotNumber(){
    Flight flight = new Flight(0123, "abc", "3/15/201a 10:39", "xyz", "03/2/2012 01:03");
    for(String str : flight.getDepartureString().split(" ")[0].split("/")){
      Integer.parseInt(str);
    }
  }

  @Test(expected = NumberFormatException.class)
  public void departureTimeIsNotNumbers(){
    Flight flight = new Flight(0123, "abc", "3/15/2015 abc:39", "xyz", "03/2/201a abc:56");
    for(String str : flight.getDepartureString().split(" ")[1].split(":")){
      Integer.parseInt(str);
    }
  }


}
