package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.AdminManageServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.AdminManageBuildingView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class BuildingEditDialogController {

    @FXML
    private TextField buildingNameField;
    @FXML
    private TextField buildingAddressField;
    @FXML
    private TextField buildingRoom_countField;

    private Building building;
    public static boolean edit = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }


    public void handleCancelClicked(ActionEvent event){

    }


    public void handleOkClicked(ActionEvent event) throws UnsupportedEncodingException {
        if(isInputValid()){
            Building temp = new Building();
            temp = readBuilding(temp);
            if(edit){
                System.out.println("Ethan");
            } else {
                AdminManageServerCommunication.createBuilding(temp.getBuildingName(), temp.getBuildingRoom_count(), temp.getBuildingAddress());
            }
        }
    }

    public Building readBuilding(Building temp){
        try {
            temp.setBuildingName(buildingNameField.getText());
            temp.setBuildingRoom_count(Integer.parseInt(buildingRoom_countField.getText()));
            temp.setBuildingAddress(buildingAddressField.getText());
            return temp;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


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