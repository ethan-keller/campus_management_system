package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.logic.AdminManageBuildingLogic;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.BuildingEditDialogView;
import nl.tudelft.oopp.demo.views.LoginView;

public class AdminManageBuildingViewController {

    @FXML
    private TableView<Building> buildingTable;

    @FXML
    private TableColumn<Building, Number> buildingIdColumn;

    @FXML
    private TableColumn<Building, String> buildingNameColumn;

    @FXML
    private TableColumn<Building, Number> buildingRoomCountColumn;

    @FXML
    private TableColumn<Building, String> maxBikesColumn;

    @FXML
    private TableColumn<Building, String> buildingAddressColumn;

    @FXML
    private Button signOutButton;

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
            buildingIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(
                    cell.getValue().getBuildingId().get()));
            buildingNameColumn.setCellValueFactory(cell -> cell.getValue().getBuildingName());
            buildingRoomCountColumn.setCellValueFactory(cell ->
                    new SimpleIntegerProperty(cell.getValue().getBuildingRoomCount().get()));
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
                // TODO: Check that building deletion was succesful before displaying alert
                AdminManageBuildingLogic.deleteBuildingLogic(selectedBuilding);
                //BuildingServerCommunication.deleteBuilding(selectedBuilding.getBuildingId().getValue());
                refresh();
                // Create an alert box.
                GeneralMethods.alertBox("Delete Building", "", "Building deleted!",
                        AlertType.INFORMATION);
            } else {
                // Create an alert box.
                GeneralMethods.alertBox("No Selection", "No Building Selected", "Please"
                        + " select a building in the table", AlertType.WARNING);
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

            // TODO: Check that building creation was succesful before displaying alert
            AdminManageBuildingLogic.createBuildingLogic(tempBuilding);
            //BuildingServerCommunication.createBuilding(tempBuilding.getBuildingName().get(),
            //tempBuilding.getBuildingRoomCount().get(),
            //tempBuilding.getBuildingAddress().get());
            refresh();
            // Create an alert box.
            GeneralMethods.alertBox("New Building", "", "Added new building!",
                    AlertType.INFORMATION);

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
                AdminManageBuildingLogic.editBuildingLogic(selectedBuilding, tempBuilding);
                //BuildingServerCommunication.updateBuilding(selectedBuilding.getBuildingId().get(),
                //tempBuilding.getBuildingName().get(), tempBuilding.getBuildingRoomCount().get(),
                //tempBuilding.getBuildingAddress().get());
                refresh();
                // Create an alert box.
                GeneralMethods.alertBox("Edit Building", "", "Edited building!",
                        AlertType.INFORMATION);

            } else {
                // Create an alert box.
                GeneralMethods.alertBox("No Selection", "No Building Selected",
                        "Please select a building in the table.", AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("building edit exception");
            e.printStackTrace();
        }
    }

    /**
     * .
     * Back button is clicked which redirects the admin back to admin home page.
     *
     * @param event is passed
     * @throws IOException is thrown
     */
    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // This loads up a new admin home page.
        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }

    /**
     * This button redirects to the admin back to the login page.
     *
     * @param event is passed.
     * @throws IOException is thrown.
     */
    @FXML
    private void signOutButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Starts a new login page.
        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

}
