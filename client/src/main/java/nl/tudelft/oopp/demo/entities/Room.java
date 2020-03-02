package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private IntegerProperty roomId;
    private StringProperty roomName;
    private IntegerProperty roomBuilding;
    private StringProperty roomType;
    private IntegerProperty roomCapacity;
    private StringProperty roomFacility;

    public Room() {
    }

    /**
     * Constructor with some initial data.
     */
    public Room(int roomId, String roomName, int roomBuilding, String roomType, int roomCapacity, String roomFacility) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomName = new SimpleStringProperty(roomName);
        this.roomBuilding = new SimpleIntegerProperty(roomBuilding);
        this.roomType = new SimpleStringProperty(roomType);
        this.roomCapacity = new SimpleIntegerProperty(roomCapacity);
        this.roomFacility = new SimpleStringProperty(roomFacility);
    }

    public int getRoomId() {
        return roomId.get();
    }

    public void setRoomId(int roomId) { this.roomId.set(roomId); }

    public IntegerProperty roomIdProperty() {
        return roomId;
    }

    public String getRoomName() {
        return roomName.get();
    }

    public void setRoomName(String roomName) {
        this.roomName.set(roomName);
    }

    public StringProperty roomNameProperty() {
        return roomName;
    }

    public int getRoomBuilding() {
        return roomBuilding.get();
    }

    public void setRoomBuilding(int roomBuilding) {
        this.roomBuilding.set(roomBuilding);
    }

    public IntegerProperty roomBuildingProperty() {
        return roomBuilding;
    }

    public String getRoomType() {
        return roomType.get();
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public int getRoomCapacity() {
        return roomCapacity.get();
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity.set(roomCapacity);
    }

    public IntegerProperty roomCapacityProperty() {
        return roomCapacity;
    }

    public String getRoomFacility() {
        return roomFacility.get();
    }

    public void setRoomFacility(String roomFacility) {
        this.roomFacility.set(roomFacility);
    }

    public StringProperty roomFacilityProperty() {
        return roomFacility;
    }

}
