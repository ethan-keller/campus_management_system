package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

public class Building {

    private IntegerProperty buildingId;
    private StringProperty buildingName;
    private IntegerProperty buildingRoom_count;
    private StringProperty buildingAddress;

    public Building() {
        this.buildingId = new SimpleIntegerProperty(-1);
        this.buildingName = new SimpleStringProperty(null);
        this.buildingRoom_count = new SimpleIntegerProperty(-1);
        this.buildingAddress = new SimpleStringProperty(null);
    }


    public Building(int buildingId, String buildingName, int buildingRoom_count, String buildingAddress) {
        this.buildingId = new SimpleIntegerProperty(buildingId);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.buildingRoom_count = new SimpleIntegerProperty(buildingRoom_count);
        this.buildingAddress = new SimpleStringProperty(buildingAddress);
    }

    public IntegerProperty getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId.set(buildingId);
    }


    public StringProperty getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName.set(buildingName);
    }


    public IntegerProperty getBuildingRoom_count() {
        return buildingRoom_count;
    }

    public void setBuildingRoom_count(int buildingRoom_count) {
        this.buildingRoom_count.set(buildingRoom_count);
    }


    public StringProperty getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress.set(buildingAddress);
    }


    /**
     * Convert server response into an ObservableList of rooms.
     */
    public static ObservableList<Building> getBuildingData() {
        try {
            ObservableList<Building> buildingData = FXCollections.observableArrayList();
            JSONArray jsonArrayBuildings = new JSONArray(BuildingServerCommunication.getAllBuildings());
            for (int i = 0; i < jsonArrayBuildings.length(); i++) {
                Building b = new Building();
                b.setBuildingId(jsonArrayBuildings.getJSONObject(i).getInt("id"));
                b.setBuildingName(jsonArrayBuildings.getJSONObject(i).getString("name"));
                b.setBuildingAddress(jsonArrayBuildings.getJSONObject(i).getString("address"));
                b.setBuildingRoom_count(jsonArrayBuildings.getJSONObject(i).getInt("room_count"));
                buildingData.add(b);
            }
            return buildingData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Building getBuildingById(int id) {
        try {
            JSONObject jsonObject = new JSONObject(BuildingServerCommunication.getBuilding(id));
            Building b = new Building();
            b.setBuildingId(jsonObject.getInt("id"));
            b.setBuildingName(jsonObject.getString("name"));
            b.setBuildingAddress(jsonObject.getString("address"));
            b.setBuildingRoom_count(jsonObject.getInt("room_count"));
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
