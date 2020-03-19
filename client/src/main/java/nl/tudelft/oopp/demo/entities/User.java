package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import org.json.JSONArray;
import org.json.JSONException;

public class User {

    private StringProperty username;
    private StringProperty userPassword;
    private IntegerProperty userType;

    public User() {
        this.username = new SimpleStringProperty(null);
        this.userPassword = new SimpleStringProperty(null);
        this.userType = new SimpleIntegerProperty(-1);
    }

    public User(String username, String userPassword, int userType) {
        this.username = new SimpleStringProperty(username);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.userType = new SimpleIntegerProperty(userType);
    }

    public StringProperty getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username.set(username); }



    public StringProperty getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) { this.userPassword.set(userPassword); }



    public IntegerProperty getUserType() {
        return userType;
    }

    public void setUserType(int userType) { this.userType.set(userType); }

    /**
     * Convert server response into an ObservableList of rooms.
     */
    public static ObservableList<User> getUserData() throws JSONException {
        ObservableList<User> userData = FXCollections.observableArrayList();
        JSONArray jsonArrayUsers = new JSONArray(UserServerCommunication.getAllUsers());
        for(int i=0; i<jsonArrayUsers.length(); i++){
            User u = new User();
            u.setUsername(jsonArrayUsers.getJSONObject(i).getString("username") );
            u.setUserPassword(jsonArrayUsers.getJSONObject(i).getString("password") );
            u.setUserType(jsonArrayUsers.getJSONObject(i).getInt("type") );
            userData.add(u);
        }
        return userData;
    }
}
