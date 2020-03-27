package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.RoomEditDialogView;


public class AdminManageRoomViewController {

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, Number> roomIdColumn;

    @FXML
    private TableColumn<Room, String> roomNameColumn;

    @FXML
    private TableColumn<Room, Number> roomBuildingColumn;

    @FXML
    private TableColumn<Room, String> roomOnlyTeachersColumn;

    @FXML
    private TableColumn<Room, Number> roomCapacityBuilding;

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
            roomIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getRoomId().get()));
            roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomName());
            roomBuildingColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    cellData.getValue().getRoomBuilding().get()));
            roomOnlyTeachersColumn.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getTeacherOnly().get() ? "yes" : "no"));
            roomCapacityBuilding.setCellValueFactory(cell -> new SimpleIntegerProperty(
                    cell.getValue().getRoomCapacity().get()));
            roomPhotoColumn.setCellValueFactory(cell -> cell.getValue().getRoomPhoto());
            roomDescriptionColumn.setCellValueFactory(cell -> cell.getValue().getRoomDescription());
            roomTypeColumn.setCellValueFactory(cell -> cell.getValue().getRoomType());

            roomTable.setItems(Room.getRoomData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Refresh the page to load more rooms into the table view.
     */
    public void refresh() {
        initialize();
    }

    /**
     * The room from the table view is selected.
     *
     * @return Selected room
     */
    public Room getSelectedRoom() {
        if (roomTable.getSelectionModel().getSelectedIndex() >= 0) {
            return roomTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * The index of the room is selected.
     *
     * @return the index of the room
     */
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
                // TODO: Check that room deletion was successful before displaying alert
                RoomServerCommunication.deleteRoom(selectedRoom.getRoomId().getValue());
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
     * .
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
            if (tempRoom == null) {
                return;
            }
            // TODO: Check that room creation was successful before displaying alert
            RoomServerCommunication.createRoom(tempRoom.getRoomName().get(), tempRoom.getRoomBuilding().get(),
                    tempRoom.getTeacherOnly().get(), tempRoom.getRoomCapacity().get(),
                    tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(),
                    tempRoom.getRoomType().get());
            refresh();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("New room");
            alert.setContentText("Added new room!");
            alert.showAndWait();
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

                if (tempRoom == null) {
                    return;
                }
                // TODO: Check that building edit was successful before displaying alert
                RoomServerCommunication.updateRoom(selectedRoom.getRoomId().get(), tempRoom.getRoomName().get(),
                        tempRoom.getRoomBuilding().get(), tempRoom.getTeacherOnly().get(),
                        tempRoom.getRoomCapacity().get(),
                        tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(),
                        tempRoom.getRoomType().get());
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

    /**
     * This takes the user back to the admin home page.
     *
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView adminHomePageView = new AdminHomePageView();
        adminHomePageView.start(stage);
    }

}
