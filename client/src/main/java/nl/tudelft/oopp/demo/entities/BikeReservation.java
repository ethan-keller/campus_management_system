package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.controllers.AdminManageUserViewController;
import org.json.JSONArray;


public class BikeReservation {
    private IntegerProperty bikeId;
    private StringProperty bikeUsername;
    private IntegerProperty bikeBuilding;
    private IntegerProperty bikeQuantity;
    private StringProperty bikeDate;
    private StringProperty bikeStartingTime;
    private StringProperty bikeEndingTime;

    public BikeReservation() {
        this.bikeId = new SimpleIntegerProperty(-1);
        this.bikeUsername = new SimpleStringProperty(null);
        this.bikeBuilding = new SimpleIntegerProperty(-1);
        this.bikeQuantity = new SimpleIntegerProperty(-1);
        this.bikeDate = new SimpleStringProperty(null);
        this.bikeStartingTime = new SimpleStringProperty(null);
        this.bikeEndingTime = new SimpleStringProperty(null);
    }

    /**
     * Constructor with some initial data.
     * Simple string property is used because it provides data binding.
     */
    public BikeReservation(int bikeId, String bikeUsername, int bikeBuilding, int bikeQuantity, String bikeDate, String bikeStartingTime, String bikeEndingTime) {
        this.bikeId = new SimpleIntegerProperty(bikeId);
        this.bikeUsername = new SimpleStringProperty(bikeUsername);
        this.bikeBuilding = new SimpleIntegerProperty(bikeBuilding);
        this.bikeQuantity = new SimpleIntegerProperty(bikeQuantity);
        this.bikeDate = new SimpleStringProperty(bikeDate);
        this.bikeStartingTime = new SimpleStringProperty(bikeStartingTime);
        this.bikeEndingTime = new SimpleStringProperty(bikeEndingTime);

    }

    public IntegerProperty getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId.set(bikeId);
    }

    public StringProperty getBikeUsername() { return bikeUsername; }

    public void setBikeUsername(String bikeUsername) { this.bikeUsername.set(bikeUsername);}


    public IntegerProperty getBikeBuilding() {
        return bikeBuilding;
    }

    public void setBikeBuilding(int bikeBuilding) {
        this.bikeBuilding.set(bikeBuilding);
    }

    public IntegerProperty getBikeQuantity() {
        return bikeQuantity;
    }

    public void setBikeQuantity(int bikeQuantity) {
        this.bikeQuantity.set(bikeQuantity);
    }


    public StringProperty getBikeDate() {
        return bikeDate;
    }


    public void setBikeDate(String bikeDate) {
        this.bikeDate.set(bikeDate);
    }


    public StringProperty getBikeStartingTime() {
        return bikeStartingTime;
    }

    public void setBikeStartingTime(String bikeStartingTime) {
        this.bikeStartingTime.set(bikeStartingTime);
    }


    public StringProperty getBikeEndingTime() {
        return bikeEndingTime;
    }

    public void setBikeEndingTime(String bikeEndingTime) {
        this.bikeEndingTime.set(bikeEndingTime);
    }

    /**
     * Convert the server sent code into an Observable List of bike reservation.
     *
     * @return Observable List of Reservations.
     */
    public static ObservableList<BikeReservation> getAllBikeReservations() {
        try {
            ObservableList<BikeReservation> bikeReservationList = FXCollections.observableArrayList();
            JSONArray jsonArrayBikeReservation = new JSONArray(BikeReservationCommunication.getAllBikeReservation());
            for (int i = 0; i < jsonArrayBikeReservation.length(); i++) {
                BikeReservation br = new BikeReservation();
                br.setBikeId(jsonArrayBikeReservation.getJSONObject(i).getInt("id"));
                br.setBikeUsername(jsonArrayBikeReservation.getJSONObject(i).getString("username"));
                br.setBikeBuilding(jsonArrayBikeReservation.getJSONObject(i).getInt("buildingId"));
                br.setBikeQuantity(jsonArrayBikeReservation.getJSONObject(i).getInt("quantity"));
                br.setBikeDate(jsonArrayBikeReservation.getJSONObject(i).getString("date"));
                br.setBikeStartingTime(jsonArrayBikeReservation.getJSONObject(i).getString("startingTime"));
                br.setBikeEndingTime(jsonArrayBikeReservation.getJSONObject(i).getString("endingTime"));
                bikeReservationList.add(br);
            }
            return bikeReservationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert the server sent code into an Observable List of bike reservations of the selected user.
     *
     * @return Observable List of Reservations.
     */
    public static ObservableList<BikeReservation> getUserBikeReservations() {
        try {
            ObservableList<BikeReservation> bikeReservationList = FXCollections.observableArrayList();
            JSONArray jsonArrayBikeReservation = new JSONArray(BikeReservationCommunication.getUserBikeReservations(AdminManageUserViewController.currentSelectedUser.getUsername().get()));
            for (int i = 0; i < jsonArrayBikeReservation.length(); i++) {
                BikeReservation br = new BikeReservation();
                br.setBikeId(jsonArrayBikeReservation.getJSONObject(i).getInt("id"));
                br.setBikeUsername(jsonArrayBikeReservation.getJSONObject(i).getString("username"));
                br.setBikeBuilding(jsonArrayBikeReservation.getJSONObject(i).getInt("buildingId"));
                br.setBikeQuantity(jsonArrayBikeReservation.getJSONObject(i).getInt("quantity"));
                br.setBikeDate(jsonArrayBikeReservation.getJSONObject(i).getString("date"));
                br.setBikeStartingTime(jsonArrayBikeReservation.getJSONObject(i).getString("startingTime"));
                br.setBikeEndingTime(jsonArrayBikeReservation.getJSONObject(i).getString("endingTime"));
                bikeReservationList.add(br);
            }
            return bikeReservationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
