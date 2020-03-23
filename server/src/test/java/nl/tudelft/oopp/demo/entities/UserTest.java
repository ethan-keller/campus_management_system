package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class UserTest {
    User u1 = new User("Ottedam", "password", 0);
    User u2 = new User("Ottedam", "password", 0);
    User u3 = new User("EthanKeller", "password01", 1);


    @Test
    void getUsername() {
        assertEquals("Ottedam", u1.getUsername());
        assertEquals("EthanKeller", u3.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("password", u1.getPassword());
        assertEquals("password01", u3.getPassword());
    }

    @Test
    void getType() {
        assertEquals(0, u1.getType());
        assertEquals(1, u3.getType());
    }

    @Test
    void testEquals() {
        assertTrue(u1.equals(u2));
        assertTrue(u2.equals(u1));
        assertTrue(u1.equals(u1));
        assertFalse(u1.equals(u3));
        assertFalse(u1.equals("pizza"));
    }
}