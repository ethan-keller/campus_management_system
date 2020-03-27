package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationsTest {
    Reservations r1 = new Reservations(1, "Otte", 4, "2020-07-07", "13-00-00", "15-00-00");
    Reservations r2 = new Reservations(1, "Otte", 4, "2020-07-07", "13-00-00", "15-00-00");
    Reservations r3 = new Reservations(3, "Leon", 6, "2020-07-07", "12-00-00", "15-00-00");

    /**
     * Test that tests getting id.
     */
    @Test
    void getId() {
        assertEquals(1, r1.getId());
        assertEquals(3, r3.getId());
    }

    /**
     * Test that tests getting the username.
     */
    @Test
    void getUsername() {
        assertEquals("Otte", r1.getUsername());
        assertEquals("Leon", r3.getUsername());

    }

    /**
     * Test that tests getting the room.
     */
    @Test
    void getRoom() {
        assertEquals(4, r1.getRoom());
        assertEquals(6, r3.getRoom());

    }

    /**
     * Test that tests getting the date.
     */
    @Test
    void getDate() {
        assertEquals("2020-07-07", r1.getDate());
    }

    /**
     * Test that tests getting the starting time.
     */
    @Test
    void getStarting_time() {
        assertEquals("13-00-00", r1.getStarting_time());

    }

    /**
     * Test that tests getting the ending time.
     */
    @Test
    void getEnding_time() {
        assertEquals("15-00-00", r1.getEnding_time());
    }

    /**
     * test the equals method.
     */
    @Test
    void testEquals() {
        assertTrue(r1.equals(r1));
        assertTrue(r2.equals(r1));
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(r3));
        assertFalse(r1.equals("pizza"));
    }
}