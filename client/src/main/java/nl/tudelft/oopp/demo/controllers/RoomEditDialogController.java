package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.item.Room;

public class RoomEditDialogController {

    ObservableList<String> availableChoices = FXCollections.observableArrayList("EEMCS", "Aula");
    @FXML
    private TextField roomNameField;
    @FXML
    private ComboBox roomBuildingField;
    @FXML
    private ChoiceBox roomTypeField;
    @FXML
    private TextField roomCapacityField;
    @FXML
    private TextField roomFacilityField;

    @FXML
    public Label testLabel;


    private Stage dialogStage;
    private Room room;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        /**
         * Testing if the choiceBox string is accessible using some command
         */
        roomTypeField.setItems(availableChoices);
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
//        roomBuildingField.setText(room.getRoomBuilding());
//        roomTypeField.setText(room.getRoomType());
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
//        if (roomBuildingField.getText() == null || roomBuildingField.getText().length() == 0) {
//            errorMessage += "No valid building name!\n";
//        }
//        if (roomTypeField.getText() == null || roomTypeField.getText().length() == 0) {
//            errorMessage += "No valid room type!\n";
//        }

        if (roomCapacityField.getText() == null || roomCapacityField.getText().length() == 0) {
            errorMessage += "No valid capacity!\n";
        } else {
            // try to parse the postal code into an int.
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


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void okClicked() {
        if (isInputValid()) {
            room.setRoomName(roomNameField.getText());
            room.setRoomBuilding(roomBuildingField.getAccessibleText());
            room.setRoomType(roomTypeField.getAccessibleText());
            room.setRoomCapacity(Integer.parseInt(roomCapacityField.getText()));
            room.setRoomFacility(roomFacilityField.getText());

            testLabel.setText(roomTypeField.getValue().toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(roomTypeField.getValue().toString());
            alert.showAndWait();

            okClicked = true;
            dialogStage.close();
        }
    }

}
