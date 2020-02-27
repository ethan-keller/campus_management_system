package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT * FROM reservation", nativeQuery = true)
    public List<Reservation> getAllReservations();

    @Query(value = "SELECT * FROM reservation WHERE id = :id", nativeQuery = true)
    public Reservation getReservation(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO reservation (id, username, room, date, starting_time, ending_time) VALUES (:id, :username, :room, :date, :starting_time, :ending_time)", nativeQuery = true)
    public void insertReservation(@Param("id") int id, @Param("username") String username, @Param("room") int room, @Param("date") String date, @Param("starting_time") String starting_time, @Param("ending_time") String ending_time);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reservation WHERE id = :id", nativeQuery = true)
    public void deleteReservation(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservation SET date = :date WHERE id = :id", nativeQuery = true)
    public void updateDate(@Param("id") int id, @Param("date") String date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservation SET starting_time = :starting_time WHERE id = :id", nativeQuery = true)
    public void updateStartingTime(@Param("id") int id, @Param("starting_time") String starting_time);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservation SET ending_time = :ending_time WHERE id = :id", nativeQuery = true)
    public void updateEndingTime(@Param("id") int id, @Param("ending_time") String ending_time);
}
