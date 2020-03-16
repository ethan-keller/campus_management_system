package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CalenderAppointmentDialogController {

    @FXML
    private DatePicker date_appointment;

    @FXML
    private TextField starting_time;

    @FXML
    private TextField ending_time;

    @FXML
    private TextArea description;

    public static String appointment;
    public static Stage dialogStage;

    @FXML
    public void initialize() {

    }

    /**
     * To check if the information filled out by the user is valid.
     * @return boolean value to verify if information is valid.
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if(date_appointment.getValue().equals("")) {
            errorMessage += "No date provided!\n";
        }
        if(starting_time.getText().equals("")) {
            errorMessage += "No starting Time provided!\n";
        }
        if(ending_time.getText().equals("")) {
            errorMessage += "No ending Time provided!\n";
        }
        if(description.getText().equals("")) {
            errorMessage += "No description provided!\n";
        }

        // If any of the fields is left blank, return true.
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

    /**
     * Called when the user clicks cancel.
     * @param event
     */
    @FXML
    public void cancelClicked(ActionEvent event) {
        this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Called when the user clicks confirm.
     * @param
     */
    @FXML
    public void confirmClicked() {}
}
