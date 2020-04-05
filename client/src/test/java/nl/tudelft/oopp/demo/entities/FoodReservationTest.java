package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class FoodReservationTest {

    private FoodReservation foodRes;

    @BeforeEach
    void setUp() {
        foodRes = new FoodReservation(20, 10, 2);
    }

    @Test
    void emptyConstructor() {
        foodRes = new FoodReservation();
        assertEquals(-1, foodRes.getFoodId().get());
        assertEquals(-1, foodRes.getFoodQuantity().get());
        assertEquals(-1, foodRes.getReservationId().get());
    }

    @Test
    void getFoodId() {
        assertEquals(10, foodRes.getFoodId().get());
    }

    @Test
    void getReservationId() {
        assertEquals(20, foodRes.getReservationId().get());
    }

    @Test
    void getFoodQuantity() {
        assertEquals(2, foodRes.getFoodQuantity().get());
    }

    @Test
    void setFoodId() {
        foodRes.setFoodId(11);
        assertEquals(11, foodRes.getFoodId().get());
    }

    @Test
    void setReservationId() {
        foodRes.setReservationId(21);
        assertEquals(21, foodRes.getReservationId().get());
    }

    @Test
    void setFoodQuantity() {
        foodRes.setFoodQuantity(3);
        assertEquals(3, foodRes.getFoodQuantity().get());
    }

    @Test
    void getUserReservationFood() {
    }
}