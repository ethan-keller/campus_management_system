package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.encode_hash.CommunicationMethods;
import nl.tudelft.oopp.demo.encode_hash.Hashing;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @Value("${encryption.secretKey}")
    private String secretKey;

    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return randomly selected {@link User}.
     */
    @PostMapping("login")
    @ResponseBody
    public String getUser(@RequestParam String username, @RequestParam String password) throws UnsupportedEncodingException {
        username = CommunicationMethods.decodeCommunication(username);
        password = CommunicationMethods.decodeCommunication(password);

        String hashedPassword = Hashing.hashIt(password);
        User user = userRepo.getUser(username);
        if(user == null){
            return "not_found";
        } else if (!user.getPassword().equals(hashedPassword)) {
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
