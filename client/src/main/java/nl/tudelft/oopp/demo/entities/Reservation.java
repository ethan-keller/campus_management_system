package nl.tudelft.oopp.demo.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.controllers.AdminManageUserViewController;
import org.json.JSONArray;


public class Reservation {

    private static Logger logger = Logger.getLogger("GlobalLogger");
    
    private IntegerProperty id;
    private StringProperty username;
    //Room means the room-id of the particular room.
    private IntegerProperty room;
    private StringProperty date;
    private StringProperty startingTime;
    private StringProperty endingTime;

    /**
     * constructor with some initial data.
     */
    public Reservation() {
        this.id = new SimpleIntegerProperty(-1);
        this.username = new SimpleStringProperty(null);
        this.room = new SimpleIntegerProperty(-1);
        this.date = new SimpleStringProperty(null);
        this.startingTime = new SimpleStringProperty(null);
        this.endingTime = new SimpleStringProperty(null);
    }

    /**
     * Constructor.
     * Simple string property is used because it provides data binding.
     *
     * @param id           int
     * @param username     String
     * @param room         int
     * @param date         String
     * @param startingTime String
     * @param endingTime   String
     */
    public Reservation(int id, String username, int room, String date, String startingTime, String endingTime) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.room = new SimpleIntegerProperty(room);
        this.date = new SimpleStringProperty(date);
        this.startingTime = new SimpleStringProperty(startingTime);
        this.endingTime = new SimpleStringProperty(endingTime);

    }

    /**
     * Getter.
     *
     * @return int, in the form of IntegerProperty.
     */
    public IntegerProperty getId() {
        return id;
    }

    /**
     * Setter.
     *
     * @param id int.
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Getter.
     *
     * @return String in the form of a StringProperty.
     */
    public StringProperty getUsername() {
        return username;
    }

    /**
     * Setter.
     *
     * @param username String, new
     */
    public void setUsername(String username) {
        this.username.set(username);
    }

    /**
     * Getter.
     *
     * @return int, in the form of IntegerProperty.
     */
    public IntegerProperty getRoom() {
        return room;
    }

    /**
     * Setter.
     *
     * @param room int, new
     */
    public void setRoom(int room) {
        this.room.set(room);
    }

    /**
     * Getter.
     *
     * @return String in the form of a StringProperty.
     */
    public StringProperty getDate() {
        return date;
    }

    /**
     * Setter.
     *
     * @param date String, new.
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    /**
     * Getter.
     *
     * @return String in the form of a StringProperty.
     */
    public StringProperty getStartingTime() {
        return startingTime;
    }

    /**
     * Setter.
     *
     * @param startingTime String, new
     */
    public void setStartingTime(String startingTime) {
        this.startingTime.set(startingTime);
    }

    /**
     * Getter.
     *
     * @return String in the form of a StringProperty.
     */
    public StringProperty getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime.set(endingTime);
    }

    /**
     * Method that returns all reservations for a particular room on a particular date.
     *
     * @param roomId        the id of the room
     * @param date          the date to be filtered on
     * @param dateConverter converts date value to String format hh:mm
     * @return List of filtered reservations
     */
    public static List<Reservation> getRoomReservationsOnDate(int roomId, LocalDate date,
                                                              StringConverter<LocalDate> dateConverter) {
        try {
            List<Reservation> list = Reservation.getReservation().stream()
                    .filter(x -> x.getRoom().get() == roomId)
                    .filter(x -> x.getDate().get().equals(dateConverter.toString(date)))
                    .collect(Collectors.toList());
            return list;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
     * Convert the server sent code into an Observable List of Reservation.
     *
     * @return Observable List of Reservations.
     */
    public static ObservableList<Reservation> getReservation() {
        try {
            ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
            JSONArray jsonArrayReservation = new JSONArray(
                    ReservationServerCommunication.getAllReservations());
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
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
     * Convert the server sent code into an Observable List of Reservation for the particular user!!.
     *
     * @return Observable List of Reservations.
     */
    public static ObservableList<Reservation> getUserReservation() {
        try {
            ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
            JSONArray jsonArrayReservation = new JSONArray(
                    ReservationServerCommunication.getUserReservations(CurrentUserManager.getUsername()));
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
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
     * Getter.
     *
     * @return ObservableList containing reservations from the selected user.
     */
    public static ObservableList<Reservation> getSelectedUserReservation() {
        try {
            ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
            JSONArray jsonArrayReservation = new JSONArray(
                    ReservationServerCommunication.getUserReservations(
                            AdminManageUserViewController.currentSelectedUser.getUsername().get()));
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
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

}
