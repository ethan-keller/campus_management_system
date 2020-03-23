package nl.tudelft.oopp.demo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private BuildingRepository buildingRepo;

    @Test
    void testAllMethods() {
        buildingRepo.insertBuilding("4roomtesting", 24, "4TestingStreet 34", 5, 5);
        int id3 = buildingRepo.getBuildingByName("4roomtesting").getId();

        buildingRepo.insertBuilding("4roomtesting2", 24, "4TestingStreet 34", 5, 5);
        int id2 = buildingRepo.getBuildingByName("4roomtesting2").getId();

        roomRepo.insertRoom("4testing", id3, false, 35, "/photos/test", "Very nice room!", "Study room");
        int id = roomRepo.getRoomByName("4testing").getId();
        Room r1 = new Room(id, "4testing", id2, false, 35, "/photos/test", "Very nice room!", "Study room");
        assertEquals(r1, roomRepo.getRoom(id));
        roomRepo.updateBuilding(id, id2);
        roomRepo.updateDescription(id, "Just a room");
        roomRepo.updateCapacity(id, 36);
        roomRepo.updateName(id, "5testing");
        roomRepo.updatePhotos(id, "/photos/test2");
        roomRepo.updateTeacherOnly(id, true);
        roomRepo.updateType(id, "Test room");
        Room r2 = new Room(id, "5testing", id2, true, 36, "/photos/test2", "Just a room", "Test room");
        assertEquals(r2, roomRepo.getRoom(id));
        int id4 = roomRepo.getRoomByName("5testing").getId();

        roomRepo.deleteRoom(id4);
        buildingRepo.deleteBuilding(id2);
        buildingRepo.deleteBuilding(id3);
    }
}