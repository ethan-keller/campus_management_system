package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.mindfusion.common.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class BikeReservationTest {

    private BikeReservation bikeRes;

    @BeforeEach
    void setUp() {
        bikeRes = new BikeReservation(10, 500, "user", 5,
                "2020-04-04", "12:00:00", "13:00:00");
    }

    @Test
    void emptyConstructor() {
        bikeRes = new BikeReservation();
        assertEquals(-1, bikeRes.getBikeReservationId().get());
        assertEquals(-1, bikeRes.getBikeReservationBuilding().get());
        assertEquals(null, bikeRes.getBikeReservationUser().get());
        assertEquals(-1, bikeRes.getBikeReservationQuantity().get());
        assertEquals(null, bikeRes.getBikeReservationDate().get());
        assertEquals(null, bikeRes.getBikeReservationStartingTime().get());
        assertEquals(null, bikeRes.getBikeReservationEndingTime().get());
    }

    @Test
    void getBikeReservationId() {
        assertEquals(10, bikeRes.getBikeReservationId().get());
    }

    @Test
    void getBikeReservationUser() {
        assertEquals(500, bikeRes.getBikeReservationBuilding().get());
    }

    @Test
    void getBikeReservationBuilding() {
        assertEquals("user", bikeRes.getBikeReservationUser().get());
    }

    @Test
    void getBikeReservationQuantity() {
        assertEquals(5, bikeRes.getBikeReservationQuantity().get());
    }

    @Test
    void getBikeReservationDate() {
        assertEquals("2020-04-04", bikeRes.getBikeReservationDate().get());
    }

    @Test
    void getBikeReservationStartingTime() {
        assertEquals("12:00:00", bikeRes.getBikeReservationStartingTime().get());
    }

    @Test
    void getBikeReservationEndingTime() {
        assertEquals("13:00:00", bikeRes.getBikeReservationEndingTime().get());
    }

    @Test
    void setBikeReservationId() {
        bikeRes.setBikeReservationId(11);
        assertEquals(11, bikeRes.getBikeReservationId().get());
    }

    @Test
    void setBikeReservationBuilding() {
        bikeRes.setBikeReservationBuilding(20);
        assertEquals(20, bikeRes.getBikeReservationBuilding().get());
    }

    @Test
    void setBikeReservationUser() {
        bikeRes.setBikeReservationUser("testuser");
        assertEquals("testuser", bikeRes.getBikeReservationUser().get());
    }

    @Test
    void setBikeReservationQuantity() {
        bikeRes.setBikeReservationQuantity(50);
        assertEquals(50, bikeRes.getBikeReservationQuantity().get());
    }

    @Test
    void setBikeReservationDate() {
        bikeRes.setBikeReservationDate("2020-04-05");
        assertEquals("2020-04-05", bikeRes.getBikeReservationDate().get());
    }

    @Test
    void setBikeReservationStartingTime() {
        bikeRes.setBikeReservationStartingTime("15:00:00");
        assertEquals("15:00:00", bikeRes.getBikeReservationStartingTime().get());
    }

    @Test
    void setBikeReservationEndingTime() {
        bikeRes.setBikeReservationEndingTime("16:00:00");
        assertEquals("16:00:00", bikeRes.getBikeReservationEndingTime().get());
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

    @Test
    void testEquals() {
        BikeReservation testBikeRes1 = new BikeReservation(1,0,"", 0, "", "", "");
        BikeReservation testBikeRes2 = new BikeReservation(10,0,"", 0, "", "", "");
        Integer testInteger = 0;

        assertTrue(bikeRes.equals(bikeRes));
        assertFalse(bikeRes.equals(testInteger));
        assertTrue(bikeRes.equals(testBikeRes2));
        assertFalse(bikeRes.equals(testBikeRes1));
    }

    @Test
    void getId() {
        assertEquals("10", bikeRes.getId());
    }

    @Test
    void getHeader() {
        assertEquals("Bike reservation", bikeRes.getHeader());
    }

    @Test
    void getStartTime() {
        DateTime time = new DateTime(2020,04,04,12,00,00);
        assertEquals(time, bikeRes.getStartTime());
    }

    @Test
    void getEndTime() {
        DateTime time = new DateTime(2020,04,04,13,00,00);
        assertEquals(time, bikeRes.getEndTime());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getColor() {
        assertEquals(Color.MAGENTA, bikeRes.getColor());
    }

    @Test
    void testHashCode() {
        BikeReservation testRes = new BikeReservation(10, 500, "user", 5,
                "2020-04-04", "12:00:00", "13:00:00");
        assertTrue(bikeRes.hashCode() == testRes.hashCode());
    }
}
