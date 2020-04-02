package nl.tudelft.oopp.demo.admin.logic;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;

public class RoomEditDialogLogic {

    /**.
     * To validate the input of the user
     * @param roomNameField - Name of room
     * @param roomBuildingComboBox - List of buildings
     * @param radioButtonYes - Yes radio box
     * @param radioButtonNo - No Radio box
     * @param roomCapacityField - Room capacity
     * @param roomTypeField - Room type
     * @param roomDescriptionField - Room description
     * @return - Boolean value to validate input
     */
    public static boolean isValidInput(TextField roomNameField, ComboBox<Building> roomBuildingComboBox,
                                       RadioButton radioButtonYes, RadioButton radioButtonNo,
                                       TextField roomCapacityField, TextField roomTypeField,
                                       TextField roomDescriptionField) {
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
