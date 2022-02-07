package oldroutes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.*;

public class SessionWith1DeliveryTest {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpResponse response;
    private String responseJson;
    private Session session;

    private Location restaurant;
    private Location customer;

    // latitude: 51.15453957886943
    // longitude: 4.473038440331574
/*
    @Given("Elke drove session on {int}-{int}-{int} with end time {int}:{int}:{int}")
    public void elke_drove_session_on_with_end_time(Integer year, Integer month, Integer day,
                                                    Integer hours, Integer minutes, Integer seconds,
                                                    io.cucumber.datatable.DataTable path)
                                    throws IOException {
        HttpGet request = new HttpGet("http://193.191.177.72:3333/testing/elke-20211214-0720");

        response = httpClient.execute(request);
        responseJson = convertResponseToString(response);
        System.out.println(responseJson);

        List<Map<String, String>> sessionPath = path.asMaps(String.class, String.class);
        System.out.println(sessionPath);
        restaurant = new Location(Double.parseDouble(sessionPath.get(0).get("longitude")), Double.parseDouble(sessionPath.get(0).get("latitude")));
        customer = new Location(Double.parseDouble(sessionPath.get(1).get("longitude")), Double.parseDouble(sessionPath.get(1).get("latitude")));
    }


    @When("Elke wants to know how many pickups she did at restaurants")
    public void elke_wants_to_know_how_many_pickups_she_did_at_restaurants() throws IOException {
        session = parseStringToJSON(responseJson);
    }
    @Then("the number of pickups at restaurants should be {int}")
    public void the_number_of_pickups_at_restaurants_should_be(int numberOfPickups) {
        Assert.assertEquals(numberOfPickups, session.getDeliveries());
    }

    @Then("the restaurants detected should be within a perimeter of {int} meters of the following coordinates")
    public void the_restaurants_detected_should_be_within_a_perimeter_of_meters_of_the_following_coordinates(int meters, io.cucumber.datatable.DataTable restaurants) {
        List<List<String>> detectedRestaurants = restaurants.asLists(String.class);
        double longitudeDetectedRestaurant = Double.parseDouble(detectedRestaurants.get(1).get(0));
        double latitudeDetectedRestaurant = Double.parseDouble(detectedRestaurants.get(1).get(1));
        assertTrue(withinRadius(restaurant.getLongitude(), restaurant.getLatitude(), 50, longitudeDetectedRestaurant, latitudeDetectedRestaurant));
    }

    @When("Elke wants to know how many deliveries she did")
    public void elke_wants_to_know_how_many_deliveries_she_did() throws IOException {
        session = parseStringToJSON(responseJson);
    }

    @Then("the number of deliveries at customers should be {int}")
    public void the_number_of_deliveries_at_customers_should_be(int numberOfDeliveries) {
        Assert.assertEquals(numberOfDeliveries, session.getDeliveries());
    }

    @Then("the deliveries detected should be within a perimeter of {int} meters of the following coordinates")
    public void the_deliveries_detected_should_be_within_a_perimeter_of_meters_of_the_following_coordinates(Integer meter, io.cucumber.datatable.DataTable customers) {
        List<List<String>> detectedCustomers = customers.asLists(String.class);
        double longitudeDetectedCustomer = Double.parseDouble(detectedCustomers.get(1).get(0));
        double latitudeDetectedCustomer = Double.parseDouble(detectedCustomers.get(1).get(1));
        assertTrue(withinRadius(customer.getLongitude(), customer.getLatitude(), 50, longitudeDetectedCustomer, latitudeDetectedCustomer));
    }

    @When("Elke wants to know all details of the session she did")
    public void elke_wants_to_know_all_details_of_the_session_she_did() throws IOException {
        session = parseStringToJSON(responseJson);
    }

    @Then("the session details should be")
    public void the_session_details_should_be(io.cucumber.datatable.DataTable sessionDetails) {
        List<List<String>> details = sessionDetails.asLists(String.class);
        System.out.println(details.get(1).get(0));
        System.out.println(session.getStartTime());
        Date date = new Date(session.getStartTime()*1000);
        System.out.println(date.getTime());
        Timestamp time = new Timestamp(date.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(formatter.format(time));
        // TODO start time
        //assertEquals(session.getStartTime(), Integer.parseInt(details.get(1).get(0)));

        System.out.println(details.get(1).get(1));
        System.out.println(session.getEndTime());
        Date end = new Date(session.getEndTime());
        System.out.println(end.getTime());
        Timestamp endtime = new Timestamp(end.getTime());
        System.out.println(formatter.format(endtime));
        // TODO end time
        //assertEquals(session.getStartTime(), Integer.parseInt(details.get(1).get(1)));

        int paidTimeInSeconds = calculateTimeInSeconds(details.get(1).get(2));
        Assert.assertEquals(session.getPaidTime(), paidTimeInSeconds);
        // TODO unpaid time => 46752 = 779 minuten???
        int unpaidTimeInSeconds = calculateTimeInSeconds(details.get(1).get(3));
        Assert.assertEquals(session.getUnpaidTime(), unpaidTimeInSeconds);
        // TODO kilometers
        //assertEquals(session.getUnpaidTime(), Integer.parseInt(details.get(1).get(4)));
        Assert.assertEquals(session.getDeliveries(), Integer.parseInt(details.get(1).get(5)));
    }
*/
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
