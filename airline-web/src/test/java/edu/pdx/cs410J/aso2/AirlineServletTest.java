package edu.pdx.cs410J.aso2;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AirlineServletTest {

//  @Test
//  public void initiallyServletContainsNoAirlineEntries() throws ServletException, IOException {
//    AirlineServlet servlet = new AirlineServlet();
//
//    HttpServletRequest request = mock(HttpServletRequest.class);
//    HttpServletResponse response = mock(HttpServletResponse.class);
//    PrintWriter pw = mock(PrintWriter.class);
//
//    when(response.getWriter()).thenReturn(pw);
//
//    servlet.doGet(request, response);
//
//    int expectedWords = 0;
//    verify(pw).println(Messages.formatWordCount(expectedWords));
//    verify(response).setStatus(HttpServletResponse.SC_OK);
//  }

    @Test
    public void addFlightToAirline() throws ServletException, IOException {
        String airlineName = "Aman Airlines";
        String flightNum = "123";
        String src = "PDX";
        String depart = "03/05/2020 01:24 pm";
        String dest = "JFK";
        String arrive = "03/05/2020 07:50 pm";
        AirlineServlet servlet = createFlight(airlineName, flightNum, src, depart, dest, arrive);
        Airline airline = servlet.getAirline(airlineName);
        assertThat(airline, not(nullValue()));
        List<Flight> flightsList = airline.getFlights();
        for(Flight flights : flightsList) {
            assertThat(flights.getNumber(), equalTo(Integer.parseInt(flightNum)));
        }
    }

    @Test
    public void getFlightsOfAirline() throws IOException, ServletException {
        String airlineName = "Aman Airlines";
        String flightNum = "123";
        String src = "PDX";
        String depart = "03/05/2020 01:24 pm";
        String dest = "JFK";
        String arrive = "03/05/2020 07:50 pm";
        AirlineServlet servlet = createFlight(airlineName, flightNum, src, depart, dest, arrive);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("airline")).thenReturn(airlineName);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ArgumentCaptor<String> textWrittenToWriter = ArgumentCaptor.forClass(String.class);
        PrintWriter pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);
        servlet.doGet(request, response);
        verify(pw).println(textWrittenToWriter.capture());
        String xml = textWrittenToWriter.getValue();
        assertThat(xml, containsString(airlineName));
        assertThat(xml, containsString(flightNum));
    }


//  @Test
//  public void addOneWordToDictionary() throws ServletException, IOException {
//    AirlineServlet servlet = new AirlineServlet();
//
//    String word = "TEST WORD";
//    String definition = "TEST DEFINITION";
//
//    HttpServletRequest request = mock(HttpServletRequest.class);
//    when(request.getParameter("word")).thenReturn(word);
//    when(request.getParameter("definition")).thenReturn(definition);
//
//    HttpServletResponse response = mock(HttpServletResponse.class);
//    PrintWriter pw = mock(PrintWriter.class);
//
//    when(response.getWriter()).thenReturn(pw);
//
//    servlet.doPost(request, response);
//    verify(pw).println(Messages.definedWordAs(word, definition));
//    verify(response).setStatus(HttpServletResponse.SC_OK);
//
//    assertThat(servlet.getDefinition(word), equalTo(definition));
//  }

    private AirlineServlet createFlight(String airline, String flightNumber, String src, String depart, String dest, String arrive) throws IOException, ServletException {
        AirlineServlet servlet = new AirlineServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("airline")).thenReturn(airline);
        when(request.getParameter("flightNumber")).thenReturn(flightNumber);
        when(request.getParameter("src")).thenReturn(src);
        when(request.getParameter("depart")).thenReturn(depart);
        when(request.getParameter("dest")).thenReturn(dest);
        when(request.getParameter("arrive")).thenReturn(arrive);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);
        servlet.doPost(request, response);
        return servlet;
    }
}
