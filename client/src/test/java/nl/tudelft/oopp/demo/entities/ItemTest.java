package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.mindfusion.common.DateTime;

import java.awt.Color;

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
class ItemTest {

    private Item item;

    private ClientAndServer mockServer;

    void expGetUserItems() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getUserItems"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"user\":\"user\",\"title\":\"title\","
                        + "\"date\":\"2020-04-04\",\"startingTime\":\"12:00:00\","
                        + "\"endingTime\":\"13:00:00\",\"description\":"
                        + "\"description\"}]"));
    }

    void expGetUserItemsError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getUserItems"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":32,"
                        + "\"user\":\"user\",\"title\":\"title\","
                        + "\"date\"]"));
    }

    void expGetAllItems() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllItems"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"user\":\"user\",\"title\":\"title\","
                        + "\"date\":\"2020-04-04\",\"startingTime\":\"12:00:00\","
                        + "\"endingTime\":\"13:00:00\",\"description\":"
                        + "\"description\"}]"));
    }

    void expGetAllItemsError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllItems"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":32,"
                        + "\"user\":"));
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
    void setUp() {
        item = new Item(10, "user", "title", "2020-04-04",
                "12:00:00", "13:00:00", "description");
    }

    @Test
    void emptyContructor() {
        item = new Item();
        assertEquals(-1, item.getItemId().get());
        assertNull(item.getUser().get());
        assertNull(item.getTitle().get());
        assertNull(item.getDate().get());
        assertNull(item.getItemStartingTime().get());
        assertNull(item.getItemEndingTime().get());
        assertNull(item.getItemDescription().get());
    }

    @Test
    void getId() {
        assertEquals(10, item.getItemId().get());
    }

    @Test
    void getUser() {
        assertEquals("user", item.getUser().get());
    }

    @Test
    void getTitle() {
        assertEquals("title", item.getTitle().get());
    }

    @Test
    void getDate() {
        assertEquals("2020-04-04", item.getDate().get());
    }

    @Test
    void getStartingTime() {
        assertEquals("12:00:00", item.getItemStartingTime().get());
    }

    @Test
    void getEndingTime() {
        assertEquals("13:00:00", item.getItemEndingTime().get());
    }

    @Test
    void getDescription() {
        assertEquals("description", item.getItemDescription().get());
    }

    @Test
    void setId() {
        item.setId(20);
        assertEquals(20, item.getItemId().get());
    }

    @Test
    void setUser() {
        item.setUser("anotheruser");
        assertEquals("anotheruser", item.getUser().get());
    }

    @Test
    void setTitle() {
        item.setTitle("anothertitle");
        assertEquals("anothertitle", item.getTitle().get());
    }

    @Test
    void setDate() {
        item.setDate("anotherdate");
        assertEquals("anotherdate", item.getDate().get());
    }

    @Test
    void setStartingTime() {
        item.setStartingTime("anotherstartingtime");
        assertEquals("anotherstartingtime", item.getItemStartingTime().get());
    }

    @Test
    void setEndingTime() {
        item.setEndingTime("anotherendingtime");
        assertEquals("anotherendingtime", item.getItemEndingTime().get());
    }

    @Test
    void setDescription() {
        item.setDescription("anotherdescription");
        assertEquals("anotherdescription", item.getItemDescription().get());

    }

    @Test
    void testEquals() {
        final Item item2 = new Item(5, "", "", "", "", "", "");
        final Item item3 = new Item(10, "", "", "", "", "", "");
        final Integer integer = 2;

        assertEquals(item, item);
        assertNotEquals(item, integer);
        assertEquals(item, item3);
        assertNotEquals(item, item2);
    }

    @Test
    void getAllItems() {
        expGetAllItems();
        ObservableList<Item> itemData = FXCollections.observableArrayList();
        itemData.add(item);
        assertEquals(itemData, Item.getAllItems());
        stopServer();
        startServer();
        expGetAllItemsError();
        assertNull(Item.getAllItems());
    }

    @Test
    void getUserItems() {
        expGetUserItems();
        ObservableList<Item> itemData = FXCollections.observableArrayList();
        itemData.add(item);
        assertEquals(itemData, Item.getUserItems("uidyed"));
        stopServer();
        startServer();
        expGetUserItemsError();
        assertNull(Item.getUserItems("ioded"));
    }

    @Test
    void testGetId() {
        assertEquals("10", item.getId());
    }

    @Test
    void testSetId() {
        item.setId(1);
        assertEquals("1", item.getId());
    }

    @Test
    void getHeader() {
        assertEquals("Item", item.getHeader());
    }

    @Test
    void getStartTime() {
        DateTime time = new DateTime(2020, 4, 4, 12, 0, 0);
        assertEquals(time, item.getStartTime());
    }

    @Test
    void getEndTime() {
        DateTime time = new DateTime(2020, 4, 4, 13, 0, 0);
        assertEquals(time, item.getEndTime());
    }

    @Test
    void testGetDescription() {
        assertEquals("description", item.getDescription());
    }


    @Test
    void getColor() {
        assertEquals(Color.ORANGE, item.getColor());
    }
}