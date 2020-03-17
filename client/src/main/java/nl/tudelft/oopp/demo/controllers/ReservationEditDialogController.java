package nl.tudelft.oopp.demo.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.views.AdminManageReservationView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.stream.Collectors;


public class ReservationEditDialogController {

    @FXML
    private ComboBox<User> username;
    @FXML
    private ComboBox<Room> room;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> starting_time;
    @FXML
    private ComboBox<String> ending_time;

    public static Reservation reservation;

    public static Stage dialogStage;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReservationEditDialogController() {}

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        Reservation reservation = AdminManageReservationViewController.currentSelectedReservation;
        date.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate dateSelected) {
                if(dateSelected != null) {
                    return formatter.format(dateSelected);
                }
                return null;
            }

            @Override
            public LocalDate fromString(String string) {
                if(string != null && !string.trim().isEmpty()) {
                    return LocalDate.parse(string, formatter);
                }
                return null;
            }
        });

        date.setOnAction(event -> {

        });

        //Initializing the observable list for the users available!!
        //The admin can make a mistake in writing the name of the user.
        ObservableList<User> oL = User.getUserData();
        username.setItems(oL);
        this.setUserComboBoxConverter(oL);

        //Initializing the observable list for the rooms available!!
        ObservableList<Room> ol = Room.getRoomData();
        room.setItems(ol);
        this.setRoomComboBoxConverter(ol);

        //If there are no reservation in the table.
        if(reservation == null)
            return;

        //username.getSelectionModel().select(oL.stream().filter(x -> x.getUsername().get().equals(reservation.getUsername().get())).collect(Collectors.toList()).get(0));

        //room.getSelectionModel().select(ol.stream().filter(x -> x.getRoomId().get() == reservation.getRoom().get()).collect(Collectors.toList()).get(0));

        //date.setText(reservation.getDate().get());
        starting_time.getItems().addAll("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00");
        ending_time.getItems().addAll("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00");

    }

    public void setRoomComboBoxConverter(ObservableList<Room> ol) {
        StringConverter<Room> converter = new StringConverter<Room>() {
            @Override
            public String toString(Room object) {
                if(object == null)
                    return "";
                else
                    return object.getRoomName().get();
            }

            @Override
            public Room fromString(String id) {
                return ol.stream().filter(x -> String.valueOf(x.getRoomId()).equals(id)).collect(Collectors.toList()).get(0);
            }
        };
        room.setConverter(converter);
    }

    public void setUserComboBoxConverter(ObservableList<User> oL) {
        StringConverter<User> converter = new StringConverter<User>() {
            @Override
            public String toString(User object) {
                if(object == null)
                    return "";
                else
                    return object.getUsername().get();
            }

            @Override
            public User fromString(String id) {
                return oL.stream().filter(x -> String.valueOf(x.getUsername()).equals(id)).collect(Collectors.toList()).get(0);
            }
        };
        username.setConverter(converter);
    }

    public static void emptyReservation() {reservation = new Reservation();}

    /**
     * Called when the OK button is clicked on the dialog box.
     * This causes the information input by the user to be stored in an object.
     * @param event
     */
    @FXML
    public void OKClicked(ActionEvent event) {
        LocalDate dateSelected = date.getValue();
        if(isInputValid()) {
            emptyReservation();
            reservation.setUsername(username.getSelectionModel().getSelectedItem().getUsername().get());
            reservation.setRoom(room.getSelectionModel().getSelectedItem().getRoomId().get());
            reservation.setDate(dateSelected.toString());
            reservation.setEnding_time(starting_time.getValue());
            reservation.setEnding_time(ending_time.getValue());

            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void CancelClicked(ActionEvent event) {
        reservation = null;
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if(username.getSelectionModel().getSelectedIndex() <= 0) {
            errorMessage += "No valid username provided!\n";
        }
        if(room.getSelectionModel().getSelectedIndex() <= 0) {
            errorMessage += "No valid Room provided! \n";
        }
        if(date.getValue().equals("")) {
            errorMessage += "No date provided!\n";
        }
        if(starting_time.getValue().equals("")) {
            errorMessage += "No starting Time provided!\n";
        }
        if(ending_time.getValue().equals("")) {
            errorMessage += "No ending Time provided!\n";
        }

        // If all the fields are valid, then true is returned.
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
