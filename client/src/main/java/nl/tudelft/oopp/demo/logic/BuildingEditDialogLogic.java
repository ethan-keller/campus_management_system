package nl.tudelft.oopp.demo.logic;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.GeneralMethods;

public class BuildingEditDialogLogic {

    public static boolean isValidInput(TextField buildingNameField, TextField buildingAddressField,
                                       TextField buildingRoomCountField) {
        String errorMessage = "";

        if (buildingNameField.getText().equals("")) {
            errorMessage += "No valid building name!\n";
        }
        if (buildingAddressField.getText().equals("")) {
            errorMessage += "No valid building address!\n";
        }
        if (buildingRoomCountField.getText().equals("")) {
            errorMessage += "No valid capacity!\n";
        } else {
            try {
                Integer.parseInt(buildingRoomCountField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid room count (must be an integer)!\n";
            }
        }
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            GeneralMethods.alertBox("Invalid Fields", "Please correct invalid fields",
                    errorMessage, Alert.AlertType.ERROR);

            return false;
        }
    }
}
