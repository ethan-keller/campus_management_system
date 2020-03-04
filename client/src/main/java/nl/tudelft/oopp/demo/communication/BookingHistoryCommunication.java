package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * How to connect the communication class to the controller?
 * How to add the various retrieved elements/list of elements into a list?
 * How to load all the information as soon as the page loads?
 */

public class BookingHistoryCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * getAllReservations method displays all the reservations stored in the server in a neat tabular manner.
     * @return
     */

    public static String getAllReservations() {
        /**
         * it creates an http request and sends it to the endpoint.
         */
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllReservations")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
        return response.body();
    }

    public static String getReservations(int id) {
        String params = "?id=" + id;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getReservation" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
        return response.body();
    }

}
