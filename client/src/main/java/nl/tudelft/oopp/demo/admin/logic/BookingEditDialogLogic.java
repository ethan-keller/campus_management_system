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
    public static String isInputValid(ComboBox<Room> bookingRoomComboBox, DatePicker bookingDate) {

        while(true) {
            // Checks whether a room from the combo box is selected and the index represents if the
            // selection of the room is valid.
            if (bookingRoomComboBox.getSelectionModel().getSelectedIndex() < 0) {
                return "No valid room selected!\n";
            }
            // Checks whether a date from the datePicker is selected
            if (bookingDate.getValue() == null) {
                return  "No valid date selected!\n";
            }
            // This string value means that all the fields are filled.
            else {
                return "Good!\n";
            }
        }
    }

    /**
     * sorts the reservation given.
     * @param reservations list of reservations to be sorted.
     */
    public static void sortReservations(List<Reservation> reservations) {
        reservations.sort(new Comparator<Reservation>() {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                // split time in hh:mm
                String[] o1StartSplit = o1.getStartingTime().get().split(":");
                int o1StartHour = Integer.parseInt(o1StartSplit[0]);
                int o1StartMinute = Integer.parseInt(o1StartSplit[1]);

                String[] o2StartSplit = o2.getStartingTime().get().split(":");
                int o2StartHour = Integer.parseInt(o2StartSplit[0]);
                int o2StartMinute = Integer.parseInt(o2StartSplit[1]);

                // compare hours and minutes
                if (o1StartHour < o2StartHour) {
                    return -1;
                } else if (o1StartHour > o2StartHour) {
                    return 1;
                }
                if (o1StartMinute < o2StartMinute) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

}
