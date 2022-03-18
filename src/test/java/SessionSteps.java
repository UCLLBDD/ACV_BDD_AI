import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Location;
import model.Session;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SessionSteps {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpResponse response;
    private static String responseJson;
    private Session session;

    private static final int timeMargin = 3;
    private Location restaurant;
    private Location customer;

    @Before
    public void setUp () {
        System.out.println("BEFORE");
        if (responseJson == null) {
            System.out.println("GET JSON");
        }
        else {
            System.out.println("ALREADY JSON");
        }
    }

    // latitude: 51.15453957886943
    // longitude: 4.473038440331574

    @Given("{string} drove session on {int}-{int}-{int} with end time {int}:{int}")
    public void elke_drove_session_on_with_end_time(String name,
                                                    Integer year, Integer month, Integer day,
                                                    Integer hours, Integer minutes,
                                                    io.cucumber.datatable.DataTable path)
                                    throws IOException {
        if (responseJson == null) {
            // backtrack
            HttpGet request = new HttpGet("http://193.191.177.72:3333/testing/basic/" + name + "-" + year + month + ((day < 10) ? "0" + day : day) + "-" + hours + ((minutes < 10) ? "0" + minutes : minutes));

            response = httpClient.execute(request);
            responseJson = utils.JsonUtils.convertResponseToString(response);
        }
        else {
            Session session = utils.JsonUtils.parseStringToJSON(responseJson);
            String endTimeFromSession = utils.TimeUtils.convertTimeInSecondsToStringFormat(session.getEndTime());
            String expectedEndTime = year+"-"+((month < 10) ? "0" + month : month)+"-"+((day < 10) ? "0" + day : day)+" "+(hours)+":"+minutes;
            System.out.println(endTimeFromSession);
            System.out.println(expectedEndTime);
            if (! endTimeFromSession.equals(expectedEndTime)) {
                System.out.println("NEW JSON LOADING ...");
                HttpGet request = new HttpGet("http://193.191.177.72:3333/testing/backtrack/" + name + "-" + year + ((month < 10) ? "0" + month : month) + ((day < 10) ? "0" + day : day) + "-" + hours + ((minutes < 10) ? "0" + minutes : minutes));

                response = httpClient.execute(request);
                responseJson = utils.JsonUtils.convertResponseToString(response);
            }
        }
        System.out.println(responseJson);

        List<Map<String, String>> sessionPath = path.asMaps(String.class, String.class);
        System.out.println(sessionPath);
        restaurant = new Location(Double.parseDouble(sessionPath.get(0).get("longitude")), Double.parseDouble(sessionPath.get(0).get("latitude")));
        customer = new Location(Double.parseDouble(sessionPath.get(1).get("longitude")), Double.parseDouble(sessionPath.get(1).get("latitude")));
    }


    @When("{string} wants to know how many pickups were done at restaurants")
    public void elke_wants_to_know_how_many_pickups_she_did_at_restaurants(String name) throws IOException {
        session = utils.JsonUtils.parseStringToJSON(responseJson);
    }
    @Then("the number of pickups at restaurants should be {int}")
    public void the_number_of_pickups_at_restaurants_should_be(int numberOfPickups) {
        assertEquals(numberOfPickups, session.getDeliveries());
    }

    @Then("the restaurants detected should be within a perimeter of {int} meters of the following coordinates")
    public void the_restaurants_detected_should_be_within_a_perimeter_of_meters_of_the_following_coordinates(int meters, io.cucumber.datatable.DataTable restaurants) {

        utils.LocationUtils.perimeterLocation(meters, restaurants, session);
        /**List<List<String>> detectedRestaurants = restaurants.asLists(String.class);
        for (int i=1; i<detectedRestaurants.size(); i++) {
            double longitudeDetectedRestaurant = Double.parseDouble(detectedRestaurants.get(i).get(0));
            double latitudeDetectedRestaurant = Double.parseDouble(detectedRestaurants.get(i).get(1));
            assertTrue(withinRadius(session.getStops().get(i).getLocation().getLongitude(), session.getStops().get(i).getLocation().getLatitude(), meters, longitudeDetectedRestaurant, latitudeDetectedRestaurant));

            // TODO laten nakijken
            String dateTime = detectedRestaurants.get(i).get(2);
            assertEquals(session.getStops().get(i).getStartTime(), dateTime);
        }**/
    }

    @When("{string} wants to know how many deliveries were done at customers")
    public void elke_wants_to_know_how_many_deliveries_she_did(String name) throws IOException {
        session = utils.JsonUtils.parseStringToJSON(responseJson);
    }

    @Then("the number of deliveries at customers should be {int}")
    public void the_number_of_deliveries_at_customers_should_be(int numberOfDeliveries) {
        assertEquals(numberOfDeliveries, session.getDeliveries());
    }

    @Then("the deliveries detected should be within a perimeter of {int} meters of the following coordinates")
    public void the_deliveries_detected_should_be_within_a_perimeter_of_meters_of_the_following_coordinates(Integer meters, io.cucumber.datatable.DataTable customers) {
        utils.LocationUtils.perimeterLocation(meters, customers, session);
    }

    @When("{string} wants to know all details of the session")
    public void elke_wants_to_know_all_details_of_the_session_she_did(String name) throws IOException {
        session = utils.JsonUtils.parseStringToJSON(responseJson);
    }

    @Then("the session details should be")
    public void the_session_details_should_be(io.cucumber.datatable.DataTable sessionDetails) {
        List<List<String>> details = sessionDetails.asLists(String.class);

        // check start time
        String actualStartTime = utils.TimeUtils.convertTimeInSecondsToStringFormat(session.getStartTime());
        String expectedStartTime = details.get(1).get(0);
        assertTrue(utils.TimeUtils.withinTime(actualStartTime, expectedStartTime, timeMargin));

        // check end time
        String actualEndTime = utils.TimeUtils.convertTimeInSecondsToStringFormat(session.getEndTime());
        String expectedEndTime = details.get(1).get(1);
        assertTrue(utils.TimeUtils.withinTime(actualEndTime, expectedEndTime, timeMargin));

        // check deliveries
        assertEquals(Integer.parseInt(details.get(1).get(2)), session.getDeliveries());

        /** TODO kilometers
        hoeveel mag de echte van de verwachte afzitten? dat is de laatste delta waarde
        Deze moet altijd meegeven worden als 1 van de 2 waarden een double is.
        In dit geval is alles voor de komma in meter, dus een deltawaarde van 1 gaat veel te laag zijn.
        Deze check blijft in commentaar staan omdat deze momenteel geen juiste data heeft.
        **/
        //assertEquals(Integer.parseInt(details.get(1).get(3)), session.getTotalDistance(), 1);

        // check paid time
        int actualPaidTimeInSeconds = session.getPaidTime();
        int expectedPaidTimeInSeconds = utils.TimeUtils.calculateTimeInSeconds(details.get(1).get(4));
        assertTrue(utils.TimeUtils.withinSeconds(actualPaidTimeInSeconds, 30, expectedPaidTimeInSeconds));

        // check unpaid time
        int actualUnpaidTimeInSeconds = session.getUnpaidTime();
        int expectedUnpaidTimeInSeconds = utils.TimeUtils.calculateTimeInSeconds(details.get(1).get(5));
        assertTrue(utils.TimeUtils.withinSeconds(actualUnpaidTimeInSeconds, 30, expectedUnpaidTimeInSeconds));

    }














}
