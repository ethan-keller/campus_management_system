package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.BuildingEditDialogView;

public class AdminManageBuildingViewController {

    @FXML
    private TableView<Building> buildingTable;

    @FXML
    private TableColumn<Building, String> buildingIdColumn;

    @FXML
    private TableColumn<Building, String> buildingNameColumn;

    @FXML
    private TableColumn<Building, String> buildingRoomCountColumn;

    @FXML
    private TableColumn<Building, String> maxBikesColumn;

    @FXML
    private TableColumn<Building, String> buildingAddressColumn;


    public static Building currentSelectedBuilding;

    public AdminManageBuildingViewController() {
    }

    /**
     * Show all the buildings in the left side table.
     */
    @FXML
    private void initialize() {
        try {
            // Initialize the room table with the four columns.
            buildingIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(
                    String.valueOf(cell.getValue().getBuildingId().get())));
            buildingNameColumn.setCellValueFactory(cell -> cell.getValue().getBuildingName());
            buildingRoomCountColumn.setCellValueFactory(cell ->
                    new SimpleStringProperty(String.valueOf(cell.getValue().getBuildingRoomCount().get())));
            maxBikesColumn.setCellValueFactory(cell ->
                    new SimpleStringProperty(String.valueOf(cell.getValue().getBuildingMaxBikes().get())));
            buildingAddressColumn.setCellValueFactory(cell -> cell.getValue().getBuildingAddress());

            // Add observable list data to the table
            buildingTable.setItems(Building.getBuildingData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the buildings on the page.
     */
    public void refresh() {
        initialize();
    }

    /**
     * Gets the currently selected building.
     *
     * @return a Building object
     */
    public Building getSelectedBuilding() {
        if (buildingTable.getSelectionModel().getSelectedIndex() >= 0) {
            return buildingTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * Gets a number representing the index of the selected building.
     *
     * @return int
     */
    public int getSelectedIndex() {
        return buildingTable.getSelectionModel().getSelectedIndex();
    }


    /**
     * Delete a building.
     */
    @FXML
    private void deleteBuildingClicked(ActionEvent event) {
        Building selectedBuilding = getSelectedBuilding();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {

                // TODO: Check that building deletion was successful before displaying alert
                BuildingServerCommunication.deleteBuilding(selectedBuilding.getBuildingId().getValue());
                refresh();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Delete building");
                alert.setContentText("Building deleted!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Building Selected");
                alert.setContentText("Please select a building in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("delete building exception");
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking the create new button.
     */
    @FXML
    private void createNewBuildingClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentSelectedBuilding = null;
            BuildingEditDialogView view = new BuildingEditDialogView();
            view.start(stage);
            Building tempBuilding = BuildingEditDialogController.building;
            if (tempBuilding == null) {
                return;
            }

            // TODO: Check that building creation was successful before displaying alert
            // Using the method which uses the max bikes as a parameter.
            BuildingServerCommunication.createBuilding(tempBuilding.getBuildingName().get(),
                    tempBuilding.getBuildingRoomCount().get(), tempBuilding.getBuildingAddress().get(),
                    tempBuilding.getBuildingMaxBikes().get());
            refresh();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("New building");
            alert.setContentText("Added new building!");
        } catch (Exception e) {
            System.out.println("building creation exception");
            e.printStackTrace();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected building.
     */
    @FXML
    private void editBuildingClicked(ActionEvent event) {
        Building selectedBuilding = getSelectedBuilding();
        int selectedIndex = getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentSelectedBuilding = selectedBuilding;

                BuildingEditDialogView view = new BuildingEditDialogView();
                view.start(stage);
                Building tempBuilding = BuildingEditDialogController.building;

                if (tempBuilding == null) {
                    return;
                }

                // TODO: Check that building edit was successful before displaying alert
                // Apparently the the maximum number of bikes in a building can't be edited.
                BuildingServerCommunication.updateBuilding(selectedBuilding.getBuildingId().get(),
                        tempBuilding.getBuildingName().get(), tempBuilding.getBuildingRoomCount().get(),
                        tempBuilding.getBuildingAddress().get());
                refresh();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Edit building");
                alert.setContentText("edited building!");
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Building Selected");
                alert.setContentText("Please select a building in the table.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("building edit exception");
            e.printStackTrace();
        }
    }

    /**.
     * Back button is clicked which redirects the admin back to admin home page.
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }

}
