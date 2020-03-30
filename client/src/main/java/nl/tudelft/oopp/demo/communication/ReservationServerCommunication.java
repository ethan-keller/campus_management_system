package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReservationServerCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * This client-server method is used to create a new reservation using the attributes
     * passed as parameters.
     * @param username - Username of the user
     * @param room - Room id
     * @param date - Date of reservation
     * @param startingTime - Starting time of reservation
     * @param endingTime - Ending time of the reservation
     * @return Boolean value which indicates to the user if Reservation creation is successful.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean createReservation(String username, int room, String date,
                                            String startingTime, String endingTime)
            throws UnsupportedEncodingException {
        String params = "username=" + username + "&room=" + room + "&date=" + date
                + "&startingTime=" + startingTime + "&endingTime=" + endingTime;
        params = GeneralMethods.encodeCommunication(params);


        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createReservation?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
            return false;
        }
        return true;
    }

    /**
     * This client-server method is used to update a reservation by the attributes passed as
     * parameters.
     * @param id - Reservation id
     * @param room - Room id
     * @param date - Date of reservation
     * @param startingTime - Starting time of reservation
     * @param endingTime - Ending time of the reservation
     * @return Boolean value which indicates to the user if Reservation update is successful.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean updateReservation(int id, int room, String date, String startingTime,
                                            String endingTime)
            throws UnsupportedEncodingException {
        String params = "id=" + id + "&room=" + room + "&date=" + date + "&startingTime="
                + startingTime + "&endingTime=" + endingTime;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/updateReservation?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("FAILED Status: " + response.statusCode() + response.body());
            return false;
        }
        return true;
    }

    /**
     * This client-server method is used to delete a reservation made by the user.
     * From client side, this method is supposed to be cancelling a reservation.
     * @param id - Reservation id
     * @return Boolean value to indicate if the reservation is deleted.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean deleteReservation(int id) throws UnsupportedEncodingException {
        String params = "id=" + id;
        params = GeneralMethods.encodeCommunication(params);


        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteReservation?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
            return false;
        }
        return true;
    }

    /**.
     * This client-server method is used to get the reservation using the user's user id
     * @param id - Reservation id
     * @return Reservation of the user.
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getReservation(int id) throws UnsupportedEncodingException {
        String params = "id=" + id;
        params = GeneralMethods.encodeCommunication(params);


        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getReservation?" + params)).build();
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
     * This client-server method is used to get all the reservations made by users and admin from
     * the database.
     * @return Reservations (ALL)
     */
    public static String getAllReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllReservations")).build();
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
     * This client-server method is used to get the reservation of a particular user who could be
     * identified by their username which is passed as a parameter.
     * @param username - Username of the user requesting the reservation
     * @return Reservations of "username"
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getUserReservations(String username) throws UnsupportedEncodingException {
        String params = "username=" + username;
        params = GeneralMethods.encodeCommunication(params);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getUserReservations?" + params)).build();
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

    public static String getCurrentId() throws UnsupportedEncodingException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/currentReservationId")).build();
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
