package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.AdminManageBuildingView;
import nl.tudelft.oopp.demo.views.AdminManageRoomView;
import org.json.JSONArray;
import org.json.JSONException;

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
    private void deleteBuildingClicked() {
        Building selectedBuilding = buildingTable.getSelectionModel().getSelectedItem();
        int selectedIndex = buildingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            AdminManageServerCommunication.deleteBuilding(selectedBuilding.getBuildingId());
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Delete building");
//            alert.setContentText(AdminManageServerCommunication.deleteBuilding(selectedBuilding.getBuildingId()));
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
    private void createNewBuildingClicked() {
        Building tempBuilding = new Building();
        boolean okClicked = adminManageBuildingView.showBuildingEditDialog(tempBuilding);
        if(okClicked){
            AdminManageServerCommunication.createBuilding(tempBuilding.getBuildingName(), tempBuilding.getBuildingRoom_count(), tempBuilding.getBuildingAddress());
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("New building");
//            alert.setContentText(AdminManageServerCommunication.createBuilding(tempBuilding.getBuildingName(), tempBuilding.getBuildingRoom_count(), tempBuilding.getBuildingAddress()));
            showBuildingDetails(tempBuilding);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected building.
     */
    @FXML
    private void editBuildingClicked() {
        Building selectedBuilding = buildingTable.getSelectionModel().getSelectedItem();
        if (selectedBuilding != null) {
            boolean okClicked = adminManageBuildingView.showBuildingEditDialog(selectedBuilding);
            if (okClicked) {
                AdminManageServerCommunication.updateBuilding(selectedBuilding.getBuildingId(), selectedBuilding.getBuildingName(), selectedBuilding.getBuildingRoom_count(), selectedBuilding.getBuildingAddress());
//                Alert alert = new Alert(AlertType.INFORMATION);
//                alert.setTitle("Edit building");
//                alert.setContentText(AdminManageServerCommunication.updateBuilding(selectedBuilding.getBuildingId(), selectedBuilding.getBuildingName(), selectedBuilding.getBuildingRoom_count(), selectedBuilding.getBuildingAddress()));
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

}
