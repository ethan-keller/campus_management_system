package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "SELECT * FROM room", nativeQuery = true)
    public List<Room> getAllRooms();

    @Query(value = "SELECT * FROM room WHERE id = :id", nativeQuery = true)
    public Room getRoom(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO room (name, building, teacher_only, capacity, photos, description, type) VALUES (:name, :building, :teacher_only, :capacity, :photos, :description, :type)", nativeQuery = true)
    public void insertRoom(@Param("name") String name,
                           @Param("building") int building, @Param("teacher_only") boolean teacher_only,
                           @Param("capacity") int capacity, @Param("photos") String photos,
                           @Param("description") String description, @Param("type") String type);

    @Modifying
    @Transactional
    @Query(value = "UPDATE room SET name = :name WHERE id = :id", nativeQuery = true)
    public void updateName(@Param("id") int id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE room SET type = :type WHERE id = :id", nativeQuery = true)
    public void updateType(@Param("id") int id, @Param("type") String type);

    @Modifying
    @Transactional
    @Query(value = "UPDATE room SET teacher_only = :teacher_only WHERE id = :id", nativeQuery = true)
    public void updateTeacherOnly(@Param("id") int id, @Param("teacher_only") boolean teacher_only);

    @Modifying
    @Transactional
    @Query(value = "UPDATE room SET capacity = :capacity WHERE id = :id", nativeQuery = true)
    public void updateCapacity(@Param("id") int id, @Param("capacity") int capacity);

    @Modifying
    @Transactional
    @Query(value = "UPDATE room SET photos = :photos WHERE id = :id", nativeQuery = true)
    public void updatePhotos(@Param("id") int id, @Param("photos") String photos);

    @Modifying
    @Transactional
    @Query(value = "UPDATE room SET description = :description WHERE id = :id", nativeQuery = true)
    public void updateDescription(@Param("id") int id, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "UPDATE room SET building = :building WHERE id = :id", nativeQuery = true)
    public void updateBuilding(@Param("id") int id, @Param("building") int building);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM room WHERE id = :id", nativeQuery = true)
    public void deleteRoom(@Param("id") int id);

    @Query(value = "SELECT * FROM room WHERE building = :BuildingId", nativeQuery = true)
    public Room getRoomByBuilding(@Param("BuildingId") int BuildingId);

    @Query(value = "SELECT * FROM room WHERE capacity <= :capMin AND capacity >= :capMax "
            , nativeQuery = true)
    public Room getRoomByCapacity(@Param("capMin") int capMin, @Param("capMax") int capMax);

    @Query(value = "SELECT * FROM room WHERE type = :role", nativeQuery = true)
    public Room getRoomByRole(@Param("role") String role);

    @Query(value = "SELECT * FROM room WHERE capacity = :capacity", nativeQuery = true)
    public Room getRoomByCapacity(@Param("capacity") String capacity);

    @Query(value = "SELECT * FROM room WHERE name = :name", nativeQuery = true)
    public Room getRoomByName(@Param("name") String name);


}
