package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendGet;
import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendPost;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;

public class ReservationServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * This client-server method is used to create a new reservation using the attributes
     * passed as parameters.
     *
     * @param username     - Username of the user
     * @param room         - Room id
     * @param date         - Date of reservation
     * @param startingTime - Starting time of reservation
     * @param endingTime   - Ending time of the reservation
     * @return Boolean value which indicates to the user if Reservation creation is successful.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean createReservation(String username, int room, String date,
                                            String startingTime, String endingTime) {
        String params = "username=" + username + "&room=" + room + "&date=" + date
                + "&startingTime=" + startingTime + "&endingTime=" + endingTime;
        return sendPost(client, "createReservation", params);
    }

    /**
     * This client-server method is used to update a reservation by the attributes passed as
     * parameters.
     *
     * @param id           - Reservation id
     * @param room         - Room id
     * @param date         - Date of reservation
     * @param startingTime - Starting time of reservation
     * @param endingTime   - Ending time of the reservation
     * @return Boolean value which indicates to the user if Reservation update is successful.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean updateReservation(int id, int room, String date, String startingTime,
                                            String endingTime) {
        String params = "id=" + id + "&room=" + room + "&date=" + date + "&startingTime="
                + startingTime + "&endingTime=" + endingTime;
        return sendPost(client, "updateReservation", params);
    }

    /**
     * This client-server method is used to delete a reservation made by the user.
     * From client side, this method is supposed to be cancelling a reservation.
     *
     * @param id - Reservation id
     * @return Boolean value to indicate if the reservation is deleted.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean deleteReservation(int id) {
        String params = "id=" + id;
        return sendPost(client, "deleteReservation", params);
    }

    /**
     * .
     * This client-server method is used to get the reservation using the user's user id
     *
     * @param id - Reservation id
     * @return Reservation of the user.
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getReservation(int id) {
        String params = "id=" + id;
        return sendGet(client, "getReservation", params);
    }

    /**
     * This client-server method is used to get all the reservations made by users and admin from
     * the database.
     *
     * @return Reservations (ALL)
     */
    public static String getAllReservations() {
        return sendGet(client, "getAllReservations", "");
    }

    /**
     * This client-server method is used to get the reservation of a particular user who could be
     * identified by their username which is passed as a parameter.
     *
     * @param username - Username of the user requesting the reservation
     * @return Reservations of "username"
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getUserReservations(String username) {
        String params = "username=" + username;
        return sendGet(client, "getUserReservations", params);
    }

    /**
     * This client-server method is used to get the id of the last reservation inserted in the database.
     *
     * @return String containing the integer value of the id
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getCurrentId() {
        return sendGet(client, "currentReservationId", "");
    }

}
