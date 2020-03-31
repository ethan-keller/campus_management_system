package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomServerCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    private static Logger logger = Logger.getLogger("GlobalLogger");

    /**
     * This client-server method is used to create a new room using the attributes passed as
     * parameters.
     * @param name - Room name
     * @param building - Building name
     * @param teacherOnly - Teacher only condition
     * @param capacity - Room capacity
     * @param photos - Photos of the room
     * @param description - Room description
     * @param type - Room type (Lecture hall, project room, etc)
     * @return Boolean value which indicates to the user if Room creation is successful.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean createRoom(String name, int building, boolean teacherOnly,
                                     int capacity, String photos, String description, String type)
            throws UnsupportedEncodingException {
        String params = "name=" + name + "&building=" + building + "&teacherOnly="
                + teacherOnly + "&capacity=" + capacity + "&photos=" + photos + "&description="
                + description + "&type=" + type;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createRoom?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
            return false;
        }
        return true;
    }

    /**
     * This client-server method is used to update a room using the passed paramters.
     * @param id - Room id
     * @param name - Room name
     * @param building - Building name
     * @param teacherOnly - Teacher only condition
     * @param capacity - Room capacity
     * @param photos - Photos of the room
     * @param description - Room description
     * @param type - Room type (Lecture hall, project room, etc)
     * @return Boolean value if the room is updated or not.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean updateRoom(int id, String name, int building, boolean teacherOnly,
                                     int capacity, String photos, String description, String type)
            throws UnsupportedEncodingException {
        String params = "id=" + id + "&name=" + name + "&building=" + building + "&teacherOnly="
                + teacherOnly + "&capacity=" + capacity + "&photos=" + photos + "&description="
                + description + "&type=" + type;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/updateRoom?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
            return false;
        }
        return true;
    }

    /**
     * This client-server method is used to delete a room from the database.
     * @param id - Room id
     * @return - Boolean value to inform if the room is deleted.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean deleteRoom(int id) throws UnsupportedEncodingException {
        String params = "id=" + id;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteRoom?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
            return false;
        }
        return true;
    }

    /**
     * This client-server method is used to get a single room which corresponds to the room id.
     * @param id - Room id
     * @return Room
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getRoom(int id) throws UnsupportedEncodingException {
        String params = "id=" + id;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getRoom?" + params)).build();
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
     * This client-server method is used to get all the rooms present in the database.
     * @return Rooms (ALL)
     */
    public static String getAllRooms() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllRooms")).build();
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
}
