package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final IntegerProperty roomId;
    private final StringProperty roomName;
    private final StringProperty roomBuilding;
    private final StringProperty roomType;
    private final IntegerProperty roomCapacity;
    private final StringProperty roomFacility;

    public Room() {
        this(000000, null, null);
    }

    /**
     * Constructor with some initial data.
     */
    public Room(int roomId, String roomName, String roomBuilding) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomName = new SimpleStringProperty(roomName);
        this.roomBuilding = new SimpleStringProperty(roomBuilding);
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

//    public static Room readRoom(String roomInfo) {
//        ObjectMapper mapper = new ObjectMaper();
//        String jsonInString = roomInfo;
//        Room room = null;
//        try {
//            System.out.println(roomInfo);
//            room = mapper.readValue(jsonInString, Room.class);
//        } catch (com.fasterxml.jackson.core.JsonProcessingException e){
//            e.printStackTrace();
//        } catch (JsonParseException e) {
//
//        }
//    }
//
//    public static Room[] readRooms(String[] rooms) {
//
//    }
//
//    public String toString() {
//        return this.roomName;
//    }
}
