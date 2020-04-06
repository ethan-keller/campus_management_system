package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BuildingTest {

    private Building building;

    private ClientAndServer mockServer;

    void expectationGetAllBuildings() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllBuildings"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":695,\"name\":\"EthanBuilding\","
                        + "\"roomCount\":0,\"address\":\"EthanStreet\",\"openingTime\":\"08:30:00\","
                        + "\"closingTime\":\"19:00:00\",\"maxBikes\":50}]"));
    }

    void expectationGetAllBuildingsError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllBuildings"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":695,\"name\":\"EthanBuilding\","
                        + "\"roomCount\":0,\"address\":\"EthanStreet\",\"openingTime\":\"08:30:00\","
                        + "\"closingTime\":\"19:00:00\",\"maxBikes\":50}"));
    }

    void expectationGetBuildingById() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getBuilding"))
                .respond(response().withStatusCode(200).withBody("{\"id\":695,\"name\":\"EthanBuilding\","
                        + "\"roomCount\":0,\"address\":\"EthanStreet\",\"openingTime\":\"08:30:00\","
                        + "\"closingTime\":\"19:00:00\",\"maxBikes\":50}"));
    }

    void expectationGetBuildingByIdError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getBuilding"))
                .respond(response().withStatusCode(200).withBody("{[[[]\""));
    }

    void expectationGetBuildingByFoodId() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getBuildingByFoodId"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":695,\"name\":\"EthanBuilding\","
                        + "\"roomCount\":0,\"address\":\"EthanStreet\",\"openingTime\":\"08:30:00\","
                        + "\"closingTime\":\"19:00:00\",\"maxBikes\":50}]"));
    }

    void expectationGetBuildingByFoodIdError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getBuildingByFoodId"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":695,\"name\":\"EthanBuilding\","
                        + "\"roomCount\":0,\"address\":\"EthanStreet\",\"openingTime\":\"08:30:00\","));
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
        building = new Building(695, "EthanBuilding", 0,
                "EthanStreet", 50,
                "08:30:00", "19:00:00");
    }

    @Test
    void equalsTest() {
        final Building b = new Building(695, "EthanBuilding", 0,
                "EthanStreet", 50,
                "08:30:00", "19:00:00");
        final Building b2 = new Building(65, "EthanBuilding", 0,
                "EthanStreet", 50,
                "08:30:00", "19:00:00");
        assertEquals(b, building);
        assertEquals(b, b);
        assertNotEquals(building, "hello");
        assertNotEquals(building, b2);

    }

    @Test
    void emptyConstructor() {
        building = new Building();
        assertEquals(-1, building.getBuildingId().get());
        assertNull(building.getBuildingName().get());
        assertEquals(0, building.getBuildingRoomCount().get());
        assertNull(building.getBuildingAddress().get());
        assertEquals(0, building.getBuildingMaxBikes().get());
        assertNull(building.getOpeningTime().get());
        assertNull(building.getClosingTime().get());
    }

    @Test
    void getBuildingId() {
        assertEquals(695, building.getBuildingId().get());
    }

    @Test
    void setBuildingId() {
        building.setBuildingId(20);
        assertEquals(20, building.getBuildingId().get());
    }

    @Test
    void getBuildingName() {
        assertEquals("EthanBuilding", building.getBuildingName().get());
    }

    @Test
    void setBuildingName() {
        building.setBuildingName("testingabuilding");
        assertEquals("testingabuilding", building.getBuildingName().get());
    }

    @Test
    void getBuildingRoomCount() {
        assertEquals(0, building.getBuildingRoomCount().get());
    }

    @Test
    void setBuildingRoomCount() {
        building.setBuildingRoomCount(30);
        assertEquals(30, building.getBuildingRoomCount().get());
    }

    @Test
    void getBuildingAddress() {
        assertEquals("EthanStreet", building.getBuildingAddress().get());
    }

    @Test
    void setBuildingAddress() {
        building.setBuildingAddress("addresstest");
        assertEquals("addresstest", building.getBuildingAddress().get());
    }

    @Test
    void getBuildingMaxBikes() {
        assertEquals(50, building.getBuildingMaxBikes().get());
    }

    @Test
    void setBuildingMaxBikes() {
        building.setBuildingMaxBikes(40);
        assertEquals(40, building.getBuildingMaxBikes().get());
    }

    @Test
    void getOpeningTime() {
        assertEquals("08:30:00", building.getOpeningTime().get());
    }

    @Test
    void setOpeningTime() {
        building.setOpeningTime("09:00:00");
        assertEquals("09:00:00", building.getOpeningTime().get());
    }

    @Test
    void getClosingTime() {
        assertEquals("19:00:00", building.getClosingTime().get());
    }

    @Test
    void setClosingTime() {
        building.setClosingTime("23:59:59");
        assertEquals("23:59:59", building.getClosingTime().get());
    }

    @Test
    void getBuildingData() {
        expectationGetAllBuildings();
        ObservableList<Building> buildingData = FXCollections.observableArrayList();
        buildingData.add(building);
        assertEquals(buildingData, Building.getBuildingData());
        stopServer();
        startServer();
        expectationGetAllBuildingsError();
        assertNull(Building.getBuildingData());
    }

    @Test
    void getBuildingById() {
        expectationGetBuildingById();
        assertEquals(building, Building.getBuildingById(0));
        stopServer();
        startServer();
        expectationGetBuildingByIdError();
        assertNull(Building.getBuildingById(0));
    }

    @Test
    void getBuildingByFoodId() {
        expectationGetBuildingByFoodId();
        ObservableList<Building> buildingData = FXCollections.observableArrayList();
        buildingData.add(building);
        assertEquals(buildingData, Building.getBuildingByFoodId(0));
        stopServer();
        startServer();
        expectationGetBuildingByFoodIdError();
        assertNull(Building.getBuildingByFoodId(0));
    }
}