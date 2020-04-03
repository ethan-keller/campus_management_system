package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendPost;

import java.net.http.HttpClient;

public class RegisterServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Tells the server to create a new user identified by username, password and user type.
     *
     * @return the body of a get request to the server.
     */
    public static boolean sendRegister(String username, String password, int userType) {
        String params = "username=" + username + "&password=" + password + "&userType=" + userType;
        return sendPost(client, "register", params);
    }


}
