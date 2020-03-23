package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BuildingControllerTest {

    @Autowired
    private BuildingController buildingCont;

    @Test
    void testAllMethods() throws UnsupportedEncodingException {
        buildingCont.createBuilding("buildingcontrollertest", 20, "teststreet", 1, 5);
        int id = buildingCont.getBuildingByName("buildingcontrollertest").getId();
        Building b1 = new Building(id, "buildingcontrollertest", 20, "teststreet", 1, 5);
        assertEquals(b1, buildingCont.getBuilding(id));

        buildingCont.updateBuilding(id, "buildingcontrollertest2", 21, "teststreet2", 2, 6);
        Building b2 = new Building(id, "buildingcontrollertest2", 21, "teststreet2", 2, 6);
        assertEquals(b2, buildingCont.getBuilding(id));

        buildingCont.deleteBuilding(id);
    }
}