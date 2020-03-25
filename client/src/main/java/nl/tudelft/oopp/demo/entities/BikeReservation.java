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

public class BikeReservation {
    private IntegerProperty bikeReservationId;
    private IntegerProperty bikeReservationBuilding;
    private StringProperty bikeReservationUser;
    private StringProperty bikeReservationDate;
    private StringProperty bikeReservationStartingTime;
    private StringProperty bikeReservationEndingTime;

    public BikeReservation() {
        bikeReservationId = new SimpleIntegerProperty(-1);
        bikeReservationBuilding = new SimpleIntegerProperty(-1);
        bikeReservationUser = new SimpleStringProperty(null);
        bikeReservationDate = new SimpleStringProperty(null);
        bikeReservationStartingTime = new SimpleStringProperty(null);
        bikeReservationEndingTime = new SimpleStringProperty(null);
    }

    public BikeReservation(int id, int building, String user, String date, String startingTime, String endingTime) {
        bikeReservationId = new SimpleIntegerProperty(id);
        bikeReservationBuilding = new SimpleIntegerProperty(building);
        bikeReservationUser = new SimpleStringProperty(user);
        bikeReservationDate = new SimpleStringProperty(date);
        bikeReservationStartingTime = new SimpleStringProperty(startingTime);
        bikeReservationEndingTime = new SimpleStringProperty(endingTime);
    }

    public int getBikeReservationId() {
        return bikeReservationId.get();
    }

    public void setBikeReservationId(int bikeReservationId) {
        this.bikeReservationId.set(bikeReservationId);
    }

    public int getBikeReservationBuilding() {
        return bikeReservationBuilding.get();
    }

    public void setBikeReservationBuilding(int bikeReservationBuilding) {
        this.bikeReservationBuilding.set(bikeReservationBuilding);
    }

    public String getBikeReservationUser() {
        return bikeReservationUser.get();
    }

    public void setBikeReservationUser(String bikeReservationUser) {
        this.bikeReservationUser.set(bikeReservationUser);
    }

    public String getBikeReservationDate() {
        return bikeReservationDate.get();
    }

    public void setBikeReservationDate(String bikeReservationDate) {
        this.bikeReservationDate.set(bikeReservationDate);
    }

    public String getBikeReservationStartingTime() {
        return bikeReservationStartingTime.get();
    }

    public void setBikeReservationStartingTime(String bikeReservationStartingTime) {
        this.bikeReservationStartingTime.set(bikeReservationStartingTime);
    }

    public String getBikeReservationEndingTime() {
        return bikeReservationEndingTime.get();
    }

    public void setBikeReservationEndingTime(String bikeReservationEndingTime) {
        this.bikeReservationEndingTime.set(bikeReservationEndingTime);
    }

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
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<BikeReservation> getUserBikeReservations(String user) {
        try {
            ObservableList<BikeReservation> bikeReservationData = FXCollections.observableArrayList();
            JSONArray jsonArrayBikeReservations = new JSONArray(BikeReservationCommunication.getUserBikeReservations(user));
            for (int i = 0; i < jsonArrayBikeReservations.length(); i++) {
                BikeReservation b = new BikeReservation();
                b.setBikeReservationId(jsonArrayBikeReservations.getJSONObject(i).getInt("id"));
                b.setBikeReservationBuilding(jsonArrayBikeReservations.getJSONObject(i).getInt("building"));
                b.setBikeReservationUser(jsonArrayBikeReservations.getJSONObject(i)
                        .getJSONObject("user").getString("username"));
                b.setBikeReservationDate(jsonArrayBikeReservations.getJSONObject(i).getString("date"));
                b.setBikeReservationStartingTime(jsonArrayBikeReservations.getJSONObject(i).getString("startingTime"));
                b.setBikeReservationEndingTime(jsonArrayBikeReservations.getJSONObject(i).getString("endingTime"));
                bikeReservationData.add(b);
            }
            return bikeReservationData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<BikeReservation> getBikeReservationByUsername(String name) {
        try {
            ObservableList<BikeReservation> bikeReservationData = FXCollections.observableArrayList();
            JSONArray jsonArrayBikeReservations = new JSONArray(BikeReservationCommunication.getUserBikeReservations(name));
            for (int i = 0; i < jsonArrayBikeReservations.length(); i++) {
                BikeReservation b = new BikeReservation();
                b.setBikeReservationId(jsonArrayBikeReservations.getJSONObject(i).getInt("id"));
                b.setBikeReservationBuilding(jsonArrayBikeReservations.getJSONObject(i).getInt("building"));
                b.setBikeReservationUser(jsonArrayBikeReservations.getJSONObject(i)
                        .getJSONObject("user").getString("username"));
                b.setBikeReservationDate(jsonArrayBikeReservations.getJSONObject(i).getString("date"));
                b.setBikeReservationStartingTime(jsonArrayBikeReservations.getJSONObject(i).getString("startingTime"));
                b.setBikeReservationEndingTime(jsonArrayBikeReservations.getJSONObject(i).getString("endingTime"));
                bikeReservationData.add(b);
            }
            return bikeReservationData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<BikeReservation> getBikeReservationsByBuilding(int id) {
        try {
            ObservableList<BikeReservation> bikeReservationData = FXCollections.observableArrayList();
            JSONArray jsonArrayBikeReservations = new JSONArray(BikeReservationCommunication.getBuildingBikeReservations(id));
            for (int i = 0; i < jsonArrayBikeReservations.length(); i++) {
                BikeReservation b = new BikeReservation();
                b.setBikeReservationId(jsonArrayBikeReservations.getJSONObject(i).getInt("id"));
                b.setBikeReservationBuilding(jsonArrayBikeReservations.getJSONObject(i).getInt("building"));
                b.setBikeReservationUser(jsonArrayBikeReservations.getJSONObject(i)
                        .getJSONObject("user").getString("username"));
                b.setBikeReservationDate(jsonArrayBikeReservations.getJSONObject(i).getString("date"));
                b.setBikeReservationStartingTime(jsonArrayBikeReservations.getJSONObject(i).getString("startingTime"));
                b.setBikeReservationEndingTime(jsonArrayBikeReservations.getJSONObject(i).getString("endingTime"));
                bikeReservationData.add(b);
            }
            return bikeReservationData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
