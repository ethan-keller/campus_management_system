package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, Long> {

    @Query(value = "SELECT * FROM reservations", nativeQuery = true)
    public List<Reservations> getAllReservations();

    @Query(value = "SELECT * FROM reservations WHERE id = :id", nativeQuery = true)
    public Reservations getReservation(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO reservations (username, room, date, startingTime, endingTime) "
            + "VALUES (LOWER(:username), :room, :date, :startingTime, :endingTime)", nativeQuery = true)
    public void insertReservation(@Param("username") String username,
                                  @Param("room") int room, @Param("date") String date,
                                  @Param("startingTime") String startingTime,
                                  @Param("endingTime") String endingTime);

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
    @Query(value = "UPDATE reservations SET startingTime = :startingTime WHERE id = :id", nativeQuery = true)
    public void updateStartingTime(@Param("id") int id, @Param("startingTime") String startingTime);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservations SET endingTime = :endingTime WHERE id = :id", nativeQuery = true)
    public void updateEndingTime(@Param("id") int id, @Param("endingTime") String endingTime);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservations SET username = :username WHERE id = :id", nativeQuery = true)
    public void updateUsername(@Param("id") int id, @Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservations SET room = :room WHERE id = :id", nativeQuery = true)
    public void updateRoom(@Param("id") int id, @Param("room") int room);

    @Query(value = "SELECT * FROM reservations WHERE username = LOWER(:username)", nativeQuery = true)
    public List<Reservations> getUserReservations(@Param("username") String username);

    @Query(value = "SELECT * FROM reservations WHERE startingTime = :startingTime "
            + "AND endingTime = :endingTime AND date = :date", nativeQuery = true)
    public Reservations getReservationByStartingTimeAndEndingTimeOnDate(
            @Param("startingTime") String startingTime,
            @Param("endingTime") String endingTime, @Param("date") String date);

    @Query(value = "SELECT * FROM reservations WHERE startingTime = :startingTime "
            + "AND date = :date", nativeQuery = true)
    public Reservations getReservationByStartingTimeOnDate(
            @Param("startingTime") String startingTime, @Param("date") String date);

    @Query(value = "SELECT * FROM reservations WHERE date = :date", nativeQuery = true)
    public Reservations getReservationByDate(@Param("date") String date);

    @Query(value = "SELECT * FROM reservations WHERE room = :room AND date = :date "
            + "AND startingTime = :startingTime", nativeQuery = true)
    public Reservations getReservationByRoomAndDateAndStartingTime(
            @Param("room") int room,
            @Param("date") String date,
            @Param("startingTime") String startingTime);

}
