package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mindfusion.common.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;


class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item(10, "user", "title", "2020-04-04",
                "12:00:00", "13:00:00", "description");
    }

    @Test
    void emptyContructor() {
        item = new Item();
        assertEquals(-1, item.getItemId().get());
        assertEquals(null, item.getUser().get());
        assertEquals(null, item.getTitle().get());
        assertEquals(null, item.getDate().get());
        assertEquals(null, item.getItemStartingTime().get());
        assertEquals(null, item.getItemEndingTime().get());
        assertEquals(null, item.getItemDescription().get());
    }

    @Test
    void getId() {
        assertEquals(10, item.getItemId().get());
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
        assertEquals("2020-04-04", item.getDate().get());
    }

    @Test
    void getStartingTime() {
        assertEquals("12:00:00", item.getItemStartingTime().get());
    }

    @Test
    void getEndingTime() {
        assertEquals("13:00:00", item.getItemEndingTime().get());
    }

    @Test
    void getDescription() {
        assertEquals("description", item.getItemDescription().get());
    }

    @Test
    void setId() {
        item.setId(20);
        assertEquals(20, item.getItemId().get());
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
        assertEquals("anotherstartingtime", item.getItemStartingTime().get());
    }

    @Test
    void setEndingTime() {
        item.setEndingTime("anotherendingtime");
        assertEquals("anotherendingtime", item.getItemEndingTime().get());
    }

    @Test
    void setDescription() {
        item.setDescription("anotherdescription");
        assertEquals("anotherdescription", item.getItemDescription().get());

    }

    @Test
    void testEquals() {
        final Item item2 = new Item(5, "", "", "", "", "", "");
        final Item item3 = new Item(10, "", "", "", "", "", "");
        final Integer integer = 2;

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

    @Test
    void testGetId() {
        assertEquals("10", item.getId());
    }

    @Test
    void testSetId() {
        item.setId(1);
        assertEquals("1", item.getId());
    }

    @Test
    void getHeader() {
        assertEquals("Item", item.getHeader());
    }

    @Test
    void getStartTime() {
        DateTime time = new DateTime(2020,4,4,12,0,0);
        assertEquals(time, item.getStartTime());
    }

    @Test
    void getEndTime() {
        DateTime time = new DateTime(2020,4,4,13,0,0);
        assertEquals(time, item.getEndTime());
    }

    @Test
    void testGetDescription() {
        assertEquals("description", item.getDescription());
        return;
    }


    @Test
    void getColor() {
        assertEquals(Color.ORANGE, item.getColor());
    }
}