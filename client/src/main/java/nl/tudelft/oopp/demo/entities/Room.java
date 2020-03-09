package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import org.json.JSONArray;
import org.json.JSONException;

public class Room {
    private IntegerProperty roomId;
    private StringProperty roomName;
    private IntegerProperty roomBuilding;
    private BooleanProperty roomTeacher_only;
    private IntegerProperty roomCapacity;
    private StringProperty roomPhoto;
    private StringProperty roomDescription;
    private StringProperty roomType;

    public Room() {
        this.roomId = new SimpleIntegerProperty(-1);
        this.roomName = new SimpleStringProperty(null);
        this.roomBuilding = new SimpleIntegerProperty(-1);
        this.roomTeacher_only = new SimpleBooleanProperty(false);
        this.roomCapacity = new SimpleIntegerProperty(-1);
        this.roomPhoto = new SimpleStringProperty(null);
        this.roomDescription = new SimpleStringProperty(null);
        this.roomType = new SimpleStringProperty(null);
    }

    /**
     * Constructor with some initial data.
     */
    public Room(int roomId, String roomName, int roomBuilding, boolean roomTeacher_only, int roomCapacity, String roomPhoto, String roomDescription, String roomType) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomName = new SimpleStringProperty(roomName);
        this.roomBuilding = new SimpleIntegerProperty(roomBuilding);
        this.roomTeacher_only = new SimpleBooleanProperty(roomTeacher_only);
        this.roomCapacity = new SimpleIntegerProperty(roomCapacity);
        this.roomPhoto = new SimpleStringProperty(roomPhoto);
        this.roomDescription = new SimpleStringProperty(roomDescription);
        this.roomType = new SimpleStringProperty(roomType);
    }

    public IntegerProperty getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId.set(roomId);
    }


    public StringProperty getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName.set(roomName);
    }


    public BooleanProperty getTeacher_only() {
        return roomTeacher_only;
    }

    public void setTeacher_only(boolean roomTeacher_only) {
        this.roomTeacher_only.set(roomTeacher_only);
    }


    public IntegerProperty getRoomBuilding() {
        return roomBuilding;
    }

    public void setRoomBuilding(int roomBuilding) {
        this.roomBuilding.set(roomBuilding);
    }


    public IntegerProperty getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity.set(roomCapacity);
    }


    public StringProperty getRoomPhoto() {
        return roomPhoto;
    }

    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto.set(roomPhoto);
    }


    public StringProperty getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription.set(roomDescription);
    }


    public StringProperty getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }


    /**
     * Convert server response into an ObservableList of rooms.
     */
    public static ObservableList<Room> getRoomData() throws JSONException {
        ObservableList<Room> roomData = FXCollections.observableArrayList();
        JSONArray jsonArrayRooms= new JSONArray(RoomServerCommunication.getAllRooms());
        for(int i=0; i<jsonArrayRooms.length(); i++){
            Room r = new Room();
            r.setRoomId(jsonArrayRooms.getJSONObject(i).getInt("id"));
            r.setRoomName(jsonArrayRooms.getJSONObject(i).getString("name"));
            r.setRoomBuilding(jsonArrayRooms.getJSONObject(i).getInt("building"));
            r.setTeacher_only(jsonArrayRooms.getJSONObject(i).getBoolean("teacher_only"));
            r.setRoomCapacity(jsonArrayRooms.getJSONObject(i).getInt("capacity"));
            r.setRoomPhoto(jsonArrayRooms.getJSONObject(i).getString("photos"));
            r.setRoomDescription(jsonArrayRooms.getJSONObject(i).getString("description"));
            r.setRoomType(jsonArrayRooms.getJSONObject(i).getString("type"));
            roomData.add(r);
        }
        return roomData;
    }

}
