package nl.tudelft.oopp.demo.admin.logic;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
<<<<<<< HEAD
=======
import javafx.scene.text.Text;
>>>>>>> develop

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
<<<<<<< HEAD
    public static String isValidInput(String roomNameField, Building roomBuildingComboBox,
                                      boolean radioButtonYes, boolean radioButtonNo,
                                      String roomCapacityField, String roomTypeField,
                                      String roomDescriptionField) {
=======
    public static boolean isValidInput(TextField roomNameField, ComboBox<Building> roomBuildingComboBox,
                                       RadioButton radioButtonYes, RadioButton radioButtonNo,
                                       TextField roomCapacityField, TextField roomTypeField,
                                       TextField roomDescriptionField, String oldFileName,
                                       boolean changedImage, Text fileName) {
>>>>>>> develop
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
<<<<<<< HEAD
        if (roomCapacityField.equals("")) {
=======
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
>>>>>>> develop
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
