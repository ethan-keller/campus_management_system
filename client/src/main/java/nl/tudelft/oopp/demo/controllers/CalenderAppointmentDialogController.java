package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    @FXML
    public void initialize() {


    }
}
