package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encryption.CommunicationMethods;
import nl.tudelft.oopp.demo.encryption.EncryptionManager;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Value("${encryption.secretKey}")
    private String secretKey;

    @PostMapping("createUser")
    @ResponseBody
    public void createUser(@RequestParam String username, @RequestParam String password, @RequestParam int type) throws UnsupportedEncodingException {
        username = CommunicationMethods.decodeCommunication(username);
        password = CommunicationMethods.decodeCommunication(password);

        try{
            String encrypted_pass = EncryptionManager.encrypt(password, secretKey);
            userRepo.insertUser(username, encrypted_pass, type);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("updateUser")
    @ResponseBody
    public void updateUser(@RequestParam String username, @RequestParam String password, @RequestParam int type) throws UnsupportedEncodingException {
        username = CommunicationMethods.decodeCommunication(username);
        password = CommunicationMethods.decodeCommunication(password);
        try{
            String encrypted_pass = EncryptionManager.encrypt(password, secretKey);
            userRepo.updatePassword(username, encrypted_pass);
            userRepo.updateType(username, type);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("deleteUser")
    @ResponseBody
    public void deleteUser(@RequestParam String username) throws UnsupportedEncodingException {
        username = CommunicationMethods.decodeCommunication(username);
        try{
            userRepo.deleteUser(username);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("getUser")
    @ResponseBody
    public User getUser(@RequestParam String username) throws UnsupportedEncodingException {
        username = CommunicationMethods.decodeCommunication(username);
        try {
            return userRepo.getUser(username);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getAllUsers")
    @ResponseBody
    public List<User> getAllUsers(){
        try {
            return userRepo.getAllUsers();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("getUser")
    @ResponseBody
    public boolean test(){
        System.out.println(userRepo.getUser("admin"));
        return true;
    }

}
