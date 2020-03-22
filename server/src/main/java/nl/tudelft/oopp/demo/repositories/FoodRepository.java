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
    public List<Food> getAllFoods();

    @Query(value = "SELECT * FROM food WHERE id = :id", nativeQuery = true)
    public Food getFood(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO food(name, price) VALUES (:name, :price)", nativeQuery = true)
    public void createFood(@Param("name") String name, @Param("price") double price);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO food_building(food_id, building_id) VALUES (:food_id, :building_id)", nativeQuery = true)
    public void addFoodToBuilding(@Param("food_id") int food_id, @Param("building_id") int building_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE food SET name = :name, price = :price WHERE id = :id", nativeQuery = true)
    public void updateFood(@Param("id") int id, @Param("name") String name, @Param("price") double price);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM food where id = :id", nativeQuery = true)
    public void deleteFood(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM food_building where food_id = :food_id AND building_id = :building_id", nativeQuery = true)
    public void deleteFoodFromBuilding(@Param("food_id") int food_id, @Param("building_id") int building_id);



}
