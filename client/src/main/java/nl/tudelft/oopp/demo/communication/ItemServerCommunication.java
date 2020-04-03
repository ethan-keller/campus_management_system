package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendGet;
import static nl.tudelft.oopp.demo.communication.GeneralCommunication.sendPost;

import java.net.http.HttpClient;

/**
 * .
 * Class that controls client-server communication for Item objects
 */
public class ItemServerCommunication {

    /**
     * HttpClient which sends and receives information to/from the server.
     */
    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Gets all the items in the database.
     *
     * @return http response in JSON format
     */
    public static String getAllItems() {
        return sendGet(client, "getAllItems", "");
    }

    /**
     * Gets one Item with the given id.
     *
     * @param id id of the wanted Item
     * @return http response in JSON format
     * @ if improperly encoded
     */
    public static String getItem(int id)  {
        String params = "id=" + id;
        return sendGet(client, "getItem", params);
    }

    /**
     * Create a new Item in the database.
     *
     * @param user         user who item belongs to
     * @param title        title of item
     * @param date         date of item
     * @param startingTime startingTime of item
     * @param endingTime   endingTime of item
     * @param description  description of item
     * @return http response in JSON format
     * @ if improperly encoded
     */
    public static boolean createItem(String user, String title, String date, String startingTime,
                                     String endingTime, String description)  {
        String params = "user=" + user + "&title=" + title + "&date=" + date + "&startingTime=" + startingTime
                + "&endingTime=" + endingTime + "&description=" + description;

        return sendPost(client, "createItem", params);
    }

    /**
     * Deletes the Item identified by the given id.
     *
     * @param id id of item that must be deleted
     * @return http response in JSON format
     * @ if improperly encoded
     */
    public static boolean deleteItem(int id)  {
        String params = "id=" + id;
        return sendPost(client, "deleteItem", params);
    }

    /**
     * Gets the id of the last inserted item in the database.
     *
     * @return http response in JSON format
     */
    public static String getCurrentId() {
        return sendGet(client, "currentId", "");
    }

    /**
     * Gets all the items of a particular user.
     *
     * @param user user who's items are needed
     * @return http response in JSON format
     * @ if improperly encoded
     */
    public static String getUserItems(String user)  {
        String params = "user=" + user;
        return sendGet(client, "getUserItems", params);
    }
}
