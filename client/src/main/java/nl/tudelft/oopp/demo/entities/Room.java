package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

public class Room {
    private IntegerProperty roomId;
    private StringProperty roomName;
    private IntegerProperty roomBuilding;
    private BooleanProperty roomTeacherOnly;
    private IntegerProperty roomCapacity;
    private StringProperty roomPhoto;
    private StringProperty roomDescription;
    private StringProperty roomType;

    public Room() {
        this.roomId = new SimpleIntegerProperty(-1);
        this.roomName = new SimpleStringProperty(null);
        this.roomBuilding = new SimpleIntegerProperty(-1);
        this.roomTeacherOnly = new SimpleBooleanProperty(false);
        this.roomCapacity = new SimpleIntegerProperty(-1);
        this.roomPhoto = new SimpleStringProperty(null);
        this.roomDescription = new SimpleStringProperty(null);
        this.roomType = new SimpleStringProperty(null);
    }

    /**
     * Constructor with some initial data.
     * Simple string property is used because it provides data binding.
     */
    public Room(int roomId, String roomName,
                int roomBuilding, boolean roomTeacherOnly,
                int roomCapacity, String roomPhoto,
                String roomDescription, String roomType) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomName = new SimpleStringProperty(roomName);
        this.roomBuilding = new SimpleIntegerProperty(roomBuilding);
        this.roomTeacherOnly = new SimpleBooleanProperty(roomTeacherOnly);
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
        return roomTeacherOnly;
    }

    public void setTeacher_only(boolean roomTeacherOnly) {
        this.roomTeacherOnly.set(roomTeacherOnly);
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
    public static ObservableList<Room> getRoomData() {
        try {
            ObservableList<Room> roomData = FXCollections.observableArrayList();
            JSONArray jsonArrayRooms = new JSONArray(RoomServerCommunication.getAllRooms());
            for (int i = 0; i < jsonArrayRooms.length(); i++) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Room getRoomById(int id) {
        try {
            JSONObject jsonObject = new JSONObject(RoomServerCommunication.getRoom(id));
            Room r = new Room();
            r.setRoomId(jsonObject.getInt("id"));
            r.setRoomName(jsonObject.getString("name"));
            r.setRoomBuilding(jsonObject.getInt("building"));
            r.setTeacher_only(jsonObject.getBoolean("teacher_only"));
            r.setRoomCapacity(jsonObject.getInt("capacity"));
            r.setRoomPhoto(jsonObject.getString("photos"));
            r.setRoomDescription(jsonObject.getString("description"));
            r.setRoomType(jsonObject.getString("type"));
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
