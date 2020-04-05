package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RoomTest {

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room(10, "name", 15, true, 20,
                "photo", "description", "type");
    }

    @Test
    void emptyConstructor() {
        room = new Room();
        assertEquals(-1, room.getRoomId().get());
        assertEquals(null, room.getRoomName().get());
        assertEquals(-1, room.getRoomBuilding().get());
        assertEquals(false, room.getTeacherOnly().get());
        assertEquals(-1, room.getRoomCapacity().get());
        assertEquals(null, room.getRoomPhoto().get());
        assertEquals(null, room.getRoomDescription().get());
        assertEquals(null, room.getRoomType().get());
    }

    @Test
    void getRoomId() {
        assertEquals(10, room.getRoomId().get());
    }

    @Test
    void getRoomName() {
        assertEquals("name", room.getRoomName().get());
    }

    @Test
    void getTeacherOnly() {
        assertEquals(true, room.getTeacherOnly().get());
    }

    @Test
    void getRoomBuilding() {
        assertEquals(15, room.getRoomBuilding().get());
    }

    @Test
    void getRoomCapacity() {
        assertEquals(20, room.getRoomCapacity().get());
    }

    @Test
    void getRoomPhoto() {
        assertEquals("photo", room.getRoomPhoto().get());
    }

    @Test
    void getRoomDescription() {
        assertEquals("description", room.getRoomDescription().get());
    }

    @Test
    void getRoomType() {
        assertEquals("type", room.getRoomType().get());
    }

    @Test
    void setRoomId() {
        room.setRoomId(5);
        assertEquals(5, room.getRoomId().get());
    }

    @Test
    void setRoomName() {
        room.setRoomName("newname");
        assertEquals("newname", room.getRoomName().get());
    }

    @Test
    void setTeacherOnly() {
        room.setTeacherOnly(false);
        assertEquals(false, room.getTeacherOnly().get());
    }

    @Test
    void setRoomBuilding() {
        room.setRoomBuilding(10);
        assertEquals(10, room.getRoomBuilding().get());
    }

    @Test
    void setRoomCapacity() {
        room.setRoomCapacity(15);
        assertEquals(15, room.getRoomCapacity().get());
    }

    @Test
    void setRoomPhoto() {
        room.setRoomPhoto("newphoto");
        assertEquals("newphoto", room.getRoomPhoto().get());
    }

    @Test
    void setRoomDescription() {
        room.setRoomDescription("newdescription");
        assertEquals("newdescription", room.getRoomDescription().get());
    }

    @Test
    void setRoomType() {
        room.setRoomType("newtype");
        assertEquals("newtype", room.getRoomType().get());
    }

    @Test
    void getRoomData() {
    }

    @Test
    void getRoomById() {
    }
}