package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return randomly selected {@link User}.
     */
    @GetMapping("login")
    @ResponseBody
    public User getUser(){
        return userRepo.getAllUsers().get(0);
    }

}
