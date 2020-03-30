package nl.tudelft.oopp.demo.controllers;

import java.io.UnsupportedEncodingException;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RoomControllerTest {

    @Autowired
    private RoomController roomCont;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private BuildingRepository buildingRepo;

    @Test
    void testAllMethods() throws UnsupportedEncodingException {
        buildingRepo.insertBuilding("4testing", 24, "4TestingStreet 34", 5, 5);
        int buildingId = buildingRepo.getBuildingByName("4testing").getId();

        buildingRepo.insertBuilding("5testing", 24, "4TestingStreet 34", 5, 5);
        int buildingId2 = buildingRepo.getBuildingByName("5testing").getId();

        roomCont.createRoom("4testing", buildingId, true, 30, "url", "very nice", "testingRoom");
        int id = roomRepo.getRoomByName("4testing").getId();

        Room r1 = new Room(id, "4testing", buildingId, true, 30, "url", "very nice", "testingRoom");
        assertEquals(r1, roomCont.getRoom(id));

        roomCont.updateRoom(id, "5testing", buildingId2, false, 25, "url2", "not nice", "testingRoom2");
        Room r2 = new Room(id, "5testing", buildingId2, false, 25, "url2", "not nice", "testingRoom2");
        assertEquals(r2, roomCont.getRoom(id));

        roomCont.deleteRoom(id);
        buildingRepo.deleteBuilding(buildingId);
        buildingRepo.deleteBuilding(buildingId2);

    }
}