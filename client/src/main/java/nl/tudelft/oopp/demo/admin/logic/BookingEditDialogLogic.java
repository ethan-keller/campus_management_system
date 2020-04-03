package nl.tudelft.oopp.demo.admin.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;

import org.controlsfx.control.RangeSlider;

public class BookingEditDialogLogic {

    /**
     * Validates the user input.
     *
     * @return true if the input is valid
     */
    public static String isInputValid(int index, String bookingDate) {

        while(true) {
            // Checks whether a room from the combo box is selected and the index represents if the
            // selection of the room is valid.
            if (index < 0) {
                return "No valid room selected!\n";
            }
            // Checks whether a date from the datePicker is selected
            if (bookingDate.equals("")) {
                return  "No valid date selected!\n";
            }
            // This string value means that all the fields are filled.
            else {
                return "Good!\n";
            }
        }
    }

}
