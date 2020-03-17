package nl.tudelft.oopp.demo.controllers;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.model.Appointment;
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
    private TextField header;

    @FXML
    private DatePicker date_appointment;

    @FXML
    private TextField starting_time;

    @FXML
    private TextField ending_time;

    @FXML
    private TextArea description;

    public static Calendar appointment;
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
        if(header.getText().equals("")) {
            errorMessage += "No header provided!\n";
        }

        // If any of the fields is left blank (except for description field), return true.
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
    public void confirmClicked(ActionEvent event) {

        if(isInputValid()){
            Appointment app = new Appointment();
            app.setHeaderText(header.getText());
            app.setDescriptionText(description.getText());

            String date = date_appointment.getValue().toString();
            String[] dateSplit = date.split("-");
            int year = Integer.parseInt(dateSplit[0]);
            int month = Integer.parseInt(dateSplit[1]);
            int day = Integer.parseInt(dateSplit[2]);

            String start = starting_time.getText();
            String[] startSplit = start.split(":");
            int startHour = Integer.parseInt(startSplit[0]);
            int startMin = Integer.parseInt(startSplit[1]);
            int startSec = Integer.parseInt(startSplit[2]);

            app.setStartTime(new DateTime(year, month, day, startHour, startMin, startSec));

            String end = ending_time.getText();
            String[] endSplit = end.split(":");
            int endHour = Integer.parseInt(endSplit[0]);
            int endMin = Integer.parseInt(endSplit[1]);
            int endSec = Integer.parseInt(endSplit[2]);

            app.setEndTime(new DateTime(year, month, day, endHour, endMin, endSec));

            appointment.getSchedule().getItems().add(app);

            this.dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        }

    }
}
