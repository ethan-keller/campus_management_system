package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final IntegerProperty roomId;
    private final StringProperty roomName;
    private final IntegerProperty roomBuilding;
    private final StringProperty roomType;
    private final IntegerProperty roomCapacity;
    private final StringProperty roomFacility;

    public Room() {
        this(000000, null, 000000);
    }

    /**
     * Constructor with some initial data.
     */
    public Room(int roomId, String roomName, int roomBuilding) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomName = new SimpleStringProperty(roomName);
        this.roomBuilding = new SimpleIntegerProperty(roomBuilding);
        this.roomType = new SimpleStringProperty("roomType");
        this.roomCapacity = new SimpleIntegerProperty(0);
        this.roomFacility = new SimpleStringProperty("roomFacility");
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
