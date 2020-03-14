package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Autowired
    private UserController userCont;

    @Test
    void createUser() throws UnsupportedEncodingException {
        userCont.getAllUsers();
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