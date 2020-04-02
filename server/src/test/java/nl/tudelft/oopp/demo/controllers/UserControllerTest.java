package nl.tudelft.oopp.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import nl.tudelft.oopp.demo.entities.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class that tests the user controller.
 * It makes use of Mockito MVC which is a part of the Mockito framework.
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserController controller;

    private User u1;
    private User u2;
    private User u3;
    private List<User> userList;

    /**
     * Set up before each test.
     */
    @BeforeEach
    void setUp() {
        u1 = new User("user1", "passHASHED1", 0);
        u2 = new User("user2", "passHASHED2", 1);
        u3 = new User("user3", "passHASHED3", 2);
        userList = Arrays.asList(u1, u2, u3);
    }

    /**
     * Test for createUser method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void createUserTest() throws Exception {
        mvc.perform(post("/createUser?username=test&password=hello&type=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for updateUser method (with password).
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void updateUserWithPasswordTest() throws Exception {
        mvc.perform(post("/updateUser2?username=test&type=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for updateUser method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void updateUserTest() throws Exception {
        mvc.perform(post("/updateUser?username=test&password=hello&type=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for deleteUser method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void deleteUserTest() throws Exception {
        mvc.perform(post("/deleteUser?username=test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for getUser method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void getUserTest() throws Exception {
        when(controller.getUser(anyString())).thenReturn(u3);

        mvc.perform(get("/getUser?username=test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(u3.getUsername())));
    }

    /**
     * Test for getAllUsers method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void getAllUsers() throws Exception {
        when(controller.getAllUsers()).thenReturn(userList);

        mvc.perform(get("/getAllUsers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].password", is(u2.getPassword())));
    }
}