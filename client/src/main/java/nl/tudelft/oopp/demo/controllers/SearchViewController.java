package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.LoginServerCommunication;

public class SearchViewController {

    ObservableList<String> buildingChoice = FXCollections.observableArrayList("EEMCS", "Drebbelweg", "Aula", "Library", "Fellowship");
//    ObservableList<String> dateChoice = FXCollections.observableArrayList("Today", "Tomorrow", "Day After");
    ObservableList<String> timeChoice = FXCollections.observableArrayList("9:00 - 12:00", "13:00 - 17:00", "17:00 - 20:00");
    ObservableList<String> capacityChoice = FXCollections.observableArrayList("4 people", "6 people", "8 people", "10 people");
    ObservableList<String> roleChoice = FXCollections.observableArrayList("Lecturer Only", "All");
    ObservableList<String> foodChoice = FXCollections.observableArrayList("Cafeteria", "CoffeeStar");

    @FXML
    private DatePicker DateChoiceBox;

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

    @FXML
    private ImageView ImageToBeAdded;

    @FXML
    private Text BuildingToBeAdded;

    @FXML
    private Text RoomToBeAdded;

    @FXML
    private Text CapacityToBeAdded;

    public void SearchBarClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        String usernameTxt = SearchBar.getText();
        //////THIS REQUIRES SEARCH ALGORITHM
        // TO BE ADDED WITH DATABASE GROUP
    }

    @FXML
    private void initialize() {
       //DateChoiceBox.setItems(dateChoice);
        TimeslotChoiceBox.setItems(timeChoice);
        BuildingChoiceBox.setItems(buildingChoice);
        CapacityChoiceBox.setItems(capacityChoice);
        RoleChoiceBox.setItems(roleChoice);
        FoodAvailableChoiceBox.setItems(foodChoice);
        //BuildingChoiceBox.setValue("mmm");
    }

    public void setRoom(Image im, String buildingName, String roomName, String capacityNumber){
        ImageToBeAdded.setImage(im);
        BuildingToBeAdded.setText(buildingName);
        RoomToBeAdded.setText(roomName);
        CapacityToBeAdded.setText(capacityNumber);

    }




}
