package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.item.Room;

public class RoomEditDialogController {
    @FXML
    private TextField roomNameField;
    @FXML
    private TextField roomBuildingField;
    @FXML
    private TextField roomTypeField;
    @FXML
    private TextField roomCapacityField;
    @FXML
    private TextField roomFacilityField;


    private Stage dialogStage;
    private Room room;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
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
     * Sets the person to be edited in the dialog.
     *
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;

        roomNameField.setText(room.getRoomName());
        roomBuildingField.setText(room.getRoomBuilding());
        roomTypeField.setText(room.getRoomType());
        roomCapacityField.setText(Integer.toString(room.getRoomCapacity()));
        roomFacilityField.setText(room.getRoomFacility());
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
    private void okClicked() {
        if (isInputValid()) {
            room.setRoomName(roomNameField.getText());
            room.setRoomBuilding(roomBuildingField.getText());
            room.setRoomType(roomTypeField.getText());
            room.setRoomCapacity(Integer.parseInt(roomCapacityField.getText()));
            room.setRoomFacility(roomFacilityField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void cancelClicked() {
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
        if (roomBuildingField.getText() == null || roomBuildingField.getText().length() == 0) {
            errorMessage += "No valid building name!\n";
        }
        if (roomTypeField.getText() == null || roomTypeField.getText().length() == 0) {
            errorMessage += "No valid room type!\n";
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
