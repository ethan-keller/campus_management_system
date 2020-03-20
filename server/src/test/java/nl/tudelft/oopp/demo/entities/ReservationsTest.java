package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationsTest {
    Reservations r1 = new Reservations(1, "Otte", 4, "2020-07-07", "13-00-00", "15-00-00");
    Reservations r2 = new Reservations(1, "Otte", 4, "2020-07-07", "13-00-00", "15-00-00");
    Reservations r3 = new Reservations(3, "Leon", 6, "2020-07-07", "12-00-00", "15-00-00");

    @Test
    void getId() {
        assertEquals(1, r1.getId());
        assertEquals(3, r3.getId());
    }

    @Test
    void getUsername() {
        assertEquals("Otte", r1.getUsername());
        assertEquals("Leon", r3.getUsername());

    }

    @Test
    void getRoom() {
        assertEquals(4, r1.getRoom());
        assertEquals(6, r3.getRoom());

    }

    @Test
    void getDate() {
        assertEquals("2020-07-07", r1.getDate());
    }

    @Test
    void getStarting_time() {
        assertEquals("13-00-00", r1.getStarting_time());

    }

    @Test
    void getEnding_time() {
        assertEquals("15-00-00", r1.getEnding_time());
    }

    @Test
    void testEquals() {
        assertTrue(r1.equals(r1));
        assertTrue(r2.equals(r1));
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(r3));
        assertFalse(r1.equals("pizza"));
    }
}