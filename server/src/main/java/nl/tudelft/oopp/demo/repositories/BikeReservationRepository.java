package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BikeReservationRepository extends JpaRepository<BikeReservation, Long> {
    @Query(value = "SELECT * FROM bikeReservation", nativeQuery = true)
    public List<BikeReservation> getAllBikeReservations();

    @Query(value = "SELECT * FROM bikeReservation WHERE id = :id", nativeQuery = true)
    public BikeReservation getBikeReservation(@Param("id") int id);

    @Query(value = "SELECT * FROM bikeReservation WHERE building = :building", nativeQuery = true)
    public List<BikeReservation> getBuildingBikeReservations(@Param("building") int building);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bikeReservation (building, num_bikes, date, starting_time, ending_time) VALUES (:building, :numBikes, :date, :startingTime, :endingTime)", nativeQuery = true)
    public void insertBikeReservation(@Param("building") int building, @Param("numBikes") int numBikes, @Param("date") String date, @Param("startingTime") String startingTime, @Param("endingTime") String endingTime);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bikeReservation WHERE id = :id", nativeQuery = true)
    public void deleteBikeReservation(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bikeReservation SET num_bikes = :numBikes WHERE id = :id", nativeQuery = true)
    public void updateBikeNum(@Param("id") int id, @Param("numBikes") int numBikes);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bikeReservation SET building = :building WHERE id = :id", nativeQuery = true)
    public void updateBuilding(@Param("id") int id, @Param("building") int building);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bikeReservation SET date = :date WHERE id = :id", nativeQuery = true)
    public void updateDate(@Param("id") int id, @Param("date") String date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bikeReservation SET starting_time = :startingTime WHERE id = :id", nativeQuery = true)
    public void updateStartingTime(@Param("id") int id, @Param("startingTime") String startingTime);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bikeReservation SET ending_time = :endingTime WHERE id = :id", nativeQuery = true)
    public void updateEndingTime(@Param("id") int id, @Param("endingTime") String endingTime);
}
