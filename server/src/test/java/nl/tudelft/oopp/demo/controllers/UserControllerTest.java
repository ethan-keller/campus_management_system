package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userCont;

    @Test
    void createUser() throws UnsupportedEncodingException {

        userCont.createUser("4testing", "4testing", 0);
        User us1 = new User("4testing", "4testing", 0);
        User us2 = userCont.getUser("4testing");
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