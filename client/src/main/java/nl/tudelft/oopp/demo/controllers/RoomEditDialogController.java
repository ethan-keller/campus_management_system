package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.json.JSONArray;
import org.json.JSONException;

public class RoomEditDialogController {

    ObservableList<Building> availableBuildings = FXCollections.observableArrayList(Building.getBuildingData());
    ObservableList<String> teacher_only = FXCollections.observableArrayList("Yes", "No");

    @FXML
    private TextField roomNameField;
    @FXML
    private ChoiceBox<Building> roomBuildingField;
    @FXML
    private ChoiceBox roomTeacher_onlyField;
    @FXML
    private TextField roomCapacityField;
    @FXML
    private TextField roomTypeField;
    @FXML
    private TextField roomDescriptionField;
    @FXML
    private TextField roomPhotoField;

    private Stage dialogStage;
    private Room room;
    private boolean okClicked = false;

    public RoomEditDialogController() throws JSONException {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        roomBuildingField.setItems(availableBuildings);
        roomTeacher_onlyField.setItems(teacher_only);
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the room to be edited in the dialog.
     *
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
        roomNameField.setText(room.getRoomName());
        roomBuildingField.setItems(availableBuildings);
        roomTeacher_onlyField.setItems(teacher_only);
        roomCapacityField.setText(Integer.toString(room.getRoomCapacity()));
        roomTypeField.setText(room.getRoomType());
        roomDescriptionField.setText(room.getRoomDescription());
        roomPhotoField.setText(room.getRoomPhoto());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkClicked() {
        if (isInputValid()) {
            room.setRoomName(roomNameField.getText());
            room.setRoomBuilding(roomBuildingField.getValue().getBuildingId().get());
            room.setTeacher_only(roomTeacher_onlyField.getAccessibleText().equals("Yes"));
            room.setRoomCapacity(Integer.parseInt(roomCapacityField.getText()));
            room.setRoomType(roomTypeField.getText());
            room.setRoomDescription(roomDescriptionField.getText());
            room.setRoomPhoto(roomPhotoField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelClicked() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (roomNameField.getText() == null || roomNameField.getText().length() == 0) {
            errorMessage += "No valid room name!\n";
        }
        if (roomBuildingField.getItems() == null) {
            errorMessage += "You must choose a building!\n";
        }
        if (roomTeacher_onlyField.getItems() == null) {
            errorMessage += "You must choose if teacher only!\n";
        }
        if (roomTypeField.getText() == null) {
            errorMessage += "You must choose a type!\n";
        }
        if (roomCapacityField.getText() == null || roomCapacityField.getText().length() == 0) {
            errorMessage += "No valid capacity!\n";
        } else {
            try {
                Integer.parseInt(roomCapacityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid capacity (must be an integer)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
