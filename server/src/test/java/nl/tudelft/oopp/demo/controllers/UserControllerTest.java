package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userCont;

    @Test
    void createUser() throws UnsupportedEncodingException {
        userCont = new UserController();
        userCont.createUser("4Testing", "4Testing", 0);
        User us1 = new User("4Testing", "4Testing", 0);
        User us2 = userCont.getUser("4Testing");
        System.out.println(us1);
        System.out.println(us2);
        assertEquals(us1, us2);
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