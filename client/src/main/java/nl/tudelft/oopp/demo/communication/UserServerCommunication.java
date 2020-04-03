package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendGet;
import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendPost;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;

public class UserServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * This client-server method is used to get all the users from the database.
     *
     * @return Users (ALL)
     */
    public static String getAllUsers() {
        return sendGet(client, "getAllUsers", "");
    }

    /**
     * This client-server method is used to get a particular user and the user is identified
     * using hos/her username.
     *
     * @param username - User's username
     * @return User
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getUser(String username) {
        String params = "username=" + username;

        return sendGet(client, "getUser", params);
    }

    /**
     * This client-server method is used to delete an user from the database.
     * The user is identified using his/her username.
     *
     * @param username - User's username
     * @return Boolean value indicating if the user is deleted.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean deleteUser(String username) {
        String params = "username=" + username;

        return sendPost(client, "deleteUser", params);
    }

    /**
     * This client-server method is used to update the information assigned to an user.
     *
     * @param username - User's username
     * @param password - User's Password
     * @param type     - Type of user (teacher, student or admin)
     * @return Boolean value to inform the user if the users' information is updated.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean updateUser(String username, String password, int type) {
        String params = "username=" + username + "&password=" + password + "&type=" + type;

        return sendPost(client, "updateUser", params);
    }

    /**
     * This client-server method is used to create a new user using the following parameters.
     *
     * @param username - User's username
     * @param password - User's Password
     * @param type     - Type of user (teacher, student or admin)
     * @return Boolean value to inform the user if the users' account is created.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean createUser(String username, String password, int type) {
        String params = "username=" + username + "&password=" + password + "&type=" + type;

        return sendPost(client, "createUser", params);
    }
}
