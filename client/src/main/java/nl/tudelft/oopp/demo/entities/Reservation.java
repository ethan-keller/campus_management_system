package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.controllers.AdminManageUserViewController;
import org.json.JSONArray;


public class Reservation {
    private IntegerProperty id;
    private StringProperty username;
    //Room means the room-id of the particular room.
    private IntegerProperty room;
    private StringProperty date;
    private StringProperty startingTime;
    private StringProperty endingTime;

    public Reservation() {
        this.id = new SimpleIntegerProperty(-1);
        this.username = new SimpleStringProperty(null);
        this.room = new SimpleIntegerProperty(-1);
        this.date = new SimpleStringProperty(null);
        this.startingTime = new SimpleStringProperty(null);
        this.endingTime = new SimpleStringProperty(null);
    }

    /**
     * Constructor with some initial data.
     * Simple string property is used because it provides data binding.
     */
    public Reservation(int id, String username, int room, String date, String startingTime, String endingTime) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.room = new SimpleIntegerProperty(room);
        this.date = new SimpleStringProperty(date);
        this.startingTime = new SimpleStringProperty(startingTime);
        this.endingTime = new SimpleStringProperty(endingTime);

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
    


    public void setDate(String date) {
        this.date.set(date);
    }


    public StringProperty getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime.set(startingTime);
    }

    public void setStarting_time(String starting_time) {
        this.startingTime.set(starting_time);
    }


    public StringProperty getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime.set(endingTime);
    }

    /**
     * Convert the server sent code into an Observable List of Reservation.
     *
     * @return Observable List of Reservations.
     */
    public static ObservableList<Reservation> getReservation() {
        try {
            ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
            JSONArray jsonArrayReservation = new JSONArray(ReservationServerCommunication.getAllReservations());
            for (int i = 0; i < jsonArrayReservation.length(); i++) {
                Reservation r = new Reservation();
                r.setId(jsonArrayReservation.getJSONObject(i).getInt("id"));
                r.setUsername(jsonArrayReservation.getJSONObject(i).getString("username"));
                r.setDate(jsonArrayReservation.getJSONObject(i).getString("date"));
                r.setRoom(jsonArrayReservation.getJSONObject(i).getInt("room"));
                r.setStartingTime(jsonArrayReservation.getJSONObject(i).getString("startingTime"));
                r.setEndingTime(jsonArrayReservation.getJSONObject(i).getString("endingTime"));
                reservationList.add(r);
            }
            return reservationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert the server sent code into an Observable List of Reservation for the particular user!!
     *
     * @return Observable List of Reservations.
     */
    public static ObservableList<Reservation> getUserReservation() {
        try {
            ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
            JSONArray jsonArrayReservation = new JSONArray(ReservationServerCommunication.getUserReservations(CurrentUserManager.getUsername()));
            for (int i = 0; i < jsonArrayReservation.length(); i++) {
                Reservation r = new Reservation();
                r.setId(jsonArrayReservation.getJSONObject(i).getInt("id"));
                r.setUsername(jsonArrayReservation.getJSONObject(i).getString("username"));
                r.setDate(jsonArrayReservation.getJSONObject(i).getString("date"));
                r.setRoom(jsonArrayReservation.getJSONObject(i).getInt("room"));
                r.setStartingTime(jsonArrayReservation.getJSONObject(i).getString("startingTime"));
                r.setEndingTime(jsonArrayReservation.getJSONObject(i).getString("endingTime"));
                reservationList.add(r);
            }
            return reservationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ObservableList<Reservation> getSelectedUserReservation() {
        try {
            ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
            JSONArray jsonArrayReservation = new JSONArray(ReservationServerCommunication.getUserReservations(AdminManageUserViewController.currentSelectedUser.getUsername().get()));
            for (int i = 0; i < jsonArrayReservation.length(); i++) {
                Reservation r = new Reservation();
                r.setId(jsonArrayReservation.getJSONObject(i).getInt("id"));
                r.setUsername(jsonArrayReservation.getJSONObject(i).getString("username"));
                r.setDate(jsonArrayReservation.getJSONObject(i).getString("date"));
                r.setRoom(jsonArrayReservation.getJSONObject(i).getInt("room"));
                r.setStartingTime(jsonArrayReservation.getJSONObject(i).getString("startingTime"));
                r.setEndingTime(jsonArrayReservation.getJSONObject(i).getString("endingTime"));
                reservationList.add(r);
            }
            return reservationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
