package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


import com.mindfusion.common.DateTime;
import org.junit.jupiter.api.*;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import java.awt.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BikeReservationTest {

    private BikeReservation bikeRes;

    private ClientAndServer mockServer;

    void expectationPost() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("getAllBikeReservation"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":66,\"building\":1673,\"user\":{\"username\":\"ethan\",\"password\":\"2689367b205c16ce32ed4200942b8b8b1e262dfc70d9bc9fbc77c49699a4f1df\",\"type\":2},\"numBikes\":4,\"date\":\"2020-04-03\",\"startingTime\":\"18:30:00\",\"endingTime\":\"21:00:00\"},{\"id\":67,\"building\":1673,\"user\":{\"username\":\"ethan\",\"password\":\"2689367b205c16ce32ed4200942b8b8b1e262dfc70d9bc9fbc77c49699a4f1df\",\"type\":2},\"numBikes\":3,\"date\":\"2020-04-10\",\"startingTime\":\"20:30:00\",\"endingTime\":\"22:00:00\"},{\"id\":70,\"building\":695,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":4,\"date\":\"2020-04-14\",\"startingTime\":\"03:00:00\",\"endingTime\":\"18:00:00\"},{\"id\":71,\"building\":2932,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":3,\"date\":\"2025-04-09\",\"startingTime\":\"12:30:00\",\"endingTime\":\"19:00:00\"},{\"id\":76,\"building\":1673,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":1,\"date\":\"2020-04-14\",\"startingTime\":\"10:00:00\",\"endingTime\":\"12:30:00\"},{\"id\":77,\"building\":1673,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":1,\"date\":\"2020-04-07\",\"startingTime\":\"08:00:00\",\"endingTime\":\"18:00:00\"},{\"id\":78,\"building\":2932,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":1,\"date\":\"2020-04-24\",\"startingTime\":\"10:00:00\",\"endingTime\":\"19:00:00\"},{\"id\":79,\"building\":2933,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":3,\"date\":\"2020-04-15\",\"startingTime\":\"10:00:00\",\"endingTime\":\"16:00:00\"},{\"id\":82,\"building\":695,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":6,\"date\":\"2020-04-14\",\"startingTime\":\"08:00:00\",\"endingTime\":\"18:00:00\"},{\"id\":83,\"building\":695,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":20,\"date\":\"2020-04-06\",\"startingTime\":\"08:30:00\",\"endingTime\":\"12:00:00\"},{\"id\":84,\"building\":695,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":20,\"date\":\"2020-04-02\",\"startingTime\":\"08:00:00\",\"endingTime\":\"17:30:00\"},{\"id\":86,\"building\":2932,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":5,\"date\":\"2020-04-09\",\"startingTime\":\"06:00:00\",\"endingTime\":\"07:00:00\"},{\"id\":89,\"building\":696,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":1,\"date\":\"2020-04-09\",\"startingTime\":\"10:00:00\",\"endingTime\":\"18:00:00\"},{\"id\":90,\"building\":696,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":1,\"date\":\"2020-04-09\",\"startingTime\":\"10:00:00\",\"endingTime\":\"18:00:00\"},{\"id\":92,\"building\":1673,\"user\":{\"username\":\"lij\",\"password\":\"a5dd06a33f7ead8a001529f8729fda22501b6ccbcbc8fd5a135937926adacba7\",\"type\":2},\"numBikes\":1,\"date\":\"2020-04-08\",\"startingTime\":\"08:00:00\",\"endingTime\":\"22:00:00\"},{\"id\":94,\"building\":696,\"user\":{\"username\":\"jim\",\"password\":\"484ae24edd22ea09a58edc2b6c58ee2b5f3879e3b267838a8726366f255fd4b9\",\"type\":2},\"numBikes\":15,\"date\":\"2020-04-27\",\"startingTime\":\"10:00:00\",\"endingTime\":\"21:00:00\"},{\"id\":95,\"building\":1673,\"user\":{\"username\":\"dibs\",\"password\":\"9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08\",\"type\":2},\"numBikes\":6,\"date\":\"2020-04-15\",\"startingTime\":\"12:30:00\",\"endingTime\":\"18:00:00\"},{\"id\":97,\"building\":2932,\"user\":{\"username\":\"trinanjan\",\"password\":\"5b2c798fa9f3f052e4fed3dbeabb6553fead82e23608a1d56a0b770e4fee183d\",\"type\":1},\"numBikes\":20,\"date\":\"2020-04-28\",\"startingTime\":\"08:00:00\",\"endingTime\":\"16:00:00\"},{\"id\":98,\"building\":1673,\"user\":{\"username\":\"trinanjan\",\"password\":\"5b2c798fa9f3f052e4fed3dbeabb6553fead82e23608a1d56a0b770e4fee183d\",\"type\":1},\"numBikes\":5,\"date\":\"2020-04-17\",\"startingTime\":\"08:00:00\",\"endingTime\":\"13:00:00\"},{\"id\":99,\"building\":695,\"user\":{\"username\":\"mam\",\"password\":\"72a5b816562da4e7b3732496e0f7004de1fa8694b1731a71c34c881af1e14c1f\",\"type\":1},\"numBikes\":5,\"date\":\"2020-04-06\",\"startingTime\":\"16:30:00\",\"endingTime\":\"19:00:00\"}]"));
    }

    void expectationGet() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET"))
                .respond(response().withStatusCode(200)
                        .withBody("Success"));
    }

    @BeforeAll
    public void startServer() {
        mockServer = startClientAndServer(8080);
        expectationPost();
        expectationGet();
    }

    @AfterAll
    public void stopServer() {
        mockServer.stop();
    }

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
