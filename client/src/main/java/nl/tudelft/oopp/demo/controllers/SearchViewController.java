package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.LoginServerCommunication;

public class SearchViewController {

    ObservableList<String> buildingChoice = FXCollections.observableArrayList("EEMCS", "Drebbelweg", "Aula", "Library", "Fellowship");
    ObservableList<String> dateChoice = FXCollections.observableArrayList("Tomorrow", "Day After");
    ObservableList<String> timeChoice = FXCollections.observableArrayList("9:00 - 12:00", "13:00 - 17:00", "17:00 - 20:00");
    ObservableList<String> capacityChoice = FXCollections.observableArrayList("4 people", "6 people", "8 people", "10 people");
    ObservableList<String> roleChoice = FXCollections.observableArrayList("Lecturer Only", "All");
    ObservableList<String> foodChoice = FXCollections.observableArrayList("Cafeteria", "CoffeeStar");

    @FXML
    private ChoiceBox DateChoiceBox;

    @FXML
    private ChoiceBox TimeslotChoiceBox;

    @FXML
    private ChoiceBox BuildingChoiceBox;

    @FXML
    private ChoiceBox CapacityChoiceBox;

    @FXML
    private ChoiceBox RoleChoiceBox;

    @FXML
    private ChoiceBox FoodAvailableChoiceBox;

    @FXML
    private TextField SearchBar;

    public void SearchBarClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        String usernameTxt = SearchBar.getText();
        /////////
    }

    @FXML
    private void initialize() {
        DateChoiceBox.setItems(dateChoice);
        TimeslotChoiceBox.setItems(timeChoice);
        BuildingChoiceBox.setItems(buildingChoice);
        CapacityChoiceBox.setItems(capacityChoice);
        RoleChoiceBox.setItems(roleChoice);
        FoodAvailableChoiceBox.setItems(foodChoice);
        //BuildingChoiceBox.setValue("mmm");
    }

}
