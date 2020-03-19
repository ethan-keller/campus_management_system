package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepo;

//    @Test
//    void testAllMethods() {
//        roomRepo.insertRoom("4testing", 34, false, 35, "/photos/test", "Very nice room!", "Study room");
//        int id = roomRepo.getRoomByName("4testing").getId();
//        Room r1 = new Room(id, "4testing", 24, false, 35, "/photos/test", "Very nice room!", "Study room");
//        assertEquals(r1, roomRepo.getRoom(id));
//        roomRepo.updateBuilding(id, 23);
//        roomRepo.updateDescription(id, "Just a room");
//        roomRepo.updateCapacity(id, 36);
//        roomRepo.updateName(id, "5testing");
//        roomRepo.updatePhotos(id,"/photos/test2");
//        roomRepo.updateTeacherOnly(id, true);
//        roomRepo.updateType(id, "Test room");
//        Room r2 = new Room(id, "5testing", 240, true, 36, "/photos/test2", "Just a room", "Test room");
//        assertEquals(r2, roomRepo.getRoom(id));
//        int id3 = roomRepo.getRoomByName("5testing").getId();
//        roomRepo.deleteRoom(id3);
//    }
}