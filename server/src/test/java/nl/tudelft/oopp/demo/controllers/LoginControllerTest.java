package nl.tudelft.oopp.demo.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class that tests the login controller.
 * It makes use of Mockito MVC which is a part of the Mockito framework.
 */
@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private UserRepository userRepo;

    @MockBean
    private LoginController controller;

    /**
     * Set up before each test.
     */
    @BeforeEach
    void setUp() {

    }

    /**
     * Test for getUser method.
     * @throws Exception if any exception with the connection (or other) occurs
     */
    @Test
    void getUserTest() throws Exception {
        when(userRepo.getUser(anyString())).thenReturn(new User("test", "pass", 0));
        when(controller.getUser(anyString(), anyString())).thenReturn("admin");

        mvc.perform(post("/login?username=test&password=pass")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}