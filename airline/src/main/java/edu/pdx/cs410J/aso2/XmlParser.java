package edu.pdx.cs410J.aso2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class XmlParser implements AirlineParser {
	private String filepath;
	private Airline airline;

	XmlParser(String filepath, Airline airline) {
		this.filepath = filepath;
		this.airline = airline;
	}

	@Override
	public AbstractAirline parse() throws ParserException {
		File file = new File(filepath);

		try {
			// if file does not exist, or is empty, nothing to read from, return the existing airline object
			// We will create new file in dumper
			if (file.exists() == false) {
				return airline;
			} else if(file.exists() && file.length() == 0){
				Util.printErrorAndExit("The XML file provided is Empty.");
			} else {
				AirlineXmlHelper helper = new AirlineXmlHelper();
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setValidating(true);
				DocumentBuilder builder = factory.newDocumentBuilder();
				builder.setErrorHandler(helper);
				builder.setEntityResolver(helper);
				Document doc = builder.parse(file);

				if (!doc.getElementsByTagName("name").item(0).getTextContent().equals(airline.getName())) {
					Util.printErrorAndExit("Airline name in the XML file does not match the input");
				}
				NodeList flightNodes = doc.getElementsByTagName("flight");
				for (int i = 0; i < flightNodes.getLength(); i++) {
					Node flightNode = flightNodes.item(i);
					if (flightNode.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) flightNode;
						String flightNumber = element.getElementsByTagName("number").item(0).getTextContent();
						String src = element.getElementsByTagName("src").item(0).getTextContent();

						Element departDate = (Element) ((Element) element.getElementsByTagName("depart").item(0))
								.getElementsByTagName("date").item(0);
						String departDay = departDate.getAttributes().item(0).getTextContent();
						String departMonth = departDate.getAttributes().item(1).getTextContent();
						String departYear = departDate.getAttributes().item(2).getTextContent();

						Element departTime = (Element) ((Element) element.getElementsByTagName("depart").item(0))
								.getElementsByTagName("time").item(0);
						String departHour = departTime.getAttributes().item(0).getTextContent();
						String departMinute = departTime.getAttributes().item(1).getTextContent();
						String departAmPm = null;
						if (departHour.matches("\\d{1,2}")) {
							if (Integer.parseInt(departHour) >= 12) {
								departAmPm = "pm";
							} else {
								departAmPm = "am";
							}
						} else
							Util.printErrorAndExit("Departure Hour is not in correct format in XML file!");
						departHour = Integer.parseInt(departHour) > 12 ? String.valueOf(Integer.parseInt(departHour)-12) : departHour;

						String dest = element.getElementsByTagName("dest").item(0).getTextContent();

						Element arriveDate = (Element) ((Element) element.getElementsByTagName("arrive").item(0))
								.getElementsByTagName("date").item(0);
						String arriveDay = arriveDate.getAttributes().item(0).getTextContent();
						String arriveMonth = arriveDate.getAttributes().item(1).getTextContent();
						String arriveYear = arriveDate.getAttributes().item(2).getTextContent();

						Element arriveTime = (Element) ((Element) element.getElementsByTagName("arrive").item(0))
								.getElementsByTagName("time").item(0);
						String arriveHour = arriveTime.getAttributes().item(0).getTextContent();
						String arriveMinute = arriveTime.getAttributes().item(1).getTextContent();

						String arriveAmPm = null;
						if (arriveHour.matches("\\d{1,2}")) {
							if (Integer.parseInt(arriveHour) >= 12) {
								arriveAmPm = "pm";
							} else {
								arriveAmPm = "am";
							}
						} else
							Util.printErrorAndExit("Arrival Hour is not in correct format in XML file!");
						arriveHour = Integer.parseInt(arriveHour) > 12 ? String.valueOf(Integer.parseInt(arriveHour)-12) : arriveHour;

						String[] data = { airline.getName(), flightNumber, src,
								departMonth + "/" + departDay + "/" + departYear, departHour + ":" + departMinute,
								departAmPm, dest, arriveMonth + "/" + arriveDay + "/" + arriveYear,
								arriveHour + ":" + arriveMinute, arriveAmPm };

						// Now validate data in XML file here
						CheckDataValidity.checkArgumentsDataValidity(data);

						// Date format for flight Date is ("MM/dd/yyyy hh:mm aa")
						Flight flight = new Flight(Integer.parseInt(flightNumber), src,
								Util.getDateFromString(departMonth + "/" + departDay + "/" + departYear + " " + ""
										+ departHour + ":" + departMinute + " " + departAmPm),
								dest, Util.getDateFromString(arriveMonth + "/" + arriveDay + "/" + arriveYear + " " + ""
										+ arriveHour + ":" + arriveMinute + " " + arriveAmPm));
						airline.addFlight(flight);
					}
				}
			}
			return airline;
		} catch (ParserConfigurationException | SAXException e) {
			Util.printErrorAndExit("The XML file is Malformatted.");
		} catch (IOException e) {
			Util.printErrorAndExit("Cannot Write to given XML file.");
		}

		return null;
	}

}
