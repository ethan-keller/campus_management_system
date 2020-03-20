package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserServerCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * This client-server method is used to get all the users from the database.
     * @return Users (ALL)
     */
    public static String getAllUsers() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllUsers")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
        return response.body();
    }

    /**
     * This client-server method is used to get a particular user and the user is identified
     * using hos/her username.
     * @param username - User's username
     * @return User
     * @throws UnsupportedEncodingException
     */
    public static String getUser(String username) throws UnsupportedEncodingException {
        String params = "username=" + username;
        params = GeneralMethods.encodeCommunication(params);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getUser?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
        return response.body();
    }

    /**
     * This client-server method is used to delete an user from the database.
     * The user is identified using his/her username.
     * @param username - User's username
     * @return Boolean value indicating if the user is deleted.
     * @throws UnsupportedEncodingException
     */
    public static boolean deleteUser(String username) throws UnsupportedEncodingException {
        String params = "username=" + username;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteUser?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
            return false;
        }
        return true;
    }

    /**
     * This client-server method is used to update the information assigned to an user.
     * @param username - User's username
     * @param password - User's Password
     * @param type - Type of user (teacher, student or admin)
     * @return Boolean value to inform the user if the users' information is updated.
     * @throws UnsupportedEncodingException
     */
    public static boolean updateUser(String username, String password, int type)
            throws UnsupportedEncodingException {
        String params = "username=" + username + "&password=" + password + "&type=" + type;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/updateUser?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
            return false;
        }
        return true;
    }

    /**
     * This client-server method is used to create a new user using the following parameters.
     * @param username - User's username
     * @param password - User's Password
     * @param type - Type of user (teacher, student or admin)
     * @return Boolean value to inform the user if the users' account is created.
     * @throws UnsupportedEncodingException
     */
    public static boolean createUser(String username, String password, int type)
            throws UnsupportedEncodingException {
        String params = "username=" + username + "&password=" + password + "&type=" + type;
        params = GeneralMethods.encodeCommunication(params);


        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createUser?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
            return false;
        }
        return true;
    }
}
