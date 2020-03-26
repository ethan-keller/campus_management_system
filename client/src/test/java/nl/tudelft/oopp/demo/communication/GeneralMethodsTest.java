package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneralMethodsTest {

    @org.junit.jupiter.api.Test
    void encodeCommunication() throws UnsupportedEncodingException {
        String input1 = "er staat een paard in de gang?!";
        String input2 = "It = a song & it is good";
        String res1 = "er+staat+een+paard+in+de+gang%3F%21";
        String res2 = "It+=+a+song+&+it+is+good";

        assertEquals(res1, GeneralMethods.encodeCommunication(input1));
        assertEquals(res2, GeneralMethods.encodeCommunication(input2));
    }

    @Test
    void filterRoomByBuildingTest(){
        Room r1 = new Room(1, "name", 23, false, 25, "Test.jpg", "cool", "lecture hall");
        Room r2 = new Room(2, "name2", 23, false, 25, "Test.jpg", "cool", "lecture hall");
        Room r3 = new Room(3, "name", 24, true, 27, "Test.jpg", "cool", "lecture hall");

        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);

        List<Room> expected = new ArrayList<Room>();
        expected.add(r1);
        expected.add(r2);

        assertEquals(expected, GeneralMethods.filterRoomByBuilding(rooms, 23));

    }

    @Test
    void filterByCapacityTest(){
        Room r1 = new Room(1, "name", 23, false, 10, "Test.jpg", "cool", "lecture hall");
        Room r2 = new Room(2, "name2", 23, false, 7, "Test.jpg", "cool", "lecture hall");
        Room r3 = new Room(3, "name", 24, true, 5, "Test.jpg", "cool", "lecture hall");
        Room r4 = new Room(4, "name", 24, true, 15, "Test.jpg", "cool", "lecture hall");
        Room r5 = new Room(5, "name", 24, true, 4, "Test.jpg", "cool", "lecture hall");


        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        rooms.add(r4);
        rooms.add(r5);

        List<Room> expected = new ArrayList<Room>();
        expected.add(r1);
        expected.add(r2);
        expected.add(r3);

        assertEquals(expected, GeneralMethods.filterRoomByCapacity(rooms, 10, 5));
    }

    @Test
    void filterByTeacherOnly(){
        Room r1 = new Room(1, "name", 23, false, 25, "Test.jpg", "cool", "lecture hall");
        Room r2 = new Room(2, "name2", 23, false, 25, "Test.jpg", "cool", "lecture hall");
        Room r3 = new Room(3, "name", 24, true, 27, "Test.jpg", "cool", "lecture hall");

        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);

        List<Room> expected = new ArrayList<Room>();
        expected.add(r1);
        expected.add(r2);

        assertEquals(expected, GeneralMethods.filterRoomByTeacher_only(rooms, false));
    }
}