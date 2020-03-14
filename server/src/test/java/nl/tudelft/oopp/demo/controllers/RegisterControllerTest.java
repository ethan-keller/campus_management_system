package nl.tudelft.oopp.demo.controllers;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControllerTest {
    private RegisterController registerCont;

    @Test
    void register() throws UnsupportedEncodingException {
        registerCont = new RegisterController();
        registerCont.register("4Testings", "4Testings");

    }
}