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

//    @Test
//    void testAllMethods() {
//        //This test is logically incorrect because two buildings with the same building id
//        //can't exist. This is against the database constraints as id is the primary key.
//
//        buildingRepo.insertBuilding("4testing", 24, "4TestingStreet 34");
//        int id = buildingRepo.getBuildingByName("4testing").getId();
//
//        Building b1 = new Building(id, "4testing", 24, "4TestingStreet 34");
//        assertEquals(24, buildingRepo.getBuilding(id).getId());
//
//        buildingRepo.updateName(id, "5testing");
//        buildingRepo.updateRoomCount(id, 25);
//        buildingRepo.updateAddress(id, "5TestingStreet 34");
//
//        //Building b2 = new Building(id, "5testing", 25, "5TestingStreet 34");
//        assertEquals(25, buildingRepo.getBuilding(id).getId());
//        buildingRepo.deleteBuilding(id);
//    }
}