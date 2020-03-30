package nl.tudelft.oopp.demo.controllers;

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
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.logic.AdminManageRoomLogic;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.RoomEditDialogView;

import java.io.IOException;


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
        return AdminManageRoomLogic.getSelectedRoom(roomTable);
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
                AdminManageRoomLogic.deleteRoomLogic(selectedRoom);
                refresh();
                // Creates an alert box to display the message.
                GeneralMethods.alertBox("Delete room", "", "Room deleted!", AlertType.INFORMATION);
            } else {
                // Creates an alert box.
                GeneralMethods.alertBox("No Selection", "No Room Selected",
                        "Please select a Room in the table.", Alert.AlertType.WARNING);
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
            AdminManageRoomLogic.createRoomLogic(tempRoom);
            refresh();
            // Creates an alert box to display the message.
            GeneralMethods.alertBox("New room", "", "New Room added!", AlertType.INFORMATION);
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
                AdminManageRoomLogic.editRoomLogic(selectedRoom, tempRoom);
                refresh();
                // Creates an alert box to display the message.
                GeneralMethods.alertBox("Edit room", "", "Room edited!", AlertType.INFORMATION);
            } else {
                // Creates an alert box.
                GeneralMethods.alertBox("No Selection", "No Room Selected",
                        "Please select a Room in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("room edit exception");
            e.printStackTrace();
        }
    }

    /**
     * This button redirects the user back to the login page.
     * @param event is passed.
     * @throws IOException is thrown.
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView adminHomePageView = new AdminHomePageView();
        adminHomePageView.start(stage);
    }

    /**
     * This redirects the admin back to the login page.
     * @param event is passed.
     * @throws IOException is thrown.
     */
    @FXML
    private void signOutClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // This open up a new login page.
        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

}
