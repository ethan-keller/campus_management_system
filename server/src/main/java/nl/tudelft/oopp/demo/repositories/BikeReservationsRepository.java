package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.BikeReservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BikeReservationsRepository extends JpaRepository<BikeReservations, Long> {
    @Query(value = "SELECT * FROM bike_reservations", nativeQuery = true)
    public List<BikeReservations> getAllBikeReservations();

    @Query(value = "SELECT * FROM bike_reservations WHERE id = :id", nativeQuery = true)
    public BikeReservations getBikeReservation(@Param("id") int id);

    @Query(value = "SELECT * FROM bike_reservations WHERE building = :building", nativeQuery = true)
    public List<BikeReservations> getBuildingBikeReservations(@Param("building") int building);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bike_reservations (building, num_bikes) VALUES (:building, :num_bikes)", nativeQuery = true)
    public void insertBikeReservation(@Param("building") int building, @Param("num_bikes") int num_bikes);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bike_reservations WHERE id = :id", nativeQuery = true)
    public void deleteBikeReservation(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bike_reservations SET num_bikes = :num_bikes WHERE id = :id", nativeQuery = true)
    public void updateBikeNum(@Param("id") int id, @Param("num_bikes") int num_bikes);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bike_reservations SET building = :building WHERE id = :id", nativeQuery = true)
    public void updateBuilding(@Param("id") int id, @Param("building") int building);
}
