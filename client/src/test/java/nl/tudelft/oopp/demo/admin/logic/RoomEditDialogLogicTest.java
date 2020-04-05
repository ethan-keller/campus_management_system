package nl.tudelft.oopp.demo.admin.logic;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomEditDialogLogicTest {

    @Test
    /**
     * Test for isValidInput.
     */
    void isValidInput() {
        String roomName = "";
        Building b = new Building(1, "Building 1", 2,
                "BuildingStreet 1", 5, "08:00", "22:00");
        boolean radioButtonYes = true;
        boolean radiButtoNo = false;
        String roomCapaciy = "22";
        String roomType = "nice room";
        String roomDescription = "It is a nice room to study in";

        assertEquals("No valid room name!\n", RoomEditDialogLogic.isValidInput(roomName, b, radioButtonYes,radiButtoNo, roomCapaciy, roomType, roomDescription));


        roomName = "CoolRoom";
        roomCapaciy = "";
        assertEquals("No valid capacity!\n", RoomEditDialogLogic.isValidInput(roomName, b, radioButtonYes, radiButtoNo, roomCapaciy, roomType, roomDescription));

        roomCapaciy = "22";
        roomType = "";
        assertEquals("No valid room type!\n", RoomEditDialogLogic.isValidInput(roomName, b, radioButtonYes, radiButtoNo, roomCapaciy, roomType, roomDescription));


        roomType = "lecture hall";
        roomDescription = "";
        assertEquals("No valid room description!\n", RoomEditDialogLogic.isValidInput(roomName, b, radioButtonYes, radiButtoNo, roomCapaciy, roomType, roomDescription));

        roomDescription = "Nice room to study in";
        assertEquals("", RoomEditDialogLogic.isValidInput(roomName, b, radioButtonYes, radiButtoNo, roomCapaciy, roomType, roomDescription));

        radioButtonYes = false;
        radiButtoNo = false;
        assertEquals("No teacher only button selected!\n", RoomEditDialogLogic.isValidInput(roomName, b, radioButtonYes, radiButtoNo, roomCapaciy, roomType, roomDescription));
    }
}