package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Building {

    private IntegerProperty buildingId;
    private StringProperty buildingName;
    private IntegerProperty buildingRoom_count;
    private StringProperty buildingAddress;

    public Building() {
    }

    public Building(int buildingId, String buildingName, int buildingRoom_count, String buildingAddress) {
        this.buildingId = new SimpleIntegerProperty(buildingId);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.buildingRoom_count = new SimpleIntegerProperty(buildingRoom_count);
        this.buildingAddress = new SimpleStringProperty(buildingAddress);
    }

    public int getBuildingId() {
        return buildingId.get();
    }

    public void setBuildingId(int buildingId) { this.buildingId.set(buildingId); }

    public String getBuildingName() {
        return buildingName.get();
    }

    public void setBuildingName(String buildingName) { this.buildingName.set(buildingName); }

    public int getBuildingRoom_count() {
        return buildingRoom_count.get();
    }

    public void setBuildingRoom_count(int buildingRoom_count) { this.buildingRoom_count.set(buildingRoom_count); }

    public String getBuildingAddress() {
        return buildingAddress.get();
    }

    public void setBuildingAddress(String buildingAddress) { this.buildingAddress.set(buildingAddress); }
}
