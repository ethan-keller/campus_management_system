package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FoodReservationServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * get all the food reservations
     * @return
     */
    public static String getAllFoodReservation() {

    }

    /**
     * get the food by reservation id
     * @param id
     * @return
     */
    public static String getUserFoodReservation(int id) {

    }

    public static boolean deleteFoodReservation(int reservationId, int foodId) {

    }

    public static boolean createFoodReservation(int reservationId, int foodId, int quantity) {

    }

}
