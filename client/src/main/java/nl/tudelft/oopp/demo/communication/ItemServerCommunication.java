package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Class that controls client-server communication for Item objects
 */
public class ItemServerCommunication {

    /**
     * HttpClient which sends and receives information to/from the server
     */
    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Gets all the items in the database
     * @return http response in JSON format
     */
    public static String getAllItems(){
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllItems")).build();
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
     * Gets one Item with the given id
     * @param id id of the wanted Item
     * @return http response in JSON format
     * @throws UnsupportedEncodingException
     */
    public static String getItem(int id) throws UnsupportedEncodingException {
        String params = "id="+id;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getItem?"+params)).build();
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
     * Create a new Item in the database
     * @param user user who item belongs to
     * @param title title of item
     * @param date date of item
     * @param startingTime startingTime of item
     * @param endingTime endingTime of item
     * @param description description of item
     * @return http response in JSON format
     * @throws UnsupportedEncodingException
     */
    public static boolean createItem(String user, String title, String date, String startingTime, String endingTime, String description) throws UnsupportedEncodingException {
        String params = "user="+user+"&title="+title+"&date="+date+"&startingTime="+startingTime+"&endingTime="+endingTime+"&description="+description;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createItem?"+params)).build();
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
     * Deletes the Item identified by the given id
     * @param id id of item that must be deleted
     * @return http response in JSON format
     * @throws UnsupportedEncodingException
     */
    public static boolean deleteItem(int id) throws UnsupportedEncodingException {
        String params = "id="+id;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteItem?"+params)).build();
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
     * Gets the id of the last inserted item in the database
     * @return http response in JSON format
     */
    public static String getCurrentId(){
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/currentId")).build();
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
     * Gets all the items of a particular user
     * @param user user who's items are needed
     * @return http response in JSON format
     * @throws UnsupportedEncodingException
     */
    public static String getUserItems(String user) throws UnsupportedEncodingException {
        String params = "user="+user;
        params = GeneralMethods.encodeCommunication(params);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getUserItems?" + params)).build();
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
}
