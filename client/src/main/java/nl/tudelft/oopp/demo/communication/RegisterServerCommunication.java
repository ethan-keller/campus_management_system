package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class RegisterServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * The sendRegister method encodes the information that is passed to the server in line 26
     * A http request is created to send all the information that is entered in the text field
     * by the user is sent to the server
     * There is a try-catch block that alerts the user if the communication between the client
     * and the server has succeeded or
     * fallen through.
     * @return the body of a get request to the server.
     * @throws Exception if communication has unsupported encoding mechanism.
     */
    public static String sendRegister(String username, String password, int userType)
            throws UnsupportedEncodingException {
        String params = "username=" + username + "&password=" + password + "&userType=" + userType;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/register?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }


}
