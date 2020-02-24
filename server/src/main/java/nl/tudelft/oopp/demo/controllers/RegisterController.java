package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepo;

    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return randomly selected.
     */
    @PostMapping("register")
    @ResponseBody
    public String register(@RequestParam String username, @RequestParam String password){
        if(userRepo.findUsersByUsername(username).isEmpty()){
            userRepo.insertNewUser(username, password);
            return "nice";
        }
        return "This username already exists!";
    }

}
