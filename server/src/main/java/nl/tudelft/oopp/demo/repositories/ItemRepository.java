package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM calendar_items", nativeQuery = true)
    public List<Item> getAllItems();

    @Query(value = "SELECT * FROM calendar_items WHERE id = :id", nativeQuery = true)
    public Item getItem(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO calendar_items (user, title, date, starting_time, ending_time, description) VALUES (:user, :title, :date, :starting_time, :ending_time, :description)", nativeQuery = true)
    public void insertItem(@Param("user") String user, @Param("title") String title, @Param("date") String date, @Param("starting_time") String starting_time, @Param("ending_time") String ending_time, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM calendar_items WHERE id = :id", nativeQuery = true)
    public void deleteItem(@Param("id") int id);

    @Query(value = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'OOPP38' AND TABLE_NAME = 'calendar_items'", nativeQuery = true)
    public int getCurrentId();

    @Query(value = "SELECT * FROM calendar_items WHERE user = :user", nativeQuery = true)
    public List<Item> getUserItems(@Param("user") String user);
}
