package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    void testHashCode() {
    }

    @Test
    void getId() {
    }

    @Test
    void getHeader() {
    }

    @Test
    void getStartTime() {
    }

    @Test
    void getEndTime() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void getColor() {
    }
}
