package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Building {

    private IntegerProperty buildingId;
    private StringProperty buildingName;
    private IntegerProperty buildingRoom_count;
    private StringProperty buildingAddress;
    private IntegerProperty buildingAvailable_bikes;
    private IntegerProperty buildingMax_bikes;

    public Building() {
        this.buildingId = new SimpleIntegerProperty(-1);
        this.buildingName = new SimpleStringProperty(null);
        this.buildingRoom_count = new SimpleIntegerProperty(-1);
        this.buildingAddress = new SimpleStringProperty(null);
        this.buildingAvailable_bikes = new SimpleIntegerProperty(-1);
        this.buildingMax_bikes = new SimpleIntegerProperty(-1);
    }

    public Building(int buildingId, String buildingName, int buildingRoom_count, String buildingAddress, int buildingAvailable_bikes, int buildingMax_bikes) {
        this.buildingId = new SimpleIntegerProperty(buildingId);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.buildingRoom_count = new SimpleIntegerProperty(buildingRoom_count);
        this.buildingAddress = new SimpleStringProperty(buildingAddress);
        this.buildingAvailable_bikes = new SimpleIntegerProperty(buildingAvailable_bikes);
        this.buildingMax_bikes = new SimpleIntegerProperty(buildingMax_bikes);
    }

    public IntegerProperty getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) { this.buildingId.set(buildingId); }




    public StringProperty getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) { this.buildingName.set(buildingName); }



    public IntegerProperty getBuildingRoom_count() {
        return buildingRoom_count;
    }

    public void setBuildingRoom_count(int buildingRoom_count) { this.buildingRoom_count.set(buildingRoom_count); }




    public StringProperty getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) { this.buildingAddress.set(buildingAddress); }


    public IntegerProperty getBuildingAvailable_bikes() {
        return buildingAvailable_bikes;
    }

    public void setBuildingAvailable_bikes(int buildingAvailable_bikes) {
        this.buildingAvailable_bikes.set(buildingAvailable_bikes);
    }



    public IntegerProperty getBuildingMax_bikes() {
        return buildingMax_bikes;
    }

    public void setBuildingMax_bikes(int buildingMax_bikes) {
        this.buildingMax_bikes.set(buildingMax_bikes);
    }


    /**
     * Convert server response into an ObservableList of rooms.
     */
    public static ObservableList<Building> getBuildingData() throws JSONException {
        ObservableList<Building> buildingData = FXCollections.observableArrayList();
        JSONArray jsonArrayBuildings= new JSONArray(BuildingServerCommunication.getAllBuildings());
        for(int i=0; i<jsonArrayBuildings.length(); i++){
            Building b = new Building();
            b.setBuildingId(2);
            b.setBuildingId(jsonArrayBuildings.getJSONObject(i).getInt("id") );
            b.setBuildingName(jsonArrayBuildings.getJSONObject(i).getString("name") );
            b.setBuildingAddress(jsonArrayBuildings.getJSONObject(i).getString("address") );
            b.setBuildingRoom_count(jsonArrayBuildings.getJSONObject(i).getInt("room_count") );
            b.setBuildingAvailable_bikes(jsonArrayBuildings.getJSONObject(i).getInt("availableBikes") );
            b.setBuildingMax_bikes(jsonArrayBuildings.getJSONObject(i).getInt("maxBikes") );
            buildingData.add(b);
        }
        return buildingData;
    }

    public static Building getBuildingById(int id) {
        try {
            JSONObject jsonObject = new JSONObject(BuildingServerCommunication.getBuilding(id));
            Building b = new Building();
            b.setBuildingId(jsonObject.getInt("id"));
            b.setBuildingName(jsonObject.getString("name"));
            b.setBuildingAddress(jsonObject.getString("address"));
            b.setBuildingRoom_count(jsonObject.getInt("room_count"));
            b.setBuildingAvailable_bikes(jsonObject.getInt("availableBikes"));
            b.setBuildingMax_bikes(jsonObject.getInt("maxBikes"));
            return b;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String toString(){
        return getBuildingName().get();
    }



}
