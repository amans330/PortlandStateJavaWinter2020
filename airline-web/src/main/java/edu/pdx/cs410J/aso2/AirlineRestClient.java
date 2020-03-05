package edu.pdx.cs410J.aso2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client. Note that this class provides
 * an example of how to make gets and posts to a URL. You'll need to change it
 * to do something other than just send Airline entries.
 */
public class AirlineRestClient extends HttpRequestHelper {
	private static final String WEB_APP = "airline";
	private static final String SERVLET = "flights";

	private final String url;

	/**
	 * Creates a client to the airline REST service running on the given host and
	 * port
	 * 
	 * @param hostName The name of the host
	 * @param port     The port
	 */
	public AirlineRestClient(String hostName, int port) {
		this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
	}

	/**
	 * Returns all Airline entries from the server
	 */
	public String searchFlights(String airline, String src, String dest) throws IOException {
		Response response = get(this.url, Map.of("airline", airline, "src", src,"dest", dest));
		if(response.getCode() != 200)	Util.printErrorAndExit(response.getContent());
		return response.getContent();
	}

	/**
	 * Returns all Airline entries from the server
	 */
	public String getFlights(String airline) throws IOException {
		Response response = get(this.url + "?airline=" + airline, Map.of());
		throwExceptionIfNotOkayHttpStatus(response);
		return response.getContent();
	}

	/**
	 * Post all Airline entries from the server
	 */
	public void postFlight(String[] args) throws IOException {
		Response response = post(this.url, Map.of("airline", args[0], "flightNumber", args[1], "src", args[2], "depart", args[3]+" "+args[4]+" "+args[5], "dest", args[6], "arrive", args[7]+" "+args[8]+" "+args[9]));
		throwExceptionIfNotOkayHttpStatus(response);
	}

	/**
	 * Returns the definition for the given word
	 */
	public String getDefinition(String word) throws IOException {
		Response response = get(this.url, Map.of("word", word));
		throwExceptionIfNotOkayHttpStatus(response);
		String content = response.getContent();
		return Messages.parseAirlineEntry(content).getValue();
	}

	public void addAirlineEntry(String word, String definition) throws IOException {
		Response response = postToMyURL(Map.of("word", word, "definition", definition));
		throwExceptionIfNotOkayHttpStatus(response);
	}

	@VisibleForTesting
	Response postToMyURL(Map<String, String> AirlineEntries) throws IOException {
		return post(this.url, AirlineEntries);
	}

	public void removeAllAirlineEntries() throws IOException {
		Response response = delete(this.url, Map.of());
		throwExceptionIfNotOkayHttpStatus(response);
	}

	private Response throwExceptionIfNotOkayHttpStatus(Response response) {
		int code = response.getCode();
		if (code != HTTP_OK) {
			throw new AirlineRestException(code);
		}
		return response;
	}

	@VisibleForTesting
	class AirlineRestException extends RuntimeException {
		AirlineRestException(int httpStatusCode) {
			super("Got an HTTP Status Code of " + httpStatusCode);
		}
	}
}
