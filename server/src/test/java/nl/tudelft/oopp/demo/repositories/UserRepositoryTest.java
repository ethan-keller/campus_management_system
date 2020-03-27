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

    /**
     * Test that tests all methods of the BuildingRepository that do not depend on the whole database.
     */
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