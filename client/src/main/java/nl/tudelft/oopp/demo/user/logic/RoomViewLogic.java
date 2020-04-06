package nl.tudelft.oopp.demo.user.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
<<<<<<< HEAD
import java.util.logging.Logger;
import java.util.stream.Collectors;

import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Reservation;
=======
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.user.CurrentUserManager;
import nl.tudelft.oopp.demo.user.controller.ReservationConfirmationViewController;
import nl.tudelft.oopp.demo.user.controller.RoomViewController;
import nl.tudelft.oopp.demo.views.ReservationConfirmationView;
>>>>>>> develop



public class RoomViewLogic {

    private static Logger logger = Logger.getLogger("GlobalLogger");

    /**
<<<<<<< HEAD
<<<<<<< HEAD
     * Method that checks if the chosen timeslot is free.
     *
     * @return true if the timeslot is free, false otherwise
     */
    public static boolean checkTimeSlotValidity(double currentStartValue, double currentEndValue,
                                                double startValue, double endValue) {
        // check if the values overlap
        if (!((currentStartValue <= startValue && currentEndValue <= startValue)
                || (currentStartValue >= endValue && currentEndValue >= endValue))) {
            return false;
=======
     * confirms the booking of a room.
     *
     * @param currentRoom    room that is selected.
     * @param thisStage      stage to start.
     * @param datePicker     datepicker on the screen.
     * @param timeSlotSlider time slot slider on the screen.
     * @return true if the user confirms, false otherwise.
     */
    public static boolean confirmBooking(Room currentRoom, Stage thisStage, DatePicker datePicker,
                                         RangeSlider timeSlotSlider) {
        try {
            // TODO: add food selection

            String date = getDatePickerConverter(datePicker).toString(datePicker.getValue());
            ReservationConfirmationViewController.date = date;
            String startTime = getRangeSliderConverter().toString(
                    timeSlotSlider.getLowValue());
            ReservationConfirmationViewController.startTime = startTime;
            String endTime = getRangeSliderConverter().toString(timeSlotSlider.getHighValue());
            ReservationConfirmationViewController.endTime = endTime;
            // set all fields to the current reservation details
            ReservationConfirmationViewController.room = currentRoom;
            // load confirmation pop up stage
            ReservationConfirmationView rcv = new ReservationConfirmationView();
            rcv.start(thisStage);
            // return true if confirmed, false otherwise
            return ReservationConfirmationViewController.confirmed;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
>>>>>>> develop
        }
        return true;
    }

<<<<<<< HEAD

    /**.
     * This compares the two reservations provides as parameter
     * @param o1 First reservation
     * @param o2 Other reservation
     * @return Integer which determines the comparison of the two reservations
=======
    /**
     * makes a reservation.
     *
     * @param currentRoomId  Room selected.
     * @param datePicker     datepicker on the screen.
     * @param timeSlotSlider time slot slider on the screen.
     * @return true if a reservation is created, false if not.
     * @throws UnsupportedEncodingException if something goes wrong with encoding.
>>>>>>> develop
     */
    public static int compare(Reservation o1, Reservation o2) {
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

    /**.
     * This method converts the Number in time format which is stored as a string
     * @param n - the number which is intended to be a time parameter
     * @return String which contains the time
     */
<<<<<<< HEAD
    public static String toStringNum(Number n) {
        // calculate hours and remaining minutes to get a correct hh:mm format
        long minutes = n.longValue();
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long remainingMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        // '%02d' means that there will be a 0 in front if its only 1 number + it's a long number
        return String.format("%02d", hours) + ":" + String.format("%02d", remainingMinutes);
=======
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
            logger.log(Level.SEVERE, e.toString());
        }
        return false;
>>>>>>> develop
    }

    /**.
     * Reverts the functionality of the above method. This methods converts the string back to the
     * number format for the time
     * @param time - Time provided as a string
     * @return A number which represents the time in the time format
     */
<<<<<<< HEAD
    public static Number fromStringTime(String time) {
        if (time != null) {
            String[] split = time.split(":");
            return Double.parseDouble(split[0]) * 60 + Double.parseDouble(split[1]);
=======
    public static boolean checkTimeSlotValidity(int currentRoomId, DatePicker datePicker,
                                                RangeSlider timeSlotSlider) {
        // get all reservations for the current room on the chosen date
        List<Reservation> roomReservations = Reservation.getRoomReservationsOnDate(currentRoomId,
                datePicker.getValue(), getDatePickerConverter(datePicker));

        // get converter to convert date value to String format hh:mm
        StringConverter<Number> timeConverter = getRangeSliderConverter();

        // if there are no reservations the timeslot is valid
        if (roomReservations.size() == 0) {
            return true;
>>>>>>> develop
        }
        return null;
    }

<<<<<<< HEAD
    /**.
     * Using the dateFormatter, this method converts the string into a local Date type
     * @param string - The date as a string
     * @param dateFormatter - DateFormatter (yyyy-MM-dd)
     * @return Local date type object which contains the equivalent form of the string
     */
    public static LocalDate fromStringDate(String string, DateTimeFormatter dateFormatter) {
        if (string != null && !string.isEmpty()) {
            // get correct LocalDate from String format
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
=======
        for (Reservation r : roomReservations) {
            // get rangeslider values + reservation values
            double currentStartValue = timeSlotSlider.getLowValue();
            double currentEndValue = timeSlotSlider.getHighValue();
            double startValue = (double) timeConverter.fromString(r.getReservationStartingTime().get());
            double endValue = (double) timeConverter.fromString(r.getReservationEndingTime().get());
>>>>>>> develop


    /**.
     * Converts the string into a list of food entities
     * @param string - The string which contains all the food
     * @param foodList - The list of food
     * @return - Food equivalent of the string
     */
    public static Food fromStringFood(String string, List<Food> foodList) {
        if (string != null) {
            return foodList.stream()
                    .filter(x -> x.getFoodName().get().equals(string))
                    .collect(Collectors.toList()).get(0);
        } else {
            return null;
        }
    }

    /**.
     * Converts a food object to a string for readability and testability
     * @param object - The food object to be converted
     * @return String which contains the food name
     */
<<<<<<< HEAD
    public static String toStringFood(Food object) {
        if (object != null) {
            return object.getFoodName().get();
        } else {
            return null;
=======
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
            logger.log(Level.SEVERE, e.toString());
>>>>>>> develop
        }
    }

    /**.
     * Converts the date from a localDate type to a string using a dateFormatter
     * @param date - The date provided in a loclaDate format
     * @param dateFormatter - DateFormatter (yyyy-MM-dd)
     * @return The string equivalence of the provided date
     */
<<<<<<< HEAD
    public static String toStringDate(LocalDate date, DateTimeFormatter dateFormatter) {
        if (date != null) {
            // get correctly formatted String
            return dateFormatter.format(date);
        } else {
            return "";
=======
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
                //                    // true if there are errors, false otherwise
                //                    boolean errors = false;
                //
                //                    // clear error messages
                //            dateError.setVisible(false);
                //            timeslotError.setVisible(false);
                //
                //                    // set error messages if necessary
                //            if(datePicker.getValue()==null)
                //
                //                    {
                //                        dateError.setVisible(true);
                //                        errors = true;
                //                    }
                //            if(!
                //
                //                    checkTimeSlotValidity() ||timeSlotSlider.getLowValue()==
                //                    timeSlotSlider.getHighValue())
                //
                //                    {
                //                        timeslotError.setVisible(true);
                //                        errors = true;
                //                    }

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
            logger.log(Level.SEVERE, e.toString());
>>>>>>> develop
        }
    }

}
