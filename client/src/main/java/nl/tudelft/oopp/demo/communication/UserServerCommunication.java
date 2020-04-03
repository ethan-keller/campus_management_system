package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendGet;
import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendPost;

import java.net.http.HttpClient;

public class UserServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * This client-server method is used to get all the users.
     *
     * @return all users
     */
    public static String getAllUsers() {
        return sendGet(client, "getAllUsers", "");
    }

    /**
     * This client-server method is used to get a particular user.
     *
     * @param username username of the user
     * @return User object
     */
    public static String getUser(String username) {
        String params = "username=" + username;
        return sendGet(client, "getUser", params);
    }

    /**
     * This client-server method is used to delete an user.
     *
     * @param username username of the user
     * @return true if communication was successful, false otherwise
     */
    public static boolean deleteUser(String username) {
        String params = "username=" + username;
        return sendPost(client, "deleteUser", params);
    }

    /**
     * This client-server method is used to update the information assigned to a user.
     *
     * @param username - User's username
     * @param password - User's Password
     * @param type     - Type of user (teacher, student or admin)
     * @return true if communication was successful, false otherwise
     */
    public static boolean updateUser(String username, String password, int type) {
        String params = "username=" + username + "&password=" + password + "&type=" + type;
        return sendPost(client, "updateUser", params);
    }

    /**
     * This client-server method is used to create a new user.
     *
     * @param username - User's username
     * @param password - User's Password
     * @param type     - Type of user (teacher, student or admin)
     * @return true if communication was successful, false otherwise
     */
    public static boolean createUser(String username, String password, int type) {
        String params = "username=" + username + "&password=" + password + "&type=" + type;
        return sendPost(client, "createUser", params);
    }
}
