package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendGet;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;

public class LoginServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * The sendLogin method encodes the information that is passed to the server in line 23
     * A http request is created to send all the information that is entered in the text field
     * by the user is sent to the server
     * There is a try-catch block that alerts the user if the communication between the client
     * and the server has succeeded or
     * fallen through.
     *
     * @return the body of a get request to the server.
     * @throws UnsupportedEncodingException if improperly encoded
     */
    public static String sendLogin(String username, String password) {
        String params = "username=" + username + "&password=" + password;

        return sendGet(client, "login", params);
    }
}
