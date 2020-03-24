package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BikeReservationRepositoryTest {

    @Autowired
    private BikeReservationRepository bikeRepo;

     @Test
     void testAllMethods() {
         bikeRepo.insertBikeReservation(697, 4, "08-09-2020", "19:00:00", "20:00:00");
         List<BikeReservation> a = bikeRepo.getBuildingBikeReservations(697);
         assertEquals(a.size(), 1);
     }
}