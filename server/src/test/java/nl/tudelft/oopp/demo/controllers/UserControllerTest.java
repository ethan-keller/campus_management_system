package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userCont;

    @Test
    void createUser() throws UnsupportedEncodingException {
        userCont.createUser("4testing", "4testing", 0);
        User us1 = new User("4testing", "4testing", 0);
        User us2 = userCont.getUser("4testing");
        assertEquals(us1, us2);
        userCont.deleteUser("4testing");
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getAllUsers() {
    }
}