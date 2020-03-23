package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.ItemServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class Item {

    private IntegerProperty id;
    private StringProperty user;
    private StringProperty title;
    private StringProperty date;
    private StringProperty startingTime;
    private StringProperty endingTime;
    private StringProperty description;

    public Item(int id, String user, String title, String date, String startingTime, String endingTime, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.user = new SimpleStringProperty(user);
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleStringProperty(date);
        this.startingTime = new SimpleStringProperty(startingTime);
        this.endingTime = new SimpleStringProperty(endingTime);
        this.description = new SimpleStringProperty(description);
    }

    public Item() {
        this.id = new SimpleIntegerProperty(-1);
        this.user = new SimpleStringProperty(null);
        this.title = new SimpleStringProperty(null);
        this.date = new SimpleStringProperty(null);
        this.startingTime = new SimpleStringProperty(null);
        this.endingTime = new SimpleStringProperty(null);
        this.description = new SimpleStringProperty(null);
    }

    public IntegerProperty getId() {
        return id;
    }


    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public StringProperty getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime.set(startingTime);
    }

    public StringProperty getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime.set(endingTime);
    }

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId());
    }

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

    public static Item getItem(int id) {
        try {
            JSONObject jsonObject = new JSONObject(ItemServerCommunication.getItem(id));
            Item item = new Item();
            item.setId(jsonObject.getInt("id"));
            item.setUser(jsonObject.getString("user"));
            item.setTitle(jsonObject.getString("title"));
            item.setDate(jsonObject.getString("date"));
            item.setStartingTime(jsonObject.getString("startingTime"));
            item.setEndingTime(jsonObject.getString("endingTime"));
            item.setDescription(jsonObject.getString("description"));
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
