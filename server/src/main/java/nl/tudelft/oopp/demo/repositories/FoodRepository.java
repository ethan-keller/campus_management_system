package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT * FROM food", nativeQuery = true)
    public List<Food> getAllFood();

    @Query(value = "SELECT * FROM food WHERE id = :id", nativeQuery = true)
    public Food getFood(@Param("id") int id);

    @Query(value = "SELECT * FROM food WHERE name = :name", nativeQuery = true)
    public Food getFoodByName(@Param("name") String name);

    @Query(value = "SELECT food.* FROM food INNER JOIN foodBuilding ON food.id = foodBuilding.food " +
            "INNER JOIN building ON building.id = foodBuilding.building WHERE building.name = :name",
            nativeQuery = true)
    public List<Food> getFoodByBuildingName(@Param("name") String name);

    @Query(value = "SELECT food.* FROM food INNER JOIN foodBuilding ON food.id = foodBuilding.food WHERE building = :id",
            nativeQuery = true)
    public List<Food> getFoodByBuildingId(@Param("id") int id);

    @Query(value = "SELECT food.*, foodReservations.quantity FROM food INNER JOIN foodReservations ON food.id = foodReservations.food WHERE" +
                " reservation = :reservationId", nativeQuery = true)
    public List<Food> getFoodByReservationId(@Param("reservationId") int reservationId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO foodBuilding (food, building) VALUES (:foodId, :buildingId)", nativeQuery = true)
    public void addFoodToBuilding(@Param("foodId") int foodId, @Param("buildingId") int buildingId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO foodReservations (reservation, food, quantity) VALUES (:reservationId, :foodId, :quantity)", nativeQuery = true)
    public void addFoodToReservation(@Param("reservationId") int reservationId, @Param("foodId") int foodId, @Param("quantity") int quantity);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO food (name, price) VALUES (:name, :price)", nativeQuery = true)
    public void insertFood(@Param("name") String name, @Param("price") int price);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM food WHERE id = :id", nativeQuery = true)
    public void deleteFood(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM foodReservations WHERE reservation = :reservationId AND food := foodId", nativeQuery = true)
    public void deleteFoodReservation(@Param("reservationId") int reservationId, @Param("foodId") int foodId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM foodBuilding WHERE building = :buildingId AND food := foodId", nativeQuery = true)
    public void deleteFoodBuilding(@Param("buildingId") int buildingId, @Param("foodId") int foodId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE foodReservations SET quantity := quantity WHERE reservation = :reservationId AND food := foodId", nativeQuery = true)
    public void updateFoodReservationQuantity(@Param("reservationId") int reservationId, @Param("foodId") int foodId, @Param("quantity") int quantity);

    @Modifying
    @Transactional
    @Query(value = "UPDATE food SET name = :name WHERE id = :id", nativeQuery = true)
    public void updateName(@Param("id") int id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE food SET price = :price WHERE id = :id", nativeQuery = true)
    public void updatePrice(@Param("id") int id, @Param("price") int price);
}