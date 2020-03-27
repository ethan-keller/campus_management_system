package nl.tudelft.oopp.demo.controllers;

import java.io.File;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.RoomEditDialogView;


/**
 * Class that controls the admin view showing all the rooms.
 * The admin can create, edit and delete rooms
 */
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

    /**
     * Default constructor for JavaFX.
     */
    public AdminManageRoomViewController() {
    }

    /**
     * Fills the TableView with the correct values for every room.
     * It also initializes a Tooltip on the image name cells. When the admin hovers on the name, he gets
     * a preview of the image.
     */
    @FXML
    private void initialize() {

        try {
            // Initialize the room table with the eight columns.
            roomIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(
                    cellData.getValue().getRoomId().get())));
            roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomName());
            roomBuildingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(
                    cellData.getValue().getRoomBuilding().get())));
            roomOnlyTeachersColumn.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getTeacherOnly().get() ? "yes" : "no"));
            roomCapacityBuilding.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(
                    cell.getValue().getRoomCapacity().get())));
            roomPhotoColumn.setCellValueFactory(cell -> cell.getValue().getRoomPhoto());
            roomDescriptionColumn.setCellValueFactory(cell -> cell.getValue().getRoomDescription());
            roomTypeColumn.setCellValueFactory(cell -> cell.getValue().getRoomType());

            // sets a Tooltip such that when the admin hovers over the image name,
            // he can get a preview of the image
            roomPhotoColumn.setCellFactory(tc -> {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : item);
                        if (!empty) {
                            Tooltip tooltip = getPhotoToolTip(this.getItem());
                            this.setTooltip(tooltip);
                        }
                    }
                };
            });

            roomTable.setItems(Room.getRoomData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a Tooltip with the image of the cell's room as its graphic.
     *
     * @param fileName name of image to find in resources
     * @return a Tooltip with the image set
     */
    private Tooltip getPhotoToolTip(String fileName) {
        ImageView image = new ImageView();
        final Tooltip tooltip = new Tooltip();

        // get the image file from resources
        File resourceImage = new File("client/src/main/resources/images/" + fileName);
        String path = resourceImage.getAbsolutePath();
        Image roomPhoto = new Image("file:" + path);
        image.setImage(roomPhoto);
        image.setPreserveRatio(true);
        // set the width to be the same as the current width of the column
        image.setFitWidth(roomPhotoColumn.getWidth());

        // set the image
        tooltip.setGraphic(image);
        return tooltip;
    }

    /**
     * Re-initializes the complete view (to immediately show the admin when a room is created or edited).
     */
    public void refresh() {
        initialize();
    }

    /**
     * Gets the currently selected room in the TableView.
     *
     * @return Room object of the selected room
     */
    public Room getSelectedRoom() {
        if (roomTable.getSelectionModel().getSelectedIndex() >= 0) {
            return roomTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * Gets the index of the selected room in the TableView.
     *
     * @return int index
     */
    public int getSelectedIndex() {
        return roomTable.getSelectionModel().getSelectedIndex();
    }


    /**
     * Deletes a room.
     *
     * @param event event that triggered this method
     */
    @FXML
    private void deleteRoomClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Room selectedRoom = getSelectedRoom();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that room deletion was successful before displaying alert
                RoomServerCommunication.deleteRoom(selectedRoom.getRoomId().getValue());
                refresh();
                Alert alert = GeneralMethods.createAlert("Delete room", "Room deleted succesfully!",
                        stage, AlertType.INFORMATION);
                alert.showAndWait();
            } else {
                Alert alert = GeneralMethods.createAlert("No selection", "Please select a room!",
                        stage, AlertType.WARNING);
                alert.setHeaderText("No room Selected");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("delete room exception");
            e.printStackTrace();
        }
    }

    /**
     * Creates a new room.
     *
     * @param event event that triggered this method
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

            if (RoomServerCommunication.createRoom(tempRoom.getRoomName().get(), tempRoom.getRoomBuilding().get(),
                    tempRoom.getTeacherOnly().get(), tempRoom.getRoomCapacity().get(),
                    tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(),
                    tempRoom.getRoomType().get())) {
                refresh();
                Alert alert = GeneralMethods.createAlert("New room", "Added a new room!",
                        stage, AlertType.INFORMATION);
                alert.showAndWait();
            } else {
                Alert alert = GeneralMethods.createAlert("New room",
                        "An error occurred, please try again!", stage, AlertType.ERROR);
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("room creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Opens dialog box for admin to edit a room.
     *
     * @param event event that triggered this method
     */
    @FXML
    private void editRoomClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Room selectedRoom = getSelectedRoom();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                currentSelectedRoom = selectedRoom;

                RoomEditDialogView view = new RoomEditDialogView();
                view.start(stage);
                Room tempRoom = RoomEditDialogController.room;

                if (tempRoom == null) {
                    return;
                }


                if (RoomServerCommunication.updateRoom(selectedRoom.getRoomId().get(),
                        tempRoom.getRoomName().get(), tempRoom.getRoomBuilding().get(),
                        tempRoom.getTeacherOnly().get(), tempRoom.getRoomCapacity().get(),
                        tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(),
                        tempRoom.getRoomType().get())) {
                    refresh();
                    Alert alert = GeneralMethods.createAlert("Edit room",
                            "The room was edited successfully", stage, AlertType.INFORMATION);
                    alert.showAndWait();
                } else {
                    Alert alert = GeneralMethods.createAlert("Edit room",
                            "An error occurred, please try again!", stage, AlertType.INFORMATION);
                    alert.showAndWait();
                }
            } else {
                Alert alert = GeneralMethods.createAlert("No Selection",
                        "Please select a room in the table.", stage, AlertType.WARNING);
                alert.setHeaderText("No Room Selected");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("room edit exception");
            e.printStackTrace();
        }
    }

    /**
     * Redirects the admin to tha admin home view.
     *
     * @param event event that triggered this method
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            AdminHomePageView adminHomePageView = new AdminHomePageView();
            adminHomePageView.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
