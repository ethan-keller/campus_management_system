package nl.tudelft.oopp.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return randomly selected.
     */
    @GetMapping("login")
    @ResponseBody
    public String getString(){
        return "testLogin";
    }

}
