package nl.tudelft.oopp.demo.logic;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.controllers.AdminManageReservationViewController;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.controlsfx.control.RangeSlider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReservationEditDialogLogic {

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    public static boolean isInputValid(ComboBox<User> username, ComboBox<Room> room, DatePicker date,
                                 RangeSlider timeslotSlider, DateTimeFormatter formatter) {
        String errorMessage = "";

        if (username.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "No valid username provided!\n";
        }
        if (room.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "No valid Room provided! \n";
        }
        if (date.getValue() == null) {
            errorMessage += "No date provided!\n";
        }
        if (!checkTimeSlotValidity(room, date, formatter, timeslotSlider) ||
                timeslotSlider.getLowValue() == timeslotSlider.getHighValue()) {
            errorMessage += "No valid timeslot selected!\n";
        }

        // If all the fields are valid, then true is returned.
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            GeneralMethods.alertBox("Invalid Fields", "Please correct the invalid fields",
                    errorMessage, Alert.AlertType.ERROR);
            return false;
        }
    }

    /**
     * Constructor for the converter that converts LocalDate objects to String yyyy-MM-dd format.
     */
    public static StringConverter<LocalDate> getDateConverter(DateTimeFormatter formatter) {
        try {
            return new StringConverter<LocalDate>() {
                @Override
                public String toString(LocalDate dateSelected) {
                    if (dateSelected != null) {
                        return formatter.format(dateSelected);
                    }
                    return "null";
                }

                @Override
                public LocalDate fromString(String string) {
                    // The date is formatted in yyyy-MM-dd format from the datePicker.
                    if (string != null && !string.trim().isEmpty()) {
                        return LocalDate.parse(string, formatter);
                    }
                    return null;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Constructor for the converter that converts LocalDate objects to String yyyy-MM-dd format.
     *
     * @return constructed StringConverter
     */
    public static StringConverter<Number> getRangeSliderConverter() {
        try {
            return new StringConverter<Number>() {
                @Override
                public String toString(Number n) {
                    // calculate hours and remaining minutes to get a correct hh:mm format
                    long minutes = n.longValue();
                    long hours = TimeUnit.MINUTES.toHours(minutes);
                    long remainingMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
                    // '%02d' means that there will be a 0 in front if its only 1 number + it's a long number
                    return String.format("%02d", hours) + ":" + String.format("%02d", remainingMinutes);
                }


                @Override
                public Number fromString(String string) {
                    String[] split = string.split(":");
                    double hours = Double.parseDouble(split[0]);
                    double minutes = Double.parseDouble(split[1]);
                    return hours * 60 + minutes;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that checks if the chosen timeslot is free.
     *
     * @return true if the timeslot is free, false otherwise
     */
    public static boolean checkTimeSlotValidity(ComboBox<Room> room, DatePicker date, DateTimeFormatter formatter,
                                                RangeSlider timeslotSlider) {
        try {
            // get currently selected room
            Room selectedRoom = room.getSelectionModel().getSelectedItem();
            if (selectedRoom == null) {
                return false;
            }
            // get all reservations for the current room on the chosen date
            List<Reservation> roomReservations = Reservation.getRoomReservationsOnDate(
                    selectedRoom.getRoomId().get(),
                    date.getValue(), getDateConverter(formatter));

            // if something went wrong with the server communication return false
            if (roomReservations == null) {
                return false;
            }

            // get converter to convert date value to String format hh:mm
            StringConverter<Number> timeConverter = getRangeSliderConverter();

            // if there are no reservations the timeslot is valid
            if (roomReservations.size() == 0) {
                return true;
            }

            Reservation res = AdminManageReservationViewController.currentSelectedReservation;

            for (Reservation r : roomReservations) {
                // if reservation equals the one we are editing, don't consider it
                if (res != null) {
                    if (r.getId().get() == res.getId().get()) {
                        continue;
                    }
                }

                // get rangeslider values + reservation values
                double currentStartValue = timeslotSlider.getLowValue();
                double currentEndValue = timeslotSlider.getHighValue();
                double startValue = (double) timeConverter.fromString(r.getStartingTime().get());
                double endValue = (double) timeConverter.fromString(r.getEndingTime().get());

                // check if the values overlap
                if (!((currentStartValue <= startValue && currentEndValue <= startValue)
                        || (currentStartValue >= endValue && currentEndValue >= endValue))) {
                    return false;
                }

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
