package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import nl.tudelft.oopp.demo.views.AdminManageRoomView;
import nl.tudelft.oopp.demo.entities.Room;
import org.json.JSONArray;
import org.json.JSONException;

public class AdminManageRoomViewController {
    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> roomIdColumn;

    @FXML
    private TableColumn<Room, String> roomNameColumn;

    @FXML
    private TableColumn<Room, String> roomBuildingColumn;

    @FXML
    private Label roomName;

    @FXML
    private Label roomBuilding;

    @FXML
    private Label roomType;

    @FXML
    private Label roomCapacity;

    @FXML
    private Label roomFacility;

    private AdminManageRoomView adminManageRoomView;

    public AdminManageRoomViewController() {
    }

    /**
     * Convert server response into an ObservableList of rooms.
     */
    public ObservableList<Room> getRoomData() throws JSONException {
        ObservableList<Room> roomData = FXCollections.observableArrayList();
        JSONArray jsonArrayRooms= new JSONArray(AdminManageServerCommunication.getAllRooms());
//        List<Room> rooms = new ArrayList<Room>();
        for(int i=0; i<jsonArrayRooms.length(); i++){
            Room r = new Room();
            r.setRoomId(jsonArrayRooms.getJSONObject(i).getInt("id") );
            r.setRoomName(jsonArrayRooms.getJSONObject(i).getString("name") );
            r.setRoomBuilding(jsonArrayRooms.getJSONObject(i).getInt("building") );
            r.setRoomType(jsonArrayRooms.getJSONObject(i).getString("type") );
            r.setRoomCapacity(jsonArrayRooms.getJSONObject(i).getInt("capacity") );
            r.setRoomFacility(jsonArrayRooms.getJSONObject(i).getString("description") );
            roomData.add(r);
        }
        return roomData;
    }

    /**
     * Show all the rooms in the left side table.
     */
    @FXML
    private void initialize() {
        // Initialize the room table with the three columns.
        roomIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().roomIdProperty().getValue().toString()));
        roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().roomNameProperty());
        roomBuildingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().roomBuildingProperty().getValue().toString()));
        // Clear room details.
        showRoomDetails(null);
        // Listen for selection changes and show the room details when changed.
        roomTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showRoomDetails(newValue));
    }

    public void setAdminManageRoomView(AdminManageRoomView adminManageRoomView) throws JSONException {
        this.adminManageRoomView = adminManageRoomView;
        // Add observable list data to the table
        roomTable.setItems(getRoomData());
    }

    /**
     * Show the rooms in the right side details.
     */
    private void showRoomDetails(Room room) {
        if(room != null) {
            roomName.setText(room.getRoomName());
            roomBuilding.setText(""+room.getRoomBuilding());
            roomType.setText(room.getRoomType());
            roomCapacity.setText(""+room.getRoomCapacity());
            roomFacility.setText(room.getRoomFacility());
        } else {
            roomName.setText("");
            roomBuilding.setText("");
            roomType.setText("");
            roomCapacity.setText("");
            roomFacility.setText("");
        }
    }

    /**
     * Delete a room.
     */
    @FXML
    private void deleteClicked() {
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        int selectedIndex = roomTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Delete room");
            alert.setContentText(AdminManageServerCommunication.deleteRoom(selectedRoom.getRoomId()));
            roomTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(adminManageRoomView.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Room Selected");
            alert.setContentText("Please select a room in the table.");
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking the create new button.
     */
    @FXML
    private void createNewRoomClicked() {
        Room tempRoom = new Room();
        boolean okClicked = adminManageRoomView.showRoomEditDialog(tempRoom);
        if(okClicked){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("New room");
            alert.setContentText(AdminManageServerCommunication.createRoom(tempRoom));
            showRoomDetails(tempRoom);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected room.
     */
    @FXML
    private void editRoomClicked() {
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            boolean okClicked = adminManageRoomView.showRoomEditDialog(selectedRoom);
            if (okClicked) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Edit room");
                alert.setContentText(AdminManageServerCommunication.updateRoom(selectedRoom));
                showRoomDetails(selectedRoom);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminManageRoomView.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Room Selected");
            alert.setContentText("Please select a room in the table.");
            alert.showAndWait();
        }
    }

}
