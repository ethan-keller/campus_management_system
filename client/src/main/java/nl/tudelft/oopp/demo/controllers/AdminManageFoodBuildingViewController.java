package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.FoodServerCommunication;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.AdminManageFoodView;
import nl.tudelft.oopp.demo.views.FoodBuildingEditDialogView;



public class AdminManageFoodBuildingViewController {

    @FXML
    private Label foodNameLabel;

    @FXML
    private TableView<Building> foodBuildingTable;

    @FXML
    private TableColumn<Building, String> foodBuildingIdColumn;

    @FXML
    private TableColumn<Building, String> foodBuildingNameColumn;

    public static Building currentSelectedFoodBuilding;

    public AdminManageFoodBuildingViewController() {
    }

    /**
     * Show all the buildings offers this type of food in the table.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize the title of the table
            foodNameLabel.setText(AdminManageFoodViewController.currentSelectedFood.getFoodName().get());
            // Initialize the room table with the four columns.
            foodBuildingIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(
                    String.valueOf(cell.getValue().getBuildingId().get())));
            foodBuildingNameColumn.setCellValueFactory(cell -> cell.getValue().getBuildingName());
            // Add observable list data to the table
            foodBuildingTable.setItems(Building.getBuildingByFoodId(
                    AdminManageFoodViewController.currentSelectedFood.getFoodId().get()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        initialize();
    }

    /**
     * Called when a building is selected.
     * @return Building
     */
    public Building getSelectedBuilding() {
        if (foodBuildingTable.getSelectionModel().getSelectedIndex() >= 0) {
            return foodBuildingTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return foodBuildingTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Delete a building.
     */
    @FXML
    private void deleteFoodBuildingClicked(ActionEvent event) {
        //Get the selected building
        Building selectedBuilding = getSelectedBuilding();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                // TODO: Check that building deletion was successful before displaying alert
                FoodServerCommunication.deleteFoodFromBuilding(
                        AdminManageFoodViewController.currentSelectedFood.getFoodId().get(),
                        selectedBuilding.getBuildingId().getValue());
                refresh();
                // An alert pop up when a building deleted successfully
                GeneralMethods.alertBox("Delete building", "",
                        "Building deleted!", Alert.AlertType.INFORMATION);
            } else {
                // An alert pop up when no building selected
                GeneralMethods.alertBox("No Selection", "No Building Selected",
                        "Please select a building in the table.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("delete user exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the add button.
     * A new dialog pop up to select
     */
    @FXML
    private void addFoodBuildingClicked(ActionEvent event) {
        try {
            // Building edit dialog pop up.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentSelectedFoodBuilding = null;
            // Get the building from the pop up dialog.
            FoodBuildingEditDialogView view = new FoodBuildingEditDialogView();
            view.start(stage);
            Building tempBuilding = FoodBuildingEditDialogController.building;
            if (tempBuilding == null) {
                return;
            } else {
                FoodServerCommunication.addFoodToBuilding(
                        AdminManageFoodViewController.currentSelectedFood.getFoodId().get(),
                        tempBuilding.getBuildingId().get());
            }
            refresh();
            // An alert pop up when a new building added.
            GeneralMethods.alertBox("New building", "",
                    "New building added!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            System.out.println("building adding exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the back button, redirect to the food view.
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageFoodView amfv = new AdminManageFoodView();
        amfv.start(stage);
    }
}
