package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user", nativeQuery = true)
    public List<User> getAllUsers();

    @Query(value = "SELECT * FROM user u WHERE u.username = :username", nativeQuery = true)
    public List<User> findUsersByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user (username, password, type) VALUES (:username, :password, 3)", nativeQuery = true)
    public void insertNewUser(@Param("username") String userName, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user WHERE username = :username", nativeQuery = true)
    public void deleteUser(@Param("username") String username);

}
