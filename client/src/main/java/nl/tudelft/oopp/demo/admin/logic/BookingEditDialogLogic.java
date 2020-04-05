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

import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.general.GeneralMethods;

import org.controlsfx.control.RangeSlider;

public class BookingEditDialogLogic {

    /**
     * Validates the user input.
     *
     * @return true if the input is valid
     */
    public static boolean isInputValid(ComboBox<Room> bookingRoomComboBox, DatePicker bookingDate,
                                       RangeSlider timeSlotSlider, Reservation reservation) {
        String errorMessage = "";

        if (bookingRoomComboBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No valid room selected!\n";
        }
        if (bookingDate.getValue() == null) {
            errorMessage += "No valid date selected!\n";
        }
        if (!checkTimeSlotValidity(bookingRoomComboBox, bookingDate, reservation, timeSlotSlider)
                || timeSlotSlider.getLowValue() == timeSlotSlider.getHighValue()) {
            errorMessage += "No valid time slot selected!\n";
        }
        if (errorMessage.equals("")) {
            return true;
        } else {
            // Show the error message.
            GeneralMethods.alertBox("Invalid Fields", "Please correct invalid fields",
                    errorMessage, Alert.AlertType.ERROR);

            return false;
        }
    }

    /**
     * Method that checks if the chosen time slot is free.
     *
     * @return true if the time slot is free, false otherwise
     */
    public static boolean checkTimeSlotValidity(ComboBox<Room> bookingRoomComboBox, DatePicker bookingDate,
                                                Reservation reservation, RangeSlider timeSlotSlider) {
        // get currently selected room
        Room selectedRoom = bookingRoomComboBox.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            return false;
        }
        // get all reservations for the current room on the chosen date
        List<Reservation> roomReservations = Reservation.getRoomReservationsOnDate(selectedRoom.getRoomId().get(),
                bookingDate.getValue(), getDatePickerConverter(bookingDate));

        // get converter to convert date value to String format hh:mm
        StringConverter<Number> timeConverter = getRangeSliderConverter();

        // if there are no reservations the timeslot is valid
        if (roomReservations.size() == 0) {
            return true;
        }

        for (Reservation r : roomReservations) {
            // if reservation equals the one we are editing, don't consider it
            if (r.getReservationId().get() == reservation.getReservationId().get()) {
                continue;
            }

            // get rangeslider values + reservation values
            double currentStartValue = timeSlotSlider.getLowValue();
            double currentEndValue = timeSlotSlider.getHighValue();
            double startValue = (double) timeConverter.fromString(r.getReservationStartingTime().get());
            double endValue = (double) timeConverter.fromString(r.getReservationEndingTime().get());

            // check if the values overlap
            if (!((currentStartValue <= startValue && currentEndValue <= startValue)
                    || (currentStartValue >= endValue && currentEndValue >= endValue))) {
                return false;
            }

        }
        return true;
    }

    /**
     * Creates a StringConverter that converts the selected value to an actual time (in String format).
     *
     * @return a StringConverter object
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
                public Number fromString(String time) {
                    if (time != null) {
                        String[] split = time.split(":");
                        return Double.parseDouble(split[0]) * 60 + Double.parseDouble(split[1]);
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
     * Creates a StringConverter that converts the selected value to a usable Date (in String format).
     *
     * @return a StringConverter object
     */
    public static StringConverter<LocalDate> getDatePickerConverter(DatePicker bookingDate) {
        try {
            return new StringConverter<LocalDate>() {
                // set the wanted pattern (format)
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    // set placeholder text for the datePicker
                    bookingDate.setPromptText(pattern.toLowerCase());
                }

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        // get correctly formatted String
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        // get correct LocalDate from String format
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sorts the reservation given.
     *
     * @param reservations list of reservations to be sorted.
     */
    public static void sortReservations(List<Reservation> reservations) {
        reservations.sort(new Comparator<Reservation>() {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                // split time in hh:mm
                String[] o1StartSplit = o1.getReservationStartingTime().get().split(":");
                int o1StartHour = Integer.parseInt(o1StartSplit[0]);
                int o1StartMinute = Integer.parseInt(o1StartSplit[1]);

                String[] o2StartSplit = o2.getReservationStartingTime().get().split(":");
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
