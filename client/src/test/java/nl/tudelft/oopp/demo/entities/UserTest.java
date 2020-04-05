package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setup() {
        user = new User("username", "password", 0);
    }

    @Test
    void emptyConstructor() {
        user = new User();
        assertEquals(null, user.getUsername().get());
        assertEquals(null, user.getUserPassword().get());
        assertEquals(-1, user.getUserType().get());
    }

    @Test
    void getUsername() {
        assertEquals("username", user.getUsername().get());
    }

    @Test
    void getUserPassword() {
        assertEquals("password", user.getUserPassword().get());
    }

    @Test
    void getUserType() {
        assertEquals(0, user.getUserType().get());
    }

    @Test
    void setUsername() {
        user.setUsername("newusername");
        assertEquals("newusername", user.getUsername().get());
    }

    @Test
    void setUserPassword() {
        user.setUserPassword("newpassword");
        assertEquals("newpassword", user.getUserPassword().get());
    }

    @Test
    void setUserType() {
        user.setUserType(2);
        assertEquals(2, user.getUserType().get());
    }

    @Test
    void getUserData() {
    }
}