package nl.tudelft.oopp.demo.admin.logic;

import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationEditDialogLogicTest {
    @Test
    void isInputValidNullTest() {
        User user = null;
        Room room = null;
        LocalDate date = null;
        double currentStartValue = 1.0;
        double currentEndValue = 1.0;
        StringConverter<LocalDate> temp = null;

        String test = ReservationEditDialogLogic.isInputValid(
                user, room, date, currentStartValue, currentEndValue, temp);
        String expected = "No valid username provided!\n"
                + "No valid Room provided! \n"
                + "No date provided!\n"
                + "No valid timeslot selected!\n";
        assertEquals(expected, test);
    }

    @Test
    void checkTimeSlotValidityNullRoomTest() {
        assertFalse(ReservationEditDialogLogic
                .checkTimeSlotValidity(null, null, 0.1, 0.1, null));
    }

    @Test
    void checkTimeSLotValidityTest() {
        Room room = new Room(
                1, "", 1, false, 1, "", "", "");
        boolean test = ReservationEditDialogLogic
                .checkTimeSlotValidity(room, null, 1, 2, null);
        assertTrue(test);
    }

    @Test
    void getRangeSliderConverter() {
        
    }
}