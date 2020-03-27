package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    Room r1 = new Room(1, "p4", 4, true, 30, "C:\\Users", "beautiful", "instruction");
    Room r2 = new Room(1, "p4", 4, true, 30, "C:\\Users", "beautiful", "instruction");
    Room r3 = new Room(2, "p5", 4, false, 35, "C:\\Pictures", "ugly", "lecture");


    /**
     * Test that tests getting the id.
     */
    @Test
    void getId() {
        assertEquals(1, r1.getId());
        assertEquals(2, r3.getId());
    }

    /**
     * Test that tests getting the name of the room.
     */
    @Test
    void getName() {
        assertEquals("p4", r1.getName());
        assertEquals("p5", r3.getName());
    }

    /**
     * Test that tests getting the building of the room.
     */
    @Test
    void getBuilding() {
        assertEquals(4, r1.getBuilding());
    }

    /**
     * Test that tests if a room is for teachers only.
     */
    @Test
    void isTeacher_only() {
        assertTrue(r1.isTeacher_only());
        assertFalse(r3.isTeacher_only());
    }

    /**
     * Test that tests getting the capacity.
     */
    @Test
    void getCapacity() {
        assertEquals(30, r1.getCapacity());
        assertEquals(35, r3.getCapacity());
    }

    /**
     * Test that tests getting the photos.
     */
    @Test
    void getPhotos() {
        assertEquals("C:\\Users", r1.getPhotos());
        assertEquals("C:\\Pictures", r3.getPhotos());
    }

    /**
     * Test that tests getting the description.
     */
    @Test
    void getDescription() {
        assertEquals("beautiful", r1.getDescription());
        assertEquals("ugly", r3.getDescription());
    }

    /**
     * Test that tests getting the type.
     */
    @Test
    void getType() {
        assertEquals("instruction", r1.getType());
        assertEquals("lecture", r3.getType());
    }

    /**
     * Test that tests the equals method.
     */
    @Test
    void testEquals() {
        assertTrue(r1.equals(r1));
        assertTrue(r2.equals(r1));
        assertTrue(r1.equals(r2));
        assertFalse(r3.equals(r1));
        assertFalse(r1.equals("pizza"));
    }
}