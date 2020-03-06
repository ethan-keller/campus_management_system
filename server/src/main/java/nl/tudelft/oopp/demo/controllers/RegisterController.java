package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encryption.CommunicationMethods;
import nl.tudelft.oopp.demo.encryption.EncryptionManager;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepo;

    @Value("${encryption.secretKey}")
    private String secretKey;

    /**
     * Puts a new user into the database. /n
     * UserType will be student.
     *
     * @param username User-provided username (must be unique from existing ones).
     * @param password User-provided password.
     * @return Returns "nice" if everything works. /n Returns "This username already exists!" if the username was already taken.
     * @throws UnsupportedEncodingException
     */
    @PostMapping("register")
    @ResponseBody
    public String register(@RequestParam String username, @RequestParam String password) throws UnsupportedEncodingException {
        username = CommunicationMethods.decodeCommunication(username);
        password = CommunicationMethods.decodeCommunication(password);

        String encryptedPassword = EncryptionManager.encrypt(password, secretKey);

        if(userRepo.getUser(username) == null){
            userRepo.insertUser(username, encryptedPassword, 2);
            return "nice";
        }
        return "This username already exists!";
    }

}
