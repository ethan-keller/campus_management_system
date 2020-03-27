package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import nl.tudelft.oopp.demo.entities.Reservations;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.ReservationsRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ReservationControllerTest {

    @Autowired
    private ReservationController reservationCont;

    @Autowired
    private ReservationsRepository reservationRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private BuildingRepository buildingRepo;

    @Autowired
    private UserRepository userRepo;

    @Test
    void testAllMethods() throws UnsupportedEncodingException {
        try {
            userRepo.deleteUser("6testing");
        } catch (Exception e) {
            e.getSuppressed();
        }
        User us1 = new User("6testing", "4testing", 2);
        userRepo.insertUser("6testing", "4testing", 2);

        buildingRepo.insertBuilding("4reservationtesting", 24, "4TestingStreet 34", 5, 5);
        int id3 = buildingRepo.getBuildingByName("4reservationtesting").getId();

        roomRepo.insertRoom("4testing", id3, false, 35, "/photos/test", "Very nice room!", "Study room");
        int roomId = roomRepo.getRoomByName("4testing").getId();

        roomRepo.insertRoom("5testing", id3, false, 35, "/photos/test", "Very nice room i think", "Study room");
        int roomId2 = roomRepo.getRoomByName("4testing").getId();

        reservationCont.createReservation("6testing", roomId, "2020-09-09", "12:00:00", "14:00:00");
        int id = reservationRepo.getReservationByRoomAndDateAndStartingTime(
                roomId, "2020-09-09", "12:00:00").getId();
        Reservations r1 = new Reservations(id, "6testing", roomId, "2020-09-09", "12:00:00", "14:00:00");
        assertEquals(r1, reservationCont.getReservation(id));

        reservationCont.updateReservation(id, "6testing", roomId2, "2020-09-10", "13:00:00", "15:00:00");
        Reservations r2 = new Reservations(id, "6testing", roomId2, "2020-09-10", "13:00:00", "15:00:00");
        assertEquals(r2, reservationCont.getReservation(id));

        reservationCont.deleteReservation(id);
        buildingRepo.deleteBuilding(id3);
        userRepo.deleteUser("6testing");
        roomRepo.deleteRoom(roomId);
        roomRepo.deleteRoom(roomId2);
    }
}