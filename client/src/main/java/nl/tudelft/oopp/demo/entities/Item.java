package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.ItemServerCommunication;
import org.json.JSONArray;

import java.util.Objects;

/**
 * Entity class for calendar items
 */
public class Item {

    private IntegerProperty id;
    private StringProperty user;
    private StringProperty title;
    private StringProperty date;
    private StringProperty startingTime;
    private StringProperty endingTime;
    private StringProperty description;

    /**
     * Constructor
     * @param id id of item
     * @param user to whom item belongs
     * @param title title of the item
     * @param date date of item
     * @param startingTime startingTime of item
     * @param endingTime endingTime of item
     * @param description description of item
     */
    public Item(int id, String user, String title, String date, String startingTime, String endingTime, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.user = new SimpleStringProperty(user);
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleStringProperty(date);
        this.startingTime = new SimpleStringProperty(startingTime);
        this.endingTime = new SimpleStringProperty(endingTime);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Default constructor
     */
    public Item() {
        this.id = new SimpleIntegerProperty(-1);
        this.user = new SimpleStringProperty(null);
        this.title = new SimpleStringProperty(null);
        this.date = new SimpleStringProperty(null);
        this.startingTime = new SimpleStringProperty(null);
        this.endingTime = new SimpleStringProperty(null);
        this.description = new SimpleStringProperty(null);
    }

    /**
     * getter for id
     * @return id of item
     */
    public IntegerProperty getId() {
        return id;
    }

    /**
     * setter for id
     * @param id of item
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * getter for user
     * @return user of item
     */
    public StringProperty getUser() {
        return user;
    }

    /**
     * setter for user
     * @param user of item
     */
    public void setUser(String user) {
        this.user.set(user);
    }

    /**
     * getter for title
     * @return title of item
     */
    public StringProperty getTitle() {
        return title;
    }

    /**
     * setter for title
     * @param title of item
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * getter for date
     * @return date of item
     */
    public StringProperty getDate() {
        return date;
    }

    /**
     * setter for date
     * @param date of item
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    /**
     * getter for startingTime
     * @return startingTime of item
     */
    public StringProperty getStartingTime() {
        return startingTime;
    }

    /**
     * setter for startingTime
     * @param startingTime of item
     */
    public void setStartingTime(String startingTime) {
        this.startingTime.set(startingTime);
    }

    /**
     * getter for endingTime
     * @return endingTime of item
     */
    public StringProperty getEndingTime() {
        return endingTime;
    }

    /**
     * setter for endingTime
     * @param endingTime of item
     */
    public void setEndingTime(String endingTime) {
        this.endingTime.set(endingTime);
    }

    /**
     * getter for description
     * @return description of item
     */
    public StringProperty getDescription() {
        return description;
    }

    /**
     * setter for description
     * @param description of item
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Equals method to compare two Item objects.
     * @param o the item to compare to
     * @return true if id's match, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId());
    }

    /**
     * Method that gets all the Items in the database and parses the JSON into ObservableList.
     * @return ObservableList with all items in the database
     */
    public static ObservableList<Item> getAllItems() {
        try {
            ObservableList<Item> itemData = FXCollections.observableArrayList();
            JSONArray itemArray = new JSONArray(ItemServerCommunication.getAllItems());
            for (int i = 0; i < itemArray.length(); i++) {
                Item item = new Item();
                item.setId(itemArray.getJSONObject(i).getInt("id"));
                item.setUser(itemArray.getJSONObject(i).getString("user"));
                item.setTitle(itemArray.getJSONObject(i).getString("title"));
                item.setDate(itemArray.getJSONObject(i).getString("date"));
                item.setStartingTime(itemArray.getJSONObject(i).getString("startingTime"));
                item.setEndingTime(itemArray.getJSONObject(i).getString("endingTime"));
                item.setDescription(itemArray.getJSONObject(i).getString("description"));
                itemData.add(item);
            }
            return itemData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all items of a particular user in the database.
     * @param user the user from which we need the items
     * @return ObservableList with all items of the user
     */
    public static ObservableList<Item> getUserItems(String user) {
        try {
            ObservableList<Item> itemData = FXCollections.observableArrayList();
            JSONArray array = new JSONArray(ItemServerCommunication.getUserItems(user));
            for (int i = 0; i < array.length(); i++) {
                Item item = new Item();
                item.setId(array.getJSONObject(i).getInt("id"));
                item.setUser(array.getJSONObject(i).getString("user"));
                item.setTitle(array.getJSONObject(i).getString("title"));
                item.setDate(array.getJSONObject(i).getString("date"));
                item.setStartingTime(array.getJSONObject(i).getString("startingTime"));
                item.setEndingTime(array.getJSONObject(i).getString("endingTime"));
                item.setDescription(array.getJSONObject(i).getString("description"));
                itemData.add(item);
            }
            return itemData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
