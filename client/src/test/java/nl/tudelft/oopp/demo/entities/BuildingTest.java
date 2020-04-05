package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BuildingTest {

    private Building building;

    @BeforeEach
    void setUp() {
        building = new Building(30, "testbuilding", 20,
                "testaddress", 30,
                "08:00:00", "23:00:00");
    }

    @Test
    void emptyConstructor() {
        building = new Building();
        assertEquals(-1, building.getBuildingId().get());
        assertEquals(null, building.getBuildingName().get());
        assertEquals(0, building.getBuildingRoomCount().get());
        assertEquals(null, building.getBuildingAddress().get());
        assertEquals(0, building.getBuildingMaxBikes().get());
        assertEquals(null, building.getOpeningTime().get());
        assertEquals(null, building.getClosingTime().get());
    }

    @Test
    void getBuildingId() {
        assertEquals(30, building.getBuildingId().get());
    }

    @Test
    void setBuildingId() {
        building.setBuildingId(20);
        assertEquals(20, building.getBuildingId().get());
    }

    @Test
    void getBuildingName() {
        assertEquals("testbuilding", building.getBuildingName().get());
    }

    @Test
    void setBuildingName() {
        building.setBuildingName("testingabuilding");
        assertEquals("testingabuilding", building.getBuildingName().get());
    }

    @Test
    void getBuildingRoomCount() {
        assertEquals(0,  building.getBuildingRoomCount().get());
    }

    @Test
    void setBuildingRoomCount() {
        building.setBuildingRoomCount(30);
        assertEquals(30,  building.getBuildingRoomCount().get());
    }

    @Test
    void getBuildingAddress() {
        assertEquals("testaddress",  building.getBuildingAddress().get());
    }

    @Test
    void setBuildingAddress() {
        building.setBuildingAddress("addresstest");
        assertEquals("addresstest",  building.getBuildingAddress().get());
    }

    @Test
    void getBuildingMaxBikes() {
        assertEquals(30,  building.getBuildingMaxBikes().get());
    }

    @Test
    void setBuildingMaxBikes() {
        building.setBuildingMaxBikes(40);
        assertEquals(40,  building.getBuildingMaxBikes().get());
    }

    @Test
    void getOpeningTime() {
        assertEquals("08:00:00",  building.getOpeningTime().get());
    }

    @Test
    void setOpeningTime() {
        building.setOpeningTime("09:00:00");
        assertEquals("09:00:00",  building.getOpeningTime().get());
    }

    @Test
    void getClosingTime() {
        assertEquals("23:00:00",  building.getClosingTime().get());
    }

    @Test
    void setClosingTime() {
        building.setClosingTime("23:59:59");
        assertEquals("23:59:59",  building.getClosingTime().get());
    }



    @Test
    void getBuildingData() {
    }

    @Test
    void getBuildingById() {
    }

    @Test
    void getBuildingByFoodId() {
    }
}