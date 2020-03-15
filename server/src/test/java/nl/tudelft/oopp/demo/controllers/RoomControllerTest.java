package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomControllerTest {

    @Autowired
    private RoomController roomCont;

    @Autowired
    private RoomRepository roomRepo;

    @Test
    void testAllMethods() throws UnsupportedEncodingException {
        roomCont.createRoom("4testing", 33, true, 30, "url", "very nice", "testingRoom");
        int id = roomRepo.getRoomByName("4testing").getId();

        Room r1 = new Room(id, "4testing", 33, true, 30, "url", "very nice", "testingRoom");
        assertEquals(r1, roomCont.getRoom(id));

        roomCont.updateRoom(id, "5testing", 11, false, 25, "url2", "not nice", "testingRoom2");
        Room r2 = new Room(id, "5testing", 11, false, 25, "url2", "not nice", "testingRoom2");
        assertEquals(r2, roomCont.getRoom(id));

        roomCont.deleteRoom(id);

    }
}