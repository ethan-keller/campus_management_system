package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.views.AdminManageReservationView;

import java.awt.*;

public class ReservationEditDialogController {

    @FXML
    private TextField username;
    @FXML
    private TextField room;
    @FXML
    private TextField date;
    @FXML
    private TextField starting_time;
    @FXML
    private TextField ending_time;

    public static Reservation reservation;

    public static Stage dialogStage;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        Reservation reservation = AdminManageReservationViewController.currentSelectedReservation;
        if(reservation == null)
            return;
        username.setText(reservation.getUsername().get());
        room.setText(String.valueOf(reservation.getRoom().get()));
        date.setText(reservation.getDate().get());
        starting_time.setText(reservation.getStarting_time().get());
        ending_time.setText(reservation.getEnding_time().get());
    }

    public static void emptyReservation() {reservation = new Reservation();}

    /**
     * Called when the OK button is clicked on the dialog box.
     * This causes the information input by the user to be stored in an object.
     * @param event
     */
    @FXML
    public void OKClicked(ActionEvent event) {
        if(isInputValid()) {
            emptyReservation();
            reservation.setUsername(username.getText());
            reservation.setRoom(Integer.parseInt(room.getText()));
            reservation.setDate(date.getText());
            reservation.setEnding_time(starting_time.getText());
            reservation.setEnding_time(ending_time.getText());

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

        if(username.getText().equals("")) {
            errorMessage += "No valid username provided!\n";
        }
        if(room.getText().equals("")) {
            errorMessage += "No valid Room provided! \n";
        }
        if(date.getText().equals("")) {
            errorMessage += "No date provided!\n";
        }
        if(starting_time.getText().equals("")) {
            errorMessage += "No starting Time provided!\n";
        }
        if(ending_time.getText().equals("")) {
            errorMessage += "No ending Time provided!\n";
        }
        else {
            try {
                Integer.parseInt(room.getText());
            }
            catch (NumberFormatException e) {
                errorMessage += "No valid room id provided!\n";
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
