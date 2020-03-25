package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Food;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FoodControllerTest {

    @Autowired
    private FoodController foodcontrol;

    @Test
    void test() {
        List<Food> a = foodcontrol.getFoodByBuildingName("MathijsBuilding");
         return;
    }
}
