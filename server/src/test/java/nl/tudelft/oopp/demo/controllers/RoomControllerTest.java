package nl.tudelft.oopp.demo.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class that tests the room controller.
 * It makes use of Mockito MVC which is a part of the Mockito framework.
 */
@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomController controller;

    private Room r1;
    private Room r2;
    private Room r3;
    private List<Room> roomList;

    /**
     * Set up before each test.
     */
    @BeforeEach
    void setUp() {
        r1 = new Room(1, "room1", 22, true, 20, "photo1.jpg",
                "description", "Project room");
        r2 = new Room(2, "room2", 22, false, 10, "photo2.jpg",
                "description", "Project room");
        r3 = new Room(3, "room3", 24, false, 2, "photo3.jpg",
                "description", "Project room");
        roomList = Arrays.asList(r1, r2, r3);
    }

    /**
     * Test for createRoom method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void createRoomTest() throws Exception {
        mvc.perform(post("/createRoom?name=room1&building=2&teacherOnly=true&capacity=30"
                + "&photos=photo.png&description=hello&type=type")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for updateRoom method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void updateRoomTest() throws Exception {
        mvc.perform(post("/updateRoom?id=3&name=room1&building=2&teacherOnly=true&capacity=30"
                + "&photos=photo.png&description=hello&type=type")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for deleteRoom method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void deleteRoomTest() throws Exception {
        mvc.perform(post("/deleteRoom?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for getRoom method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void getRoomTest() throws Exception {
        when(controller.getRoom(anyInt())).thenReturn(r2);

        mvc.perform(get("/getRoom?id=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(r2.getName())));
    }

    /**
     * Test for getAllRooms method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void getAllRoomsTest() throws Exception {
        when(controller.getAllRooms()).thenReturn(roomList);

        mvc.perform(get("/getAllRooms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name", is(r2.getName())));
    }
}