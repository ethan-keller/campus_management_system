package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class RoomTest {

    Room r1 = new Room(1, "p4", 4, true, 30, "C:\\Users", "beautiful", "instruction");
    Room r2 = new Room(1, "p4", 4, true, 30, "C:\\Users", "beautiful", "instruction");
    Room r3 = new Room(2, "p5", 4, false, 35, "C:\\Pictures", "ugly", "lecture");


    @Test
    void getId() {
        assertEquals(1, r1.getId());
        assertEquals(2, r3.getId());
    }

    @Test
    void getName() {
        assertEquals("p4", r1.getName());
        assertEquals("p5", r3.getName());
    }

    @Test
    void getBuilding() {
        assertEquals(4, r1.getBuilding());
    }

    @Test
    void isTeacherOnly() {
        assertTrue(r1.isTeacherOnly());
        assertFalse(r3.isTeacherOnly());
    }

    @Test
    void getCapacity() {
        assertEquals(30, r1.getCapacity());
        assertEquals(35, r3.getCapacity());
    }

    @Test
    void getPhotos() {
        assertEquals("C:\\Users", r1.getPhotos());
        assertEquals("C:\\Pictures", r3.getPhotos());
    }

    @Test
    void getDescription() {
        assertEquals("beautiful", r1.getDescription());
        assertEquals("ugly", r3.getDescription());
    }

    @Test
    void getType() {
        assertEquals("instruction", r1.getType());
        assertEquals("lecture", r3.getType());
    }

    @Test
    void testEquals() {
        assertTrue(r1.equals(r1));
        assertTrue(r2.equals(r1));
        assertTrue(r1.equals(r2));
        assertFalse(r3.equals(r1));
        assertFalse(r1.equals("pizza"));
    }
}