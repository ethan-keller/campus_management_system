package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BikeReservationTest {

    private BikeReservation bikeRes;

    @BeforeEach
    void setUp() {
        bikeRes = new BikeReservation(10, 500, "user", 5,
                "2020-04-04", "12:00:00", "13:00:00");
    }

    @Test
    void getBikeReservationId() {
        assertEquals(10, bikeRes.getBikeReservationId());
    }

    @Test
    void getBikeReservationUser() {
        assertEquals(500, bikeRes.getBikeReservationBuilding());
    }

    @Test
    void getBikeReservationBuilding() {
        assertEquals("user", bikeRes.getBikeReservationUser());
    }

    @Test
    void getBikeReservationQuantity() {
        assertEquals(5, bikeRes.getBikeReservationQuantity());
    }

    @Test
    void getBikeReservationDate() {
        assertEquals("2020-04-04", bikeRes.getBikeReservationDate());
    }

    @Test
    void getBikeReservationStartingTime() {
        assertEquals("12:00:00", bikeRes.getBikeReservationStartingTime());
    }

    @Test
    void getBikeReservationEndingTime() {
        assertEquals("13:00:00", bikeRes.getBikeReservationStartingTime());
    }

    @Test
    void setBikeReservationId() {
        bikeRes.setBikeReservationId(11);
        assertEquals(11, bikeRes.getBikeReservationId());
    }

    @Test
    void setBikeReservationBuilding() {
        bikeRes.setBikeReservationBuilding(20);
        assertEquals(20, bikeRes.getBikeReservationBuilding());
    }

    @Test
    void setBikeReservationUser() {
        bikeRes.setBikeReservationUser("testuser");
        assertEquals("testuser", bikeRes.getBikeReservationUser());
    }

    @Test
    void setBikeReservationQuantity() {
        bikeRes.setBikeReservationQuantity(50);
        assertEquals(50, bikeRes.getBikeReservationQuantity());
    }

    @Test
    void setBikeReservationDate() {
        bikeRes.setBikeReservationDate("2020-04-05");
        assertEquals("2020-04-05", bikeRes.getBikeReservationDate());
    }

    @Test
    void setBikeReservationStartingTime() {
        bikeRes.setBikeReservationStartingTime("15:00:00");
        assertEquals("15:00:00", bikeRes.getBikeReservationStartingTime());
    }

    @Test
    void setBikeReservationEndingTime() {
        bikeRes.setBikeReservationEndingTime("16:00:00");
        assertEquals("16:00:00", bikeRes.getBikeReservationEndingTime());
    }

    @Test
    void getBikeReservationData() {
    }

    @Test
    void getUserBikeReservations() {
    }

    @Test
    void getBikeReservationById() {
    }

    @Test
    void getBikeReservationsByBuilding() {
    }

    @Test
    void getBikeReservationsOnDate() {
    }
}
