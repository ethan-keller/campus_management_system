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
    private IntegerProperty buildingRoomCount;
    private StringProperty buildingAddress;

    public Building() {
        this.buildingId = new SimpleIntegerProperty(-1);
        this.buildingName = new SimpleStringProperty(null);
        this.buildingRoomCount = new SimpleIntegerProperty(-1);
        this.buildingAddress = new SimpleStringProperty(null);
    }


    public Building(int buildingId, String buildingName, int buildingRoomCount, String buildingAddress) {
        this.buildingId = new SimpleIntegerProperty(buildingId);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.buildingRoomCount = new SimpleIntegerProperty(buildingRoomCount);
        this.buildingAddress = new SimpleStringProperty(buildingAddress);
    }

    public IntegerProperty getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) { this.buildingId.set(buildingId); }




    public StringProperty getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) { this.buildingName.set(buildingName); }



    public IntegerProperty getBuildingRoomCount() {
        return buildingRoomCount;
    }

    public void setBuildingRoomCount(int buildingRoomCount) { this.buildingRoomCount.set(buildingRoomCount); }




    public StringProperty getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) { this.buildingAddress.set(buildingAddress); }


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
            b.setBuildingRoomCount(jsonArrayBuildings.getJSONObject(i).getInt("roomCount") );
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
            b.setBuildingRoomCount(jsonObject.getInt("roomCount"));
            return b;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
