package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query(value = "SELECT * FROM building", nativeQuery = true)
    public List<Building> getAllBuildings();

    @Query(value = "SELECT * FROM building WHERE id = :id", nativeQuery = true)
    public Building getBuilding(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO building (name, roomCount, address, availableBikes, maxBikes) VALUES " +
            "(:name, :roomCount, :address, :availableBikes, :maxBikes)", nativeQuery = true)
    public void insertBuilding(@Param("name") String name, @Param("roomCount") int roomCount,
                               @Param("address") String address, @Param("availableBikes") int availableBikes,
                               @Param("maxBikes") int maxBikes);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM building WHERE id = :id", nativeQuery = true)
    public void deleteBuilding(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE building SET name = :name WHERE id = :id", nativeQuery = true)
    public void updateName(@Param("id") int id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE building SET roomCount = :roomCount WHERE id = :id", nativeQuery = true)
    public void updateRoomCount(@Param("id") int id, @Param("roomCount") int roomCount);

    @Modifying
    @Transactional
    @Query(value = "UPDATE building SET address = :address WHERE id = :id", nativeQuery = true)
    public void updateAddress(@Param("id") int id, @Param("address") String address);

    @Modifying
    @Transactional
    @Query(value = "UPDATE building SET availableBikes = :availableBikes WHERE id = :id", nativeQuery = true)
    public void updateAvailableBikes(@Param("id") int id, @Param("availableBikes") int availableBikes);

    @Modifying
    @Transactional
    @Query(value = "UPDATE building SET maxBikes = :maxBikes WHERE id = :id", nativeQuery = true)
    public void updateMaxBikes(@Param("id") int id, @Param("maxBikes") int maxBikes);

    @Query(value = "SELECT * FROM building WHERE name = :name", nativeQuery = true)
    public Building getBuildingByName(@Param("name") String name);

}
