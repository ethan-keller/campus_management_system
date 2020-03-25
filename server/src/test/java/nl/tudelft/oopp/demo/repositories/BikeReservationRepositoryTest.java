package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BikeReservationRepositoryTest {

    @Autowired
    private BikeReservationRepository bikeRepo;

     @Test
     void testAllMethods() {
         List<BikeReservation> a = bikeRepo.getUserBikeReservations("mathijsuser");
         return;
     }
}