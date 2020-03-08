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

public class Reservation {
    private IntegerProperty id;
    private StringProperty username;
    private IntegerProperty room;
    private StringProperty date;
    private StringProperty starting_time;
    private StringProperty ending_time;

    public Reservation() {
    }
    /**
     * Constructor with some initial data.
     * Simple string property is used because it provides data binding.
     */
    public Reservation(int id, String username, int room, String date, String starting_time, String ending_time) {
        this.id =  new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.room = new SimpleIntegerProperty(room);
        this.date = new SimpleStringProperty(date);
        this.starting_time = new SimpleStringProperty(starting_time);
        this.ending_time = new SimpleStringProperty(ending_time);

    }

    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }


    public String getUsername() {
        return username.get();
    }
    public void setUsername(String username) {
        this.username = new SimpleStringProperty(username);
    }


    public int getRoom() {
        return room.get();
    }
    public void setRoom(int room) {
        this.room = new SimpleIntegerProperty(room);
    }


    public String getDate() {
        return date.get();
    }
    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }


    public String getStarting_time() {
        return starting_time.get();
    }
    public void setStarting_time(String starting_time) {this.starting_time = new SimpleStringProperty(starting_time); }


    public String getEnding_time() {
        return ending_time.get();
    }
    public void setEnding_time(String ending_time) {
        this.ending_time = new SimpleStringProperty(ending_time);
    }


    public static ObservableList<Reservation> getReservation() throws JSONException {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        JSONArray jsonArrayReservation= new JSONArray(AdminManageServerCommunication.getAllReservations());
        for(int i=0; i<jsonArrayReservation.length(); i++) {
            Reservation r = new Reservation();
            r.setId(jsonArrayReservation.getJSONObject(i).getInt("id"));
            r.setUsername(jsonArrayReservation.getJSONObject(i).getString("username"));
            r.setDate(jsonArrayReservation.getJSONObject(i).getString("date"));
            r.setRoom(jsonArrayReservation.getJSONObject(i).getInt("room"));
            r.setStarting_time(jsonArrayReservation.getJSONObject(i).getString("starting_time"));
            r.setEnding_time(jsonArrayReservation.getJSONObject(i).getString("ending_time"));
            reservationList.add(r);
        }
        return reservationList;
    }
}




