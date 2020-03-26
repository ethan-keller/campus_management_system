package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginControllerTest {

    @Autowired
    private LoginController loginCont;

    @Autowired
    private UserController userCont;

    @Test
    void getUser() throws UnsupportedEncodingException {
        userCont.deleteUser("logintest");
        userCont.deleteUser("logintest2");
        userCont.deleteUser("logintest3");
        userCont.deleteUser("logintest4");

        userCont.createUser("logintest", "login", 2);
        assertEquals("student", loginCont.getUser("logintest", "login"));
        userCont.createUser("logintest2", "login", 1);
        assertEquals("teacher", loginCont.getUser("logintest2", "login"));
        userCont.createUser("logintest3", "login", 0);
        assertEquals("admin", loginCont.getUser("logintest3", "login"));

        assertEquals("not_found", loginCont.getUser("thisDoesNotExist", "pizza"));
        assertEquals("wrong_password", loginCont.getUser("logintest", "wrong"));

        userCont.createUser("logintest4", "login", -2);
        assertEquals("error", loginCont.getUser("logintest4", "login"));

        userCont.deleteUser("logintest");
        userCont.deleteUser("logintest2");
        userCont.deleteUser("logintest3");
        userCont.deleteUser("logintest4");


    }
}