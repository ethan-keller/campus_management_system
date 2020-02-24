package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.LoginServerCommunication;

public class SearchViewController {


    @FXML
    ChoiceBox DateChoiceBox;

    @FXML
    ChoiceBox TimeslotChoiceBox;

    @FXML
    ChoiceBox BuildingChoiceBox;

    @FXML
    ChoiceBox CapacityChoiceBox;

    @FXML
    ChoiceBox RoleChoiceBox;

    @FXML
    ChoiceBox FoodAvailableChoiceBox;

    @FXML
    TextField SearchBar;

    public void SearchBarClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        String usernameTxt = SearchBar.getText();
        /////////
    }


    private void initialize() {
        BuildingChoiceBox.setValue("Project Room");
    }

}
