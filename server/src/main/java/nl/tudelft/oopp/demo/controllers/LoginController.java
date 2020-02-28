package nl.tudelft.oopp.demo.controllers;

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

import java.util.List;

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
    public String getUser(@RequestParam String username, @RequestParam String password){

        String encryptedPassword = EncryptionManager.encrypt(password, secretKey);

        if(userRepo.getUser(username) == null){
            return "Wrong credentials";
        } else if (!userRepo.getUser(username).getPassword().equals(encryptedPassword)){
            return "Wrong credentials";
        }
        return "Login granted!";
    }

}
