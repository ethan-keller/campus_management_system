package nl.tudelft.oopp.demo.admin.logic;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.general.GeneralMethods;

public class RoomEditDialogLogic {

    /**
     * .
     * To validate the input of the user
     *
     * @param roomNameField        - Name of room
     * @param roomBuildingComboBox - List of buildings
     * @param radioButtonYes       - Yes radio box
     * @param radioButtonNo        - No Radio box
     * @param roomCapacityField    - Room capacity
     * @param roomTypeField        - Room type
     * @param roomDescriptionField - Room description
     * @return - Boolean value to validate input
     */
    public static boolean isValidInput(TextField roomNameField, ComboBox<Building> roomBuildingComboBox,
                                       RadioButton radioButtonYes, RadioButton radioButtonNo,
                                       TextField roomCapacityField, TextField roomTypeField,
                                       TextField roomDescriptionField, String oldFileName,
                                       boolean changedImage, Text fileName) {
        String errorMessage = "";

        if (roomNameField.getText().equals("")) {
            errorMessage += "No valid room name!\n";
        }
        if (roomBuildingComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid building selected!\n";
        }
        if (!radioButtonYes.isSelected() && !radioButtonNo.isSelected()) {
            errorMessage += "No teacher only button selected!\n";
        }
        if (roomDescriptionField.getText().length() >= 270) {
            errorMessage += "The description of the room can't be more than 270 characters";
        }
        // checks if there already exists an image with this name
        File imageFolder = new File("client/src/main/resources/images");
        // if admin creates new room or updates image, check if the image already exists
        if (oldFileName == null || changedImage) {
            for (File f : imageFolder.getAbsoluteFile().listFiles()) {
                if (f.getName().equals(fileName.getText())) {
                    errorMessage += "This file name is already used, please choose another one!\n";
                    break;
                }
            }
        }

        if (roomCapacityField.getText().equals("")) {
            errorMessage += "No valid capacity!\n";
        } else {
            try {
                Integer.parseInt(roomCapacityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid room count (must be an integer)!\n";
            }
        }
        if (roomTypeField.getText().equals("")) {
            errorMessage += "No valid room type!\n";
        }
        if (roomDescriptionField.getText().equals("")) {
            errorMessage += "No valid room description!\n";
        }

        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            GeneralMethods.alertBox("Invalid Fields", "Please correct the invalid fields",
                    errorMessage, Alert.AlertType.ERROR);

            return false;
        }
    }
}
