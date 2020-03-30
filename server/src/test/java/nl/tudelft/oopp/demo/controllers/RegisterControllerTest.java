package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RegisterControllerTest {

    @Autowired
    private RegisterController registerCont;

    @Autowired
    private UserController userCont;


    @Test
    void register() throws UnsupportedEncodingException {
        userCont.deleteUser("registertest");
        assertEquals("Your account is created", registerCont.register("registertest", "password", 2));
        assertEquals("This username already exists!", registerCont.register("registertest", "password", 2));

        userCont.deleteUser("registertest");


    }
}