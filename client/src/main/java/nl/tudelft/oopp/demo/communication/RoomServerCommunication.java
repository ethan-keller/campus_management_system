package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendGet;
import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendPost;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;

public class RoomServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * This client-server method is used to create a new room using the attributes passed as
     * parameters.
     *
     * @param name        - Room name
     * @param building    - Building name
     * @param teacherOnly - Teacher only condition
     * @param capacity    - Room capacity
     * @param photos      - Photos of the room
     * @param description - Room description
     * @param type        - Room type (Lecture hall, project room, etc)
     * @return Boolean value which indicates to the user if Room creation is successful.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean createRoom(String name, int building, boolean teacherOnly,
                                     int capacity, String photos, String description, String type) {
        String params = "name=" + name + "&building=" + building + "&teacherOnly="
                + teacherOnly + "&capacity=" + capacity + "&photos=" + photos + "&description="
                + description + "&type=" + type;

        return sendPost(client, "createRoom", params);
    }

    /**
     * This client-server method is used to update a room using the passed paramters.
     *
     * @param id          - Room id
     * @param name        - Room name
     * @param building    - Building name
     * @param teacherOnly - Teacher only condition
     * @param capacity    - Room capacity
     * @param photos      - Photos of the room
     * @param description - Room description
     * @param type        - Room type (Lecture hall, project room, etc)
     * @return Boolean value if the room is updated or not.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean updateRoom(int id, String name, int building, boolean teacherOnly,
                                     int capacity, String photos, String description, String type) {
        String params = "id=" + id + "&name=" + name + "&building=" + building + "&teacherOnly="
                + teacherOnly + "&capacity=" + capacity + "&photos=" + photos + "&description="
                + description + "&type=" + type;
        return sendPost(client, "updateRoom", params);
    }

    /**
     * This client-server method is used to delete a room from the database.
     *
     * @param id - Room id
     * @return - Boolean value to inform if the room is deleted.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean deleteRoom(int id) {
        String params = "id=" + id;

        return sendPost(client, "deleteRoom", params);
    }

    /**
     * This client-server method is used to get a single room which corresponds to the room id.
     *
     * @param id - Room id
     * @return Room
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getRoom(int id) {
        String params = "id=" + id;

        return sendGet(client, "getRoom", params);
    }

    /**
     * This client-server method is used to get all the rooms present in the database.
     *
     * @return Rooms (ALL)
     */
    public static String getAllRooms() {
        return sendGet(client, "getAllRooms", "");
    }
}
