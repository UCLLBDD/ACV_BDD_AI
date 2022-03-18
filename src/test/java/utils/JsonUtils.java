package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Session;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class JsonUtils {


    public static String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }

    public static Session parseStringToJSON (String jsonString) throws IOException {
        return new ObjectMapper().readValue(jsonString, Session.class);
    }
}
