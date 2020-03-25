package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FoodServerCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    public static void createFood(String name, int price) {
        String params = "name=" + name + "&price=" + price;
        sendPost("createFood", params);
    }

    public static void addFoodToBuilding(int foodId, int buildingId) {
        String params = "food=" + foodId + "&building=" + buildingId;
        sendPost("addFoodToBuilding", params);
    }

    public static void addFoodToReservation(int foodId, int reservationId) {
        String params = "food=" + foodId + "&reservation=" + reservationId + "&quantity=1";
        sendPost("addFoodToReservation", params);
    }

    public static void addFoodToReservation(int foodId, int reservationId, int quantity) {
        String params = "food=" + foodId + "&reservation=" + reservationId + "&quantity=" + quantity;
        sendPost("addFoodToReservation", params);
    }

    public static void updateFood(int id, String name, int price) {
        String params = "id=" + id + "&name=" + name + "&price=" + price;
        sendPost("updateFood", params);
    }

    public static void deleteFoodFromReservation(int foodId, int reservationId) {
        String params = "food=" + foodId + "&reservation=" + reservationId;
        sendPost("deleteFoodFromReservation", params);
    }

    public static void deleteFoodFromBuilding(int foodId, int buildingId) {
        String params = "food=" + foodId + "&building=" + buildingId;
        sendPost("deleteFoodFromBuilding", params);
    }

    public static void updateFoodReservationQuantity(int foodId, int reservationId, int quantity) {
        String params = "food=" + foodId + "&reservation=" + reservationId + "&quantity=" + quantity;
        sendPost("updateFoodReservationQuantity", params);
    }

    public static void deleteFood(int id) {
        String params = "id=" + id;
        sendPost("deleteFood", params);
    }

    public static String getFood(int id) {
        String params = "id=" + id;
        return sendGet("getFood", params);
    }

    public static String getFoodByName(String name) {
        String params = "name=" + name;
        return sendGet("getFoodByName", params);
    }

    public static String getFoodByReservation(int reservation) {
        String params = "reservation=" + reservation;
        return sendGet("getFoodByReservation", params);
    }

    public static String getFoodByBuildingId(int buildingId) {
        String params = "building=" + buildingId;
        return sendGet("getFoodByBuildingId", params);
    }

    public static String getFoodByBuildingName(String name) {
        String params = "name=" + name;
        return sendGet("getFoodByBuildingName", params);
    }

    public static String getAllFood() {
        return sendGet("getAllFood", "");
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
