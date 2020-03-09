package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Building;

public class BuildingEditDialogController {

    @FXML
    private TextField buildingNameField;
    @FXML
    private TextField buildingAddressField;
    @FXML
    private TextField buildingRoom_countField;

    public static Building building;

    private Stage dialogStage;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        Building building = AdminManageBuildingViewController.currentSelectedBuilding;
        if (building == null) return;
        buildingNameField.setText(building.getBuildingName().get());
        buildingAddressField.setText(building.getBuildingAddress().get());
        buildingRoom_countField.setText(String.valueOf(building.getBuildingRoom_count().get()));
    }

    private static void emptyBuilding() {
        building = new Building();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        if (isInputValid()) {
            emptyBuilding();
            building.setBuildingName(buildingNameField.getText());
            building.setBuildingRoom_count(Integer.parseInt(buildingRoom_countField.getText()));
            building.setBuildingAddress(buildingAddressField.getText());
            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        building = null;
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (buildingNameField.getText().equals("")) {
            errorMessage += "No valid building name!\n";
        }
        if (buildingAddressField.getText().equals("")) {
            errorMessage += "No valid building address!\n";
        }
        if (buildingRoom_countField.getText().equals("")) {
            errorMessage += "No valid capacity!\n";
        } else {
            try {
                Integer.parseInt(buildingRoom_countField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid room count (must be an integer)!\n";
            }
        }
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
        
    }

}
