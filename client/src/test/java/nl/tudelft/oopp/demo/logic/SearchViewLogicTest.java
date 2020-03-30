package nl.tudelft.oopp.demo.logic;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.SearchView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



class SearchViewLogicTest {

    private static List<Room> rooms;
    private static Room r1;
    private static Room r2;
    private static Room r3;
    private static Room r4;
    private static Room r5;

    private static List<Building> buildings;
    private static Building b1;
    private static Building b2;
    private static Building b3;

    private static List<Reservation> reservations;
    private static Reservation rs1;
    private static Reservation rs2;
    private static Reservation rs3;
    private static Reservation rs4;


    @BeforeAll
    public static void makeRooms() {
        rooms = new ArrayList<Room>();
        r1 = new Room(1, "Room 1", 1, true, 4, "picture.jpg", "nice room", "lecture hall");
        r2 = new Room(2, "Room 2", 1, true, 12, "picture.jpg", "nice room", "lecture hall");
        r3 = new Room(3, "Room 3", 2, false, 7, "picture.jpg", "nice room", "lecture hall");
        r4 = new Room(4, "Room 4", 2, false, 20, "picture.jpg", "nice room", "lecture hall");
        r5 = new Room(5, "Room 5", 3, false, 45, "picture.jpg", "nice room", "lecture hall");

        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        rooms.add(r4);
        rooms.add(r5);
    }

    public List<Building> makeBuildings(){
        buildings = new ArrayList<>();
        b1 = new Building(1, "Building 1", 2, "BuildingStreet 1", 5, 5);
        b2 = new Building(2, "Building 2", 2, "BuildingStreet 2", 10, 10);
        b3 = new Building(3, "Building 3", 1, "BuildingStreet 3", 20, 20);

        buildings.add(b1);
        buildings.add(b2);
        buildings.add(b3);

        return buildings;
    }

    public List<Reservation> makeReservations(){
        reservations = new ArrayList<Reservation>();

        rs1 = new Reservation(1, "Test", 1, "2020-05-05", "08:00:00", "23:59:00");
        rs2 = new Reservation(2, "Test", 2, "2020-05-05", "09:00:00", "23:59:00");
        rs3 = new Reservation(3, "Test", 2, "2020-05-05", "08:00:00", "08:30:00");
        rs4 = new Reservation(4, "Test", 3, "2020-05-05", "08:00:00", "23:59:00");

        reservations.add(rs1);
        reservations.add(rs2);
        reservations.add(rs3);
        reservations.add(rs4);

        return reservations;
    }



    @Test
    void filterRoomByBuilding() {
        List<Room> expected = new ArrayList<Room>();

        expected.add(r1);
        expected.add(r2);

        assertEquals(expected, SearchViewLogic.filterRoomByBuilding(rooms, 1));

    }

    @Test
    void filterRoomByTeacherOnly() {
        List<Room> expected = new ArrayList<Room>();

        expected.add(r1);
        expected.add(r2);

        assertEquals(expected, SearchViewLogic.filterRoomByTeacherOnly(rooms, true));
    }

    @Test
    void filterRoomByCapacity() {
        List<Room> expected1 = new ArrayList<Room>();
        List<Room> expected2 = new ArrayList<Room>();
        List<Room> expected3 = new ArrayList<Room>();
        List<Room> expected4 = new ArrayList<Room>();

        expected1.add(r1);
        assertEquals(expected1, SearchViewLogic.filterRoomByCapacity(rooms, "1-5"));

        makeRooms();
        expected2.add(r3);
        assertEquals(expected2, SearchViewLogic.filterRoomByCapacity(rooms, "5-10"));

        makeRooms();
        expected3.add(r2);
        expected3.add(r4);
        assertEquals(expected3, SearchViewLogic.filterRoomByCapacity(rooms, "10-20"));

        makeRooms();
        expected4.add(r4);
        expected4.add(r5);
        assertEquals(expected4, SearchViewLogic.filterRoomByCapacity(rooms, "20+"));
    }

    @Test
    void filterBySearch() {
        makeBuildings();

        List<Room> expected = new ArrayList<Room>();
        expected.add(r5);
        expected.add(r3);

        assertEquals(expected, SearchViewLogic.filterBySearch(rooms, "3", buildings));
    }

    @Test
    void filterRoomsByDate() {
        makeBuildings();
        makeReservations();

        List<Room> expected = new ArrayList<Room>();
        expected.add(r2);
        expected.add(r4);
        expected.add(r5);

        SearchViewLogic.filterRoomsByDate(rooms, "2020-05-05", reservations);
        assertEquals(expected, rooms);
    }

    @Test
    void filterByBike() {
        makeBuildings();

        List<Room> expected = new ArrayList<Room>();
        expected.add(r1);
        expected.add(r2);
        expected.add(r3);
        expected.add(r4);
        expected.add(r5);


        assertEquals(expected, SearchViewLogic.filterByBike(rooms, buildings, "1+"));

        expected = new ArrayList<Room>();
        expected.add(r1);
        expected.add(r2);
        expected.add(r3);
        expected.add(r4);
        expected.add(r5);

        assertEquals(expected, SearchViewLogic.filterByBike(rooms, buildings, "5+"));

        expected = new ArrayList<Room>();
        expected.add(r3);
        expected.add(r4);
        expected.add(r5);

        assertEquals(expected, SearchViewLogic.filterByBike(rooms, buildings, "10+"));

        expected = new ArrayList<Room>();
        expected.add(r5);

        assertEquals(expected, SearchViewLogic.filterByBike(rooms, buildings, "20+"));
    }
}