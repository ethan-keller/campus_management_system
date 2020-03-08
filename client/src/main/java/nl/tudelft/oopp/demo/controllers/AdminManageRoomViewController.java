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
import nl.tudelft.oopp.demo.views.RoomEditDialogView;
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
    private TableColumn<Room, String> roomOnlyTeachersColumn;

    @FXML
    private TableColumn<Room, String> roomCapacityBuilding;

    @FXML
    private TableColumn<Room, String> roomPhotoColumn;

    @FXML
    private TableColumn<Room, String> roomDescriptionColumn;

    @FXML
    private TableColumn<Room, String> roomTypeColumn;


    public static Room currentSelectedRoom;

    public AdminManageRoomViewController() {
    }

    /**
     * Show all the rooms in the left side table.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize the room table with the eight columns.
            roomIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRoomId().get())));
            roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomName());
            roomBuildingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRoomBuilding().get())));
            roomOnlyTeachersColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTeacher_only().get() ? "yes" : "no"));
            roomCapacityBuilding.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getRoomCapacity().get())));
            roomPhotoColumn.setCellValueFactory(cell -> cell.getValue().getRoomPhoto());
            roomDescriptionColumn.setCellValueFactory(cell -> cell.getValue().getRoomDescription());
            roomTypeColumn.setCellValueFactory(cell -> cell.getValue().getRoomType());

            roomTable.setItems(Room.getRoomData());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void refresh() {
        initialize();
    }

    public Room getSelectedRoom() {
        if (roomTable.getSelectionModel().getSelectedIndex() >= 0) {
            return roomTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return roomTable.getSelectionModel().getSelectedIndex();
    }


    /**
     * Delete a room.
     */
    @FXML
    private void deleteRoomClicked(ActionEvent event) {
        Room selectedRoom = getSelectedRoom();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that room deletion was succesful before displaying alert
                AdminManageServerCommunication.deleteBuilding(selectedRoom.getRoomId().getValue());
                refresh();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Delete room");
                alert.setContentText("Room deleted!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No room Selected");
                alert.setContentText("Please select a room in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("delete room exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     */
    @FXML
    private void createNewRoomClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentSelectedRoom = null;
            RoomEditDialogView view = new RoomEditDialogView();
            view.start(stage);
            Room tempRoom = RoomEditDialogController.room;
            if (tempRoom == null) return;
            // TODO: Check that room creation was succesful before displaying alert
            AdminManageServerCommunication.createRoom(tempRoom.getRoomName().get(), tempRoom.getRoomBuilding().get(), tempRoom.getTeacher_only().get(), tempRoom.getRoomCapacity().get(), tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(), tempRoom.getRoomType().get());
            refresh();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("New room");
            alert.setContentText("Added new room!");
        } catch (Exception e) {
            System.out.println("room creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected room.
     */
    @FXML
    private void editRoomClicked(ActionEvent event) {
        Room selectedRoom = getSelectedRoom();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedRoom = selectedRoom;

                RoomEditDialogView view = new RoomEditDialogView();
                view.start(stage);
                Room tempRoom = RoomEditDialogController.room;

                if (tempRoom == null) return;
                // TODO: Check that building edit was succesful before displaying alert
                AdminManageServerCommunication.updateRoom(selectedRoom.getRoomId().get(), tempRoom.getRoomName().get(), tempRoom.getRoomBuilding().get(), tempRoom.getTeacher_only().get(), tempRoom.getRoomCapacity().get(), tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(), tempRoom.getRoomType().get());
                refresh();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Edit room");
                alert.setContentText("edited room!");
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Room Selected");
                alert.setContentText("Please select a room in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("room edit exception");
            e.printStackTrace();
        }
    }


    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }

}
