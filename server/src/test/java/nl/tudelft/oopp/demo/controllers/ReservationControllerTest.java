package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Reservations;
import nl.tudelft.oopp.demo.repositories.ReservationsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationControllerTest {

    @Autowired
    private ReservationController reservationCont;

    @Autowired
    private ReservationsRepository reservationRepo;

    @Test
    void testAllMethods() throws UnsupportedEncodingException {
        reservationCont.createReservation("4testing", 14, "2020-09-09", "12:00:00", "14:00:00");
        int id = reservationRepo.getReservationByRoomAndDateAndStarting_time(14, "2020-09-09", "12:00:00").getId();
        Reservations r1 = new Reservations(id, "4testing", 14, "2020-09-09", "12:00:00", "14:00:00");
        assertEquals(r1, reservationCont.getReservation(id));

        reservationCont.updateReservation(id, "jim", 2, "2020-09-10", "13:00:00", "15:00:00");
        Reservations r2 = new Reservations(id, "jim", 2, "2020-09-10", "13:00:00", "15:00:00");
        assertEquals(r2, reservationCont.getReservation(id));

        reservationCont.deleteReservation(id);

    }
}