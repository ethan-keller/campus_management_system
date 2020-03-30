package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BikeReservation {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    private IntegerProperty bikeReservationId;
    private IntegerProperty bikeReservationBuilding;
    private StringProperty bikeReservationUser;
    private IntegerProperty bikeReservationQuantity;
    private StringProperty bikeReservationDate;
    private StringProperty bikeReservationStartingTime;
    private StringProperty bikeReservationEndingTime;

    /**
     * Contructor to initialize without parameters.
     */
    public BikeReservation() {
        bikeReservationId = new SimpleIntegerProperty(-1);
        bikeReservationBuilding = new SimpleIntegerProperty(-1);
        bikeReservationUser = new SimpleStringProperty(null);
        bikeReservationQuantity = new SimpleIntegerProperty(-1);
        bikeReservationDate = new SimpleStringProperty(null);
        bikeReservationStartingTime = new SimpleStringProperty(null);
        bikeReservationEndingTime = new SimpleStringProperty(null);
    }

    /**
     * Constructor with parameters.
     * @param id New ID
     * @param building New BuildingID
     * @param user New username
     * @param date New date
     * @param startingTime New starting time
     * @param endingTime New ending time
     */
    public BikeReservation(int id, int building, String user, int quantity, String date,
                           String startingTime, String endingTime) {
        bikeReservationId = new SimpleIntegerProperty(id);
        bikeReservationBuilding = new SimpleIntegerProperty(building);
        bikeReservationUser = new SimpleStringProperty(user);
        bikeReservationQuantity = new SimpleIntegerProperty(quantity);
        bikeReservationDate = new SimpleStringProperty(date);
        bikeReservationStartingTime = new SimpleStringProperty(startingTime);
        bikeReservationEndingTime = new SimpleStringProperty(endingTime);
    }

    /**
     * Gets bike reservation id.
     * @return ID
     */
    public IntegerProperty getBikeReservationId() {
        return bikeReservationId;
    }

    /**
     * Sets Bike reservation id.
     * @param bikeReservationId new ID
     */
    public void setBikeReservationId(int bikeReservationId) {
        this.bikeReservationId.set(bikeReservationId);
    }

    /**
     * Gets bike reservation building.
     * @return Building
     */
    public IntegerProperty getBikeReservationBuilding() {
        return bikeReservationBuilding;
    }

    /**
     * Sets bike reservation building.
     * @param bikeReservationBuilding new Building ID
     */
    public void setBikeReservationBuilding(int bikeReservationBuilding) {
        this.bikeReservationBuilding.set(bikeReservationBuilding);
    }

    /**
     * Gets bike reservation user.
     * @return User
     */
    public StringProperty getBikeReservationUser() {
        return bikeReservationUser;
    }

    /**
     * Sets bike reservation user.
     * @param bikeReservationUser New username
     */
    public void setBikeReservationUser(String bikeReservationUser) {
        this.bikeReservationUser.set(bikeReservationUser);
    }

    /**
     * Gets bike reservation quantity.
     * @return number of bikes
     */
    public IntegerProperty getBikeReservationQuantity() {
        return bikeReservationQuantity;
    }

    /**
     * Sets bike reservation quantity.
     * @param bikeReservationQuantity quantity of bikes
     */
    public void setBikeReservationQuantity(int bikeReservationQuantity) {
        this.bikeReservationQuantity.set(bikeReservationQuantity);
    }

    /**
     * Gets bike reservation date.
     * @return Date
     */
    public StringProperty getBikeReservationDate() {
        return bikeReservationDate;
    }

    /**
     * Sets bike reservation date.
     * @param bikeReservationDate New date in String(yyyy-mm-dd)
     */
    public void setBikeReservationDate(String bikeReservationDate) {
        this.bikeReservationDate.set(bikeReservationDate);
    }

    /**
     * Gets bike reservation starting time.
     * @return Starting time
     */
    public StringProperty getBikeReservationStartingTime() {
        return bikeReservationStartingTime;
    }

    /**
     * Sets bike reservation starting time.
     * @param bikeReservationStartingTime New starting time in String(hh:m:ss)
     */
    public void setBikeReservationStartingTime(String bikeReservationStartingTime) {
        this.bikeReservationStartingTime.set(bikeReservationStartingTime);
    }

    /**
     * Gets bike reservation ending time.
     * @return Ending time
     */
    public StringProperty getBikeReservationEndingTime() {
        return bikeReservationEndingTime;
    }

    /**
     * Sets bike reservation ending time.
     * @param bikeReservationEndingTime New ending time in String(hh-mm-ss)
     */
    public void setBikeReservationEndingTime(String bikeReservationEndingTime) {
        this.bikeReservationEndingTime.set(bikeReservationEndingTime);
    }

    /**
     * Gets all Bike reservations from the database.
     * @return List of BikeReservation
     */
    public static ObservableList<BikeReservation> getBikeReservationData() {
        try {
            ObservableList<BikeReservation> bikeReservationData = FXCollections.observableArrayList();
            String temp = BikeReservationCommunication.getAllBikeReservation();
            JSONArray jsonArrayBikeReservations = new JSONArray(temp);
            for (int i = 0; i < jsonArrayBikeReservations.length(); i++) {
                BikeReservation b = new BikeReservation();
                b.setBikeReservationId(jsonArrayBikeReservations.getJSONObject(i).getInt("id"));
                b.setBikeReservationBuilding(jsonArrayBikeReservations.getJSONObject(i).getInt("building"));
                b.setBikeReservationUser(jsonArrayBikeReservations.getJSONObject(i)
                        .getJSONObject("user").getString("username"));
                b.setBikeReservationDate(jsonArrayBikeReservations.getJSONObject(i).getString("date"));
                b.setBikeReservationStartingTime(jsonArrayBikeReservations
                        .getJSONObject(i).getString("startingTime"));
                b.setBikeReservationEndingTime(jsonArrayBikeReservations.getJSONObject(i).getString("endingTime"));
                bikeReservationData.add(b);
            }
            return bikeReservationData;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
     * Gets all Bike reservations made by a user.
     * @param user The Username
     * @return List of BikeReservation
     */
    public static ObservableList<BikeReservation> getUserBikeReservations(String user) {
        try {
            ObservableList<BikeReservation> bikeReservationData = FXCollections.observableArrayList();
            JSONArray jsonArrayBikeReservations = new JSONArray(
                    BikeReservationCommunication.getUserBikeReservations(user)
            );
            for (int i = 0; i < jsonArrayBikeReservations.length(); i++) {
                BikeReservation b = new BikeReservation();
                b.setBikeReservationId(jsonArrayBikeReservations.getJSONObject(i).getInt("id"));
                b.setBikeReservationBuilding(
                        jsonArrayBikeReservations.getJSONObject(i).getInt("building"));
                b.setBikeReservationUser(jsonArrayBikeReservations.getJSONObject(i)
                        .getJSONObject("user").getString("username"));
                b.setBikeReservationDate(
                        jsonArrayBikeReservations.getJSONObject(i).getString("date"));
                b.setBikeReservationStartingTime(jsonArrayBikeReservations
                        .getJSONObject(i).getString("startingTime"));
                b.setBikeReservationEndingTime(jsonArrayBikeReservations
                        .getJSONObject(i).getString("endingTime"));
                bikeReservationData.add(b);
            }
            return bikeReservationData;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
     * Gets bike reservation with a particular ID.
     * @param id BikeReservation ID
     * @return Returns a BikeReservation object
     */
    public static BikeReservation getBikeReservationById(int id) {
        try {
            JSONObject jsonObject = new JSONObject(BikeReservationCommunication.getBikeReservation(id));
            BikeReservation b = new BikeReservation();
            b.setBikeReservationId(jsonObject.getInt("id"));
            b.setBikeReservationBuilding(jsonObject.getInt("building"));
            b.setBikeReservationUser(jsonObject.getJSONObject("user").getString("username"));
            b.setBikeReservationDate(jsonObject.getString("date"));
            b.setBikeReservationStartingTime(jsonObject.getString("startingTime"));
            b.setBikeReservationEndingTime(jsonObject.getString("endingTime"));
            return b;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    /**
     * Gets Bike reservations that are reserved at a particular building.
     * @param id The Building ID
     * @return Returns a list of BikeReservation
     */
    public static ObservableList<BikeReservation> getBikeReservationsByBuilding(int id) {
        try {
            ObservableList<BikeReservation> bikeReservationData = FXCollections.observableArrayList();
            JSONArray jsonArrayBikeReservations = new JSONArray(
                    BikeReservationCommunication.getBuildingBikeReservations(id)
            );
            for (int i = 0; i < jsonArrayBikeReservations.length(); i++) {
                BikeReservation b = new BikeReservation();
                b.setBikeReservationId(jsonArrayBikeReservations
                        .getJSONObject(i).getInt("id"));
                b.setBikeReservationBuilding(jsonArrayBikeReservations
                        .getJSONObject(i).getInt("building"));
                b.setBikeReservationUser(jsonArrayBikeReservations.getJSONObject(i)
                        .getJSONObject("user").getString("username"));
                b.setBikeReservationDate(jsonArrayBikeReservations
                        .getJSONObject(i).getString("date"));
                b.setBikeReservationStartingTime(jsonArrayBikeReservations
                        .getJSONObject(i).getString("startingTime"));
                b.setBikeReservationEndingTime(jsonArrayBikeReservations
                        .getJSONObject(i).getString("endingTime"));
                bikeReservationData.add(b);
            }
            return bikeReservationData;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }
}
