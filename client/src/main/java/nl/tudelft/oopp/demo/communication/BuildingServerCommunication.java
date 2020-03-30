package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildingServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    private static Logger logger = Logger.getLogger("GlobalLogger");

    /**
     * This is a server-client communication method which is used to receive all the buildings
     * present in the database.
     * @return This particular method returns a String of all buildings present in the database.
     */
    public static String getAllBuildings() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllBuildings")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return null;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * This is a client-server communication class which receives a certain building using the building id.
     * @param id - Building id
     * @return Building is returned
     * @throws UnsupportedEncodingException is thrown
     */
    public static String getBuilding(int id) throws UnsupportedEncodingException {
        String params = "id=" + id;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getBuilding?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return null;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Returns a list of buildings in JSON from the database based on the food ID.
     * @param id The food id
     * @return Returns a JSON list
     */
    public static String getBuildingByFoodId(int id) {
        String params = "id=" + id;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getBuildingByFoodId?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return null;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * This is a client-server communication method which is used to create a new building using the attributes.
     * Attributes are the parameters.
     * @param name - Name of the building
     * @param roomCount - Number of rooms the building has
     * @param address - Address of the building
     * @return :Boolean value to inform the user if building creating was successful.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean createBuilding(String name, int roomCount, String address)
            throws UnsupportedEncodingException {
        String params = "name=" + name + "&roomCount=" + roomCount + "&address=" + address
                + "&availableBikes=0&maxBikes=0";

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createBuilding?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
            return false;
        }
        return true;
    }

    /**
     * Creating a building with the given parameters.
     * @param name - Name of the building
     * @param roomCount - Number of rooms in the building
     * @param address - Address of the building
     * @param maxBikes - Maximum bikes available to that building
     * @return Boolean value which informs the user if the building creation is successful or not.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean createBuilding(String name, int roomCount, String address, int maxBikes)
            throws UnsupportedEncodingException {
        String params = "name=" + name + "&roomCount=" + roomCount + "&address=" + address + "&availableBikes="
                + maxBikes + "&maxBikes=" + maxBikes;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createBuilding?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
            return false;
        }
        return true;
    }

    /**.
     * Creating a building
     * @param name - Name of the building
     * @param roomCount - Number of rooms in the building
     * @param address - Address of the building
     * @param availableBikes - Available bikes left in the building
     * @param maxBikes - Maximum bikes available to that building
     * @return Boolean value which informs the user if the building creation is successful or not.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean createBuilding(String name, int roomCount, String address, int availableBikes,
                                         int maxBikes) throws UnsupportedEncodingException {
        String params = "name=" + name + "&roomCount=" + roomCount + "&address=" + address
                + "&availableBikes=" + availableBikes + "&maxBikes=" + maxBikes;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createBuilding?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
            return false;
        }
        return true;
    }

    /**
     * This is a client-server communication method which is used to delete a certain building.
     * The building is recognized by the parameter passed.
     * @param id - Building id
     * @return Boolean value which announces to the user whether the building is deleted.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean deleteBuilding(int id) throws UnsupportedEncodingException {
        String params = "id=" + id;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteBuilding?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
            return false;
        }
        return true;
    }

    /**
     * This is a client-server communication method which is used to update the attributes of a building.
     * Attributes are the parameters.
     * @param id - Building id
     * @param name - Name of the building
     * @param roomCount - Number of rooms the building has
     * @param address - Address of the building
     * @param maxBikes - Maximum number of bikes available in the building
     * @return Boolean value which is used to display a message to the client if the building is updated.
     * @throws UnsupportedEncodingException is thrown
     */
    public static boolean updateBuilding(int id, String name, int roomCount, String address, int maxBikes)
            throws UnsupportedEncodingException {
        String params = "id=" + id + "&name=" + name + "&roomCount=" + roomCount + "&address=" + address
                + "&maxBikes=" + maxBikes;
        params = GeneralMethods.encodeCommunication(params);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/updateBuilding?" + params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
        if (response.statusCode() != 200) {
            logger.log(Level.SEVERE, "Server responded with status code: " + response.statusCode());
            return false;
        }
        return true;
    }
}
