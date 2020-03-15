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

    @Autowired UserController userCont;


    @Test
    void register() throws UnsupportedEncodingException {
        // remove user from database before testing
        userCont.deleteUser("registerTest");
        assertEquals("Your account is created", registerCont.register("registerTest", "password"));
        assertEquals("This username already exists!", registerCont.register("registerTest", "password"));
    }
}