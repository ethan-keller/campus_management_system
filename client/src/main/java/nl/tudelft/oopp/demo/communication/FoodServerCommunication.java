package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FoodServerCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Creates an HTTP request to add a food in the database.
     * @param name The name of the new food
     * @param price The price of the new food
     */
    public static void createFood(String name, double price) {
        String params = "name=" + name + "&price=" + price;
        sendPost("createFood", params);
    }

    /**
     * Creates an HTTP request to add a particular food to be available at a building.
     * @param foodId The ID of the food
     * @param buildingId The ID of the building
     */
    public static void addFoodToBuilding(int foodId, int buildingId) {
        String params = "food=" + foodId + "&building=" + buildingId;
        sendPost("addFoodToBuilding", params);
    }

    /**
     * Creates an HTTP request to add Food to a particular reservation without specifying quantity.
     * @param foodId The id of the food
     * @param reservationId The id of the reservation
     */
    public static void addFoodToReservation(int foodId, int reservationId) {
        String params = "food=" + foodId + "&reservation=" + reservationId + "&quantity=1";
        sendPost("addFoodToReservation", params);
    }

    /**
     * Creates an HTTP request to add Food to a particular reservation with specifying quantity.
     * @param foodId The id of the food
     * @param reservationId The id of the reservation
     * @param quantity The quantity of the chosen food
     */
    public static void addFoodToReservation(int foodId, int reservationId, int quantity) {
        String params = "food=" + foodId + "&reservation=" + reservationId + "&quantity=" + quantity;
        sendPost("addFoodToReservation", params);
    }

    /**
     * Creates an HTTP request to update a food in the database.
     * @param id The food ID
     * @param name The new name of the food
     * @param price The new price of the food
     */
    public static void updateFood(int id, String name, double price) {
        String params = "id=" + id + "&name=" + name + "&price=" + price;
        sendPost("updateFood", params);
    }

    /**
     * Creates an HTTP request to remove food from a reservation.
     * @param foodId The ID of the food to be removed.
     * @param reservationId The ID of the reservation to remove the food from
     */
    public static void deleteFoodFromReservation(int foodId, int reservationId) {
        String params = "food=" + foodId + "&reservation=" + reservationId;
        sendPost("deleteFoodFromReservation", params);
    }

    /**
     * Creates an HTTP request to delete a food from being available at a building.
     * @param foodId The ID of the food
     * @param buildingId The ID of the building
     */
    public static void deleteFoodFromBuilding(int foodId, int buildingId) {
        String params = "food=" + foodId + "&building=" + buildingId;
        sendPost("deleteFoodFromBuilding", params);
    }

    /**
     * Creates an HTTP request to update the quantity of a food ordered at a reservation.
     * @param foodId The ID of the food
     * @param reservationId The ID of the reservation
     * @param quantity The new quantity of the food
     */
    public static void updateFoodReservationQuantity(int foodId, int reservationId, int quantity) {
        String params = "food=" + foodId + "&reservation=" + reservationId + "&quantity=" + quantity;
        sendPost("updateFoodReservationQuantity", params);
    }

    /**
     * Creates an HTTP request to remove a food from the database.
     * @param id The ID of the food
     */
    public static void deleteFood(int id) {
        String params = "id=" + id;
        sendPost("deleteFood", params);
    }

    /**
     * Returns a food object in JSON from the database based on the food ID.
     * @param id The food ID
     * @return Returns a JSON object
     */
    public static String getFood(int id) {
        String params = "id=" + id;
        return sendGet("getFood", params);
    }

    /**
     * Returns a food object in JSON from the database based on the food name.
     * @param name The food name
     * @return Returns a JSON object
     */
    public static String getFoodByName(String name) {
        String params = "name=" + name;
        return sendGet("getFoodByName", params);
    }

    /**
     * Returns a list of food objects in JSON from the database that are ordered with a reservation<br>
     *  based on the reservation ID.
     * @param reservation The reservation ID
     * @return Returns a JSON List
     */
    public static String getFoodByReservation(int reservation) {
        String params = "reservation=" + reservation;
        return sendGet("getFoodByReservation", params);
    }

    /**
     * Returns a list of food objects in JSON from the database that are available at a building<br>
     *  based on the building ID.
     * @param buildingId The building ID
     * @return Returns a JSON List
     */
    public static String getFoodByBuildingId(int buildingId) {
        String params = "building=" + buildingId;
        return sendGet("getFoodByBuildingId", params);
    }

    /**
     * Returns a list of food objects in JSON from the database that are available at a building<br>
     *  based on the building name.
     * @param name The building name
     * @return Returns a JSON List
     */
    public static String getFoodByBuildingName(String name) {
        String params = "name=" + name;
        return sendGet("getFoodByBuildingName", params);
    }

    /**
     * Returns a list of all foods from the database in JSON format.
     * @return Returns a JSON List
     */
    public static String getAllFood() {
        return sendGet("getAllFoods", "");
    }

    /**
     * Sends the actual HTTP GET request to the server.
     * @param url The url to send the request to.
     * @param params The parameters that the server needs.
     * @return Returns the response the server sends back.
     */
    public static String sendGet(String url, String params) {
        try {
            params = GeneralMethods.encodeCommunication(params);
        } catch (UnsupportedEncodingException e) {
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

    /**
     * Sends the actual HTTP POST request to the server.
     * @param url The url to send the request to.
     * @param params The parameters that the server needs.
     */
    public static void sendPost(String url, String params) {
        try {
            params = GeneralMethods.encodeCommunication(params);
        } catch (UnsupportedEncodingException e) {
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
