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
     * Test that tests all methods in the BuildingController that do not depend on the whole database.
     * @throws UnsupportedEncodingException when there is a error with encoding.
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