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
    private void getRoom() {
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

    public void initializeTimeSlot() {
        TimeslotChoiceBox.getItems().add("0:00 - 1:00");
        TimeslotChoiceBox.getItems().add("1:00 - 2:00");
        TimeslotChoiceBox.getItems().add("2:00 - 3:00");
        TimeslotChoiceBox.getItems().add("3:00 - 4:00");
        TimeslotChoiceBox.getItems().add("4:00 - 5:00");
        TimeslotChoiceBox.getItems().add("5:00 - 6:00");
        TimeslotChoiceBox.getItems().add("6:00 - 7:00");
        TimeslotChoiceBox.getItems().add("7:00 - 8:00");
        TimeslotChoiceBox.getItems().add("8:00 - 9:00");
        TimeslotChoiceBox.getItems().add("9:00 - 10:00");
        TimeslotChoiceBox.getItems().add("10:00 - 11:00");
        TimeslotChoiceBox.getItems().add("11:00 - 12:00");
        TimeslotChoiceBox.getItems().add("12:00 - 13:00");
        TimeslotChoiceBox.getItems().add("13:00 - 14:00");
        TimeslotChoiceBox.getItems().add("14:00 - 15:00");
        TimeslotChoiceBox.getItems().add("15:00 - 16:00");
        TimeslotChoiceBox.getItems().add("16:00 - 17:00");
        TimeslotChoiceBox.getItems().add("17:00 - 18:00");
        TimeslotChoiceBox.getItems().add("18:00 - 19:00");
        TimeslotChoiceBox.getItems().add("19:00 - 20:00");
        TimeslotChoiceBox.getItems().add("20:00 - 21:00");
        TimeslotChoiceBox.getItems().add("21:00 - 22:00");
        TimeslotChoiceBox.getItems().add("22:00 - 23:00");
        TimeslotChoiceBox.getItems().add("23:00 - 00:00");
    }

    public void initializeBuilding(){
        BuildingChoiceBox.getItems().add("EWI");
        BuildingChoiceBox.getItems().add("Drebbelweg");
    }

    public void initializeCapacity(){
        CapacityChoiceBox.getItems().add("1 - 10");
        CapacityChoiceBox.getItems().add("10 - 30");
        CapacityChoiceBox.getItems().add("30 - 100");
        CapacityChoiceBox.getItems().add("100 - ");
    }

    public void initializeRole(){
        RoleChoiceBox.getItems().add("Teachers only");
        RoleChoiceBox.getItems().add("Students only");
    }

    public void initializeFoodAvailable(){
        FoodAvailableChoiceBox.getItems().add("No Food Available");
    }







}
