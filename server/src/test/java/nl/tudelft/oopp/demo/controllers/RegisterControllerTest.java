package nl.tudelft.oopp.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RegisterControllerTest {

    @Autowired
    private RegisterController registerCont;

    @Autowired
    private UserController userCont;

    /**
     * Deletes the user if it already exists and then creates the account and tries to create it again but got the right error message.
     * @throws UnsupportedEncodingException
     */

    @Test
    void register() throws UnsupportedEncodingException {
        userCont.deleteUser("registertest");
        assertEquals("Your account is created", registerCont.register("registertest", "password"));
        assertEquals("This username already exists!", registerCont.register("registertest", "password"));

        userCont.deleteUser("registertest");



    }
}