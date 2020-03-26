package nl.tudelft.oopp.demo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    void testAllMethods() {
        try{
            userRepo.deleteUser("6testing");
        }
        catch (Exception e){
            e.getSuppressed();
        }
        User us1 = new User("6testing", "4testing", 2);
        userRepo.insertUser("6testing", "4testing", 2);
        assertTrue(userRepo.getUser("6testing").equals(us1));

        userRepo.updatePassword("6testing", "5testing");
        User us2 = new User("6testing", "5testing", 2);
        assertEquals(us2, userRepo.getUser("6testing"));

        userRepo.deleteUser("6testing");
    }


    @Test
    void testUpdatePassword(){
        try{
            userRepo.deleteUser("6testing");
        }
        catch (Exception e){
            e.getSuppressed();
        }
        userRepo.insertUser("6testing", "4testing", 2);
        userRepo.updatePassword("6testing", "5testing");
        User us2 = new User("6testing", "5testing", 2);
        assertEquals(us2, userRepo.getUser("6testing"));
        userRepo.deleteUser("6testing");
    }

}