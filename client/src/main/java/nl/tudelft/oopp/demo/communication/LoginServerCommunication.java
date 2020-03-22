package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * The sendLogin method encodes the information that is passed to the server in line 23
<<<<<<< HEAD
     * A http request is created to send all the information that is entered in the text field
     * by the user is sent to the server
     * There is a try-catch block that alerts the user if the communication between the client
     * and the server has succeeded or
=======
     * A http request is created to send all the information that is entered in the text field by the user is sent to the server
     * There is a try-catch block that alerts the user if the communication between the client and the server has succeeded or
>>>>>>> develop
     * fallen through.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication has unsupported encoding mechanism.
     */
    public static String sendLogin(String username, String password)
            throws UnsupportedEncodingException {
        String params = "username=" + username + "&password=" + password;
        params = GeneralMethods.encodeCommunication(params);


        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/login?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
        return response.body();
    }
}
