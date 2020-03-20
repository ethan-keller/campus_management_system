package nl.tudelft.oopp.demo.filtering;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.ReservationsRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomFilteringTest {

    @Autowired
    private RoomRepository roomRepo;

    @Test
    void filterRooms() {
        List<Room> rooms = new ArrayList<Room>();
        List<Room> rooms2 = new ArrayList<Room>();
        Room r1 = new Room(1, "room 1", 23, false, 40, "hey", "descrip", "whatever you will");
        Room r2 = new Room(2, "room 2", 23, false, 25, "hey2", "descrip2", "whatever you will2");
        Room r3 = new Room(3, "room 3", 24, false, 40, "hey", "descrip", "whatever you will");
        Room r4 = new Room(4, "room 4", 23, true, 40, "hey", "descrip", "whatever you will");
        Room r5 = new Room(5, "room 5", 23, false, 19, "hey", "descrip", "whatever you will");

        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        rooms.add(r4);
        rooms.add(r5);

        rooms2.add(r1);
        rooms2.add(r2);



        rooms = RoomFiltering.filterRooms(rooms, 23, false, 20);
        assertEquals(rooms2, rooms);
    }
}