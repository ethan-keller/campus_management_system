package nl.tudelft.oopp.demo.logic;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.GeneralMethods;
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.controllers.ReservationConfirmationViewController;
import nl.tudelft.oopp.demo.controllers.RoomViewController;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ReservationConfirmationView;
import nl.tudelft.oopp.demo.views.SearchView;
import org.controlsfx.control.RangeSlider;
import org.w3c.dom.ranges.Range;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoomViewLogic {

    /**
     *
     * @param currentRoom
     * @param thisStage
     * @param datePicker
     * @param timeSlotSlider
     * @return true if the user confirms, false otherwise
     */
    public static boolean confirmBooking(Room currentRoom, Stage thisStage, DatePicker datePicker, RangeSlider timeSlotSlider) {
        try {
            // TODO: add food selection

            String date = getDatePickerConverter(datePicker).toString(datePicker.getValue());
            String startTime = getRangeSliderConverter().toString(timeSlotSlider.getLowValue());
            String endTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());
            // set all fields to the current reservation details
            ReservationConfirmationViewController.room = currentRoom;
            ReservationConfirmationViewController.date = date;
            ReservationConfirmationViewController.startTime = startTime;
            ReservationConfirmationViewController.endTime = endTime;
            // load confirmation pop up stage
            ReservationConfirmationView rcv = new ReservationConfirmationView();
            rcv.start(thisStage);
            // return true if confirmed, false otherwise
            return ReservationConfirmationViewController.confirmed;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createReservation(int currentRoomId, DatePicker datePicker, RangeSlider timeSlotSlider) throws UnsupportedEncodingException {
        String date = getDatePickerConverter(datePicker).toString(datePicker.getValue());
        String startTime = getRangeSliderConverter().toString(timeSlotSlider.getLowValue());
        String endTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());

        return ReservationServerCommunication.createReservation(CurrentUserManager.getUsername(),
                currentRoomId, date, startTime,
                endTime.contains("24") ? "23:59" : endTime);
    }

    /**
     * Validator.
     * Checks if fields are correctly filled and shows errors and warnings if
     * the user forgot some fields.
     *
     * @return true if everything is filled in correctly, false otherwise
     */
    public static boolean isInputValid(Text dateError, Text foodError, Text timeSlotError, DatePicker datePicker, ComboBox<String> foodChoice, int currentRoomId, RoomViewController rvc, RangeSlider timeSlotSlider) {
        try {
            // true if there are errors, false otherwise
            boolean errors = false;

            // clear error messages
            dateError.setVisible(false);
            foodError.setVisible(false);
            timeSlotError.setVisible(false);

            // set error messages if necessary
            if (datePicker.getValue() == null) {
                dateError.setVisible(true);
                errors = true;
            }
            if (foodChoice.getSelectionModel().getSelectedItem() == null) {
                foodError.setVisible(true);
                errors = true;
            }
            if (!checkTimeSlotValidity(currentRoomId, datePicker, timeSlotSlider) || timeSlotSlider.getLowValue() == timeSlotSlider.getHighValue()) {
                timeSlotError.setVisible(true);
                errors = true;
            }

            // return true if no errors where triggered
            return !errors;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method that checks if the chosen timeslot is free.
     *
     * @return true if the timeslot is free, false otherwise
     */
    private static boolean checkTimeSlotValidity(int currentRoomId, DatePicker datePicker, RangeSlider timeSlotSlider) {
        // get all reservations for the current room on the chosen date
        List<Reservation> roomReservations = Reservation.getRoomReservationsOnDate(currentRoomId,
                datePicker.getValue(), getDatePickerConverter(datePicker));

        // get converter to convert date value to String format hh:mm
        StringConverter<Number> timeConverter = getRangeSliderConverter();

        // if there are no reservations the timeslot is valid
        if (roomReservations.size() == 0) {
            return true;
        }

        for (Reservation r : roomReservations) {
            // get rangeslider values + reservation values
            double currentStartValue = timeSlotSlider.getLowValue();
            double currentEndValue = timeSlotSlider.getHighValue();
            double startValue = (double) timeConverter.fromString(r.getStartingTime().get());
            double endValue = (double) timeConverter.fromString(r.getEndingTime().get());

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
    public static StringConverter<LocalDate> getDatePickerConverter(DatePicker datePicker) {
        try {
            return new StringConverter<LocalDate>() {
                // set the wanted pattern (format)
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    // set placeholder text for the datePicker
                    datePicker.setPromptText(pattern.toLowerCase());
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
     * Create cellFactory for the datePicker that disables all days before today and weekend days.
     * It also marks them red to make sure the user understands why they are disabled.
     *
     * @return a CallBack object used to set the dayCellFactory for the datePicker in
     * configureDatePicker
     */
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        try {
            final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {

                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            // Disable all days before today + weekend days
                            if (item.isBefore(LocalDate.now()) || item.getDayOfWeek() == DayOfWeek.SATURDAY
                                    || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                                // disable the 'button'
                                setDisable(true);
                                // make them red
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    };
                }
            };
            return dayCellFactory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
