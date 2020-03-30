package nl.tudelft.oopp.demo.logic;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.communication.user.CurrentUserManager;
import nl.tudelft.oopp.demo.controllers.ReservationConfirmationViewController;
import nl.tudelft.oopp.demo.controllers.RoomViewController;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ReservationConfirmationView;

import org.controlsfx.control.RangeSlider;

public class RoomViewLogic {

    /**
     * confirms the booking of a room.
     * @param currentRoom room that is selected.
     * @param thisStage stage to start.
     * @param datePicker datepicker on the screen.
     * @param timeSlotSlider time slot slider on the screen.
     * @return true if the user confirms, false otherwise.
     */
    public static boolean confirmBooking(Room currentRoom, Stage thisStage, DatePicker datePicker,
                                         RangeSlider timeSlotSlider) {
        try {
            // TODO: add food selection

            String date = RoomViewController.getDatePickerConverter(datePicker).toString(datePicker.getValue());
            ReservationConfirmationViewController.date = date;
            String startTime = RoomViewController.getRangeSliderConverter().toString(
                    timeSlotSlider.getLowValue());
            ReservationConfirmationViewController.startTime = startTime;
            String endTime = RoomViewController.getRangeSliderConverter().toString(timeSlotSlider.getHighValue());
            ReservationConfirmationViewController.endTime = endTime;
            // set all fields to the current reservation details
            ReservationConfirmationViewController.room = currentRoom;
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

    /**
     *
     * @param currentRoomId Room selected.
     * @param datePicker datepicker on the screen.
     * @param timeSlotSlider time slot slider on the screen.
     * @return true if a reservation is created, false if not.
     * @throws UnsupportedEncodingException if something goes wrong with encoding.
     */
    public static boolean createReservation(int currentRoomId, DatePicker datePicker, RangeSlider timeSlotSlider)
            throws UnsupportedEncodingException {
        String date = RoomViewController.getDatePickerConverter(datePicker).toString(datePicker.getValue());
        String startTime = RoomViewController.getRangeSliderConverter().toString(timeSlotSlider.getLowValue());
        String endTime = RoomViewController.getRangeSliderConverter().toString(timeSlotSlider.getHighValue());

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
    public static boolean isInputValid(Text dateError, Text foodError, Text timeSlotError, DatePicker datePicker,
                                       ComboBox<String> foodChoice, int currentRoomId, RoomViewController rvc,
                                       RangeSlider timeSlotSlider) {
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
            if (!checkTimeSlotValidity(currentRoomId, datePicker, timeSlotSlider)
                    || timeSlotSlider.getLowValue() == timeSlotSlider.getHighValue()) {
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
    private static boolean checkTimeSlotValidity(int currentRoomId, DatePicker datePicker,
                                                 RangeSlider timeSlotSlider) {
        // get all reservations for the current room on the chosen date
        List<Reservation> roomReservations = Reservation.getRoomReservationsOnDate(currentRoomId,
                datePicker.getValue(), RoomViewController.getDatePickerConverter(datePicker));

        // get converter to convert date value to String format hh:mm
        StringConverter<Number> timeConverter = RoomViewController.getRangeSliderConverter();

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
}
