package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    void setup() {
        reservation = new Reservation(10, "username", 20,
                "date", "startingtime", "endingtime");
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
        assertEquals("date", reservation.getDate().get());
    }

    @Test
    void getStartingTime() {
        assertEquals("startingtime", reservation.getReservationStartingTime().get());
    }

    @Test
    void getEndingTime() {
        assertEquals("endingtime", reservation.getReservationEndingTime().get());
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
}