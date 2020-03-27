package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    Building b1 = new Building(1, "Name", 5, "drebbelweg aan zee");
    Building b2 = new Building(1, "Name", 5, "drebbelweg aan zee");
    Building b3 = new Building(2, "Name", 5, "drebbelweg aan zee");

    /**
     * Tests getting id.
     */
    @Test
    void getId() {
        assertEquals(1, b1.getId());
        assertEquals(1, b2.getId());
        assertEquals(2, b3.getId());
    }
    /**
     * Tests getting building name.
     */
    @Test
    void getName() {
        assertEquals("Name", b1.getName());
    }

    /**
     * Tests getting room count.
     */
    @Test
    void getRoom_count() {
        assertEquals(5, b1.getRoom_count());
    }

    /**
     * Tests getting address.
     */
    @Test
    void getAddress() {
        assertEquals("drebbelweg aan zee", b1.getAddress());
    }

    /**
     * Tests if two buildings are equal.
     */
    @Test
    void testEquals() {
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));
        assertFalse(b1.equals(b3));
        assertTrue(b1.equals(b1));
        assertFalse(b1.equals("pizza"));
    }
}