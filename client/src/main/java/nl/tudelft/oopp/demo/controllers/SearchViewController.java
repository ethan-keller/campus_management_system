package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.BookingHistoryView;
import nl.tudelft.oopp.demo.views.CancelBookingView;
import nl.tudelft.oopp.demo.views.LoginView;
import nl.tudelft.oopp.demo.views.RegisterView;

import java.io.IOException;

public class SearchViewController {


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

    public void BookingHistoryButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        BookingHistoryView bookingHistoryView = new BookingHistoryView();
        bookingHistoryView.start(stage);
    }

    public void signOutButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginView loginView = new LoginView();
        loginView.start(stage);
    }

    public void cancelBookingClicked(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        CancelBookingView cancelBookingView = new CancelBookingView();
        cancelBookingView.start(stage);
    }


    public void setRoom(Image im, String buildingName, String roomName, String capacityNumber) {
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

    public void initializeBuilding() {
        BuildingChoiceBox.getItems().add("EWI");
        BuildingChoiceBox.getItems().add("Drebbelweg");
    }

    public void initializeCapacity() {
        CapacityChoiceBox.getItems().add("1 - 10");
        CapacityChoiceBox.getItems().add("10 - 30");
        CapacityChoiceBox.getItems().add("30 - 100");
        CapacityChoiceBox.getItems().add("100 - ");
    }

    public void initializeRole() {
        RoleChoiceBox.getItems().add("Teachers only");
        RoleChoiceBox.getItems().add("Students only");
    }

    public void initializeFoodAvailable() {
        FoodAvailableChoiceBox.getItems().add("No Food Available");
    }

}
