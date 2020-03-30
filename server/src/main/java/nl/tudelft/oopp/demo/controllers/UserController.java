package nl.tudelft.oopp.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import nl.tudelft.oopp.demo.encodehash.CommunicationMethods;
import nl.tudelft.oopp.demo.encodehash.Hashing;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Value("${encryption.secretKey}")
    private String secretKey;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a user entry in the database.
     *
     * @param username The user-provided username.
     * @param password The unencrypted user-provided password.
     * @param type     The user type //TODO figure out what numbers corespond to what type
     * @throws UnsupportedEncodingException Tells the user that they have used the wrong encoding.
     */
    @PostMapping("createUser")
    @ResponseBody
    public void createUser(@RequestParam String username, @RequestParam String password,
                           @RequestParam int type) throws UnsupportedEncodingException {

        username = CommunicationMethods.decodeCommunication(username);
        password = CommunicationMethods.decodeCommunication(password);

        try {
            String encryptedPass = Hashing.hashIt(password);
            userRepo.insertUser(username, encryptedPass, type);
            logger.info("User: -create- Username: " + username + " - Type: " + type);
        } catch (Exception e) {
            logger.error("User: -create- ERROR", e);
        }
    }

    /**
     * Replaces the database entry at the provided username to the new data.
     * Also encrypts the new password.
     *
     * @param username The username (String) of the to be updated user.
     * @param password The new unencrypted password (String) of the user.
     * @param type     The new type of the user (int).
     * @throws UnsupportedEncodingException Tells the user that they have used the wrong encoding.
     */
    @PostMapping("updateUser")
    @ResponseBody
    public void updateUser(@RequestParam String username, @RequestParam String password,
                           @RequestParam int type) throws UnsupportedEncodingException {

        username = CommunicationMethods.decodeCommunication(username);
        password = CommunicationMethods.decodeCommunication(password);
        try {
            String encryptedPass = Hashing.hashIt(password);
            userRepo.updatePassword(username, encryptedPass);
            userRepo.updateType(username, type);
            logger.info("User: -update- Username: " + username + " - NEW data -> Password: ? - Type: " + type);
        } catch (Exception e) {
            logger.error("User: -update- ERROR", e);
        }
    }

    /**
     * Replaces the database entry at the provided username to the new data.
     *
     * @param username The username (String) of the to be updated user.
     * @param type     The new type of the user (int).
     * @throws UnsupportedEncodingException Tells the user that they have used the wrong encoding.
     */
    @PostMapping("updateUser2")
    @ResponseBody
    public void updateUser(@RequestParam String username,
                           @RequestParam int type) throws UnsupportedEncodingException {

        username = CommunicationMethods.decodeCommunication(username);

        try {
            userRepo.updateType(username, type);
            logger.info("User: -updateType- Username: " + username + " - NEW data ->  Type: " + type);
        } catch (Exception e) {
            logger.error("User: -updateType- ERROR", e);
        }
    }

    /**
     * Deletes the user.
     *
     * @param username The username (String) of the to be deleted user.
     * @throws UnsupportedEncodingException Tells the user that they have used the wrong encoding.
     */
    @PostMapping("deleteUser")
    @ResponseBody
    public void deleteUser(@RequestParam String username) throws UnsupportedEncodingException {

        username = CommunicationMethods.decodeCommunication(username);

        try {
            userRepo.deleteUser(username);
            logger.info("User: -delete- Username: " + username);
        } catch (Exception e) {
            logger.error("User: -delete- ERROR", e);
        }
    }

    /**
     * Retrieves the user-info from the database.
     *
     * @param username The username of the user woes info is to be retrieved.
     * @return A User in Json.
     * @throws UnsupportedEncodingException Tells the user that they have used the wrong encoding.
     */
    @GetMapping("getUser")
    @ResponseBody
    public User getUser(@RequestParam String username) throws UnsupportedEncodingException {

        username = CommunicationMethods.decodeCommunication(username);

        try {
            return userRepo.getUser(username);
        } catch (Exception e) {
            logger.error("User: -get- ERROR", e);
        }
        return null;
    }

    /**
     * Retrieves the info of all the users in the database.
     *
     * @return A List of User in Json.
     */
    @GetMapping("getAllUsers")
    @ResponseBody
    public List<User> getAllUsers() {
        try {
            return userRepo.getAllUsers();
        } catch (Exception e) {
            logger.error("User: -getAllUsers- ERROR", e);
        }
        return null;
    }

}
