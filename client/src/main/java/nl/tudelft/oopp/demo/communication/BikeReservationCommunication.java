package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BikeReservationCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    public static void createBikeReservation(int building, String user, int numBikes, String date,
                                             String startingTime, String endingTime) {
        String params = "building=" + building + "&user=" + user + "&numBikes=" + numBikes +
                "&date=" + date + "&startingTime=" + startingTime +
                "&endingTime=" + endingTime;
        sendPost("createBikeReservation", params);
    }

    public static void updateBikeReservation(int id, int building, String user, int numBikes, String date,
                                      String startingTime, String endingTime) {
        String params = "id=" + id + "&building=" + building + "&user=" + user +
                "&numBikes=" + numBikes + "&date=" + date +
                "&startingTime=" + startingTime + "&endingTime=" + endingTime;
        sendPost("updateBikeReservation", params);
    }

    public static void deleteBikeReservation(int id) {
        String params = "id=" + id;
        sendPost("deleteBikeReservation", params);
    }

    public static String getBikeReservation(int id) {
        String params = "id=" + id;
        return sendGet("getBikeReservation", params);
    }

    public static String getAllBikeReservation() {
        return sendGet("getAllBikeReservation", "");
    }

    public static String getBuildingBikeReservations(int building) {
        String params = "building=" + building;
        return sendGet("getBuildingBikeReservations", params);
    }

    public static String getUserBikeReservations(String user) {
        String params = "user=" + user;
        return sendGet("getUserBikeReservations", params);
    }

    public static String sendGet(String url, String params) {
        try {
            params = GeneralMethods.encodeCommunication(params);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/" + url + "?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
        return response.body();
    }

    public static void sendPost(String url, String params) {
        try {
            params = GeneralMethods.encodeCommunication(params);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("http://localhost:8080/" + url + "?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }
}
