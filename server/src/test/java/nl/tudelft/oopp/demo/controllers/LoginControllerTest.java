package nl.tudelft.oopp.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginControllerTest {

    @Autowired
    private LoginController loginCont;

    @Autowired
    private UserController userCont;

    /**
     * First deletes all the users if the are already in the database.
     * Creates the users.
     * Tries to get the accounts and checks if the account gets returned or if the password is incorrect if it gives the right error.
     * @throws UnsupportedEncodingException
     */
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