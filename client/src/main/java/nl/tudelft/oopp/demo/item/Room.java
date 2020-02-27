package nl.tudelft.oopp.demo.item;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final StringProperty roomId;
    private final StringProperty roomName;
    private final StringProperty roomBuilding;
    private final StringProperty roomType;
    private final IntegerProperty roomCapacity;
    private final StringProperty roomFacility;

    public Room() {
        this(null, null, null, null, 0,null);
    }

    /**
     * Constructor with some initial data.
     */
    public Room(String roomId, String roomName, String roomBuilding, String roomType, int roomCapacity, String roomFacility) {
        this.roomId = new SimpleStringProperty(roomId);
        this.roomName = new SimpleStringProperty(roomName);
        this.roomBuilding = new SimpleStringProperty(roomBuilding);
        this.roomType = new SimpleStringProperty(roomType);
        this.roomCapacity = new SimpleIntegerProperty(roomCapacity);
        this.roomFacility = new SimpleStringProperty(roomFacility);
    }

    public String getRoomId() {
        return roomId.get();
    }

    public void setRoomId(String roomId) {
        this.roomId.set(roomId);
    }

    public StringProperty roomIdProperty() {
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

    public String getRoomBuilding() {
        return roomBuilding.get();
    }

    public void setRoomBuilding(String roomBuilding) {
        this.roomBuilding.set(roomBuilding);
    }

    public StringProperty roomBuildingProperty() {
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
