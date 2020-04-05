package nl.tudelft.oopp.demo.user.logic;

import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomViewLogicTest {

    @Test
    void checkTimeSlotValidity() {
        double currentStartValue = 15;
        double currentEndValue = 0;
        double startValue =14;
        double endValue = 14;

        assertFalse(RoomViewLogic.checkTimeSlotValidity(currentStartValue, currentEndValue, startValue, endValue));

        currentStartValue = 4;
        currentEndValue = 14;

        assertTrue(RoomViewLogic.checkTimeSlotValidity(currentStartValue, currentEndValue, startValue, endValue));
    }

    @Test
    void compare() {
        Reservation o1 = new Reservation(1, "jim", 1, "2020-05-05", "12:00:00", "14:00:00");
        Reservation o2 = new Reservation(2, "jim", 1, "2020-05-05", "13:00:00", "14:00:00");

        assertEquals(-1, RoomViewLogic.compare(o1, o2));

        o2.setStartingTime("12:30:00");
        assertEquals(-1, RoomViewLogic.compare(o1, o2));

        o1.setStartingTime("13:30:00");
        assertEquals(1, RoomViewLogic.compare(o1, o2));

        o2.setStartingTime("13:00:00");
        assertEquals(1, RoomViewLogic.compare(o1, o2));
    }

    @Test
    void testToString() {
        int time = 150;
        assertEquals("02:30", RoomViewLogic.toStringNum(time));
    }

    @Test
    void fromString() {
        String time ="03:30";
        assertEquals(210, RoomViewLogic.fromStringTime(time).intValue());

        assertNull(RoomViewLogic.fromStringTime(null));
    }

    @Test
    void testFromString() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        String date = "";
        assertNull(RoomViewLogic.fromStringDate(date, dateFormatter));

        date = "2020-05-05";
        LocalDate expected = LocalDate.parse(date, dateFormatter);

        assertEquals(expected, RoomViewLogic.fromStringDate(date, dateFormatter));
    }

    @Test
    void testToString1() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        String date = "2020-05-05";
        LocalDate given = LocalDate.parse(date, dateFormatter);

        assertEquals("2020-05-05", RoomViewLogic.toStringDate(given, dateFormatter));

        given = null;
        assertEquals("", RoomViewLogic.toStringDate(given, dateFormatter));
    }

    @Test
    void testFromString1() {
        Food f1 = new Food(1, "Hamburger", 14.50);
        Food f2 = new Food(2, "Pizza", 12.50);

        List<Food> foodList = new ArrayList<Food>();
        foodList.add(f1);
        foodList.add(f2);

        assertEquals(f1, RoomViewLogic.fromStringFood("Hamburger", foodList));

        foodList = null;
        assertNull(RoomViewLogic.fromStringFood(null, foodList));
    }

    @Test
    void testToString2() {
        Food f1 = new Food(1, "Hamburger", 14.50);

        assertEquals("Hamburger", RoomViewLogic.toStringFood(f1));
        assertNull(RoomViewLogic.toStringFood(null));
    }
}