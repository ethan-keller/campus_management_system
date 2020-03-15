package nl.tudelft.oopp.demo.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class BookingEditDialogController {

    @FXML
    private ComboBox<Building> bookingBuildingComboBox;

    @FXML
    private ComboBox<Room> bookingRoomComboBox;

    @FXML
    private DatePicker bookingDate;

    @FXML
    private ComboBox<String> bookingStarting_time;

    @FXML
    private ComboBox<String> bookingEnding_time;

    public ObservableList<Building> olb;

    public ObservableList<Room> olr;

    public static Reservation reservation;

    private Stage dialogStage;

    public BookingEditDialogController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            olb = Building.getBuildingData();
            bookingBuildingComboBox.setItems(olb);
            this.setBookingBuildingComboBoxConverter(olb);
            bookingBuildingComboBox.setOnAction(e -> buildingSelected());

            olr = Room.getRoomData();
            bookingRoomComboBox.setItems(olr);
            this.setBookingRoomComboBoxConverter(olr);

            bookingStarting_time.getItems().addAll("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00");
            bookingEnding_time.getItems().addAll("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setBookingBuildingComboBoxConverter(ObservableList<Building> olb){
        StringConverter<Building> converter = new StringConverter<Building>() {
            @Override
            public String toString(Building object) {
                if (object == null) return "";
                else return object.getBuildingName().get();
            }

            @Override
            public Building fromString(String id) {
                return olb.stream().filter(x -> String.valueOf(x.getBuildingId()).equals(id)).collect(Collectors.toList()).get(0);
            }
        };
        bookingBuildingComboBox.setConverter(converter);
    }

    public void setBookingRoomComboBoxConverter(ObservableList<Room> olb){
        StringConverter<Room> converter = new StringConverter<Room>() {
            @Override
            public String toString(Room object) {
                if (object == null) return "";
                else return object.getRoomName().get();
            }

            @Override
            public Room fromString(String id) {
                return olr.stream().filter(x -> String.valueOf(x.getRoomId()).equals(id)).collect(Collectors.toList()).get(0);
            }
        };
        bookingRoomComboBox.setConverter(converter);
    }

    public void buildingSelected() {
        if(!bookingBuildingComboBox.getValue().equals(null)){
            olr = olr.filtered(olr -> olr.getRoomBuilding().equals(bookingBuildingComboBox.getValue().getBuildingId().get()));
        }
    }

    private static void emptyReservation(){
        reservation = new Reservation();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkClicked(ActionEvent event) {
        if (isInputValid()) {
            emptyReservation();
            reservation.setUsername(AdminManageUserViewController.currentSelectedUser.getUsername().get());
            reservation.setRoom(this.bookingRoomComboBox.getSelectionModel().getSelectedItem().getRoomId().get());
            reservation.setDate(this.bookingDate.getValue().toString());
            reservation.setStarting_time(this.bookingStarting_time.getValue());
            reservation.setEnding_time(this.bookingEnding_time.getValue());

            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelClicked(ActionEvent event) {
        reservation = null;
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (bookingRoomComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid room selected!\n";
        }
        if (bookingDate.getValue().equals(null)) {
            errorMessage += "No valid date selected!\n";
        }

        /////// Starting time and ending time to be implemented...

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
