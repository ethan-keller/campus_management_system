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
class FoodTest {

    public Food food;

    private ClientAndServer mockServer;

    void getFoodById() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getFood"))
                .respond(response().withStatusCode(200).withBody("{\"id\":10,"
                        + "\"name\":\"testfood\",\"price\":7.50}"));
    }

    void getFoodByIdError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getFood"))
                .respond(response().withStatusCode(200).withBody("{\"id\":10,"
                        + "\"namerice\":10.2"));
    }

    void getAllFood() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllFood"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"name\":\"testfood\",\"price\":7.50}]"));
    }

    void getAllFoodError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllFoodReservations"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"namee\":10.2}]"));
    }

    void getFoodByBuildingId() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getFoodByBuildingId"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"name\":\"testfood\",\"price\":7.50}]"));
    }

    void getFoodByBuildingIdError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getFoodByBuildingId"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"name\":.2}]"));
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
        food = new Food(10, "testfood", 7.50);
    }

    @Test
    void emptyConstructor() {
        food = new Food();
        assertEquals(-1, food.getFoodId().get());
        assertNull(food.getFoodName().get());
        assertEquals(-1.0, food.getFoodPrice().get());
    }

    @Test
    void getFoodId() {
        assertEquals(10, food.getFoodId().get());
    }

    @Test
    void getFoodName() {
        assertEquals("testfood", food.getFoodName().get());
    }

    @Test
    void getFoodPrice() {
        assertEquals(7.50, food.getFoodPrice().get());
    }

    @Test
    void setFoodId() {
        food.setFoodId(11);
        assertEquals(11, food.getFoodId().get());
    }

    @Test
    void setFoodName() {
        food.setFoodName("newFoodName");
        assertEquals("newFoodName", food.getFoodName().get());
    }

    @Test
    void setFoodPrice() {
        food.setFoodPrice(5.00);
        assertEquals(5.00, food.getFoodPrice().get());
    }


    @Test
    void getAllFoodData() {
        getAllFood();
        ObservableList<Food> foodData = FXCollections.observableArrayList();
        foodData.add(food);
        assertEquals(foodData, Food.getAllFoodData());
        stopServer();
        startServer();
        getAllFoodError();
        assertNull(Food.getAllFoodData());
    }

    @Test
    void getFoodByBuildingIdTest() {
        getFoodByBuildingId();
        ObservableList<Food> foodData = FXCollections.observableArrayList();
        foodData.add(food);
        assertEquals(foodData, Food.getFoodByBuildingId(9));
        stopServer();
        startServer();
        getFoodByBuildingIdError();
        assertNull(Food.getFoodByBuildingId(0));
    }

    @Test
    void getFoodByIdTest() {
        getFoodById();
        assertEquals(food, Food.getFoodById(9));
        stopServer();
        startServer();
        getFoodByIdError();
        assertNull(Food.getFoodById(0));
    }

    @Test
    void testEquals() {
        final Food food2 = new Food(20, "anotherFood", 20.00);
        final Food food3 = new Food(10, "testfood", 7.50);
        final Integer integer = 2;

        assertEquals(food, food);
        assertNotEquals(null, food);
        assertEquals(food, food3);
        assertNotEquals(food, food2);
        assertNotEquals(food, integer);
    }
}