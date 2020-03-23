package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT * FROM food", nativeQuery = true)
    public List<Food> getAllFood();

    @Query(value = "SELECT * FROM food WHERE id = :id", nativeQuery = true)
    public Food getFood(@Param("id") int id);


}