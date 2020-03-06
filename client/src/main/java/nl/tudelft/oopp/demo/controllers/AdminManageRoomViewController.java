package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.AdminManageRoomView;
import nl.tudelft.oopp.demo.entities.Room;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
        roomTable.setItems(Room.getRoomData());
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
            roomFacility.setText(room.getRoomDescription());
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
    private void deleteClicked() throws UnsupportedEncodingException {
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        int selectedIndex = roomTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            AdminManageServerCommunication.deleteRoom(selectedRoom.getRoomId());
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Delete room");
//            alert.setContentText(AdminManageServerCommunication.deleteRoom(selectedRoom.getRoomId()));
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
    private void createNewRoomClicked() throws UnsupportedEncodingException {
        Room tempRoom = new Room();
        boolean okClicked = adminManageRoomView.showRoomEditDialog(tempRoom);
        if(okClicked){
            AdminManageServerCommunication.createRoom(tempRoom.getRoomName(), tempRoom.getRoomBuilding(), tempRoom.getTeacher_only(),
                    tempRoom.getRoomCapacity(), tempRoom.getRoomPhoto(), tempRoom.getRoomDescription(), tempRoom.getRoomType());
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("New room");
//            alert.setContentText(AdminManageServerCommunication.createRoom(tempRoom));
            showRoomDetails(tempRoom);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected room.
     */
    @FXML
    private void editRoomClicked() throws UnsupportedEncodingException {
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            boolean okClicked = adminManageRoomView.showRoomEditDialog(selectedRoom);
            if (okClicked) {
                AdminManageServerCommunication.updateRoom(selectedRoom.getRoomId(), selectedRoom.getRoomName(), selectedRoom.getRoomBuilding(),
                        selectedRoom.getTeacher_only(), selectedRoom.getRoomCapacity(), selectedRoom.getRoomPhoto(), selectedRoom.getRoomDescription(), selectedRoom.getRoomType());
//                Alert alert = new Alert(AlertType.INFORMATION);
//                alert.setTitle("Edit room");
//                alert.setContentText(AdminManageServerCommunication.updateRoom(selectedRoom));
//                showRoomDetails(selectedRoom);
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

    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }
}
