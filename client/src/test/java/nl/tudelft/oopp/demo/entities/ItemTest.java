package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item(10, "user", "title", "date",
                "startingtime", "endingtime", "description");
    }

    @Test
    void emptyContructor() {
        item = new Item();
        assertEquals(-1, item.getId().get());
        assertEquals(null, item.getUser().get());
        assertEquals(null, item.getTitle().get());
        assertEquals(null, item.getDate().get());
        assertEquals(null, item.getStartingTime().get());
        assertEquals(null, item.getEndingTime().get());
        assertEquals(null, item.getDescription().get());
    }

    @Test
    void getId() {
        assertEquals(10, item.getId().get());
    }

    @Test
    void getUser() {
        assertEquals("user", item.getUser().get());
    }

    @Test
    void getTitle() {
        assertEquals("title", item.getTitle().get());
    }

    @Test
    void getDate() {
        assertEquals("date", item.getDate().get());
    }

    @Test
    void getStartingTime() {
        assertEquals("startingtime", item.getStartingTime().get());
    }

    @Test
    void getEndingTime() {
        assertEquals("endingtime", item.getEndingTime().get());
    }

    @Test
    void getDescription() {
        assertEquals("description", item.getDescription().get());
    }

    @Test
    void setId() {
        item.setId(20);
        assertEquals(20, item.getId().get());
    }

    @Test
    void setUser() {
        item.setUser("anotheruser");
        assertEquals("anotheruser", item.getUser().get());
    }

    @Test
    void setTitle() {
        item.setTitle("anothertitle");
        assertEquals("anothertitle", item.getTitle().get());
    }

    @Test
    void setDate() {
        item.setDate("anotherdate");
        assertEquals("anotherdate", item.getDate().get());
    }

    @Test
    void setStartingTime() {
        item.setStartingTime("anotherstartingtime");
        assertEquals("anotherstartingtime", item.getStartingTime().get());
    }

    @Test
    void setEndingTime() {
        item.setEndingTime("anotherendingtime");
        assertEquals("anotherendingtime", item.getEndingTime().get());
    }

    @Test
    void setDescription() {
        item.setDescription("anotherdescription");
        assertEquals("anotherdescription", item.getDescription().get());

    }

    @Test
    void testEquals() {
        Item item2 = new Item(5, "", "", "", "", "", "");
        Item item3 = new Item(10, "", "", "", "", "", "");
        Integer integer = 2;

        assertTrue(item.equals(item));
        assertFalse(item.equals(integer));
        assertTrue(item.equals(item3));
        assertFalse(item.equals(item2));
    }

    @Test
    void getAllItems() {
    }

    @Test
    void getUserItems() {
    }
}