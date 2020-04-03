package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendPost;

import java.net.http.HttpClient;

public class RegisterServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * The sendRegister method encodes the information that is passed to the server in line 26
     * A http request is created to send all the information that is entered in the text field
     * by the user is sent to the server
     * There is a try-catch block that alerts the user if the communication between the client
     * and the server has succeeded or
     * fallen through.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication has unsupported encoding mechanism.
     */
    public static boolean sendRegister(String username, String password, int userType) {
        String params = "username=" + username + "&password=" + password + "&userType=" + userType;
        return sendPost(client, "register", params);
    }


}
