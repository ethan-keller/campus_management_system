package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegisterServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a quote from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String sendRegister(String username, String password) {
        String params = "?username=" + username + "&password=" + password;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/register" + params)).build();
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
