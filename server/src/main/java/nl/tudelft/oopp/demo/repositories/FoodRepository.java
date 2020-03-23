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
    @Query(value = "UPDATE food SET name = :name WHERE id = :id", nativeQuery = true)
    public void updateName(@Param("id") int id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE food SET price = :price WHERE id = :id", nativeQuery = true)
    public void updatePrice(@Param("id") int id, @Param("price") int price);
}