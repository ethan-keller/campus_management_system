package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
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



    public boolean getTeacher_only() {
        return roomTeacher_only.get();
    }

    public void setTeacher_only(boolean roomTeacher_only) {
        this.roomTeacher_only.set(roomTeacher_only);
    }

    public BooleanProperty roomTeacher_onlyProperty() {
        return roomTeacher_only;
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



    public int getRoomCapacity() {
        return roomCapacity.get();
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity.set(roomCapacity);
    }

    public IntegerProperty roomCapacityProperty() {
        return roomCapacity;
    }


    public String getRoomPhoto() {
        return roomPhoto.get();
    }

    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto.set(roomPhoto);
    }

    public StringProperty roomPhotoProperty() {
        return roomPhoto;
    }



    public String getRoomDescription() {
        return roomDescription.get();
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription.set(roomDescription);
    }

    public StringProperty roomDescriptionProperty() {
        return roomDescription;
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


    /**
     * Convert server response into an ObservableList of rooms.
     */
    public static ObservableList<Room> getRoomData() throws JSONException {
        ObservableList<Room> roomData = FXCollections.observableArrayList();
        JSONArray jsonArrayRooms= new JSONArray(AdminManageServerCommunication.getAllRooms());
        for(int i=0; i<jsonArrayRooms.length(); i++){
            Room r = new Room();
            r.setRoomId(jsonArrayRooms.getJSONObject(i).getInt("id") );
            r.setRoomName(jsonArrayRooms.getJSONObject(i).getString("name") );
            r.setRoomBuilding(jsonArrayRooms.getJSONObject(i).getInt("building") );
            r.setTeacher_only(jsonArrayRooms.getJSONObject(i).getBoolean("teacher_only"));
            r.setRoomCapacity(jsonArrayRooms.getJSONObject(i).getInt("capacity") );
            r.setRoomPhoto(jsonArrayRooms.getJSONObject(i).getString("photos"));
            r.setRoomDescription(jsonArrayRooms.getJSONObject(i).getString("description") );
            r.setRoomType(jsonArrayRooms.getJSONObject(i).getString("type") );
            roomData.add(r);
        }
        return roomData;
    }

}
