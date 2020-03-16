package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Reservations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationsRepositoryTest {

    @Autowired
    private ReservationsRepository reservationsRepo;

    @Autowired
    private UserRepository userRepo;

    @Test
    void testAllMethods() {
        userRepo.insertUser("4testing", "4testing", 0);
        reservationsRepo.insertReservation("4testing", 14, "2020-05-29", "12:00:00", "14:00:00");
        int id = reservationsRepo.getReservationByRoomAndDateAndStarting_time(14, "2020-05-29", "12:00:00").getId();

        Reservations r1 = new Reservations(id, "4testing", 14, "2020-05-29", "12:00:00", "14:00:00");
        assertEquals(r1, reservationsRepo.getReservation(id));

        reservationsRepo.updateDate(id, "2020-06-29");
        reservationsRepo.updateEndingTime(id, "15:00:00");
        reservationsRepo.updateRoom(id, 2);
        reservationsRepo.updateStartingTime(id, "10:00:00");

        Reservations r2 = new Reservations(id, "4testing", 2, "2020-06-29", "10:00:00", "15:00:00");
        assertEquals(r2, reservationsRepo.getReservation(id));

        userRepo.deleteUser("4testing");
        reservationsRepo.deleteReservation(id);
    }
}