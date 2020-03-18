package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, Long> {

    @Query(value = "SELECT * FROM reservations", nativeQuery = true)
    public List<Reservations> getAllReservations();

    @Query(value = "SELECT * FROM reservations WHERE id = :id", nativeQuery = true)
    public Reservations getReservation(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO reservations (username, room, date, starting_time, ending_time) VALUES (LOWER(:username), :room, :date, :starting_time, :ending_time)", nativeQuery = true)
    public void insertReservation(@Param("username") String username, @Param("room") int room, @Param("date") String date, @Param("starting_time") String starting_time, @Param("ending_time") String ending_time);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reservations WHERE id = :id", nativeQuery = true)
    public void deleteReservation(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservations SET date = :date WHERE id = :id", nativeQuery = true)
    public void updateDate(@Param("id") int id, @Param("date") String date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservations SET starting_time = :starting_time WHERE id = :id", nativeQuery = true)
    public void updateStartingTime(@Param("id") int id, @Param("starting_time") String starting_time);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservations SET ending_time = :ending_time WHERE id = :id", nativeQuery = true)
    public void updateEndingTime(@Param("id") int id, @Param("ending_time") String ending_time);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservations SET username = :username WHERE id = :id", nativeQuery = true)
    public void updateUsername(@Param("id") int id, @Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservations SET room = :room WHERE id = :id", nativeQuery = true)
    public void updateRoom(@Param("id") int id, @Param("room") int room);

    @Query(value = "SELECT * FROM reservations WHERE starting_time = :starting_time AND ending_time = :ending_time AND date = :date", nativeQuery = true)
    public Reservations getReservationByStarting_timeAndEnding_timeOnDate(@Param("starting_time") String starting_time, @Param("ending_time") String ending_time, @Param("date") String date);

    @Query(value = "SELECT * FROM reservations WHERE starting_time = :starting_time AND date = :date", nativeQuery = true)
    public Reservations getReservationByStarting_timeOnDate(@Param("starting_time") String starting_time, @Param("date") String date);

    @Query(value = "SELECT * FROM reservations WHERE date = :date", nativeQuery = true)
    public Reservations getReservationByDate(@Param("date") String date);

    @Query(value = "SELECT * FROM reservations WHERE room = :room AND date = :date AND starting_time = :starting_time", nativeQuery = true)
    public Reservations getReservationByRoomAndDateAndStarting_time(@Param("room") int room, @Param("date") String date, @Param("starting_time") String starting_time);

    @Query(value = "SELECT * FROM reservations WHERE username = LOWER(:username)", nativeQuery = true)
    public List<Reservations> getUserReservations(@Param("username") String username);
}
