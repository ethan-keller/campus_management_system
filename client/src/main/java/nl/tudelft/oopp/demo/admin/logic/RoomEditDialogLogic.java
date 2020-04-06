package nl.tudelft.oopp.demo.admin.logic;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
    public static String isValidInput(String roomNameField, Building roomBuildingComboBox,
                                      boolean radioButtonYes, boolean radioButtonNo,
                                      String roomCapacityField, String roomTypeField,
                                      String roomDescriptionField) {
        String errorMessage = "";

        if (roomNameField.equals("")) {
            errorMessage += "No valid room name!\n";
        }
        //if (roomBuildingComboBox.getSelectionModel().) {
        //errorMessage += "No valid building selected!\n";
        //}
        if (!radioButtonYes && !radioButtonNo) {
            errorMessage += "No teacher only button selected!\n";
        }
        if (roomCapacityField.equals("")) {
            errorMessage += "No valid capacity!\n";
        }
        if (roomTypeField.equals("")) {
            errorMessage += "No valid room type!\n";
        }
        if (roomDescriptionField.equals("")) {
            errorMessage += "No valid room description!\n";
        }

        return errorMessage;
    }
}
