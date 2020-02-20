package edu.pdx.cs410J.aso2;

import java.io.IOException;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlDumper implements AirlineDumper {
	private final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm",  Locale.ENGLISH);
	private String filePath;
	private boolean textToXML = false;
	
	/**
	 * 
	 * @param filePath The path of the XML file where you want to write the Airline object
	 */
	XmlDumper(String filePath){
		this.filePath = filePath;
	}
	
	/**
	 * @param airline airline object which needs to be written to XML file
	 */
	@Override
	public void dump(AbstractAirline airline) throws IOException {
		try {
			AirlineXmlHelper helper = new AirlineXmlHelper();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);
            File file = new File(filePath);
            
            // delete the file, we'll create new one from scratch with new data. 
            file.delete();
            
            //now create a new one.
            file.createNewFile();
            
            Document document = builder.newDocument();
            
            // start creating XML document
            Element rootElement = document.createElement("airline");
            document.appendChild(rootElement);
            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(airline.getName()));
            rootElement.appendChild(name);
            
            Element airlineNode = document.getDocumentElement();
            
            //start appending all the flights in XML
            List<Flight> flightList = (List<Flight>)airline.getFlights();
            for(int i = 0; i < flightList.size(); i++) {
            	Flight flight = flightList.get(i);
            	
            	Element flightElement = document.createElement("flight");

                Element number = document.createElement("number");
                number.setTextContent(String.valueOf(flight.getNumber()));
                flightElement.appendChild(number);

                Element src = document.createElement("src");
                src.setTextContent(flight.getSource());
                flightElement.appendChild(src);
                
                // make depart node
                Element depart = document.createElement("depart");
                Date departureDate = flight.getDeparture();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(departureDate);
                Element subDate = document.createElement("date");
                subDate.setAttribute("day", calendar.get(Calendar.DATE) + "");
                subDate.setAttribute("month", calendar.get(Calendar.MONTH) + 1 +"");
                subDate.setAttribute("year", calendar.get(Calendar.YEAR) + "");
                Element subTime = document.createElement("time");
                subTime.setAttribute("hour", calendar.get(Calendar.HOUR_OF_DAY) + "");
                subTime.setAttribute("minute", calendar.get(Calendar.MINUTE) + "");
                depart.appendChild(subDate);
                depart.appendChild(subTime);
                flightElement.appendChild(depart);

                Element textNodeDest = document.createElement("dest");
                textNodeDest.setTextContent(flight.getDestination());
                flightElement.appendChild(textNodeDest);

                // make arrive node
                Element arrive = document.createElement("arrive");
                Date arrivalDate = flight.getArrival();
                calendar.setTime(arrivalDate);
                subDate = document.createElement("date");
                subDate.setAttribute("day", calendar.get(Calendar.DATE) + "");
                subDate.setAttribute("month", calendar.get(Calendar.MONTH) + 1 +"");
                subDate.setAttribute("year", calendar.get(Calendar.YEAR) + "");
                subTime = document.createElement("time");
                subTime.setAttribute("hour", calendar.get(Calendar.HOUR_OF_DAY) + "");
                subTime.setAttribute("minute", calendar.get(Calendar.MINUTE) + "");
                arrive.appendChild(subDate);
                arrive.appendChild(subTime);
                flightElement.appendChild(arrive);
                
                airlineNode.appendChild(flightElement);
            }
            
            // Standard code to create a XML file
            Transformer tFormer = TransformerFactory.newInstance().newTransformer();
            tFormer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            tFormer.setOutputProperty(OutputKeys.ENCODING, "us-ascii");
            tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
            DOMImplementation domImpl = document.getImplementation();
            DocumentType doctype = domImpl.createDocumentType("doctype", "", "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
            tFormer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
            tFormer.setOutputProperty(OutputKeys.INDENT, "yes");
            document.setXmlStandalone(true);
            Source source = new DOMSource(document);
            Result result = new StreamResult(filePath);
            tFormer.transform(source, result);
            System.out.println("Added data to XML file.");
            
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
