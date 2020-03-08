package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.AdminHomePageView;
import nl.tudelft.oopp.demo.views.AdminManageBuildingView;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AdminManageBuildingViewController {
    @FXML
    private TableView<Building> buildingTable;

    @FXML
    private TableColumn<Building, String> buildingIdColumn;

    @FXML
    private TableColumn<Building, String> buildingNameColumn;

    @FXML
    private Label buildingName;

    @FXML
    private Label buildingAddress;

    @FXML
    private Label buildingRoomCount;

    private AdminManageBuildingView adminManageBuildingView;

    public AdminManageBuildingViewController() {
    }

    /**
     * Show all the buildings in the left side table.
     */
    @FXML
    private void initialize() {
        // Initialize the room table with the three columns.
        buildingIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().buildingIdProperty().getValue().toString()));
        buildingNameColumn.setCellValueFactory(cellData -> cellData.getValue().buildingNameProperty());
        // Clear room details.
        showBuildingDetails(null);
        // Listen for selection changes and show the room details when changed.
        buildingTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showBuildingDetails(newValue));
    }

    public void setAdminManageBuildingView(AdminManageBuildingView adminManageBuildingView) throws JSONException {
        this.adminManageBuildingView = adminManageBuildingView;
        // Add observable list data to the table
        buildingTable.setItems(Building.getBuildingData());
    }

    /**
     * Show the buildings in the right side details.
     */
    private void showBuildingDetails(Building building) {
        if(building != null) {
            buildingName.setText(building.getBuildingName());
            buildingAddress.setText(building.getBuildingAddress());
            buildingRoomCount.setText(""+building.getBuildingRoom_count());
        } else {
            buildingName.setText("");
            buildingAddress.setText("");
            buildingRoomCount.setText("");
        }
    }

    /**
     * Delete a building.
     */
    @FXML
    private void deleteBuildingClicked() throws UnsupportedEncodingException {
        Building selectedBuilding = buildingTable.getSelectionModel().getSelectedItem();
        int selectedIndex = buildingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            BuildingServerCommunication.deleteBuilding(selectedBuilding.getBuildingId());
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Delete building");
//            alert.setContentText(BuildingServerCommunication.deleteBuilding(selectedBuilding.getBuildingId()));
            buildingTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminManageBuildingView.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Building Selected");
            alert.setContentText("Please select a building in the table.");
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking the create new button.
     */
    @FXML
    private void createNewBuildingClicked() throws UnsupportedEncodingException {
        Building tempBuilding = new Building();
        boolean okClicked = adminManageBuildingView.showBuildingEditDialog(tempBuilding);
        if(okClicked){
            BuildingServerCommunication.createBuilding(tempBuilding.getBuildingName(), tempBuilding.getBuildingRoom_count(), tempBuilding.getBuildingAddress());
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("New building");
//            alert.setContentText(BuildingServerCommunication.createBuilding(tempBuilding.getBuildingName(), tempBuilding.getBuildingRoom_count(), tempBuilding.getBuildingAddress()));
            showBuildingDetails(tempBuilding);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected building.
     */
    @FXML
    private void editBuildingClicked() throws UnsupportedEncodingException {
        Building selectedBuilding = buildingTable.getSelectionModel().getSelectedItem();
        if (selectedBuilding != null) {
            boolean okClicked = adminManageBuildingView.showBuildingEditDialog(selectedBuilding);
            if (okClicked) {
                BuildingServerCommunication.updateBuilding(selectedBuilding.getBuildingId(), selectedBuilding.getBuildingName(), selectedBuilding.getBuildingRoom_count(), selectedBuilding.getBuildingAddress());
//                Alert alert = new Alert(AlertType.INFORMATION);
//                alert.setTitle("Edit building");
//                alert.setContentText(BuildingServerCommunication.updateBuilding(selectedBuilding.getBuildingId(), selectedBuilding.getBuildingName(), selectedBuilding.getBuildingRoom_count(), selectedBuilding.getBuildingAddress()));
                showBuildingDetails(selectedBuilding);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(adminManageBuildingView.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Building Selected");
            alert.setContentText("Please select a room in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void backClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdminHomePageView ahpv = new AdminHomePageView();
        ahpv.start(stage);
    }

}
