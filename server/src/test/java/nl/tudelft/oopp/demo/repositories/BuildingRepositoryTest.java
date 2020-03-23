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

     @Test
     void testAllMethods() {
         //This test is logically incorrect because two buildings with the same building id
         //can't exist. This is against the database constraints as id is the primary key.
         buildingRepo.insertBuilding("4testing", 24, "4TestingStreet 34", 4, 5);
         int id = buildingRepo.getBuildingByName("4testing").getId();

         Building b1 = new Building(id, "4testing", 24, "4TestingStreet 34", 4, 5);
         assertEquals(b1, buildingRepo.getBuilding(id));

         buildingRepo.updateName(id, "5testing");
         buildingRepo.updateRoomCount(id, 25);
         buildingRepo.updateAddress(id, "5TestingStreet 34");
         buildingRepo.updateAvailableBikes(id, 5);
         buildingRepo.updateMaxBikes(id, 6);

         Building b2 = new Building(id, "5testing", 25, "5TestingStreet 34", 5, 6);
         assertEquals(b2, buildingRepo.getBuilding(id));
         buildingRepo.deleteBuilding(id);
     }
}