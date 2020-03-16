package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    void getUser() {
        User user = userRepo.getUser("Jim");
        User user2 = new User("jim", "484ae24edd22ea09a58edc2b6c58ee2b5f3879e3b267838a8726366f255fd4b9", 2);
        assertTrue(user2.equals(user));
    }

    @Test
    void testAllMethods() {
        User us1 = new User("6testing", "4testing", 2);
        userRepo.insertUser("6testing", "4testing", 2);
        assertTrue(userRepo.getUser("6testing").equals(us1));
        userRepo.updatePassword("6testing", "5testing");
        User us2 = new User("6testing", "5testing", 2);
        assertEquals(us2, userRepo.getUser("6testing"));
        userRepo.deleteUser("6testing");
    }

}