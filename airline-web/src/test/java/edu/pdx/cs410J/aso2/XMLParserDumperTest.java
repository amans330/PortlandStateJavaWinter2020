package edu.pdx.cs410J.aso2;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;
import org.testng.xml.XMLParser;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class XMLParserDumperTest{
    @Test
    public void TestXMLDumper() throws IOException, ParserConfigurationException {
        Airline airline = new Airline("Aman");
        int flightNumber = 123;
        String src = "PDX";
        Date depart = Util.getDateFromString("03/21/2020 07:00 AM");
        Date arrive = Util.getDateFromString("03/21/2020 09:00 AM");
        String dest = "JFK";
        Flight flight = new Flight(flightNumber, src, depart, dest, arrive);
        airline.addFlight(flight);

        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter((stringWriter));
        XmlDumper xmlDumper = new XmlDumper(pw);
        xmlDumper.dump(airline);

        String xmlString = stringWriter.toString();
        assertThat(xmlString, containsString("Aman"));
    }

    @Test
    public void TestXMLParser() throws IOException, ParserException {
        String xml = "<?xml version=\"1.0\" encoding=\"us-ascii\"?>\n" +
                "<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">\n" +
                "<airline>\n" +
                "  <name>AirDave</name>\n" +
                "  <flight>\n" +
                "    <number>123</number>\n" +
                "    <src>PDX</src>\n" +
                "    <depart>\n" +
                "      <date day=\"19\" month=\"7\" year=\"2020\"/>\n" +
                "      <time hour=\"13\" minute=\"2\"/>\n" +
                "    </depart>\n" +
                "    <dest>ORD</dest>\n" +
                "    <arrive>\n" +
                "      <date day=\"19\" month=\"7\" year=\"2020\"/>\n" +
                "      <time hour=\"18\" minute=\"22\"/>\n" +
                "    </arrive>\n" +
                "  </flight>\n" +
                "</airline>";
        XmlParser parser = new XmlParser(xml);
        Airline airline = (Airline)parser.parse();
        assertThat(airline.getFlights().size(), equalTo(1));
    }
}
