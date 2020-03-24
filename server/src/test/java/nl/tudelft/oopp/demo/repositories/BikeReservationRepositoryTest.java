package nl.tudelft.oopp.demo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BikeReservationRepositoryTest {

    @Autowired
    private BikeReservationRepository bikeRepo;

     @Test
     void testAllMethods() {
         bikeRepo.updateBuilding(3, 696);
         bikeRepo.updateBikeNum(3, 6);
         bikeRepo.updateDate(3, "2020-04-05");
         bikeRepo.updateStartingTime(3, "14:00:00");
         bikeRepo.updateEndingTime(3, "15:00:00");
         bikeRepo.deleteBikeReservation(3);
     }
}