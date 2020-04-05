package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    public Food food;

    @BeforeEach
    void setup() {
        food = new Food(10, "testfood", 7.50);
    }

    @Test
    void emptyConstructor() {
        food = new Food();
        assertEquals(-1, food.getFoodId().get());
        assertEquals(null, food.getFoodName().get());
        assertEquals(-1.0, food.getFoodPrice().get());
    }

    @Test
    void getFoodId() {
        assertEquals(10, food.getFoodId().get());
    }

    @Test
    void getFoodName() {
        assertEquals("testfood", food.getFoodName().get());
    }

    @Test
    void getFoodPrice() {
        assertEquals(7.50, food.getFoodPrice().get());
    }

    @Test
    void setFoodId() {
        food.setFoodId(11);
        assertEquals(11, food.getFoodId().get());
    }

    @Test
    void setFoodName() {
        food.setFoodName("newFoodName");
        assertEquals("newFoodName", food.getFoodName().get());
    }

    @Test
    void setFoodPrice() {
        food.setFoodPrice(5.00);
        assertEquals(5.00, food.getFoodPrice().get());
    }


    @Test
    void getAllFoodData() {
    }

    @Test
    void getFoodByBuildingId() {
    }

    @Test
    void getFoodByBuildingName() {
    }

    @Test
    void getFoodById() {
    }

    @Test
    void testEquals() {
        Food food2 = new Food(20, "anotherFood", 20.00);
        Food food3 = new Food(10, "testfood", 7.50);
        Integer integer = 2;

        assertTrue(food.equals(food));
        assertFalse(food.equals(null));
        assertTrue(food.equals(food3));
        assertFalse(food.equals(food2));
        assertFalse(food.equals(integer));
    }
}