package nl.tudelft.oopp.demo.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Food;

import java.util.stream.Collectors;

public class FoodBuildingEditDialogController {

    @FXML
    private ComboBox<Building> foodBuildingComboBox;

    private ObservableList<Building> olb;

    public static Building building;

    private Stage dialogStage;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize and add listener to the building combobox
        olb = Building.getBuildingData();
        foodBuildingComboBox.setItems(olb);
        this.setFoodBuildingComboBoxConverter(olb);
    }

    /**
     * Set the building combobox converter
     * @param olb
     */
    public void setFoodBuildingComboBoxConverter(ObservableList<Building> olb){
        StringConverter<Building> converter = new StringConverter<Building>() {
            @Override
            public String toString(Building object) {
                if (object == null) return "";
                else return object.getBuildingName().get();
            }

            @Override
            public Building fromString(String id) {
                return olb.stream().filter(x -> String.valueOf(x.getBuildingId()) == id).collect(Collectors.toList()).get(0);
            }
        };
        foodBuildingComboBox.setConverter(converter);
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
            building.setBuildingId(this.foodBuildingComboBox.getSelectionModel().getSelectedItem().getBuildingId().get());
            building.setBuildingName(this.foodBuildingComboBox.getSelectionModel().getSelectedItem().getBuildingName().get());
            building.setBuildingRoom_count(this.foodBuildingComboBox.getSelectionModel().getSelectedItem().getBuildingRoom_count().get());
            building.setBuildingAddress(this.foodBuildingComboBox.getSelectionModel().getSelectedItem().getBuildingAddress().get());
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
     * Validates the user input.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (foodBuildingComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid building selected!\n";
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
