package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.json.JSONArray;
import org.json.JSONException;

public class BuildingEditDialogController {

    @FXML
    private TextField buildingNameField;
    @FXML
    private TextField buildingAddressField;
    @FXML
    private TextField buildingRoom_countField;

    private Stage dialogStage;
    private Building building;
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
     * Sets the builidng to be edited in the dialog.
     *
     * @param building
     */
    public void setBuilding(Building building) {
        this.building = building;
        buildingNameField.setText(building.getBuildingName());
        buildingAddressField.setText(building.getBuildingAddress());
        buildingRoom_countField.setText(Integer.toString(building.getBuildingRoom_count()));
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
            building.setBuildingName(buildingNameField.getText());
            building.setBuildingAddress(buildingAddressField.getAccessibleText());
            building.setBuildingRoom_count(Integer.parseInt(buildingRoom_countField.getText()));

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

        if (buildingNameField.getText() == null || buildingNameField.getText().length() == 0) {
            errorMessage += "No valid building name!\n";
        }
        if (buildingAddressField.getText() == null || buildingAddressField.getText().length() == 0) {
            errorMessage += "No valid building address!\n";
        }
        if (buildingRoom_countField.getText() == null || buildingRoom_countField.getText().length() == 0) {
            errorMessage += "No valid capacity!\n";
        } else {
            try {
                Integer.parseInt(buildingRoom_countField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid room count (must be an integer)!\n";
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
