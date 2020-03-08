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

import java.io.UnsupportedEncodingException;

public class Reservation {
    private IntegerProperty id;
    private StringProperty username;
    private IntegerProperty room;
    private StringProperty date;
    private StringProperty starting_time;
    private StringProperty ending_time;

    public Reservation() {
        this.id = new SimpleIntegerProperty(-1);
        this.username = new SimpleStringProperty(null);
        this.room = new SimpleIntegerProperty(-1);
        this.date = new SimpleStringProperty(null);
        this.starting_time = new SimpleStringProperty(null);
        this.ending_time = new SimpleStringProperty(null);
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

    public IntegerProperty getId() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }


    public StringProperty getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username.set(username);
    }


    public IntegerProperty getRoom() {
        return room;
    }
    public void setRoom(int room) {
        this.room.set(room);
    }


    public StringProperty getDate() {
        return date;
    }
    public void setDate(String date) {this.date.set(date); }


    public StringProperty getStarting_time() {
        return starting_time;
    }
    public void setStarting_time(String starting_time) {this.starting_time.set(starting_time); }


    public StringProperty getEnding_time() {
        return ending_time;
    }
    public void setEnding_time(String ending_time) {
        this.ending_time.set(ending_time);
    }

    /**
     * Convert the server sent code into an Observable List of Reservation.
     * @return Observable List of Reservations.
     * @throws JSONException
     */
    public static ObservableList<Reservation> getReservation() throws JSONException, UnsupportedEncodingException {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        JSONArray jsonArrayReservation= new JSONArray(AdminManageServerCommunication.getReservation(1));
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




