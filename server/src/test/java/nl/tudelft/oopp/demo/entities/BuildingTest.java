package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class BuildingTest {
    Building b1 = new Building(1, "Name", 5, "drebbelweg aan zee", 4, 5);
    Building b2 = new Building(1, "Name", 5, "drebbelweg aan zee", 4, 5);
    Building b3 = new Building(2, "Name", 5, "drebbelweg aan zee", 4, 5);

    @org.junit.jupiter.api.Test
    void getId() {
        assertEquals(1, b1.getId());
        assertEquals(1, b2.getId());
        assertEquals(2, b3.getId());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals("Name", b1.getName());
    }

    @org.junit.jupiter.api.Test
    void getRoomCount() {
        assertEquals(5, b1.getRoomCount());
    }

    @Test
    void getAddress() {
        assertEquals("drebbelweg aan zee", b1.getAddress());
    }

    @Test
    void getAvailableBikes() {
        assertEquals(4, b1.getAvailableBikes());
    }

    @Test
    void getMaxBikes() {
        assertEquals(5, b1.getMaxBikes());
    }

    @Test
    void testEquals() {
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));
        assertFalse(b1.equals(b3));
        assertTrue(b1.equals(b1));
        assertFalse(b1.equals("pizza"));
    }
}