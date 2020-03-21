package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuildingControllerTest {

    @Autowired
    private BuildingController buildingCont;

    /**
     * Test all methods of the building controller.
     * Makes a building, then updates it, tests if it's equal and then deletes it.
     * @throws UnsupportedEncodingException
     */
    @Test
    void testAllMethods() throws UnsupportedEncodingException {
      buildingCont.createBuilding("buildingcontrollertest", 20, "teststreet");
      int id = buildingCont.getBuildingByName("buildingcontrollertest").getId();
      Building b1 = new Building(id, "buildingcontrollertest", 20, "teststreet");
      assertEquals(b1, buildingCont.getBuilding(id));

      buildingCont.updateBuilding(id, "buildingcontrollertest2", 21, "teststreet2");
      Building b2 = new Building(id, "buildingcontrollertest2", 21, "teststreet2");
      assertEquals(b2, buildingCont.getBuilding(id));

      buildingCont.deleteBuilding(id);
    }
}