package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.mindfusion.common.DateTime;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import nl.tudelft.oopp.demo.admin.controller.AdminManageUserViewController;
import nl.tudelft.oopp.demo.user.calendar.logic.CalendarPaneLogic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationTest {

    private Reservation reservation;

    private ClientAndServer mockServer;

    void expGetAllReservations() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllReservations"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"username\":\"username\",\"room\":20,\"date\":\"2020-04-04\","
                        + "\"startingTime\":\"12:00:00\",\"endingTime\":\"13:00:00\"}]"));
    }

    void expGetAllReservationsError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getAllReservations"))
                .respond(response().withStatusCode(200).withBody("\"id\":10,"
                        + "\"username\":\"username\",\"room\":20,\"date\":\"2020-04-04\","
                        + "\"startingTime\":\"12:00:00\",\"endingTime\":\"13:00:00\"}"));
    }

    void expGetReservation() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getReservation"))
                .respond(response().withStatusCode(200).withBody("{\"id\":10,"
                        + "\"username\":\"username\",\"room\":20,\"date\":\"2020-04-04\","
                        + "\"startingTime\":\"12:00:00\",\"endingTime\":\"13:00:00\"}"));
    }

    void expGetReservationError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getReservation"))
                .respond(response().withStatusCode(200).withBody("\"id\":10,"
                        + "\"username\":\"username\",\"room\":20,\"date\":\"2020-04-04\","
                        + "\"startingTime\":\"12:00:00\",\"endingTime\":\"13:00:00\"}"));
    }

    void expGetUserReservations() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getUserReservations"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"username\":\"username\",\"room\":20,\"date\":\"2020-04-04\","
                        + "\"startingTime\":\"12:00:00\",\"endingTime\":\"13:00:00\"}]"));
    }

    void expGetUserReservationsError() {
        new MockServerClient("127.0.0.1", 8080)
                .when(request().withMethod("GET").withPath("/getUserReservations"))
                .respond(response().withStatusCode(200).withBody("[{\"id\":10,"
                        + "\"username\":\"username\",\"room\":20,\"date\":\"2020-04-04\","
                        + "\"startingTime\":\"12:00:00\",\"endingTime\":\"13:00:00\"}"));
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
        reservation = new Reservation(10, "username", 20,
                "2020-04-04", "12:00:00", "13:00:00");
    }

    @Test
    void emptyConstructor() {
        reservation = new Reservation();
        assertEquals(-1, reservation.getReservationId().get());
        assertNull(reservation.getUsername().get());
        assertEquals(-1, reservation.getRoom().get());
        assertNull(reservation.getDate().get());
        assertNull(reservation.getReservationStartingTime().get());
        assertNull(reservation.getReservationEndingTime().get());
    }

    @Test
    void getId() {
        assertEquals(10, reservation.getReservationId().get());
    }

    @Test
    void getUsername() {
        assertEquals("username", reservation.getUsername().get());
    }

    @Test
    void getRoom() {
        assertEquals(20, reservation.getRoom().get());
    }

    @Test
    void getDate() {
        assertEquals("2020-04-04", reservation.getDate().get());
    }

    @Test
    void getStartingTime() {
        assertEquals("12:00:00", reservation.getReservationStartingTime().get());
    }

    @Test
    void getEndingTime() {
        assertEquals("13:00:00", reservation.getReservationEndingTime().get());
    }

    @Test
    void setId() {
        reservation.setId(5);
        assertEquals(5, reservation.getReservationId().get());
    }

    @Test
    void setUsername() {
        reservation.setUsername("newusername");
        assertEquals("newusername", reservation.getUsername().get());
    }

    @Test
    void setRoom() {
        reservation.setRoom(50);
        assertEquals(50, reservation.getRoom().get());
    }

    @Test
    void setDate() {
        reservation.setDate("newdate");
        assertEquals("newdate", reservation.getDate().get());
    }

    @Test
    void setStartingTime() {
        reservation.setStartingTime("newstartingtime");
        assertEquals("newstartingtime", reservation.getReservationStartingTime().get());
    }

    @Test
    void setEndingTime() {
        reservation.setEndingTime("newendingtime");
        assertEquals("newendingtime", reservation.getReservationEndingTime().get());
    }

    @Test
    void equals() {
        final Reservation testRes = new Reservation(1, "", 0, "", "", "");
        final Reservation testRes2 = new Reservation(10, "", 0, "", "", "");
        Integer integer = 2;

        assertEquals(reservation, reservation);
        assertNotEquals(reservation, integer);
        assertNotEquals(reservation, testRes);
        assertEquals(reservation, testRes2);
    }

    @Test
    void getRoomReservationsOnDate() {
        LocalDate ld = LocalDate.of(2020, 4, 4);

        expGetAllReservations();
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        reservationList.add(reservation);
        assertEquals(reservationList, Reservation.getRoomReservationsOnDate(20, ld, getConverter()));
        stopServer();
        startServer();
        expGetAllReservationsError();
        assertNull(Reservation.getRoomReservationsOnDate(20, ld, getConverter()));
    }

    @Test
    void getReservation() {
        expGetAllReservations();
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        reservationList.add(reservation);
        assertEquals(reservationList, Reservation.getAllReservations());
        stopServer();
        startServer();
        expGetAllReservationsError();
        assertNull(Reservation.getAllReservations());
    }

    @Test
    void getUserReservation() {
        expGetUserReservations();
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        reservationList.add(reservation);
        assertEquals(reservationList, Reservation.getUserReservation());
        stopServer();
        startServer();
        expGetUserReservationsError();
        assertNull(Reservation.getUserReservation());
    }

    @Test
    void getSelectedUserReservation() {
        stopServer();
        startServer();
        AdminManageUserViewController.currentSelectedUser = new User("username",
                "pass", 0);
        expGetUserReservations();
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        reservationList.add(reservation);
        assertEquals(reservationList, Reservation.getSelectedUserReservation());
        stopServer();
        startServer();
        expGetUserReservationsError();
        assertNull(Reservation.getSelectedUserReservation());
    }

    @Test
    void testGetId() {
        assertEquals("10", reservation.getId());
    }

    @Test
    void getHeader() {
        assertEquals("Reservation", reservation.getHeader());
    }

    @Test
    void getStartTime() {
        DateTime datetime = new DateTime(2020, 4, 4, 12, 0, 0);
        assertEquals(datetime, reservation.getStartTime());
    }

    @Test
    void getEndTime() {
        DateTime datetime = new DateTime(2020, 4, 4, 13, 0, 0);
        assertEquals(datetime, reservation.getEndTime());
    }

    @Test
    void getDescription() {
        final Food f = new Food(1, "Pizza", 10);
        final FoodReservation fr = new FoodReservation(10, 1, 20);
        final Building b = new Building(1, "test",
                0, null, 0, null, null);
        final Room r = new Room(20, "test", 1, false,
                0, null, null, null);
        CalendarPaneLogic.foodList = Arrays.asList(f);
        CalendarPaneLogic.foodReservationList = Arrays.asList(fr);
        CalendarPaneLogic.buildingList = Arrays.asList(b);
        CalendarPaneLogic.roomList = Arrays.asList(r);
        String expected = "test\ntest\n12:00 - 13:00\n20x Pizza\ntotal price = 200.0 euro(s)";
        assertEquals(expected, reservation.getDescription());
    }

    @Test
    void getColor() {
        assertEquals(Color.CYAN, reservation.getColor());
    }

    private StringConverter<LocalDate> getConverter() {
        try {
            return new StringConverter<>() {
                // set the wanted pattern (format)
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        // get correctly formatted String
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        // get correct LocalDate from String format
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}