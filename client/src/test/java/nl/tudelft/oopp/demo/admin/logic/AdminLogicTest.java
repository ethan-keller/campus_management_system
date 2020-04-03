package nl.tudelft.oopp.demo.admin.logic;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.TableView;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminLogicTest {

    private static List<Building> buildings;
    private static Building b1;
    private static Building b2;
    private static Building b3;

    private static List<Room> rooms;
    private static Room r1;
    private static Room r2;
    private static Room r3;
    private static Room r4;
    private static Room r5;

    /**
     * makes a List of rooms to filter on.
     */
    @BeforeEach
    public void makeRooms() {
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

    /**
     * makes buildings to filter on.
     * @return
     */
    public List<Building> makeBuildings() {
        buildings = new ArrayList<>();
        b1 = new Building(1, "Building 1", 2,
                "BuildingStreet 1", 5, "08:00", "22:00");
        b2 = new Building(2, "Building 2", 2,
                "BuildingStreet 2", 10,"08:00", "22:00");
        b3 = new Building(3, "Building 3", 1, "BuildingStreet 3",
                20, "09:00", "21:30");

        buildings.add(b1);
        buildings.add(b2);
        buildings.add(b3);

        return buildings;
    }

    @Test
    void deleteBuildingLogicTest() {
        
    }

}
