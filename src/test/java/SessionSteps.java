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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SessionSteps {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpResponse response;
    private static String responseJson;
    private Session session;

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
            responseJson = convertResponseToString(response);
        }
        else {
            Session session = parseStringToJSON(responseJson);
            String endTimeFromSession = convertTimeInSecondsToStringFormat(session.getEndTime());
            String expectedEndTime = year+"-"+((month < 10) ? "0" + month : month)+"-"+((day < 10) ? "0" + day : day)+" "+(hours)+":"+minutes;
            System.out.println(endTimeFromSession);
            System.out.println(expectedEndTime);
            if (! endTimeFromSession.equals(expectedEndTime)) {
                System.out.println("NEW JSON LOADING ...");
                HttpGet request = new HttpGet("http://193.191.177.72:3333/testing/backtrack/" + name + "-" + year + ((month < 10) ? "0" + month : month) + ((day < 10) ? "0" + day : day) + "-" + hours + ((minutes < 10) ? "0" + minutes : minutes));

                response = httpClient.execute(request);
                responseJson = convertResponseToString(response);
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
        session = parseStringToJSON(responseJson);
    }
    @Then("the number of pickups at restaurants should be {int}")
    public void the_number_of_pickups_at_restaurants_should_be(int numberOfPickups) {
        assertEquals(numberOfPickups, session.getDeliveries());
    }

    @Then("the restaurants detected should be within a perimeter of {int} meters of the following coordinates")
    public void the_restaurants_detected_should_be_within_a_perimeter_of_meters_of_the_following_coordinates(int meters, io.cucumber.datatable.DataTable restaurants) {
        List<List<String>> detectedRestaurants = restaurants.asLists(String.class);
        for (int i=1; i<detectedRestaurants.size(); i++) {
            double longitudeDetectedRestaurant = Double.parseDouble(detectedRestaurants.get(i).get(0));
            double latitudeDetectedRestaurant = Double.parseDouble(detectedRestaurants.get(i).get(1));
            assertTrue(withinRadius(restaurant.getLongitude(), restaurant.getLatitude(), 50, longitudeDetectedRestaurant, latitudeDetectedRestaurant));

            // TODO timestamp
        }
    }

    @When("{string} wants to know how many deliveries were done at customers")
    public void elke_wants_to_know_how_many_deliveries_she_did(String name) throws IOException {
        session = parseStringToJSON(responseJson);
    }

    @Then("the number of deliveries at customers should be {int}")
    public void the_number_of_deliveries_at_customers_should_be(int numberOfDeliveries) {
        assertEquals(numberOfDeliveries, session.getDeliveries());
    }

    @Then("the deliveries detected should be within a perimeter of {int} meters of the following coordinates")
    public void the_deliveries_detected_should_be_within_a_perimeter_of_meters_of_the_following_coordinates(Integer meter, io.cucumber.datatable.DataTable customers) {
        List<List<String>> detectedCustomers = customers.asLists(String.class);
        for (int i=1; i<detectedCustomers.size(); i++) {
            double longitudeDetectedCustomer = Double.parseDouble(detectedCustomers.get(i).get(0));
            double latitudeDetectedCustomer = Double.parseDouble(detectedCustomers.get(i).get(1));
            assertTrue(withinRadius(customer.getLongitude(), customer.getLatitude(), 50, longitudeDetectedCustomer, latitudeDetectedCustomer));

            // TODO timestamp
        }
    }

    @When("{string} wants to know all details of the session")
    public void elke_wants_to_know_all_details_of_the_session_she_did(String name) throws IOException {
        session = parseStringToJSON(responseJson);
    }

    @Then("the session details should be")
    public void the_session_details_should_be(io.cucumber.datatable.DataTable sessionDetails) {
        List<List<String>> details = sessionDetails.asLists(String.class);

        // check start time
        String actualStartTime = convertTimeInSecondsToStringFormat(session.getStartTime());
        String expectedStartTime = details.get(1).get(0);
        assertEquals(expectedStartTime, actualStartTime);

        // check end time
        String actualEndTime = convertTimeInSecondsToStringFormat(session.getEndTime());
        String expectedEndTime = details.get(1).get(1);
        assertEquals(expectedEndTime, actualEndTime);

        // check deliveries
        assertEquals(Integer.parseInt(details.get(1).get(2)), session.getDeliveries());

        // TODO kilometers
        //assertEquals(Integer.parseInt(details.get(1).get(3)), session.getKilometers());

        // check paid time
        int actualPaidTimeInSeconds = session.getPaidTime();
        int expectedPaidTimeInSeconds = calculateTimeInSeconds(details.get(1).get(4));
        assertTrue(withinSeconds(actualPaidTimeInSeconds, 30, expectedPaidTimeInSeconds));

        // check unpaid time
        int actualUnpaidTimeInSeconds = session.getUnpaidTime();
        int expectedUnpaidTimeInSeconds = calculateTimeInSeconds(details.get(1).get(5));
        assertTrue(withinSeconds(actualUnpaidTimeInSeconds, 30, expectedUnpaidTimeInSeconds));

    }

    private boolean withinSeconds(int paidTime, int seconds, int paidTimeInSeconds) {
       return ((paidTime>paidTimeInSeconds) ? ((paidTime-paidTimeInSeconds) <= seconds) : ((paidTimeInSeconds-paidTime) <= seconds ));
    }

    private String convertTimeInSecondsToStringFormat(long time) {
        // *1000 for secondsToMiliseconds
        // +639 for our timezone
        Date date = new Date(time*1000+639);
        Timestamp timestamp = new Timestamp(time*1000+639);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(timestamp);
    }

    private int calculateTimeInSeconds(String timeString) {
        String[] timeArray = timeString.split(":");
        int paidTimeInSeconds = 0;
        if (Integer.parseInt(timeArray[0]) > 0) {
            paidTimeInSeconds += Integer.parseInt(timeArray[0])*60*60;
        }
        if (Integer.parseInt(timeArray[1]) > 0) {
            paidTimeInSeconds += Integer.parseInt(timeArray[1])*60;
        }
        if (Integer.parseInt(timeArray[2]) > 0) {
            paidTimeInSeconds += Integer.parseInt(timeArray[2]);
        }
        return paidTimeInSeconds;
    }

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }

    private Session parseStringToJSON (String jsonString) throws IOException {
        return new ObjectMapper().readValue(jsonString, Session.class);
    }

    private boolean withinRadius (double longitude_given, double latitude_given, int diameter, double longitude_to_check, double latitude_to_check) {
        double lat = 0;
        double lon = 0;
        if (longitude_to_check > longitude_given) {
            lat = new BigDecimal(longitude_to_check - longitude_given).setScale(7, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            lat = new BigDecimal(longitude_given - longitude_to_check).setScale(7, RoundingMode.HALF_UP).doubleValue();
        }
        if (latitude_to_check > latitude_given) {
            lon = new BigDecimal(latitude_to_check - latitude_given).setScale(7, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            lon = new BigDecimal(latitude_given-latitude_to_check).setScale(7, RoundingMode.HALF_UP).doubleValue();
        }
        double distance = Math.sqrt(((lon)*(lon))-((lat)*(lat)));
        return distance < diameter/2;
    }
}
