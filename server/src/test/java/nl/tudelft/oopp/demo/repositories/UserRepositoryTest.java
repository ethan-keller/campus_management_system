package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    void getAllUsers() {

    }

    @Test
    void getUser() {
        User user = userRepo.getUser("admin");
        System.out.println(user);
    }

    @Test
    void insertUser() {

    }

    @Test
    void deleteUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void updateType() {
    }
}