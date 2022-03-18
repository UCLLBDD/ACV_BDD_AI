package utils;

import model.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocationUtils {

    public static boolean withinRadius(double longitude_given, double latitude_given, int diameter, double longitude_to_check, double latitude_to_check) {
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

    public static boolean  perimeterLocation(Integer meters, io.cucumber.datatable.DataTable dataTable, Session session)
    {
        List<List<String>> detectedObject = dataTable.asLists(String.class);
        for (int i=1; i<detectedObject.size(); i++) {
            double longitudeDetectedObject = Double.parseDouble(detectedObject.get(i).get(0));
            double latitudeDetectedObject = Double.parseDouble(detectedObject.get(i).get(1));
            assertTrue(utils.LocationUtils.withinRadius(session.getStops().get(i).getLocation().getLongitude(), session.getStops().get(i).getLocation().getLatitude(), meters, longitudeDetectedObject, latitudeDetectedObject));

            // TODO laten nakijken
            String dateTime = detectedObject.get(i).get(2);
            assertEquals(session.getStops().get(i).getEndTime(), dateTime);
        }
        return true;
    }

}
