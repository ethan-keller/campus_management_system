package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BikeReservationCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    public static void createBikeReservation(int building, int numBikes, String date, String startingTime, String endingTime) {
        String params = "building=" + building + "&numBikes=" + numBikes +
                "&date=" + date + "&startingTime=" + startingTime +
                "&endingTime=" + endingTime;
        send("createBikeReservation", params);
    }

    public static void updateBikeReservation(int id, int building, int numBikes, String date,
                                      String startingTime, String endingTime) {
        String params = "id=" + id + "&building=" + building + "&numBikes=" + numBikes +
                "&date=" + date + "&startingTime=" + startingTime +
                "&endingTime=" + endingTime;
        send("updateBikeReservation", params);
    }

    public static void deleteBikeReservation(int id) {
        String params = "id=" + id;
        send("deleteBikeReservation", params);
    }

    public static String getBikeReservation(int id) {
        String params = "id=" + id;
        return send("getBikeReservation", params);
    }

    public static String getAllBikeReservation() {
        return send("getBikeReservation", "");
    }

    public static String getBuildingBikeReservation(int building) {
        String params = "building=" + building;
        return send("getBuildingBikeReservation", params);
    }



    public static String send(String url, String params) {
        try {
            params = GeneralMethods.encodeCommunication(params);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("http://localhost:8080/" + url + params)).build();
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
}
