package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BikeReservationCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    private static Logger logger = Logger.getLogger("GlobalLogger");

    /**
     * Adds a bike reservation to the database.
     *
     * @param building The Building id to which the bikes belong
     * @param user The username of the user making the reservation
     * @param numBikes The number of bikes reserved
     * @param date The date of the reservation
     * @param startingTime The starting time of the reservation
     * @param endingTime The ending time of the reservation
     */
    public static void createBikeReservation(int building, String user, int numBikes, String date,
                                             String startingTime, String endingTime) {
        String params = "building=" + building + "&user=" + user + "&numBikes=" + numBikes
                + "&date=" + date + "&startingTime=" + startingTime
                + "&endingTime=" + endingTime;
        sendPost("createBikeReservation", params);
    }

    /**
     * Updates a previously made bike reservation.
     *
     * @param id The id of the reservation
     * @param building The new building of the reservation
     * @param user The new user of the reservation
     * @param numBikes The new number of bikes reserved
     * @param date The new date of the reservation
     * @param startingTime The new starting time of the reservation
     * @param endingTime The new ending time of the reservation
     */
    public static void updateBikeReservation(int id, int building, String user, int numBikes, String date,
                                      String startingTime, String endingTime) {
        String params = "id=" + id + "&building=" + building + "&user=" + user
                + "&numBikes=" + numBikes + "&date=" + date
                + "&startingTime=" + startingTime + "&endingTime=" + endingTime;
        sendPost("updateBikeReservation", params);
    }

    /**
     * Deletes a bike reservation from the database.
     *
     * @param id The id of the bike reservation
     */
    public static void deleteBikeReservation(int id) {
        String params = "id=" + id;
        sendPost("deleteBikeReservation", params);
    }

    /**
     * Rretreives a bike reservation from the database by id.
     *
     * @param id The id of the bike reservation
     * @return Returns a BikeReservation object
     */
    public static String getBikeReservation(int id) {
        String params = "id=" + id;
        return sendGet("getBikeReservation", params);
    }

    /**
     * Retrieves all bike reservations from the database.
     *
     * @return Returns a list of BikeReservations
     */
    public static String getAllBikeReservation() {
        return sendGet("getAllBikeReservation", "");
    }

    /**
     * Retrieves all the bike reservations of the bikes that belong to the building of buildingId.
     *
     * @param building The id of the building
     * @return Returns a list of bike reservations.
     */
    public static String getBuildingBikeReservations(int building) {
        String params = "building=" + building;
        return sendGet("getBuildingBikeReservations", params);
    }

    /**
     * Retrieves all bike reservations that have been made by a user.
     * @param user The username of the particular user
     * @return Returns a list of bike reservations
     */
    public static String getUserBikeReservations(String user) {
        String params = "user=" + user;
        return sendGet("getUserBikeReservations", params);
    }

    /**
     * Sends a HTTP GET request to the server with the specified paramaters and returns the response.
     *
     * @param url The URL to send the request to.
     * @param params The paramaters to add the the url.
     * @return Returns the response
     */
    private static String sendGet(String url, String params) {
        try {
            params = GeneralMethods.encodeCommunication(params);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, e.toString());
            return null;
        }
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/" + url + "?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return null;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Sends a HTTP POST request to the server with the specified paramaters.
     *
     * @param url The URL to send the request to.
     * @param params The paramaters to add the the url.
     */
    private static void sendPost(String url, String params) {
        try {
            params = GeneralMethods.encodeCommunication(params);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, e.toString());
            return;
        }
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("http://localhost:8080/" + url + "?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
        }
    }
}
