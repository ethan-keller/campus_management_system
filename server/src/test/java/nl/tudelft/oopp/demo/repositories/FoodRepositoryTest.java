package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Food;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodrepo;

    @Test
    void testReservationJoin() {
        List<Food> a = foodrepo.getFoodByReservationId(358);
        assertEquals(a.size(), 1);
    }

     @Test
     void testBuildingJoin() {
         List<Food> a = foodrepo.getFoodByBuildingName("MathijsBuilding");
         assertEquals(a.size(), 3);
     }
}