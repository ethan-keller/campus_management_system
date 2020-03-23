package nl.tudelft.oopp.demo.controllers;

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
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.views.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdminFoodBuildingViewController {

    @FXML
    private Label foodNameLabel;

    @FXML
    private TableView<Building> foodBuildingTable;

    @FXML
    private TableColumn<Building, String> foodBuildingIdColumn;

    @FXML
    private TableColumn<Building, String> foodBuildingNameColumn;

    public static Building currentSelectedFoodBuilding;

    public AdminFoodBuildingViewController() {
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
            foodBuildingIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBuildingId().get())));
            foodBuildingNameColumn.setCellValueFactory(cell -> cell.getValue().getBuildingName());
            // Add observable list data to the table
            foodBuildingTable.setItems(Food.getFoodBuilding(AdminManageFoodViewController.currentSelectedFood.getFoodId().get()));
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
                FoodServerCommunication.deleteFoodFromBuilding(AdminManageFoodViewController.currentSelectedFood.getFoodId().get(), selectedBuilding.getBuildingId().getValue());
                refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete building");
                alert.setContentText("Building deleted!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Building Selected");
                alert.setContentText("Please select a building in the table.");
                alert.showAndWait();
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentSelectedFoodBuilding = null;
            FoodBuildingEditDialogView view = new FoodBuildingEditDialogView();
            view.start(stage);
            Building tempBuilding = FoodBuildingEditDialogController.building;
            if (tempBuilding == null) return;
            else FoodServerCommunication.addFoodToBuilding(AdminManageFoodViewController.currentSelectedFood.getFoodId().get(),tempBuilding.getBuildingId().get());
            refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New building");
            alert.setContentText("Added new building!");
        } catch (Exception e) {
            System.out.println("building adding exception");
            e.printStackTrace();
        }
    }

    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminManageFoodView amfv = new AdminManageFoodView();
        amfv.start(stage);
    }
}
