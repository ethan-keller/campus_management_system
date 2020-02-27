package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.MainApp;
import nl.tudelft.oopp.demo.views.AdminManageRoomView;
import nl.tudelft.oopp.demo.item.Room;
import java.io.IOException;

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

    private AdminManageRoomView adminManageRoomView = new AdminManageRoomView();

    public AdminManageRoomViewController() {
    }

    public void setAdminManageRoomView(AdminManageRoomView adminManageRoomView) {
        this.adminManageRoomView = adminManageRoomView;

        // Add observable list data to the table
        roomTable.setItems(adminManageRoomView.getRoomData());
    }

    /**
     * Show all the rooms in the left side table.
     */
    @FXML
    private void initialize() {
        roomIdColumn.setCellValueFactory(cellData -> cellData.getValue().roomIdProperty());
        roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().roomNameProperty());
        roomBuildingColumn.setCellValueFactory(cellData -> cellData.getValue().roomBuildingProperty());
        showRoomDetails(null);
        roomTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showRoomDetails(newValue));
    }

    /**
     * Show the rooms in the right side details.
     */
    private void showRoomDetails(Room room) {
        if(room != null) {
            roomName.setText(room.getRoomName());
            roomBuilding.setText(room.getRoomBuilding());
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
        int selectedIndex = roomTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
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
            adminManageRoomView.getRoomData().add(tempRoom);
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
                showRoomDetails(selectedRoom);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminManageRoomView.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

}
