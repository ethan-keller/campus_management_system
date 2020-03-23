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
    private StringProperty starting_time;
    private StringProperty ending_time;
    private StringProperty description;

    public Item(int id, String user, String title, String date, String starting_time, String ending_time, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.user = new SimpleStringProperty(user);
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleStringProperty(date);
        this.starting_time = new SimpleStringProperty(starting_time);
        this.ending_time = new SimpleStringProperty(ending_time);
        this.description = new SimpleStringProperty(description);
    }

    public Item() {
        this.id = new SimpleIntegerProperty(-1);
        this.user = new SimpleStringProperty(null);
        this.title = new SimpleStringProperty(null);
        this.date = new SimpleStringProperty(null);
        this.starting_time = new SimpleStringProperty(null);
        this.ending_time = new SimpleStringProperty(null);
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

    public StringProperty getStarting_time() {
        return starting_time;
    }

    public void setStarting_time(String starting_time) {
        this.starting_time.set(starting_time);
    }

    public StringProperty getEnding_time() {
        return ending_time;
    }

    public void setEnding_time(String ending_time) {
        this.ending_time.set(ending_time);
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
                item.setStarting_time(itemArray.getJSONObject(i).getString("starting_time"));
                item.setEnding_time(itemArray.getJSONObject(i).getString("ending_time"));
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
            item.setStarting_time(jsonObject.getString("starting_time"));
            item.setEnding_time(jsonObject.getString("ending_time"));
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
                item.setStarting_time(array.getJSONObject(i).getString("starting_time"));
                item.setEnding_time(array.getJSONObject(i).getString("ending_time"));
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
