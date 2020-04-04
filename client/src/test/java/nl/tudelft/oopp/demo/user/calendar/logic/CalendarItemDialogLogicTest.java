package nl.tudelft.oopp.demo.user.calendar.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class CalendarItemDialogLogicTest {

    @Test
    void setItemTypeTest() {
        CalendarItemDialogLogic.setItemType(Color.ORANGE);
        assertTrue(CalendarItemDialogLogic.item);
        assertFalse(CalendarItemDialogLogic.reservation);
        assertFalse(CalendarItemDialogLogic.bikeReservation);
        CalendarItemDialogLogic.setItemType(Color.MAGENTA);
        assertTrue(CalendarItemDialogLogic.bikeReservation);
        CalendarItemDialogLogic.setItemType(Color.CYAN);
        assertTrue(CalendarItemDialogLogic.reservation);
    }

    @Test
    void deleteItemTest() {

    }

    @Test
    void deleteReservationTest() {
    }
}