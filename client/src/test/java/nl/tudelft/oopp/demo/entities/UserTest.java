package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {

    private User user;

    private ClientAndServer mockServer;

    void expGetAllUsers() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllUsers"))
                .respond(response().withStatusCode(200).withBody("[{\"username\":\"username\","
                        + "\"password\":\"password\",\"type\":\"0\"}]"));
    }

    void expGetAllUsersError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllUsers"))
                .respond(response().withStatusCode(200).withBody("[{\"username\":\"username\","
                        + "\"password\":\"password\",\"type\";\"0\"}"));
    }

    @BeforeAll
    public void startServer() {
        mockServer = startClientAndServer(8080);
    }

    @AfterAll
    public void stopServer() {
        mockServer.stop();
    }

    @BeforeEach
    void setup() {
        user = new User("username", "password", 0);
    }

    @Test
    void equalsTest() {
        User u = new User("username", "password", 0);
        User u2 = new User("ededed", "password", 0);
        assertEquals(u, u);
        assertEquals(user, u);
        assertNotEquals(u2, u);
        assertNotEquals(u2, "HELLO");
    }

    @Test
    void emptyConstructor() {
        user = new User();
        assertNull(user.getUsername().get());
        assertNull(user.getUserPassword().get());
        assertEquals(-1, user.getUserType().get());
    }

    @Test
    void getUsername() {
        assertEquals("username", user.getUsername().get());
    }

    @Test
    void getUserPassword() {
        assertEquals("password", user.getUserPassword().get());
    }

    @Test
    void getUserType() {
        assertEquals(0, user.getUserType().get());
    }

    @Test
    void setUsername() {
        user.setUsername("newusername");
        assertEquals("newusername", user.getUsername().get());
    }

    @Test
    void setUserPassword() {
        user.setUserPassword("newpassword");
        assertEquals("newpassword", user.getUserPassword().get());
    }

    @Test
    void setUserType() {
        user.setUserType(2);
        assertEquals(2, user.getUserType().get());
    }

    @Test
    void getUserData() {
        expGetAllUsers();
        ObservableList<User> userData = FXCollections.observableArrayList();
        userData.add(user);
        assertEquals(userData, User.getUserData());
        stopServer();
        startServer();
        expGetAllUsersError();
        assertNull(User.getUserData());
    }
}