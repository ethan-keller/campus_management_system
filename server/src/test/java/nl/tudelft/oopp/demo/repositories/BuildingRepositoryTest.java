package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuildingRepositoryTest {

    @Autowired
    private BuildingRepository buildingRepo;

    /**
     * Test that tests all methods of the BuildingRepository that do not depend on the whole database.
     */
    @Test
    void testAllMethods() {
        buildingRepo.insertBuilding("4testing", 24, "4TestingStreet 34");
        int id = buildingRepo.getBuildingByName("4testing").getId();

        Building b1 = new Building(id, "4testing", 24, "4TestingStreet 34");
        assertEquals(b1, buildingRepo.getBuilding(id));

        buildingRepo.updateName(id, "5testing");
        buildingRepo.updateRoomCount(id, 25);
        buildingRepo.updateAddress(id, "5TestingStreet 34");

        Building b2 = new Building(id, "5testing", 25, "5TestingStreet 34");
        assertEquals(b2, buildingRepo.getBuilding(id));
        buildingRepo.deleteBuilding(id);
    }
}