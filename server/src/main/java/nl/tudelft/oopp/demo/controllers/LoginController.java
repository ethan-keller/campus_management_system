package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encryption.CommunicationMethods;
import nl.tudelft.oopp.demo.encryption.EncryptionManager;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @Value("${encryption.secretKey}")
    private String secretKey;

    /**
     * Authenticates the user and retrieves the user type.
     * @param username The user-provided username.
     * @param password The unencrypted user-provided password
     * @return Returns the user types when successful (admin, teacher, student). /n Returns "not_found" when username doesn't exist. /n Returns "wrong_password" when the password doesn't match.
     * @throws UnsupportedEncodingException
     */
    @PostMapping("login")
    @ResponseBody
    public String getUser(@RequestParam String username, @RequestParam String password) throws UnsupportedEncodingException {
        username = CommunicationMethods.decodeCommunication(username);
        password = CommunicationMethods.decodeCommunication(password);

        String encryptedPassword = EncryptionManager.encrypt(password, secretKey);
        User user = userRepo.getUser(username);
        if(user == null){
            return "not_found";
        } else if (!user.getPassword().equals(encryptedPassword)) {
            return "wrong_password";
        } else if(user.getType() == 0){
            return "admin";
        } else if(user.getType() == 1){
            return "teacher";
        } else if(user.getType() == 2){
            return "student";
        }
        return "error";
    }
}
