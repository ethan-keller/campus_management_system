package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userCont;

    @Test
    void testAllMethods() throws UnsupportedEncodingException {
        userCont.createUser("4testing", "4testing", 0);
        User us1 = new User("4testing", "4testing", 0);
        User us2 = userCont.getUser("4testing");
        assertEquals(us1, us2);

        userCont.updateUser("4testing", 1);
        User us3 = new User("4testing", "4testing", 1);
        assertEquals(us3, userCont.getUser("4testing"));

        userCont.updateUser("4testing", "5testing", 2);
        User us4 = new User("4testing", "5testing", 2);
        assertEquals(us4, userCont.getUser("4testing"));


        userCont.deleteUser("4testing");
    }
}