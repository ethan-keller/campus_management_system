package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.mindfusion.common.DateTime;

import java.awt.Color;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nl.tudelft.oopp.demo.user.calendar.logic.CalendarPaneLogic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BikeReservationTest {

    private BikeReservation bikeRes;

    private ClientAndServer mockServer;

    void expectationGetAllBikeReservations() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllBikeReservation"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":66,\"building\":1673,"
                        + "\"user\":{\"username\":\"ethan\",\"password\":\"2689367b205c16ce32ed4200942"
                        + "b8b8b1e262dfc70d9bc9fbc77c49699a4f1df\",\"type\":2},\"numBikes\":4,\"date"
                        + "\":\"2020-04-03\",\"startingTime\":\"18:30:00\",\"endingTime\":\"21:00:00\"}]"));
    }

    void expectationGetAllBikeReservationsError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllBikeReservation"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":66,\"bu"));
    }

    void expectationGetUserBikeReservations() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getUserBikeReservations"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":66,\"building\":1673,"
                        + "\"user\":{\"username\":\"ethan\",\"password\":\"2689367b205c16ce32ed4200942"
                        + "b8b8b1e262dfc70d9bc9fbc77c49699a4f1df\",\"type\":2},\"numBikes\":4,\"date"
                        + "\":\"2020-04-03\",\"startingTime\":\"18:30:00\",\"endingTime\":\"21:00:00\"}]"));
    }

    void expectationGetUserBikeReservationsError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getUserBikeReservations"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":66,\"building\":1673,"
                        + "\"user\":{\"username\":\"ethan\",\"password\":\"2"));
    }

    void expectationGetBikeReservationById() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getBikeReservation"))
                .respond(response().withStatusCode(200).withBody("{\"id\":66,\"building\":1673,"
                        + "\"user\":{\"username\":\"ethan\",\"password\":\"2689367b205c16ce32ed4200942"
                        + "b8b8b1e262dfc70d9bc9fbc77c49699a4f1df\",\"type\":2},\"numBikes\":4,\"date"
                        + "\":\"2020-04-03\",\"startingTime\":\"18:30:00\",\"endingTime\":\"21:00:00\"}"));
    }

    void expectationGetBikeReservationByIdError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getBikeReservation"))
                .respond(response().withStatusCode(200).withBody("[{\"id\""));
    }

    void expectationGetBuildingBikeReservations() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getBuildingBikeReservations"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":66,\"building\":1673,"
                        + "\"user\":{\"username\":\"ethan\",\"password\":\"2689367b205c16ce32ed4200942"
                        + "b8b8b1e262dfc70d9bc9fbc77c49699a4f1df\",\"type\":2},\"numBikes\":4,\"date"
                        + "\":\"2020-04-03\",\"startingTime\":\"18:30:00\",\"endingTime\":\"21:00:00\"}]"));
    }

    void expectationGetBuildingBikeReservationsError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getBuildingBikeReservations"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":6"));
    }

    @BeforeAll
    public void startServer() {
        mockServer = startClientAndServer(8080);
    }

    @AfterAll
    public void stopServer() {
        mockServer.stop();
    }

    @BeforeEach
    void setUp() {
        bikeRes = new BikeReservation(66, 1673, "ethan", 4,
                "2020-04-03", "18:30:00", "21:00:00");
    }

    @Test
    void emptyConstructor() {
        bikeRes = new BikeReservation();
        assertEquals(-1, bikeRes.getBikeReservationId().get());
        assertEquals(-1, bikeRes.getBikeReservationBuilding().get());
        assertNull(bikeRes.getBikeReservationUser().get());
        assertEquals(-1, bikeRes.getBikeReservationQuantity().get());
        assertNull(bikeRes.getBikeReservationDate().get());
        assertNull(bikeRes.getBikeReservationStartingTime().get());
        assertNull(bikeRes.getBikeReservationEndingTime().get());
    }

    @Test
    void getBikeReservationId() {
        assertEquals(66, bikeRes.getBikeReservationId().get());
    }

    @Test
    void getBikeReservationUser() {
        assertEquals(1673, bikeRes.getBikeReservationBuilding().get());
    }

    @Test
    void getBikeReservationBuilding() {
        assertEquals("ethan", bikeRes.getBikeReservationUser().get());
    }

    @Test
    void getBikeReservationQuantity() {
        assertEquals(4, bikeRes.getBikeReservationQuantity().get());
    }

    @Test
    void getBikeReservationDate() {
        assertEquals("2020-04-03", bikeRes.getBikeReservationDate().get());
    }

    @Test
    void getBikeReservationStartingTime() {
        assertEquals("18:30:00", bikeRes.getBikeReservationStartingTime().get());
    }

    @Test
    void getBikeReservationEndingTime() {
        assertEquals("21:00:00", bikeRes.getBikeReservationEndingTime().get());
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
        expectationGetAllBikeReservations();
        ObservableList<BikeReservation> bikeReservationData1 = FXCollections.observableArrayList();
        bikeReservationData1.add(bikeRes);
        ObservableList<BikeReservation> bikeReservationData2 = BikeReservation.getBikeReservationData();
        assertEquals(bikeReservationData1, bikeReservationData2);
        mockServer.stop();
        startServer();
        expectationGetAllBikeReservationsError();
        assertNull(BikeReservation.getBikeReservationData());
    }

    @Test
    void getUserBikeReservations() {
        expectationGetUserBikeReservations();
        ObservableList<BikeReservation> bikeReservationData1 = FXCollections.observableArrayList();
        bikeReservationData1.add(bikeRes);
        ObservableList<BikeReservation> bikeReservationData2 = BikeReservation.getUserBikeReservations("ethan");
        assertEquals(bikeReservationData1, bikeReservationData2);
        mockServer.stop();
        startServer();
        expectationGetUserBikeReservationsError();
        assertNull(BikeReservation.getUserBikeReservations("ethan"));
    }

    @Test
    void getBikeReservationById() {
        expectationGetBikeReservationById();
        BikeReservation bikeReservationData2 = BikeReservation.getBikeReservationById(66);
        assertEquals(bikeRes, bikeReservationData2);
        mockServer.stop();
        startServer();
        expectationGetBikeReservationByIdError();
        assertNull(BikeReservation.getBikeReservationById(66));
    }

    @Test
    void getBikeReservationsByBuilding() {
        expectationGetBuildingBikeReservations();
        ObservableList<BikeReservation> bikeReservationData1 = FXCollections.observableArrayList();
        bikeReservationData1.add(bikeRes);
        ObservableList<BikeReservation> bikeReservationData2 = BikeReservation.getBikeReservationsByBuilding(1673);
        assertEquals(bikeReservationData1, bikeReservationData2);
        mockServer.stop();
        startServer();
        expectationGetBuildingBikeReservationsError();
        assertNull(BikeReservation.getBikeReservationsByBuilding(1673));
    }

    @Test
    void testEquals() {
        final BikeReservation testBikeRes1 = new BikeReservation(1, 0, "", 0, "", "", "");
        final BikeReservation testBikeRes2 = new BikeReservation(66, 0, "", 0, "", "", "");
        Integer testInteger = 0;

        assertEquals(bikeRes, bikeRes);
        assertNotEquals(bikeRes, testInteger);
        assertEquals(bikeRes, testBikeRes2);
        assertNotEquals(bikeRes, testBikeRes1);
    }

    @Test
    void getId() {
        assertEquals("66", bikeRes.getId());
    }

    @Test
    void getHeader() {
        assertEquals("Bike reservation", bikeRes.getHeader());
    }

    @Test
    void getStartTime() {
        DateTime time = new DateTime(2020, 4, 3, 18, 30, 0);
        assertEquals(time, bikeRes.getStartTime());
    }

    @Test
    void getEndTime() {
        DateTime time = new DateTime(2020, 4, 3, 21, 0, 0);
        assertEquals(time, bikeRes.getEndTime());
    }

    @Test
    void getDescription() {
        CalendarPaneLogic.buildingList = new ArrayList<>();
        Building b = new Building(1673, "test", 0,
                "test", 30, "18:30:00", "21:00:00");
        CalendarPaneLogic.buildingList.add(b);
        String expected = "test\n18:30 - 21:00\n4 bike(s)";
        assertEquals(expected, bikeRes.getDescription());
    }

    @Test
    void getColor() {
        assertEquals(Color.MAGENTA, bikeRes.getColor());
    }

}
