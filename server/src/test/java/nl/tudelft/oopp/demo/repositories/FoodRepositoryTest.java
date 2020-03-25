package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Food;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodrepo;

    @Test
    void testReservationJoin() {
        List<Food> a = foodrepo.getFoodByReservationId(358);
        //assertEquals(a.size(), );
    }

     @Test
     void testBuildingJoin() {
         foodrepo.addFoodToReservation(358, 5, 6);
         //assertEquals(a.size(), 4);
     }
}