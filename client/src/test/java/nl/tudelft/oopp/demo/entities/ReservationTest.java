package nl.tudelft.oopp.demo.entities;

import com.mindfusion.common.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    void setup() {
        reservation = new Reservation(10, "username", 20,
                "2020-04-04", "12:00:00", "13:00:00");
    }

    @Test
    void emptyConstructor() {
        reservation = new Reservation();
        assertEquals(-1, reservation.getReservationId().get());
        assertEquals(null, reservation.getUsername().get());
        assertEquals(-1, reservation.getRoom().get());
        assertEquals(null, reservation.getDate().get());
        assertEquals(null, reservation.getReservationStartingTime().get());
        assertEquals(null, reservation.getReservationEndingTime().get());
    }

    @Test
    void getId() {
        assertEquals(10, reservation.getReservationId().get());
    }

    @Test
    void getUsername() {
        assertEquals("username", reservation.getUsername().get());
    }

    @Test
    void getRoom() {
        assertEquals(20, reservation.getRoom().get());
    }

    @Test
    void getDate() {
        assertEquals("2020-04-04", reservation.getDate().get());
    }

    @Test
    void getStartingTime() {
        assertEquals("12:00:00", reservation.getReservationStartingTime().get());
    }

    @Test
    void getEndingTime() {
        assertEquals("13:00:00", reservation.getReservationEndingTime().get());
    }

    @Test
    void setId() {
        reservation.setId(5);
        assertEquals(5, reservation.getReservationId().get());
    }

    @Test
    void setUsername() {
        reservation.setUsername("newusername");
        assertEquals("newusername", reservation.getUsername().get());
    }

    @Test
    void setRoom() {
        reservation.setRoom(50);
        assertEquals(50, reservation.getRoom().get());
    }

    @Test
    void setDate() {
        reservation.setDate("newdate");
        assertEquals("newdate", reservation.getDate().get());
    }

    @Test
    void setStartingTime() {
        reservation.setStartingTime("newstartingtime");
        assertEquals("newstartingtime", reservation.getReservationStartingTime().get());
    }

    @Test
    void setEndingTime() {
        reservation.setEndingTime("newendingtime");
        assertEquals("newendingtime", reservation.getReservationEndingTime().get());
    }

    @Test
    void equals() {
        Reservation testRes = new Reservation(1,"", 0, "", "", "");
        Reservation testRes2 = new Reservation(10,"", 0, "", "", "");
        Integer integer = 2;

        assertTrue(reservation.equals(reservation));
        assertFalse(reservation.equals(integer));
        assertFalse(reservation.equals(testRes));
        assertTrue(reservation.equals(testRes2));
    }

    @Test
    void getRoomReservationsOnDate() {
    }

    @Test
    void getReservation() {
    }

    @Test
    void getUserReservation() {
    }

    @Test
    void getSelectedUserReservation() {
    }

    @Test
    void testGetId() {
        assertEquals("10", reservation.getId());
    }

    @Test
    void getHeader() {
        assertEquals("Reservation", reservation.getHeader());
    }

    @Test
    void getStartTime() {
        DateTime datetime = new DateTime(2020,4,4,12,00,00);
        assertEquals(datetime, reservation.getStartTime());
    }

    @Test
    void getEndTime() {
        DateTime datetime = new DateTime(2020,4,4,13,00,00);
        assertEquals(datetime, reservation.getEndTime());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getColor() {
        assertEquals(Color.CYAN, reservation.getColor());
    }
}