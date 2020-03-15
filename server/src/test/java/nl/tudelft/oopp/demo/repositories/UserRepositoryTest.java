package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Reservations;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.junit.jupiter.api.Assertions.*;


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
        userRepo.deleteUser("4testing");
        User us1 = new User("4testing", "4testing", 2);
        userRepo.insertUser("4testing", "4testing", 2);
        assertTrue(userRepo.getUser("4testing").equals(us1));
        userRepo.updatePassword("4testing", "5testing");
        User us2 = new User("4testing", "5testing", 2);
        assertEquals(us2, userRepo.getUser("4testing"));
        userRepo.deleteUser("4testing");
    }

}