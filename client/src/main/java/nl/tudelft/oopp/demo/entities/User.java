package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import org.json.JSONArray;
import org.json.JSONException;

public class User {

    private StringProperty username;
    private StringProperty password;
    private IntegerProperty type;

    public User() {
        this.username = new SimpleStringProperty(null);
        this.password = new SimpleStringProperty(null);
        this.type = new SimpleIntegerProperty(-1);
    }

    /**
     * Constructor with some initial data.
     * Simple string property is used because it provides data binding.
     */
    public User(String username, String password, int type) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.type = new SimpleIntegerProperty(type);
    }

    public StringProperty getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password.set(password);
    }

    public IntegerProperty getType() {
        return type;
    }
    public void setType(int type) {
        this.type.set(type);
    }

    /**
     * Convert the server sent code into an Observable List of Reservation.
     * @return Observable List of Reservations.
     * @throws JSONException
     */
    public static ObservableList<User> getUsers() throws JSONException {
        ObservableList<User> userList = FXCollections.observableArrayList();
        JSONArray jsonArrayUser= new JSONArray(AdminManageServerCommunication.getAllUsers());
        for(int i=0; i<jsonArrayUser.length(); i++) {

            User u = new User();
            u.setUsername(jsonArrayUser.getJSONObject(i).getString("username"));
            u.setPassword(jsonArrayUser.getJSONObject(i).getString("password"));
            u.setType(jsonArrayUser.getJSONObject(i).getInt("type"));

            userList.add(u);
        }
        return userList;
    }
}
