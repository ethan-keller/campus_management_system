package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FoodServerCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    public void createFood(String name, int price) throws UnsupportedEncodingException {
        String params = "name=" + name + "&price=" + price;
        send("createFood", params);
    }

    public void addFoodToBuilding(int foodId, int buildingId) {
        String params = "foodId=" + foodId + "&buildingId=" + buildingId;
        send("addFoodToBuilding", params);
    }

    public void addFoodToReservation(int foodId, int reservationId) {
        String params = "foodId=" + foodId + "&reservationId=" + reservationId;
        send("addFoodToReservation", params);
    }

    public void updateFood(int id, int name, int price) {
        String params = "id=" + id + "&name=" + name + "&price=" + price;
        send("updateFood", params);
    }

    public void deleteFoodFromReservation(int foodId, int reservationId) {
        String params = "foodId=" + foodId + "&reservationId=" + reservationId;
        send("deleteFoodFromReservation", params);
    }

    public void deleteFoodFromBuilding(int foodId, int buildingId) {
        String params = "foodId=" + foodId + "&buildingId=" + buildingId;
        send("deleteFoodFromBuilding", params);
    }

    public void updateFoodReservationQuantity(int foodId, int reservationId) {
        String params = "foodId=" + foodId + "&reservationId=" + reservationId;
        send("updateFoodReservationQuantity", params);
    }

    public void deleteFood(int id) {
        String params = "id=" + id;
        send("deleteFood", params);
    }

    public String getFood(int id) {
        String params = "id=" + id;
        return send("getFood", params);
    }

    public String getFoodByName(String name) {
        String params = "name=" + name;
        return send("getFoodByName", params);
    }

    public String getFoodByReservation(int reservationId) {
        String params = "reservationId=" + reservationId;
        return send("getFoodByReservation", params);
    }

    public String getFoodByBuildingId(int buildingId) {
        String params = "buildingId=" + buildingId;
        return send("getFoodByBuildingId", params);
    }

    public String getFoodByBuildingName(int name) {
        String params = "name=" + name;
        return send("getFoodByBuildingName", params);
    }

    public String getAllFood() {
        return send("getAllFood", "");
    }


    public String send(String url, String params) {
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
